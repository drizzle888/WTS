package com.farm.lucene;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import com.farm.core.ParameterService;
import com.farm.lucene.face.WordAnalyzerFace;
import com.farm.lucene.server.DocIndexImpl;
import com.farm.lucene.server.DocIndexInter;
import com.farm.lucene.server.DocQueryImpl;
import com.farm.lucene.server.DocQueryInter;
import com.farm.parameter.FarmParameterService;

public class FarmLuceneFace {
	private static final Logger log = Logger.getLogger(FarmLuceneFace.class);
	private static String fileDirPath;

	private FarmLuceneFace() {
	}

	private static FarmLuceneFace obj;

	/**
	 * 获得服务实例
	 * 
	 * @return
	 */
	public static FarmLuceneFace inctance() {
		if (obj == null) {
			obj = new FarmLuceneFace();
		}
		return obj;
	}

	/**
	 * 分词服务
	 * 
	 * @return
	 */
	public WordAnalyzerFace getWordAnalyzerFace() {
		return new WordAnalyzerFace();
	}

	/**
	 * 建立索引服务
	 * 
	 * @param indexDir
	 * @return
	 * @throws IOException
	 */
	public DocIndexInter getDocIndex(File indexDir) throws IOException {
		return DocIndexImpl.getInstance(indexDir);
	}

	/**
	 * 查询索引服务
	 * 
	 * @param indexFile
	 * @return
	 */
	public DocQueryInter getDocQuery(File indexDir) {
		return DocQueryImpl.getInstance(indexDir);
	}

	/**
	 * 多索引查询服务
	 * 
	 * @param indexFiles
	 * @return
	 */
	public DocQueryInter getDocQuery(List<File> indexDirs) {
		return DocQueryImpl.getInstance(indexDirs);
	}

	/**
	 * 获得索引路径（luncene_index_dir+File.separator + path）
	 * 
	 * @param path
	 *            基于所配置目录的相对路径
	 * @return
	 */
	public File getIndexPathFile(String path) {
		File indexDir = new File(getFileDirPath() + File.separator + path);
		if(!indexDir.exists()){
			indexDir.mkdirs();
		}
		return indexDir;
	}

	public static String getFileDirPath() {
		if (fileDirPath == null) {
			ParameterService paras = FarmParameterService.getInstance();
			String path = paras.getParameter("config.file.luncene_index_dir");
			try {
				if (path.startsWith("WEBROOT")) {
					path = path.replace("WEBROOT", paras.getParameter("farm.constant.webroot.path "));
				}
				String separator = File.separator;
				if (separator.equals("\\")) {
					separator = "\\\\";
				}
				path = path.replaceAll("\\\\", "/").replaceAll("/", separator);
			} catch (Exception e) {
				log.warn(path + ":路径地址有误！");
				path = paras.getParameter("config.doc.dir");
			}
			fileDirPath = path;
		}
		return fileDirPath;
	}
}
