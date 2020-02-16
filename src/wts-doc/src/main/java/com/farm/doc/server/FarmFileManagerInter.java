package com.farm.doc.server;

import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.farm.core.auth.domain.LoginUser;
import com.farm.doc.domain.Docfiletext;
import com.farm.doc.domain.FarmDocfile;
import com.farm.doc.server.FarmFileManagerInter.FILE_TYPE;

public interface FarmFileManagerInter {
	/**
	 * 附件类型
	 * 
	 * @author 王东
	 * 
	 */
	public enum FILE_TYPE {
		// 1:图片,2:资源,3:压缩,0:其他
		RESOURCE_IMG("1"), RESOURCE_FILE("2"), RESOURCE_ZIP("3"), OHTER("0"), WEB_FILE("4");
		private String value;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		FILE_TYPE(String value) {
			this.value = value;
		}
	}

	public enum FILE_APPLICATION_TYPE {
		// 1:图片,2:资源,3:压缩,0:其他
		WEBURL_IMG("推荐服务图标"), RESUME_PHOTO("个人档案照片"), RESUME_FILE("个人档案附件"), USER_IMG("用户头像"), OTHER("未知"), ROOMNOTE(
				"答题室描述"), PAPERNOTE("试卷描述"), PAPER_CHAPTERNOTE("试卷章节描述"), SUBJECTNOTE("题描述"), SUBJECT_ANSWERNOTE("题选项描述");
		private String value;

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		FILE_APPLICATION_TYPE(String value) {
			this.value = value;
		}
	}

	public enum IMG_TYPE {
		MAX("max", "max", 1200), MED("med", "med", 960), MIN("min", "min", 330), ROTATE90("rotate90", "rotate",
				90), ROTATE0("rotate0", "rotate", 0);
		/**
		 * url标记
		 */
		private String urlIndex;
		/**
		 * 文件标记
		 */
		private String fileIndex;
		private int num;

		public String getUrlIndex() {
			return urlIndex;
		}

		public String getFileIndex() {
			return fileIndex;
		}

		public int getNum() {
			return num;
		}

		/**
		 * @param value
		 * @param num
		 */
		IMG_TYPE(String urlIndex, String fileIndex, int num) {
			this.urlIndex = urlIndex;
			this.num = num;
			this.fileIndex = fileIndex;
		}

		/**
		 * 获得枚举类型，根据url的标记
		 * 
		 * @param type
		 * @return
		 */
		public static IMG_TYPE getEnum(String type) {
			for (IMG_TYPE typenode : values()) {
				if (typenode.getUrlIndex().toUpperCase().equals(type.toUpperCase())) {
					return typenode;
				}
			}
			return null;
		}
	}

	/**
	 * 保存一个附件到系统中
	 * 
	 * @param file
	 * @param type
	 * @param title
	 * @return 附件ID
	 */
	public String saveFile(File file, FILE_TYPE type, String title, LoginUser user, String processKey);

	/**
	 * 保存一个附件到系统中
	 * 
	 * @param data
	 *            附件byte[]
	 * @param resourceImg
	 *            附件类型
	 * @param imgName
	 *            附件的名稱
	 * @param currentUser
	 * @return
	 */
	public String saveFile(byte[] fileData, FILE_TYPE type, String title, LoginUser user);

	/**
	 * 保存一个附件到系统中
	 * 
	 * @param inStream
	 *            文件流
	 * @param filename
	 *            文件名称
	 * @param title
	 *            文件title
	 * @param type
	 *            文件类型
	 * @param user
	 *            当前用户
	 * @return
	 */
	public String saveFile(InputStream inStream, String filename, String title, FILE_TYPE file_type, LoginUser user);

	/**
	 * 由文件id获得下载链接
	 * 
	 * @param fileid
	 *            文件id
	 * @return
	 */
	public String getFileURL(String fileid);

	/**
	 * 由文件id获得图片链接
	 * 
	 * @param fileid
	 *            文件id
	 * @return
	 */
	public String getImgURL(String fileid, IMG_TYPE IMG_TYPE);

	/**
	 * 由文件id获得人员照片链接
	 * 
	 * @param fileid
	 *            文件id
	 * @return
	 */
	public String getPhotoURL(String fileid);

	/**
	 * 由文件id获得文件对象
	 * 
	 * @param fileid
	 * @return
	 */
	public FarmDocfile getFile(String fileid);

	/**
	 * 获取物理文件（不走缓存）
	 * 
	 * @param docFile
	 * @return
	 */
	public File getFile(FarmDocfile docFile);

	/**
	 * 由文件id获得文件对象
	 * 
	 * @param fileid
	 * @return
	 */
	public FarmDocfile getFileNoCache(String fileid);

	/**
	 * 获得文件的文本内容预览
	 * 
	 * @param fileid
	 * @return
	 */
	public Docfiletext getFiletext(String fileid);

	/**
	 * 获得一个默认图片
	 * 
	 * @param file
	 * @return
	 */
	public File getNoneImg();

	/**
	 * 获得一个无授权的提示图片
	 * 
	 * @param file
	 * @return
	 */
	public File getNoRightImg();

