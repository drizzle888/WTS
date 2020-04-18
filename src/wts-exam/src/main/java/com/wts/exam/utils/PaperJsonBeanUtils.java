package com.wts.exam.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.wts.exam.domain.ex.WtsPaperBean;

public class PaperJsonBeanUtils {

	/**
	 * 将封装对象导出为对象
	 * 
	 * @param papaerFile
	 * @param paperj
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void writeToFile(File papaerFile, WtsPaperBean paperj) throws FileNotFoundException, IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(papaerFile));
		try {
			oos.writeObject(paperj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		oos.close();
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
		ObjectInputStream ois = new ObjectInputStream(inputStream);
		try {
			WtsPaperBean bean = (WtsPaperBean) ois.readObject();
			return bean;
		} finally {
			ois.close();
		}
	}

}
