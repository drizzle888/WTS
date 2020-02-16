package com.farm.report.jxls;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URISyntaxException;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Workbook;

public class ReportManagerImpl implements ReportManagerInter {

	public String path;
	public String opath;
	private static final Logger log = Logger.getLogger(ReportManagerImpl.class);

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public void generate(String fileName, Map<String, Object> parameter)
			throws ReportException {
		XLSTransformer transformer = new XLSTransformer();
		Workbook wb;
		if (path == null) {
			path = "report";
		}
		if (opath == null) {
			opath = "output";
		}
		try {
			String classPath = this.getClass().getClassLoader().getResource("").toURI().getPath()
					+ path + File.separator + fileName;
			String classPath2 = this.getClass().getClassLoader()
					.getResource("").toURI().getPath()
					+ opath;
			log.info("模板目录:" + classPath);
			log.info("报表输出目录:" + classPath2);
			wb = transformer.transformXLS(new FileInputStream(new File(
					classPath)), parameter);
			File file = new File(classPath2);
			file.mkdirs();
			classPath2 = classPath2 + File.separator + fileName;
			file = new File(classPath2);
			file.createNewFile();
			wb.write(new FileOutputStream(file));
		} catch (Exception e) {
			throw new ReportException(e);
		}
	}

	public String getOpath() {
		return opath;
	}

	public void setOpath(String opath) {
		this.opath = opath;
	}

	@Override
	public String getReportPath(String fileName) {
		String classPath = null;
		try {
			classPath = this.getClass().getClassLoader().getResource("").toURI()
					.getPath()
					+ opath + File.separator + fileName;
			return classPath;
		} catch (URISyntaxException e) {
			log.error(e+e.getMessage(), e);
		}
		return classPath;
	}

}
