package com.farm.doc.controller;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.farm.core.page.ViewMode;
import com.farm.doc.domain.FarmDocfile;
import com.farm.doc.domain.ex.PasteBase64Img;
import com.farm.doc.server.FarmFileManagerInter;
import com.farm.doc.server.FarmFileManagerInter.FILE_TYPE;
import com.farm.doc.server.FarmFileManagerInter.IMG_TYPE;
import com.farm.doc.server.commons.FarmDocFiles;
import com.farm.doc.server.commons.FileCopyProcessCache;
import com.farm.doc.server.utils.FileDownloadUtils;
import com.farm.doc.util.VerifyCodeUtils;
import com.farm.parameter.FarmParameterService;
import com.farm.util.latex.LatexUtils;
import com.farm.web.WebUtils;

/**
 * 文件上传下载
 * 
 * @author autoCode
 * 
 */
@RequestMapping("/actionImg")
@Controller
public class FileAccessController extends WebUtils {
	private static final Logger log = Logger.getLogger(FileAccessController.class);
	@Resource
	private FarmFileManagerInter farmFileManagerImpl;
	private static String IMG_EXNAMES_STR = null;
	private static String MEDIA_EXNAMES_STR = null;

	/**
	 * 上传附件文件
	 * 
	 * @param file
	 * @param limittypes
	 *            如果该参数不为空，则按照该参数验证文件类型，如果为空则按照默认配置验证参数
	 * @param request
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/PubFPupload.do")
	@ResponseBody
	public Map<String, Object> upload(@RequestParam(value = "imgFile", required = false) MultipartFile file,
			String processkey, String limittypes, HttpServletRequest request, ModelMap model, HttpSession session) {
		int error;
		String message;
		String url = null;
		String id = null;
		String fileName = "";
		try {
			String fileid = null;
			// 验证大小
			String maxLength = FarmParameterService.getInstance().getParameter("config.doc.upload.length.max");
			if (file.getSize() > Long.valueOf(maxLength)) {
				throw new Exception("文件不能超过" + Long.valueOf(maxLength) / 1024 + "kb");
			}
			CommonsMultipartFile cmFile = (CommonsMultipartFile) file;
			DiskFileItem item = (DiskFileItem) cmFile.getFileItem();
			{// 小于8k不生成到临时文件，临时解决办法。zhanghc20150919
				if (!item.getStoreLocation().exists()) {
					item.write(item.getStoreLocation());
				}
			}
			String typesstr = FarmParameterService.getInstance().getParameter("config.doc.upload.types");
			if (limittypes != null && !limittypes.isEmpty()) {
				typesstr = limittypes;
			}
			try {
				fileName = URLEncoder.encode(item.getName(), "utf-8").replaceAll("\\+", "%20");
			} catch (Exception e) {
				fileName = URLEncoder.encode(item.getName(), "utf-8");
			}
			// 验证类型
			List<String> types = parseIds(typesstr.toUpperCase().replaceAll("，", ","));

			if (!types.contains(FarmDocFiles.getExName(file.getOriginalFilename()).toUpperCase())) {
				throw new Exception("上传文件类型错误，允许的类型为：" + typesstr.toUpperCase().replaceAll("，", ","));
			}
			if (file.getOriginalFilename().length() > 256) {
				throw new RuntimeException("上传文件名超长，请将文件名控制在256个字符以内");
			}
			String filekey = session.getId() + processkey;
			fileid = farmFileManagerImpl.saveFile(item.getStoreLocation(), FILE_TYPE.RESOURCE_FILE,
					file.getOriginalFilename(), getCurrentUser(session), filekey);
			error = 0;
			url = farmFileManagerImpl.getFileURL(fileid);
			message = "";
			id = fileid;
		} catch (Exception e) {
			log.error(e.getMessage());
			error = 1;
			message = e.getMessage();
		}
		return ViewMode.getInstance().putAttr("error", error).putAttr("url", url).putAttr("message", message)
				.putAttr("id", id).putAttr("fileName", fileName).returnObjMode();
	}

	/**
	 * 上传图片文件
	 * 
	 * @return
	 */
	@RequestMapping(value = "/PubFPuploadImg.do")
	@ResponseBody
	public Map<String, Object> PubFPuploadImg(@RequestParam(value = "imgFile", required = false) MultipartFile file,
			HttpServletRequest request, ModelMap model, HttpSession session) {
		int error;
		log.debug("go to ImgUpload");
		String message;
		String url = null;
		String id = null;
		String fileName = "";
		try {
			String fileid = null;
			// 验证大小
			String maxLength = FarmParameterService.getInstance().getParameter("config.doc.img.upload.length.max");
			if (file.getSize() > Long.valueOf(maxLength)) {
				throw new Exception("文件不能超过" + Long.valueOf(maxLength) / 1024 + "kb");
			}
			CommonsMultipartFile cmFile = (CommonsMultipartFile) file;
			DiskFileItem item = (DiskFileItem) cmFile.getFileItem();
			{// 小于8k不生成到临时文件，临时解决办法。zhanghc20150919
				if (!item.getStoreLocation().exists()) {
					item.write(item.getStoreLocation());
				}
			}
			try {
				fileName = URLEncoder.encode(item.getName(), "utf-8").replaceAll("\\+", "%20");
			} catch (Exception e) {
				fileName = URLEncoder.encode(item.getName(), "utf-8");
			}
			// 验证类型
			initIMG_EXNAMES();
			if (!farmFileManagerImpl.isImgByExname(FarmDocFiles.getExName(file.getOriginalFilename()).toUpperCase())) {
				throw new Exception("文件类型错误，允许的类型为：图片(" + IMG_EXNAMES_STR + ")");
			}
			if (file.getOriginalFilename().length() > 256) {
				throw new RuntimeException("上传文件名超长，请将文件名控制在256个字符以内");
			}
			fileid = farmFileManagerImpl.saveFile(item.getStoreLocation(), FILE_TYPE.RESOURCE_IMG,
					file.getOriginalFilename(), getCurrentUser(session), null);
			error = 0;
			url = farmFileManagerImpl.getImgURL(fileid, IMG_TYPE.MAX);
			message = "";
			id = fileid;
		} catch (Exception e) {
			error = 1;
			message = e.getMessage();
		}
		return ViewMode.getInstance().putAttr("error", error).putAttr("url", url).putAttr("message", message)
				.putAttr("id", id).putAttr("fileName", fileName).returnObjMode();
	}