	/**
	 * 获得一个默认头像
	 * 
	 * @param file
	 * @return
	 */
	public File getNonePhoto();

	/**
	 * 判断一个文件是否图片
	 * 
	 * @param fileid
	 *            文件主键
	 * @return
	 */
	public boolean isImg(String fileid);

	/**
	 * 判断一个文件是否图片
	 * 
	 * @param exname
	 *            文件后缀名
	 * @return
	 */
	public boolean isImgByExname(String exname);

	/**
	 * 判断一个文件是否多媒体
	 * 
	 * @param exname
	 *            文件后缀名
	 * @return
	 */
	public boolean isMediaByExname(String exname);

	/**
	 * 判断一组文件是否全是图片
	 * 
	 * @param exnames
	 *            文件名序列
	 * @return
	 */
	public boolean isImgByExname(Set<String> exnames);

	/**
	 * 获得格式化的图片文件
	 * 
	 * @param file
	 * @param type
	 * @return
	 */
	public File getFormatImgFile(FarmDocfile file, IMG_TYPE type);

	/**
	 * 将文件状态改为提交状态，否则为临时状态
	 * 
	 * @param fileId
	 *            附件id
	 * @param note
	 *            FILE_APPLICATION_TYPE枚举值.记录附件用途
	 */
	public void submitFile(String fileId, String note);

	/**
	 * 将文件设置为临时状态
	 * 
	 * @param fileId
	 */
	public void cancelFile(String fileId);

	/**
	 * 将文件设置为永久保存（因为多处引用）
	 * 
	 * @param fileid
	 */
	public void permanentFile(String fileid);

	/**
	 * 更新文件状态，旧的删除，新的设置为已使用
	 * 
	 * @param oldfileId
	 * @param newfileId
	 * @param user
	 */
	@Deprecated
	public void updateFileState(String oldfileId, String newfileId, LoginUser user);

	/**
	 * @param oldfileId
	 * @param newfileId
	 * @param user
	 * @param note
	 *            FILE_APPLICATION_TYPE枚举值.记录附件用途
	 */
	public void updateFileState(String oldfileId, String newfileId, LoginUser user, String note);

	/**
	 * 删除一个文件（配置文件中配置图片文件是否允许删除）
	 * 
	 * @param fileId
	 */
	public void delFile(String fileId, LoginUser user);

	/**
	 * 创建并使用一个新的文件（并附带一个已经存在的File对象）
	 * 
	 * @param exname
	 *            扩展名
	 * @param content
	 *            备注
	 * @param user
	 * @return
	 */
	public FarmDocfile openFile(String exname, String content, LoginUser user);

	/**
	 * 获得文档的所有附件,带File对象
	 * 
	 * @param docid
	 */
	public List<FarmDocfile> getAllFileForDoc(String docid);

	/**
	 * 获得文档某版本下的附件,带File对象
	 * 
	 * @param docid
	 */
	public List<FarmDocfile> getAllFileForText(String textid);

	/**
	 * 获得文档某版本下的附件,不带File对象
	 * 
	 * @param docid
	 */
	public List<FarmDocfile> getAllDocFileForText(String textid);

	/**
	 * 获得文档的所有附件,不带File对象
	 * 
	 * @param docid
	 */
	public List<FarmDocfile> getAllDocfileForDoc(String docid);

	/**
	 * 获得文档的某类型的所有附件
	 * 
	 * @param docid
	 * @param exname
	 *            扩展名如.doc
	 * @return
	 */
	public List<FarmDocfile> getAllTypeFileForDoc(String docid, String exname);

	/**
	 * 文档是否包含一个附件
	 * 
	 * @param id
	 * @param zipfileId
	 * @return
	 */
	public boolean containFileByDoc(String docid, String fileId);

	/**
	 * 更新附件文本值
	 * 
	 * @param docid
	 *            附件关联知识的id
	 * @param fileid
	 *            附件id
	 * @param text
	 *            文本值
	 */
	public void updateFileText(String docid, String fileid, String text);

	/**
	 * 记录附件被下载一次(在同一IP下载同一个知识不被重复统计)
	 * 
	 * @param fileid
	 * @param ip
	 * @param currentUser
	 *            暂时无用，可以为空
	 */
	public void recordDownload(String fileid, String ip, LoginUser currentUser);

	/**
	 * 刷新附件缓存
	 */
	public void clearCache();

	/**
	 * 判断一个附件是否存在于一个知识的附件中
	 * 
	 * @param docid
	 * @param fileid
	 * @return
	 */
	public boolean isFileExistByDocId(String docid, String fileid);

	/**
	 * 提交一个附件
	 * 
	 * @param fileid
	 * @param value
	 * @param appid
	 */
	public void submitFile(String fileid, String value, String appid);

	/**
	 * 提交一個應用的超文本中的圖片附件
	 * 
	 * @param html
	 * @param appid
	 */
	public void submitFileByAppHtml(String roomnote, String appid, FILE_APPLICATION_TYPE TYPE);

	/**
	 * 取消一個應用中的附件的提交狀態
	 * 
	 * @param id
	 */
	public void cancelFilesByApp(String appid);

}
