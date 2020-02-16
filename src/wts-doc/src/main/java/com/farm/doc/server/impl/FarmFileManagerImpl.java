package com.farm.doc.server.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.annotation.Resource;
import javax.imageio.ImageIO;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.farm.core.auth.domain.LoginUser;
import com.farm.core.sql.query.DBRule;
import com.farm.core.time.TimeTool;
import com.farm.doc.dao.DocfiletextDaoInter;
import com.farm.doc.dao.FarmDocfileDaoInter;
import com.farm.doc.domain.Docfiletext;
import com.farm.doc.domain.FarmDocfile;
import com.farm.doc.server.FarmFileManagerInter;
import com.farm.doc.server.commons.FarmDocFiles;
import com.farm.parameter.FarmParameterService;
import com.farm.util.cache.FarmCacheName;
import com.farm.util.cache.FarmCaches;
import com.farm.util.web.WebVisitBuff;
import com.farm.web.WebUtils;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class FarmFileManagerImpl implements FarmFileManagerInter {
	@Resource
	private FarmDocfileDaoInter farmDocfileDao;
	@Resource
	private DocfiletextDaoInter docfiletextDaoImpl;
	private static final Logger log = Logger.getLogger(FarmFileManagerImpl.class);

	@Override
	@Transactional
	public String saveFile(File file, FILE_TYPE type, String title, LoginUser user, String processKey) {
		String exName = FarmDocFiles.getExName(title);
		if (exName.trim().toUpperCase().replace(".", "").equals("ZIP")) {
			type = FILE_TYPE.RESOURCE_ZIP;
		}
		if (".JPG .JPEG .GIF .PNG .BMP".indexOf(exName.trim().toUpperCase().replace(".", "")) >= 0) {
			type = FILE_TYPE.RESOURCE_IMG;
		}
		String userId = null;
		String userName = null;
		if (user == null || user.getName() == null) {
			userId = "none";
			userName = "none";
		} else {
			userId = user.getId();
			userName = user.getName();
		}
		// 重命名
		file = FarmDocFiles.renameFile(file, UUID.randomUUID().toString().replaceAll("-", "") + exName + ".file");
		FarmDocfile docfile = new FarmDocfile(FarmDocFiles.generateDir(),
				UUID.randomUUID().toString().replaceAll("-", ""), type.getValue(), title, file.getName(),
				TimeTool.getTimeDate14(), TimeTool.getTimeDate14(), userName, userId, userName, userId, "0", null,
				exName, Float.valueOf(String.valueOf(file.length())));
		if (user == null || user.getName() == null) {
			docfile.setCusername("none");
			docfile.setEusername("none");
		}
		FarmDocFiles.copyFile(file, FarmDocFiles.getFileDirPath() + docfile.getDir(), processKey);
		docfile = farmDocfileDao.insertEntity(docfile);
		return docfile.getId();
	}

	@Override
	@Transactional
	public String saveFile(byte[] fileData, FILE_TYPE type, String title, LoginUser user) {
		String exName = FarmDocFiles.getExName(title);
		String filename = UUID.randomUUID().toString().replaceAll("-", "") + exName + ".file";
		if (exName.trim().toUpperCase().replace(".", "").equals("ZIP")) {
			type = FILE_TYPE.RESOURCE_ZIP;
		}
		String userId = null;
		String userName = null;
		if (user == null || user.getName() == null) {
			userId = "none";
			userName = "none";
		} else {
			userId = user.getId();
			userName = user.getName();
		}
		FarmDocfile docfile = new FarmDocfile(FarmDocFiles.generateDir(),
				UUID.randomUUID().toString().replaceAll("-", ""), type.getValue(), title, filename,
				TimeTool.getTimeDate14(), TimeTool.getTimeDate14(), userName, userId, userName, userId, "0", null,
				exName, Float.valueOf(String.valueOf(0)));
		if (user == null || user.getName() == null) {
			docfile.setCusername("none");
			docfile.setEusername("none");
		}
		long length = FarmDocFiles.saveFile(fileData, filename, FarmDocFiles.getFileDirPath() + docfile.getDir());
		docfile.setLen(Float.valueOf(String.valueOf(length)));
		docfile = farmDocfileDao.insertEntity(docfile);
		return docfile.getId();
	}

	@Override
	@Transactional
	public String saveFile(InputStream inStream, String filename, String title, FILE_TYPE type, LoginUser user) {
		String exName = FarmDocFiles.getExName(title);
		filename = UUID.randomUUID().toString().replaceAll("-", "") + exName + ".file";
		if (exName.trim().toUpperCase().replace(".", "").equals("ZIP")) {
			type = FILE_TYPE.RESOURCE_ZIP;
		}
		String userId = null;
		String userName = null;
		if (user == null || user.getName() == null) {
			userId = "none";
			userName = "none";
		} else {
			userId = user.getId();
			userName = user.getName();
		}
		FarmDocfile docfile = new FarmDocfile(FarmDocFiles.generateDir(),
				UUID.randomUUID().toString().replaceAll("-", ""), type.getValue(), title, filename,
				TimeTool.getTimeDate14(), TimeTool.getTimeDate14(), userName, userId, userName, userId, "0", null,
				exName, Float.valueOf(String.valueOf(0)));
		if (user == null || user.getName() == null) {
			docfile.setCusername("none");
			docfile.setEusername("none");
		}
		long length = FarmDocFiles.saveFile(inStream, filename, FarmDocFiles.getFileDirPath() + docfile.getDir());
		docfile.setLen(Float.valueOf(String.valueOf(length)));
		docfile = farmDocfileDao.insertEntity(docfile);
		return docfile.getId();
	}

	@Override
	@Transactional
	public String getFileURL(String fileid) {
		FarmDocfile file = getFile(fileid);
		String url = FarmParameterService.getInstance().getParameter("config.doc.download.url") + fileid + "&safecode="
				+ file.getServerid();
		return url;
	}

	@Override
	public String getImgURL(String fileid, IMG_TYPE type) {
		String url = FarmParameterService.getInstance().getParameter("config.doc.img.url") + fileid + "&type="
				+ type.getUrlIndex();
		return url;
	}

	@Override
	@Transactional
	public FarmDocfile getFileNoCache(String fileid) {
		FarmDocfile file = farmDocfileDao.getEntity(fileid);
		return file;
	}

	public File getFile(FarmDocfile docFile) {
		return new File(FarmDocFiles.getFileDirPath() + File.separator + docFile.getDir() + docFile.getFilename());
	}

	@Override
	@Transactional
	public FarmDocfile getFile(String fileid) {
		FarmDocfile docFile = null;
		if (fileid == null) {
			return null;
		}
		FarmDocfile val = (FarmDocfile) FarmCaches.getInstance().getCacheData(fileid, FarmCacheName.FileCache);
		if (val != null) {
			log.debug("load file from cache");
			docFile = val;
		} else {
			docFile = farmDocfileDao.getEntity(fileid);
			if (docFile == null) {
				return null;
			}
			docFile.setFile(getFile(docFile));
			// 如果文件是大小是0的话就刷新文件大小
			if (docFile.getLen() == 0) {
				docFile.setLen(Float.valueOf(String.valueOf(docFile.getFile().length())));
				if (docFile.getLen() == 0) {
					docFile.setLen(Float.valueOf(-1));
				}
				farmDocfileDao.editEntity(docFile);
			}
			FarmCaches.getInstance().putCacheData(fileid, docFile, FarmCacheName.FileCache);
		}
		// 将对象克隆后传出，避免对象被污染
		try {
			return (FarmDocfile) BeanUtils.cloneBean(docFile);
		} catch (IllegalAccessException | InstantiationException | InvocationTargetException
				| NoSuchMethodException e) {
			log.warn("附件对象引用克隆失败", e);
			return docFile;
		}
	}

	@Override
	@Transactional
	public void submitFile(String fileId, String note) {
		submitFile(fileId, note, null);
	}

	@Override
	@Transactional
	public void cancelFile(String fileId) {
		if (fileId == null) {
			return;
		}
		FarmDocfile file = farmDocfileDao.getEntity(fileId);
		if (file == null) {
			return;
		}
		if (file.getPstate().equals("3")) {
			log.warn("文件被永久保留，因为多处引用，文件不能修改文件状态!");
			return;
		}
		file.setPstate("0");
		farmDocfileDao.editEntity(file);
		file.setFile(getFile(file));
		FarmCaches.getInstance().putCacheData(file.getId(), file, FarmCacheName.FileCache);
	}

	@Override
	@Transactional
	public void delFile(String fileId, LoginUser user) {
		{
			// 如果时使用中的文件不能被删除
			// FarmDocfile file = getFile(fileId);
			FarmDocfile file = farmDocfileDao.getEntity(fileId);
			if (file == null) {
				return;
			}
			if (file.getPstate().equals("1")) {
				throw new RuntimeException("使用中的文件不能被删除!");
			}
			if (file.getPstate().equals("3")) {
				log.warn("文件被永久保留，因为多处引用，文件不能被删除!");
				return;
			}
			if (StringUtils.isBlank(fileId)) {
				return;
			}
			/**
			 * 如果不能删除图片则保留图片，直接返回
			 **/
			boolean isRemoveImgable = FarmParameterService.getInstance().getParameter("config.logic.remove.img.able")
					.toUpperCase().equals("TRUE");
			if (isImg(fileId)) {
				if (!isRemoveImgable) {
					FarmDocfile docfile = farmDocfileDao.getEntity(fileId);
					docfile.setPcontent("因禁止删除图片而保留");
					farmDocfileDao.editEntity(docfile);
					return;
				}
			}
		}
		if (fileId == null) {
			return;
		}
		FarmDocfile docfile = farmDocfileDao.getEntity(fileId);
		if (docfile == null) {
			return;
		}
		File file = this.getFile(fileId).getFile();
		{
			// 删除附件得文字描述
			docfiletextDaoImpl.deleteFileTextByFileid(fileId);
		}
		{ // 如果该附件时知识得内容图则删除该附件（不知道爲何在删除知识的时候报错了，所以先注释掉）
			// Doc docbean = farmDocDao.getdocByImgid(fileId);
			// if (docbean != null) {
			// docbean.setImgid(null);
			// farmDocDao.editEntity(docbean);
			// }
		}
		farmDocfileDao.deleteEntity(docfile);
		try {
			if (file.exists()) {
				if (file.delete()) {
					log.info("删除成功！");
				} else {
					log.error("文件删除失败,未能删除请手动删除！");
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		FarmCaches.getInstance().removeCacheData(fileId, FarmCacheName.FileCache);
	}

	@Override
	@Transactional
	public FarmDocfile openFile(String exname, String content, LoginUser user) {
		FILE_TYPE type = FILE_TYPE.OHTER;
		String name = UUID.randomUUID().toString().replaceAll("-", "");
		String filename = name + "." + exname + ".file";
		FarmDocfile docfile = new FarmDocfile(FarmDocFiles.generateDir(),
				UUID.randomUUID().toString().replaceAll("-", ""), type.getValue(), name + "." + exname, filename,
				TimeTool.getTimeDate14(), TimeTool.getTimeDate14(), user.getName(), user.getId(), user.getName(),
				user.getId(), "0", content, exname, Float.valueOf(0));
		File file = getFile(docfile);
		try {
			if (!file.createNewFile()) {
				throw new RuntimeException("文件创建失败!");
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		docfile = farmDocfileDao.insertEntity(docfile);
		docfile.setFile(file);
		return docfile;
	}

	@Override
	@Transactional
	public List<FarmDocfile> getAllFileForDoc(String docid) {
		List<FarmDocfile> refiles = farmDocfileDao.getDocFilesByDocId(docid);
		for (FarmDocfile file : refiles) {
			file = getFile(file.getId());
		}
		return refiles;
	}

	@Override
	@Transactional
	public List<FarmDocfile> getAllFileForText(String textid) {
		List<FarmDocfile> refiles = farmDocfileDao.getDocFilesByDocTextId(textid);
		for (FarmDocfile file : refiles) {
			file = getFile(file.getId());
		}
		return refiles;
	}

	@Override
	@Transactional
	public List<FarmDocfile> getAllDocfileForDoc(String docid) {
		return farmDocfileDao.getDocFilesByDocId(docid);
	}

	@Override
	public List<FarmDocfile> getAllTypeFileForDoc(String docid, String exname) {
		List<FarmDocfile> refiles = farmDocfileDao.getDocFilesByDocId(docid);
		List<FarmDocfile> newrefiles = new ArrayList<FarmDocfile>();
		for (FarmDocfile file : refiles) {
			if (file.getExname().toUpperCase().equals(exname.toUpperCase())) {
				file = getFile(file.getId());
				newrefiles.add(file);
			}
		}
		return newrefiles;
	}

	@Override
	public boolean containFileByDoc(String docid, String fileId) {
		List<FarmDocfile> list = farmDocfileDao.getDocFilesByDocId(docid);
		for (FarmDocfile node : list) {
			if (node.getId().equals(fileId)) {
				return true;
			}
		}
		return false;
	}

	@Override
	@Transactional
	public void updateFileText(String docid, String fileid, String text) {
		if (text == null) {
			return;
		}
		// 查找该附件文本
		List<DBRule> rules = new ArrayList<>();
		rules.add(new DBRule("FILEID", fileid, "="));
		rules.add(new DBRule("DOCID", docid, "="));
		List<Docfiletext> lists = docfiletextDaoImpl.selectEntitys(rules);
		if (text != null && text.length() > 60000) {
			text = text.substring(0, 60000);
		}
		String textmin = text + "";
		if (text != null && text.length() > 120) {
			textmin = text.substring(0, 120);
		}
		if (lists.size() > 0) {
			// 找到的话就更新
			Docfiletext filetext = lists.get(0);
			filetext.setDescribes(text);
			filetext.setDescribesmin(textmin);
			docfiletextDaoImpl.editEntity(filetext);
		} else {// 找不到就插入
			Docfiletext filetext = new Docfiletext();
			filetext.setDescribes(text);
			filetext.setDocid(docid);
			filetext.setFileid(fileid);
			filetext.setDescribesmin(textmin);
			docfiletextDaoImpl.insertEntity(filetext);
		}
	}

	@Override
	@Transactional
	public Docfiletext getFiletext(String fileid) {
		List<DBRule> rules = new ArrayList<>();
		rules.add(new DBRule("FILEID", fileid, "="));
		List<Docfiletext> lists = docfiletextDaoImpl.selectEntitys(rules);
		if (lists.size() > 0) {
			return lists.get(0);
		}
		return null;
	}

	@Override
	@Transactional
	public void updateFileState(String oldfileId, String newfileId, LoginUser user) {
		String note = null;
		if (StringUtils.isNotBlank(oldfileId)) {
			FarmDocfile oldFile = farmDocfileDao.getEntity(oldfileId);
			if (oldFile != null) {
				note = oldFile.getPcontent();
			}
		} else {
			note = FILE_APPLICATION_TYPE.OTHER.getValue();
		}
		updateFileState(oldfileId, newfileId, user, note);
	}

	@Override
	@Transactional
	public void updateFileState(String oldfileId, String newfileId, LoginUser user, String note) {
		try {
			if (StringUtils.isNotBlank(oldfileId)) {
				cancelFile(oldfileId);
			}
			if (StringUtils.isNotBlank(newfileId)) {
				submitFile(newfileId, note);
			}
			if (newfileId != null && !newfileId.equals(oldfileId)) {
				cancelFile(oldfileId);
				delFile(oldfileId, user);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	@Override
	public File getNonePhoto() {
		String imgpath = FarmParameterService.getInstance().getParameter("config.doc.none.photo.path");
		return new File(FarmParameterService.getInstance().getParameter("farm.constant.webroot.path") + File.separator
				+ imgpath.replaceAll("\\\\", File.separator).replaceAll("//", File.separator));
	}

	@Override
	public File getNoneImg() {
		String imgpath = FarmParameterService.getInstance().getParameter("config.doc.none.img.path");
		return new File(FarmParameterService.getInstance().getParameter("farm.constant.webroot.path") + File.separator
				+ imgpath.replaceAll("\\\\", File.separator).replaceAll("//", File.separator));
	}

	@Override
	public File getNoRightImg() {
		String imgpath = FarmParameterService.getInstance().getParameter("config.doc.noright.img.path");
		return new File(FarmParameterService.getInstance().getParameter("farm.constant.webroot.path") + File.separator
				+ imgpath.replaceAll("\\\\", File.separator).replaceAll("//", File.separator));
	}

	@Override
	public String getPhotoURL(String fileid) {
		String url = FarmParameterService.getInstance().getParameter("config.doc.photo.url") + fileid;
		return url;
	}

	@Override
	public File getFormatImgFile(FarmDocfile docfile, IMG_TYPE type) {
		File file = new File(docfile.getFile().getPath());
		// 变换图片
		try {
			String prefixName = "jpg";// exname.substring(exname.lastIndexOf(".")
										// + 1);
			if (type.equals(IMG_TYPE.MAX) || type.equals(IMG_TYPE.MED) || type.equals(IMG_TYPE.MIN)) {
				return trimImg(prefixName, type, file);
			}
			if (type.equals(IMG_TYPE.ROTATE90) || type.equals(IMG_TYPE.ROTATE0)) {
				// 旋转图片
				// 文件后缀名
				if (prefixName == null || prefixName.trim().isEmpty()) {
					throw new RuntimeException();
				}
				File newfile = FarmDocFiles.getFormatImg(file, type, prefixName);
				// 判断图片是否已经存在了
				if (!newfile.exists()) {
					// 不存在则由max图片变换
					File maxfile = FarmDocFiles.getFormatImg(file, IMG_TYPE.MAX, prefixName);
					if (!maxfile.exists()) {
						// max不存在则创建max
						maxfile = trimImg(prefixName, IMG_TYPE.MAX, file);
					}
					if (type.getNum() != 0) {
						Thumbnails.of(maxfile).scale(1f).rotate(type.getNum()).toFile(newfile);
					} else {
						FarmDocFiles.copyFile(maxfile, newfile);
					}
				} else {
					// 存在则由本图片变换
					if (type.getNum() != 0) {
						Thumbnails.of(newfile).scale(1f).rotate(type.getNum()).toFile(newfile);
					}
				}
				return newfile;
			}
		} catch (Exception e) {
			log.warn(e.getMessage() == null ? "空指针异常"
					: e.getMessage() + "附件ID为：" + docfile.getId() + "文件：" + file.getPath());
			return docfile.getFile();
		}
		return docfile.getFile();
	}

	/**
	 * 裁剪图片
	 * 
	 * @param prefixName
	 *            文件后缀名
	 * @param type
	 *            裁剪类型
	 * @param file
	 *            文件
	 * @return 裁剪后的图片
	 * @throws IOException
	 */
	private File trimImg(String prefixName, IMG_TYPE type, File file) throws IOException {
		// 生成缩略图
		// 文件后缀名
		if (prefixName == null || prefixName.trim().isEmpty()) {
			throw new RuntimeException();
		}
		File newfile = FarmDocFiles.getFormatImg(file, type, prefixName);
		// 判断图片是否已经存在了
		if (!newfile.exists()) {
			if (file.getPath().indexOf("." + type.getFileIndex() + "." + prefixName) > 0) {
				throw new RuntimeException("原图片已经为缩略图，此文件可能会引起缩略图重复生成异常请检查程序代码，或所执行操作！");
			}
			// 不存在则直接变换
			BufferedImage bufferedImage = ImageIO.read(file);
			int width = bufferedImage.getWidth();
			int toWidth = type.getNum();
			if (width < toWidth) {
				toWidth = width;
			}
			Thumbnails.of(file).width(toWidth).toFile(newfile);
		}
		return newfile;
	}

	private static List<String> imgtypes = null;
	private static List<String> mediatypes = null;

	@Override
	@Transactional
	public boolean isImg(String fileid) {
		FarmDocfile docfile = getFile(fileid);
		if (docfile == null) {
			return false;
		}
		return isImgByExname(docfile.getExname());
	}

	@Override
	@Transactional
	public boolean isImgByExname(String exname) {
		WebUtils wu = new WebUtils();
		if (imgtypes == null) {
			imgtypes = wu.parseIds(FarmParameterService.getInstance().getParameter("config.doc.img.upload.types")
					.toUpperCase().replaceAll("，", ","));
		}
		if (!imgtypes.contains(FarmDocFiles.getExName(exname).toUpperCase())) {
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public boolean isMediaByExname(String exname) {
		WebUtils wu = new WebUtils();
		if (mediatypes == null) {
			mediatypes = wu.parseIds(FarmParameterService.getInstance().getParameter("config.doc.media.upload.types")
					.toUpperCase().replaceAll("，", ","));
		}
		if (!mediatypes.contains(FarmDocFiles.getExName(exname).toUpperCase())) {
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public boolean isImgByExname(Set<String> exnames) {
		for (String exname : exnames) {
			if (!isImgByExname(exname)) {
				return false;
			}
		}
		return true;
	}

	@Override
	@Transactional
	public void recordDownload(String fileid, String ip, LoginUser currentUser) {
		FarmDocfile file = farmDocfileDao.getEntity(fileid);
		WebVisitBuff visit = WebVisitBuff.getInstance("DOWNLOAD_NUM", 500);
		if (visit.canVisite(fileid + ip)) {
			file.setDownum(file.getDownum() + 1);
		}
		farmDocfileDao.editEntity(file);
	}

	@Override
	@Transactional
	public List<FarmDocfile> getAllDocFileForText(String textid) {
		List<FarmDocfile> refiles = farmDocfileDao.getDocFilesByDocTextId(textid);
		return refiles;
	}

	@Override
	public void clearCache() {
		FarmCaches.getInstance().clearCache(FarmCacheName.FileCache);
	}

	@Override
	public boolean isFileExistByDocId(String docid, String fileid) {
		List<FarmDocfile> files = getAllDocfileForDoc(docid);
		for (FarmDocfile file : files) {
			if (file.getId().equals(fileid)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void permanentFile(String fileid) {
		if (fileid == null) {
			return;
		}
		FarmDocfile file = farmDocfileDao.getEntity(fileid);
		if (file == null) {
			return;
		}
		file.setPstate("3");
		farmDocfileDao.editEntity(file);
		file.setFile(getFile(file));
		FarmCaches.getInstance().putCacheData(file.getId(), file, FarmCacheName.FileCache);
	}

	@Override
	@Transactional
	public void submitFile(String fileId, String note, String appid) {
		if (StringUtils.isBlank(fileId)) {
			return;
		}
		FarmDocfile file = farmDocfileDao.getEntity(fileId);
		if (file == null) {
			return;
		}
		if (file.getPstate().equals("3")) {
			log.warn("文件被永久保留，因为多处引用，文件不能修改文件状态!");
			return;
		}
		file.setPcontent(note);
		file.setPstate("1");
		file.setEtime(TimeTool.getTimeDate14());
		if (StringUtils.isNotBlank(appid)) {
			file.setAppid(appid);
		}
		farmDocfileDao.editEntity(file);
	}

	@Override
	@Transactional
	public void submitFileByAppHtml(String roomnote, String appid, FILE_APPLICATION_TYPE TYPE) {
		List<String> files = FarmDocFiles.getFilesIdFromHtml(roomnote);
		for (String fileid : files) {
			FarmDocfile file = getFileNoCache(fileid);
			if (file != null) {
				// 处理附件
				submitFile(fileid, TYPE.getValue(), appid);
			}
		}
	}

	@Override
	@Transactional
	public void cancelFilesByApp(String appid) {
		List<DBRule> rules = new ArrayList<>();
		rules.add(new DBRule("appid", appid, "="));
		List<FarmDocfile> lists = farmDocfileDao.selectEntitys(rules);
		for (FarmDocfile file : lists) {
			file.setPstate("0");
			file.setEtime(TimeTool.getTimeDate14());
			farmDocfileDao.editEntity(file);
		}
	}

}