	/**
	 * 上传base64编码的图片
	 * 
	 * @return
	 */
	@RequestMapping(value = "/PubUpBase64File.do")
	@ResponseBody
	public Map<String, Object> PubUpBase64File(String fileDataBase64, HttpServletRequest request, HttpSession session) {
		int error = 0;
		log.debug("go to ImgUpload");
		String message = null;
		String url = null;
		String fileName = "";
		String fileid = null;
		try {
			PasteBase64Img imgfile = new PasteBase64Img(fileDataBase64);
			if (!imgfile.isImgFile()) {
				throw new RuntimeException("the file is not IMG file!");
			}
			String maxLength = FarmParameterService.getInstance().getParameter("config.doc.upload.length.max");
			if (imgfile.getData().length > Long.valueOf(maxLength)) {
				throw new Exception("文件不能超过" + Long.valueOf(maxLength) / 1024 + "kb");
			}
			// 验证类型
			initIMG_EXNAMES();
			if (!farmFileManagerImpl.isImgByExname(imgfile.getImgTypeSuffix())
					&& !farmFileManagerImpl.isMediaByExname(imgfile.getImgTypeSuffix())) {
				throw new Exception("文件类型错误，允许的类型为：图片(" + IMG_EXNAMES_STR + ")/多媒体(" + MEDIA_EXNAMES_STR + ")");
			}
			fileid = farmFileManagerImpl.saveFile(imgfile.getData(), FILE_TYPE.RESOURCE_IMG, imgfile.getImgName(),
					getCurrentUser(session));
			error = 0;
			url = farmFileManagerImpl.getImgURL(fileid, IMG_TYPE.MAX);
			message = "";
			fileName = imgfile.getImgName();
		} catch (Exception e) {
			error = 1;
			message = e.getMessage();
		}
		return ViewMode.getInstance().putAttr("error", error).putAttr("url", url).putAttr("message", message)
				.putAttr("id", fileid).putAttr("fileName", fileName).returnObjMode();
	}

