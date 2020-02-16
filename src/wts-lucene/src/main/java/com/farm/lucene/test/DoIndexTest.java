package com.farm.lucene.test;

import java.io.File;

import org.apache.lucene.document.Field.Index;
import org.apache.lucene.document.Field.Store;

import com.farm.lucene.FarmLuceneFace;
import com.farm.lucene.adapter.DocMap;
import com.farm.lucene.server.DocIndexImpl;

public class DoIndexTest {
	private static File indexDir = new File(FarmLuceneFace.getFileDirPath() + File.separator + "taskId");

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// List<String> strList = new ArrayList<String>();
		// strList.add("在开始学习lucene时，在网上找到很多的例子，全部是早期的版本，在lucene3.5时，好多方法已不推荐使用");
		// strList
		// .add("IKAnalyzer是一个开源的，基于java语言开发的轻量级的中文分词工具包。从2006年12月推出1.0版开始，IKAnalyzer已经推出
		// 了3个大版本。最初，它是以开源项目Luence为应...");
		// strList
		// .add("国内情况 悬赏分太少只能帮你到这 了 ...lucene是个全文检索工具 IKAnalyzer是个分词包可以用来做中文的检索
		// ... ");
		// strList
		// .add("分词器的建议使用，IKAnalyzer，在开源中文分词器里算是很好的，而且一直在稳定的更新版本。
		// ...建两个Field吧，把内容和路径都建索引，内容分词，路径不...");
		// strList
		// .add("这两天正好在玩lucene,没用庖丁分词,主要是嫌它要配置环境,麻烦下面是demo,记得要加lucene-core-2.3.2.jar和lucene-Analyzer.jar以及IKAnalyzer.jar这几...");
		// creatOrAppendIndexToFile(strList);
		// search("DOC", "分词");
		// -----------------------------------------------------------------------------
		// DocIndexImpl dii = (DocIndexImpl) DocIndexImpl.getInstance(indexDir);
		// DocMap mpa = new DocMap("asdfsafdasfd1asf");
		// mpa
		// .put(
		// "TEXT",
		// "分词器的建议使用，IKAnalyzer，在开源中文分词器里算是很好的，而且一直在稳定的更新版本。
		// ...建两个Field吧，把内容和路径都建索引，内容分词，路径不...",
		// Store.YES, Index.ANALYZED);
		// dii.indexDoc(mpa);
		// dii.close();
	}
}
