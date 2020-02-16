package com.farm.doc.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.farm.core.auth.domain.LoginUser;
import com.farm.doc.domain.FarmDocfile;
import com.farm.doc.server.FarmFileManagerInter;
import com.farm.doc.server.commons.FarmDocFiles;
import com.farm.util.web.FarmHtmlUtils;

public class HtmlUtils {

	// 替换doc中的图片地址为物理地址
	public static String escapeImgSrcToFileSrc(List<FarmDocfile> files, String htmltext) {
		Document document = Jsoup.parseBodyFragment(htmltext);
		Elements imgs = document.getElementsByTag("img");
		for (Element ele : imgs) {
			String urlStr = ele.attr("src");
			String fileid = FarmDocFiles.getFileIdFromImgUrl(urlStr);
			if (fileid != null) {
				// 查找并处理该文件
				for (FarmDocfile file : files) {
					if (file.getId().equals(fileid)) {
						// 构造附件地址
						String filePath = "file" + file.getDir().replaceAll("\\\\", "/") + file.getName();
						ele.attr("src", filePath);
					}
				}
			}
		}
		return document.getElementsByTag("body").html();
	}

	/**
	 * 删除Html标签
	 * 
	 * @param inputString
	 * @return
	 */
	public static String HtmlRemoveTag(String html) {
		return FarmHtmlUtils.HtmlRemoveTag(html);// 返回文本字符串
	}

	/**
	 * 下载网络资源
	 * 
	 * @param eurl
	 *            资源地址
	 * @return fileid
	 * @throws IOException
	 */
	public static String downloadWebImg(String eurl, FarmFileManagerInter farmFileManagerImpl) throws IOException {
		String exname = null;
		try {
			if (eurl.lastIndexOf("?") > 0) {
				exname = eurl.substring(0, eurl.lastIndexOf("?"));
			} else {
				exname = eurl;
			}
			if (eurl.lastIndexOf(".") > 0) {
				exname = eurl.substring(eurl.lastIndexOf(".") + 1);
			} else {
				exname = eurl;
			}
			if (exname == null || exname.length() > 10) {
				exname = "gif";
			}
		} catch (Exception e) {
			exname = "gif";
		}
		try {
			URL innerurl = new URL(eurl);
			// 创建连接的地址
			HttpURLConnection connection = (HttpURLConnection) innerurl.openConnection();
			// 返回Http的响应状态码
			InputStream input = null;
			try {
				input = connection.getInputStream();
			} catch (Exception e) {
				System.out.println(e + e.getMessage());
				return eurl;
			}
			FarmDocfile file = farmFileManagerImpl.openFile(exname, eurl.length() > 128 ? eurl.substring(0, 128) : eurl,
					new LoginUser() {
						@Override
						public String getName() {
							return "NONE";
						}

						@Override
						public String getLoginname() {
							return "NONE";
						}

						@Override
						public String getId() {
							return "NONE";
						}
						
						@Override
						public String getType() {
							return "NONE";
						}

						@Override
						public String getIp() {
							return "NONE";
						}
					});
			OutputStream fos = new FileOutputStream(file.getFile());
			// 获取输入流
			try {
				int bytesRead = 0;
				byte[] buffer = new byte[8192];
				while ((bytesRead = input.read(buffer, 0, 8192)) != -1) {
					fos.write(buffer, 0, bytesRead);
				}
			} finally {
				input.close();
				fos.close();
			}
			return file.getId();
			// config.file.client.html.resource.url
		} catch (IOException e) {
			throw e;
		}
	}

}
