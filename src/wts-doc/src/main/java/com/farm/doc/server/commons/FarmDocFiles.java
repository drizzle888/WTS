package com.farm.doc.server.commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.farm.core.time.TimeTool;
import com.farm.doc.server.FarmFileManagerInter.IMG_TYPE;
import com.farm.parameter.FarmParameterService;

public class FarmDocFiles {
	private static final Logger log = Logger.getLogger(FarmDocFiles.class);
	private static final Pattern FilePattern = Pattern.compile("[\\\\/:*?\"<>|\\\\.]");

	/**
	 * 过滤文件名中的特殊字符
	 * 
	 * @param str
	 * @return
	 */
	public static String filenameFilter(String str) {
		return str == null ? null : FilePattern.matcher(str).replaceAll("");
	}

	/**
	 * 生成文件目录
	 * 
	 * @return
	 */
	public static String generateDir() {
		// 把临时文件拷贝到上传目录下
		String dirPath = File.separator + TimeTool.getTimeDate12().substring(0, 4) + File.separator
				+ TimeTool.getTimeDate12().substring(4, 6) + File.separator + TimeTool.getTimeDate12().substring(6, 8)
				+ File.separator + TimeTool.getTimeDate12().substring(8, 10) + File.separator;
		File accessFile = new File(getFileDirPath() + dirPath);
		accessFile.mkdirs();
		if (!accessFile.exists()) {
			throw new RuntimeException("文件目录" + accessFile.getPath() + "创建失败，请检查本系统是否有文件夹创建权限!");
		}
		return dirPath;
	}

	/**
	 * 重命名文件
	 * 
	 * @param file
	 * @param filename
	 */
	public static File renameFile(File file, String filename) {
		String c = file.getParent();
		File mm = new File(c + File.separator + filename);
		if (!file.renameTo(mm)) {
			throw new RuntimeException("file rename fail!");
		}
		return mm;
	}

	/**
	 * 获得文件保存目录
	 * 
	 * @return
	 */
	public static String getFileDirPath() {
		return getDirPath("config.doc.dir");
	}

	/**
	 * 获得文件导出目录
	 * 
	 * @return
	 */
	public static String getExportDirPath() {
		File f = new File(getDirPath("config.doc.file.export"));
		f.mkdirs();
		return f.getPath();
	}

	/**
	 * 清空导出目录
	 * 
	 * @return
	 */
	public static void clearExportDir() {
		delAllFile(getExportDirPath());
	}

	/**
	 * 获得文件导入目录
	 * 
	 * @return
	 */
	public static String getImportDirPath() {
		File f = new File(getDirPath("config.doc.file.import"));
		f.mkdirs();
		return f.getPath();
	}

	/**
	 * 读取一个目录配置
	 * 
	 * @param parameterKey
	 * @return
	 */
	private static String getDirPath(String parameterKey) {
		String path = FarmParameterService.getInstance().getParameter(parameterKey);
		try {
			if (path.startsWith("WEBROOT")) {
				path = path.replace("WEBROOT",
						FarmParameterService.getInstance().getParameter("farm.constant.webroot.path"));
			}
			String separator = File.separator;
			if (separator.equals("\\")) {
				separator = "\\\\";
			}
			path = path.replaceAll("\\\\", "/").replaceAll("/", separator);
		} catch (Exception e) {
			log.warn(path + ":路径地址有误！");
			path = FarmParameterService.getInstance().getParameter(parameterKey);
		}
		return path;
	}

	/**
	 * 获得文件的扩展名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getExName(String fileName) {
		try {
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		} catch (Exception e) {
			return "none";
		}
	}

	/**
	 * 从图片的url中抓取附件id
	 * 
	 * @param url
	 * @return
	 */
	public static String getFileIdFromImgUrl(String urlStr) {
		String download_url = FarmParameterService.getInstance().getParameter("config.doc.download.url");// 在html中找到附件文件
		String img_url = FarmParameterService.getInstance().getParameter("config.doc.img.url");
		String fileid = null;
		if (urlStr.indexOf(img_url) >= 0) {
			if (urlStr.lastIndexOf("&") > 0) {
				String splits = urlStr.substring(urlStr.indexOf(img_url) + img_url.length(), urlStr.lastIndexOf("&"));
				fileid = splits;
			} else {
				String splits = urlStr.substring(urlStr.indexOf(img_url) + img_url.length());
				fileid = splits;
			}
		}
		// 为了适配旧的图片url
		if (urlStr.indexOf(download_url) >= 0) {
			if (urlStr.lastIndexOf("&") > 0) {
				String splits = urlStr.substring(urlStr.indexOf(download_url) + download_url.length(),
						urlStr.lastIndexOf("&"));
				fileid = splits;
			} else {
				String splits = urlStr.substring(urlStr.indexOf(download_url) + download_url.length());
				fileid = splits;
			}
		}
		return fileid;
	}