	@RequestMapping(value = "/PubUploadProcess.do")
	@ResponseBody
	public Map<String, Object> PubUploadProcess(String processkey, HttpSession session) {
		ViewMode view = ViewMode.getInstance();
		Integer process = 0;
		try {
			String filekey = session.getId() + processkey;
			process = FileCopyProcessCache.getProcess(filekey);
		} catch (Exception e) {
			view.setError(e.getMessage(), e);
		}
		return view.putAttr("process", process).returnObjMode();
	}

	/**
	 * 上传多媒体文件（含图片）
	 * 
	 * @return
	 */
	@RequestMapping(value = "/PubFPuploadMedia.do")
	@ResponseBody
	public Map<String, Object> PubFPuploaMedia(@RequestParam(value = "imgFile", required = false) MultipartFile file,
			HttpServletRequest request, ModelMap model, HttpSession session) {
		int error;
		log.debug("go to ImgUpload");
		String message;
		String url = null;
		String id = null;
		String fileName = "";
		try {
			String fileid = null;
			// 验证大小
			String maxLength = FarmParameterService.getInstance().getParameter("config.doc.upload.length.max");
			if (file.getSize() > Long.valueOf(maxLength)) {
				throw new Exception("文件不能超过" + Long.valueOf(maxLength) / 1024 + "kb");
			}
			CommonsMultipartFile cmFile = (CommonsMultipartFile) file;
			DiskFileItem item = (DiskFileItem) cmFile.getFileItem();
			{// 小于8k不生成到临时文件，临时解决办法。zhanghc20150919
				if (!item.getStoreLocation().exists()) {
					item.write(item.getStoreLocation());
				}
			}
			try {
				fileName = URLEncoder.encode(item.getName(), "utf-8").replaceAll("\\+", "%20");
			} catch (Exception e) {
				fileName = URLEncoder.encode(item.getName(), "utf-8");
			}
			// 验证类型
			initIMG_EXNAMES();
			if (!farmFileManagerImpl.isImgByExname(FarmDocFiles.getExName(file.getOriginalFilename()).toUpperCase())
					&& !farmFileManagerImpl
							.isMediaByExname(FarmDocFiles.getExName(file.getOriginalFilename()).toUpperCase())) {
				throw new Exception("文件类型错误，允许的类型为：图片(" + IMG_EXNAMES_STR + ")/多媒体(" + MEDIA_EXNAMES_STR + ")");
			}
			if (file.getOriginalFilename().length() > 256) {
				throw new RuntimeException("上传文件名超长，请将文件名控制在256个字符以内");
			}
			fileid = farmFileManagerImpl.saveFile(item.getStoreLocation(), FILE_TYPE.RESOURCE_IMG,
					file.getOriginalFilename(), getCurrentUser(session), null);
			error = 0;
			url = farmFileManagerImpl.getImgURL(fileid, IMG_TYPE.MAX);
			message = "";
			id = fileid;
		} catch (Exception e) {
			error = 1;
			message = e.getMessage();
		}
		return ViewMode.getInstance().putAttr("error", error).putAttr("url", url).putAttr("message", message)
				.putAttr("id", id).putAttr("fileName", fileName).returnObjMode();
	}

	/**
	 * 下载图片
	 * 
	 * @return
	 */
	@RequestMapping("/Publoadimg")
	public void loadimg(String id, String type, HttpServletRequest request, HttpServletResponse response) {
		FarmDocfile file = null;
		try {
			file = farmFileManagerImpl.getFile(id);
		} catch (Exception e) {
			file = null;
		}
		if (file == null || file.getFile() == null || !file.getFile().exists()) {
			file = new FarmDocfile();
			file.setFile(farmFileManagerImpl.getNoneImg());
			file.setName("default.png");
			file.setExname("png");
		}
		File returnfile = file.getFile();
		int cutSize = FarmParameterService.getInstance().getParameterInt("config.limit.cut.png.length");
		{
			// 验证该文件不是图片类型
			if (farmFileManagerImpl.isImgByExname(file.getExname())) {
				// 只有非PNG或者大小大于100K的图片需要转换
				if (!file.getExname().toUpperCase().equals("PNG") || file.getFile().length() > (1024 * cutSize)) {
					// 变换图片(gif图片不变换)
					if (type != null && !type.isEmpty() && !file.getExname().toUpperCase().equals("GIF")) {
						IMG_TYPE imgType = IMG_TYPE.getEnum(type);
						if (imgType != null) {
							returnfile = farmFileManagerImpl.getFormatImgFile(file, imgType);
						}
					}
				}
			}
			// else{
			// //throw new RuntimeException("非图片文件不能下载为图片");
			// //非图片(多媒体，视频，音频)
			// }
		}
		downloadFile(returnfile, file.getName(), response, request);
	}

