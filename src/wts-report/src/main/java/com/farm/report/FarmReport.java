package com.farm.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.farm.report.jxls.ReportManagerImpl;
import com.farm.report.jxls.ReportManagerInter;

public class FarmReport {
	private Map<String, Object> para = null;
	private String templeteFileName = null;

	private FarmReport() {
	}

	public static FarmReport newInstance(String templeteFileName) {
		FarmReport obj = new FarmReport();
		obj.para = new HashMap<String, Object>();
		obj.templeteFileName = templeteFileName;
		return obj;
	}

	public FarmReport addParameter(String key, Object val) {
		para.put(key, val);
		return this;
	}

	public InputStream generate() throws Exception {
		reportIMP.generate(templeteFileName, para);
		return new FileInputStream(new File(reportIMP.getReportPath(templeteFileName)));
	}

	public void generateForHttp(HttpServletResponse response, String fileName) throws Exception {
		reportIMP.generate(templeteFileName, para);
		FileInputStream input = new FileInputStream(new File(reportIMP.getReportPath(templeteFileName)));
		response.setContentType("application/vnd.ms-excel");
		OutputStream fOut = null;
		try {
			// 进行转码，使其支持中文文件名
			response.setHeader("content-disposition",
					"attachment;filename=" + new String(fileName.getBytes("UTF-8"), "iso-8859-1") + ".xls");
			fOut = response.getOutputStream();
			byte[] buffer = new byte[1024];
			while (input.read(buffer) > 0) {
				fOut.write(buffer);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			input.close();
			try {
				fOut.flush();
				fOut.close();
			} catch (IOException e) {
			}
		}
	}

	@SuppressWarnings("resource")
	public byte[] generateForByte() throws Exception {
		reportIMP.generate(templeteFileName, para);
		File file = new File(reportIMP.getReportPath(templeteFileName));
		long fileSize = file.length();
		if (fileSize > Integer.MAX_VALUE) {
			System.out.println("file too big...");
			return null;
		}
		FileInputStream fi = new FileInputStream(file);
		byte[] buffer = new byte[(int) fileSize];
		int offset = 0;
		int numRead = 0;
		while (offset < buffer.length && (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
			offset += numRead;
		}
		// 确保所有数据均被读取
		if (offset != buffer.length) {
			throw new IOException("Could not completely read file " + file.getName());
		}
		fi.close();
		return buffer;
	}

	private final static ReportManagerInter reportIMP = new ReportManagerImpl();
}
