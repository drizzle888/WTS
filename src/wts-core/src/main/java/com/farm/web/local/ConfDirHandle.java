package com.farm.web.local;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class ConfDirHandle {
	/**
	 * 对配置文件做处理
	 * 
	 * @param strPath
	 *            配置文件夹路径
	 * @param index
	 *            配置文件关键字
	 * @param handle
	 * @param context
	 */
	public static void findDirForConf(String strPath, String[] indexs,
			ConfHandleInter handle, Object context) {
		try {
			strPath=URLDecoder.decode(strPath,"utf-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("ERROR:配置文件夹路径,解析错误");
		}
		File dir = new File(strPath);
		File[] files = dir.listFiles();
		if (files == null)
			return;
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				findDirForConf(files[i].getAbsolutePath(), indexs, handle,
						context);
			} else {
				String strFileName = files[i].getAbsolutePath().toLowerCase();
				for (String index : indexs) {
					if (strFileName.replace("\\", ".").replace("/", ".")
							.indexOf(index) >= 0) {
						handle.execute(context, files[i]);
					}
				}
			}
		}
	}
}
