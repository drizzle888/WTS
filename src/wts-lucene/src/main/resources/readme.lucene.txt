使用FarmLuceneFace.inctance()创建索引服务功能


//创建索引
DocIndexInter  dii =  FarmLuceneFace.inctance().getDocIndex.getInstance(indexDir);
		DocMap mpa = new DocMap("asdfsafdasfd1asf");
		mpa
				.put(
						"TEXT",
						"分词器的建议使用，IKAnalyzer，在开源中文分词器里算是很好的，而且一直在稳定的更新版本。 ...建两个Field吧，把内容和路径都建索引，内容分词，路径不...",
						Store.YES, Index.ANALYZED);
		dii.indexDoc(mpa);
		dii.close();
//查询索引
 DocQueryInter query = FarmLuceneFace.inctance().getDocQuery.getInstance(indexDir);
IRResult result=query.query("WHERE(ID=asdfsafdasfdasf) ORDER_BY(TEXT:STRING ASC)",
				 1, 10);
		 
		 for (Map<String, String> node:  result.getResultList()) {
			System.out.println(node.get("TEXT"));
			System.out.println(node.get("ID"));
			System.out.println("-----------------------------------------------------------------");
		}