	/**
	 * 重html中获取系统中附件id
	 * 
	 * @param html
	 * @return
	 */
	public static List<String> getFilesIdFromHtml(String html) {
		List<String> list = new ArrayList<String>();
		Set<String> set = new HashSet<>();
		if (html == null) {
			return list;
		}
		Document doc = Jsoup.parse(html);
		// 寻找图片
		for (Element node : doc.getElementsByTag("img")) {
			String urlStr = node.attr("src");
			String id = getFileIdFromImgUrl(urlStr);
			if (id != null) {
				set.add(id);
			}
		}
		// 寻找多媒体
		for (Element node : doc.getElementsByTag("embed")) {
			String urlStr = node.attr("src");
			String id = getFileIdFromImgUrl(urlStr);
			if (id != null) {
				set.add(id);
			}
		}
		// 寻找附件
		for (Element node : doc.getElementsByTag("a")) {
			String urlStr = node.attr("href");
			String id = getFileIdFromImgUrl(urlStr);
			if (id != null) {
				set.add(id);
			}
		}
		list.addAll(set);
		return list;
	}

	/**
	 * 拷贝一个文件到新的地址
	 * 
	 * @param file
	 * @param newPath
	 *            复制到的文件地址
	 * @param fileKey
	 *            文件key，可以用来兑换文件的拷贝进度
	 */
	public static void copyFile(File file, String newPath, String fileKey) {
		int byteread = 0;
		File oldfile = file;
		if (oldfile.exists()) { // 文件存在时
			InputStream inStream = null;
			FileOutputStream fs = null;
			try {
				inStream = new FileInputStream(file);
				fs = new FileOutputStream(newPath + file.getName());
				byte[] buffer = new byte[9999];
				int readed = 0;
				while ((byteread = inStream.read(buffer)) != -1) {
					if (StringUtils.isNotBlank(fileKey)) {
						readed = readed + buffer.length;
						int percentage = (int) (1.0 * readed / file.length() * 100);
						FileCopyProcessCache.setProcess(fileKey, percentage);
					}
					fs.write(buffer, 0, byteread);
				}
			} catch (FileNotFoundException e) {
				throw new RuntimeException("拷贝一个文件到新的地址:" + e.getMessage());
			} catch (IOException e) {
				throw new RuntimeException(e);
			} finally {
				try {
					if (inStream != null) {
						inStream.close();
					}
					if (fs != null) {
						fs.close();
					}
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	public static void copyFile(File file, File newfile) {
		int byteread = 0;
		File oldfile = file;
		if (oldfile.exists()) { // 文件存在时
			InputStream inStream = null;
			FileOutputStream fs = null;
			try {
				inStream = new FileInputStream(file);
				fs = new FileOutputStream(newfile);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					fs.write(buffer, 0, byteread);
				}
			} catch (FileNotFoundException e) {
				log.error(e.getMessage());
			} catch (IOException e) {
				log.error(e.getMessage());
			} finally {
				try {
					inStream.close();
					fs.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * 将文件流保存到一个地址
	 * 
	 * @param inStream
	 * @param filename
	 * @param newPath
	 * @return
	 */
	public static Long saveFile(InputStream inStream, String filename, String newPath) {
		int byteread = 0;
		FileOutputStream fs = null;
		try {
			fs = new FileOutputStream(newPath + filename);
			byte[] buffer = new byte[1444];
			while ((byteread = inStream.read(buffer)) != -1) {
				fs.write(buffer, 0, byteread);
			}
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		} finally {
			try {
				inStream.close();
				fs.close();
			} catch (IOException e) {
			}
		}
		File file = new File(newPath + filename);
		return file.length();
	}

	/**
	 * 获得图片的特定类型文件
	 * 
	 * @param file
	 *            图片文件
	 * @param type
	 *            图片类型
	 * @return
	 */
	public static File getFormatImg(File file, IMG_TYPE type, String prefix) {
		String plusName = "." + type.getFileIndex() + "." + prefix;
		return new File(file.getPath() + plusName);
	}

	// 删除文件夹
	// param folderPath 文件夹完整绝对路径
	private static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
		} catch (Exception e) {
			log.error(e + e.getMessage(), e);
		}
	}

	// 删除指定文件夹下所有文件
	// param path 文件夹完整绝对路径
	private static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	public static long saveFile(byte[] fileData, String filename, String newPath) {
		FileOutputStream fs = null;
		try {
			fs = new FileOutputStream(newPath + filename);
			fs.write(fileData);
			fs.flush();
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		} finally {
			try {
				fs.close();
			} catch (IOException e) {
			}
		}
		File file = new File(newPath + filename);
		return file.length();
	}
}