	/**
	 * 播放mp4/mp3
	 * 
	 * @return
	 */
	@RequestMapping("/PubPlayMedia")
	public void playMedia(String id, HttpServletRequest request, HttpServletResponse response) {
		FarmDocfile file = null;
		try {
			file = farmFileManagerImpl.getFile(id);
		} catch (Exception e) {
			file = null;
		}
		if (file == null || file.getFile() == null || !file.getFile().exists()) {
			file = new FarmDocfile();
			file.setFile(farmFileManagerImpl.getNoneImg());
			file.setName("default.png");
			file.setExname("png");
		}
		File returnfile = file.getFile();
		try {
			String contentType = "application/octet-stream";
			if (file.getExname().toUpperCase().indexOf("MP3") >= 0) {
				contentType = "audio/mp3";
			}
			if (file.getExname().toUpperCase().indexOf("MP4") >= 0) {
				contentType = "video/mp4";
			}
			FileDownloadUtils.sendVideoFile(request, response, returnfile, file.getName(), contentType);
		} catch (Exception e) {
			log.error(e);
		}
	}

	/**
	 * 下载文件
	 * 
	 * @return
	 */
	@RequestMapping("/Publoadfile")
	public void loadfile(String id, String safecode, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		FarmDocfile file = null;
		try {
			file = farmFileManagerImpl.getFile(id);
		} catch (Exception e) {
			file = null;
		}
		if (file == null || file.getFile() == null || !file.getFile().exists()) {
			// 文件不存在
			file = new FarmDocfile();
			file.setFile(farmFileManagerImpl.getNoneImg());
			file.setName("default.png");
			file.setExname("png");
		} else {
			// 文件没有下载权限
			if (!file.getServerid().equals(safecode)) {
				String isCheckSafecode = FarmParameterService.getInstance()
						.getParameter("config.doc.downloadfile.safecode.valid");
				// 是否验证安全码
				if (isCheckSafecode.toUpperCase().equals("TRUE")) {
					file = new FarmDocfile();
					file.setFile(farmFileManagerImpl.getNoRightImg());
					file.setName("noRight.png");
					file.setExname("png");
				}
			}
		}
		downloadFile(file.getFile(), file.getName(), response, request);
		farmFileManagerImpl.recordDownload(id, getCurrentIp(request), getCurrentUser(session));
	}

	/**
	 * 下载用户头像
	 * 
	 * @return
	 */
	@RequestMapping("/Publoadphoto")
	public void downloadPhone(String id, HttpServletRequest request, HttpServletResponse response) {
		FarmDocfile file = null;
		try {
			if (id != null && id.trim().toUpperCase().equals("NULL")) {
				id = null;
			}
			file = farmFileManagerImpl.getFile(id);
		} catch (Exception e) {
			file = null;
		}
		if (file == null || file.getFile() == null || !file.getFile().exists()) {
			file = new FarmDocfile();
			file.setFile(farmFileManagerImpl.getNonePhoto());
			file.setName("default.png");
			file.setExname("png");
		} else {
			int cutSize = FarmParameterService.getInstance().getParameterInt("config.limit.cut.png.length");
			// 变换图片(gif图片不变换)
			if (!file.getExname().toUpperCase().equals("GIF")) {
				if (!file.getExname().toUpperCase().equals("PNG") || file.getFile().length() > (1024 * cutSize)) {
					file.setFile(farmFileManagerImpl.getFormatImgFile(file, IMG_TYPE.MIN));
				}
			}
		}
		{
			// 验证该文件不是图片类型
			if (!farmFileManagerImpl.isImgByExname(file.getExname())) {
				throw new RuntimeException("非图片文件不能下载为用户头像");
			}
		}
		downloadFile(file.getFile(), file.getName(), response, request);
	}

	/**
	 * 下载验证码
	 * 
	 * @return
	 */
	@RequestMapping("/Pubcheckcode")
	public void checkcode(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		// 生成随机字串
		String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
		// 存入会话session
		session = request.getSession(true);
		// 删除以前的
		session.removeAttribute("verCode");
		session.setAttribute("verCode", verifyCode.toUpperCase());
		// 生成图片
		int w = 100, h = 30;
		try {
			VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
		} catch (IOException e) {
			log.error("下载验证码", e);
		}
	}

