package com.wts.exam.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.apache.commons.codec.binary.Base64;

import com.farm.core.config.AppConfig;
import com.google.gson.Gson;
import com.wts.exam.domain.Paper;
import com.wts.exam.domain.ex.WtsPaperBean;

public class WtsPaperBeanUtils {

	/**
	 * 将封装对象导出为对象
	 * 
	 * @param papaerFile
	 * @param paperj
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void writeToFile(File papaerFile, WtsPaperBean paperj) throws FileNotFoundException, IOException {
		// ObjectOutputStream oos = new ObjectOutputStream(new
		// FileOutputStream(papaerFile));
		// try {
		// oos.writeObject(paperj);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		// oos.close();
		paperj.setVersion(AppConfig.getString("config.sys.version"));
		Gson gson = new Gson();
		String beanjson = gson.toJson(paperj);
		FileOutputStream bos = new FileOutputStream(papaerFile);
		bos.write(Base64.encodeBase64(beanjson.getBytes()));
		bos.close();
	}

	/**
	 * 从wtsp文件中读取答卷封装对象
	 * 
	 * @param inputStream
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static WtsPaperBean readFromFile(InputStream inputStream) throws IOException, ClassNotFoundException {
		// ObjectInputStream ois = new ObjectInputStream(inputStream);
		// try {
		// WtsPaperBean bean = (WtsPaperBean) ois.readObject();
		// return bean;
		// } finally {
		// ois.close();
		// }
		byte[] allbyte = readStream(inputStream);
		String beanjson = new String(Base64.decodeBase64(allbyte));
		Gson gson = new Gson();
		WtsPaperBean bean = gson.fromJson(beanjson, WtsPaperBean.class);
		return bean;
	}

	public static byte[] readStream(InputStream inStream) throws IOException {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		try {
			byte[] buffer = new byte[1024];
			int len = -1;
			while ((len = inStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
		} finally {
			outStream.close();
			inStream.close();
		}
		return outStream.toByteArray();
	}
}
