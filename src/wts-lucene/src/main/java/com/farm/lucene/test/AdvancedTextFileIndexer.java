package com.farm.lucene.test;

import java.io.File;
import java.io.IOException;


import com.farm.lucene.server.DocIndexImpl;
import com.farm.lucene.server.DocIndexInter;

public class AdvancedTextFileIndexer {

	public static void main(String[] areg) throws IOException {
		DocIndexInter indexWriter = DocIndexImpl.getInstance(new File("D:\\wcp3server\\resource\\index\\docindex"));
		indexWriter.mergeIndex();
	}

}