	/**
	 * 下载logo
	 * 
	 * @return
	 */
	@RequestMapping("/Publogo")
	public void logo(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String webPath = FarmParameterService.getInstance().getParameter("farm.constant.webroot.path");
		String filePath = "/view/web-simple/atext/png/icon".replaceAll("/", File.separator.equals("/") ? "/" : "\\\\");
		File file = new File(webPath + filePath);
		downloadFile(file, "logo", response, request);
	}

	/**
	 * 下载附件得图标（图片就显示图片，非图片显示文件类型图标）
	 * 
	 * @return
	 */
	@RequestMapping("/PubIcon")
	public void icon(String id, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		FarmDocfile file = null;
		try {
			file = farmFileManagerImpl.getFile(id);
		} catch (Exception e) {
			file = null;
		}
		if (file == null || file.getFile() == null || !file.getFile().exists()) {
			// 文件不存在
			file = new FarmDocfile();
			file.setFile(farmFileManagerImpl.getNoneImg());
			file.setName("default.png");
			file.setExname("png");
		} else {
			// 判断是否图片
			if (farmFileManagerImpl.isImg(id)) {
				int cutSize = FarmParameterService.getInstance().getParameterInt("config.limit.cut.png.length");
				// 变换图片(gif图片不变换)
				if (!file.getExname().toUpperCase().equals("GIF")) {
					if (!file.getExname().toUpperCase().equals("PNG") || file.getFile().length() > (1024 * cutSize)) {
						file.setFile(farmFileManagerImpl.getFormatImgFile(file, IMG_TYPE.MIN));
					}
				}
			} else {
				String webPath = FarmParameterService.getInstance().getParameter("farm.constant.webroot.path");
				String filePath = "/text/img/fileicon/".replaceAll("/", File.separator.equals("/") ? "/" : "\\\\");
				file.setFile(new File(webPath + filePath + file.getExname().toUpperCase() + ".png"));
			}
		}
		downloadFile(file.getFile(), file.getName(), response, request);
	}

	/**
	 * 下载其他文件从session中
	 * 
	 * @return
	 */
	@RequestMapping("/PubloadSessionfile")
	public void loadSessionfile(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		File file = new File((String) session.getAttribute("SESSION_FILEPATH"));
		downloadFile(file, (String) session.getAttribute("SESSION_FILENAME"), response, request);
	}

	/**
	 * 下载maxlogo
	 * 
	 * @return
	 */
	@RequestMapping("/PubHomelogo")
	public void maxlogo(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		String webPath = FarmParameterService.getInstance().getParameter("farm.constant.webroot.path");
		String filePath = "/view/web-simple/atext/png/maxicon".replaceAll("/",
				File.separator.equals("/") ? "/" : "\\\\");
		File file = new File(webPath + filePath);
		downloadFile(file, "homelogo", response, request);
	}

	/**
	 * response下载文件
	 * 
	 * @param file
	 * @param filename
	 * @param response
	 * @param request
	 * @return true 的话表示第一次请求 false 表示后面多次请求
	 */
	private void downloadFile(File file, String filename, HttpServletResponse response, HttpServletRequest request) {
		if (file.length() > 104857600) {
			FileDownloadUtils.rangeStreamDownloadFile(request, response, file, filename, "application/octet-stream");
		} else {
			FileDownloadUtils.simpleDownloadFile(file, filename, "application/octet-stream", response);
		}
	}

	/**
	 * 初始化允许的图片类型
	 */
	private void initIMG_EXNAMES() {
		if (IMG_EXNAMES_STR == null) {
			IMG_EXNAMES_STR = FarmParameterService.getInstance().getParameter("config.doc.img.upload.types")
					.toUpperCase().replaceAll("，", ",");
		}
		if (MEDIA_EXNAMES_STR == null) {
			MEDIA_EXNAMES_STR = FarmParameterService.getInstance().getParameter("config.doc.media.upload.types")
					.toUpperCase().replaceAll("，", ",");
		}
	}

	public void setFarmFileManagerImpl(FarmFileManagerInter farmFileManagerImpl) {
		this.farmFileManagerImpl = farmFileManagerImpl;
	}
}
