package com.farm.lucene.server;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.farm.lucene.adapter.DocMap;
import com.farm.lucene.common.IndexTaskDomain;

public class DocIndexImpl implements DocIndexInter {
	private static final Logger log = Logger.getLogger(DocIndexImpl.class);
	/**
	 * 索引目录
	 */
	private File indexDir = null;
	/**
	 * 中文分词器
	 */
	private static Analyzer analyzer = new IKAnalyzer();
	/**
	 * 临时内存索引
	 */
	private IndexWriter ramWriter = null;
	/**
	 * 临时内存索引目录
	 */
	private Directory ramdirectory = null;

	/**
	 * 
	 */
	private IndexReader reader = null;

	private DocIndexImpl() {
	}

	/**
	 * 工厂构造器(创建该对象后执行close即可以初始化索引目录)
	 * 
	 * @param indexDir
	 *            索引文件路径
	 * @return
	 * @throws IOException
	 */
	public static DocIndexInter getInstance(File indexDir) throws IOException {
		DocIndexImpl indexFace = new DocIndexImpl();
		indexFace.indexDir = indexDir;
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35, analyzer);
		iwc.setOpenMode(OpenMode.CREATE);
		indexFace.ramdirectory = new RAMDirectory();
		indexFace.ramWriter = new IndexWriter(indexFace.ramdirectory, iwc);
		if (!indexDir.exists()) {
			indexDir.mkdirs();
		}
		return indexFace;
	}

	/**
	 * 初始化索引目录
	 * 
	 * @param indexDir
	 * @return
	 * @throws IOException
	 */
	public static void initDir(File indexDir) throws IOException {
		DocIndexImpl indexFace = new DocIndexImpl();
		try {
			indexFace.indexDir = indexDir;
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35, analyzer);
			iwc.setOpenMode(OpenMode.CREATE);
			indexFace.ramdirectory = new RAMDirectory();
			indexFace.ramWriter = new IndexWriter(indexFace.ramdirectory, iwc);
			if (!indexDir.exists()) {
				indexDir.mkdirs();
			}
		} finally {
			try {
				indexFace.close();
			} catch (Exception e) {
				log.error(e + e.getMessage(), e);
			}
		}
	}

	@Override
	public void indexDoc(DocMap doc) throws Exception {
		// 建立新索引
		Document document = new Document();
		{
			for (String key : doc.keySet()) {
				Field field = new Field(key, doc.getValue(key) == null ? " " : doc.getValue(key), doc.getStore(key),
						doc.getIndex(key));
				document.add(field);
			}
		}
		ramWriter.addDocument(document);
		log.info("索引引擎" + "建立内存索引:" + doc.getInfo().replace("\n", ""));
	}

	/**
	 * 创建IndexWriter文件对象并初始化配置索引配置
	 * 
	 * @return
	 * @throws IOException
	 */
	private IndexWriter openFSIndexWriter() throws IOException {
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_35, analyzer);
		iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
		return new IndexWriter(FSDirectory.open(indexDir), iwc);
	}

	@Override
	public void close() throws Exception {
		try {
			if (reader != null) {
				reader.close();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		try {
			if (ramWriter != null) {
				ramWriter.close();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		IndexWriter fswriter = null;
		try {
			// 将内存索引合并到物理索引
			fswriter = openFSIndexWriter();
			fswriter.addIndexes(new Directory[] { ramdirectory });
			fswriter.close();
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		log.info("关闭索引" + "，并建立硬盘索引:" + indexDir);

	}

	@SuppressWarnings("deprecation")
	@Override
	public void deleteFhysicsIndex(String docId) throws Exception {
		long startTime = System.currentTimeMillis();
		if (reader == null) {
			Directory dir = FSDirectory.open(indexDir);
			if (!IndexReader.indexExists(dir)) {
				return;
			}
			reader = IndexReader.open(dir, false);
		}
		Term term = new Term("ID", docId);
		reader.deleteDocuments(term);
		reader.flush();
		long endTime = System.currentTimeMillis();
		log.info("删除磁盘索引ID:" + docId + ",total time: " + (endTime - startTime) + " ms");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void deleteFhysicsIndex(List<String> docids, IndexTaskDomain domain) throws Exception {
		int n = 0;
		if (reader == null) {
			Directory dir = FSDirectory.open(indexDir);
			if (!IndexReader.indexExists(dir)) {
				return;
			}
			reader = IndexReader.open(dir, false);
		}
		for (String docid : docids) {
			try {
				domain.setProcessNum(n++);
				log.info("删除索引：" + n + "/" + docids.size());
				// 删除分类索引
				Term term = new Term("ID", docid);
				reader.deleteDocuments(term);
			} catch (Exception e) {
				throw new RuntimeException(e + "删除索引");
			}
		}
		reader.flush();
	}

	@Override
	public void mergeIndex() {
		IndexWriter indexWriter = null;
		try {
			Directory index2 = FSDirectory.open(indexDir);
			Directory merged = FSDirectory.open(indexDir);
			IndexWriter writer = new IndexWriter(merged, new IKAnalyzer(), true, IndexWriter.MaxFieldLength.UNLIMITED);
			writer.addIndexes(new Directory[] { index2 });
			writer.optimize();
			writer.close();
		} catch (Exception e) {
			System.out.println("合并索引出错！");
			e.printStackTrace();
		} finally {
			try {
				if (indexWriter != null)
					indexWriter.close();
			} catch (Exception e) {
			}
		}
	}
}
