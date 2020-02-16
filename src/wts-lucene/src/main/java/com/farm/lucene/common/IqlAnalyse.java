package com.farm.lucene.common;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;

import com.farm.parameter.FarmParameterService;


public class IqlAnalyse implements IqlAnalyseInter {
	// WHERE(sdf,dfd=sdfsfd)ORDER_BY(dddd ASC,dddd ASC)
	private static final Logger log = Logger.getLogger(IqlAnalyse.class);

	public IqlAnalyse(String IQL, int cPage, int psize) {
		if (LUCENE_QUERY_MAXNUM == 0) {
			String para = null;
			try {
				para = FarmParameterService.getInstance().getParameter("config.file.luncene_max_query_num");
				LUCENE_QUERY_MAXNUM = Integer.valueOf(para);
			} catch (Exception e) {
				log.error("系统参数LUCENE_QUERY_MAXNUM错误:" + para);
			}
		}
		iql = IQL.trim();
		if (iql.indexOf("WHERE(") >= 0) {
			String where;
			where = iql.substring(iql.indexOf("WHERE(") + "WHERE(".length(), iql.indexOf(")"));
			String[] whereArray = where.split("=");
			if (whereArray.length < 2) {
				throw new RuntimeException("where错误");
			}
			title = whereArray[0];
			value = whereArray[1];

		}
		if (iql.indexOf("ORDER_BY(") >= 0) {
			String iqlpart = iql.substring(iql.indexOf("ORDER_BY("));
			group = iqlpart.substring(iqlpart.indexOf("ORDER_BY(") + "ORDER_BY(".length(), iqlpart.indexOf(")"));
		}

		this.currentPage = cPage;
		pageSize = psize;
	}

	private String iql;
	private String title;
	private String value;
	// 排序语句
	private String group;
	// 分页语句
	private int currentPage;
	private int pageSize;
	private static int LUCENE_QUERY_MAXNUM;

	public static void main(String[] args) {
		// AloneBaseManager.instance().getParameterFace().initConfig();
		// new IqlAnalyse("WHERE(sdf,dfd=sdfsfd)ORDER_BY(dddd ASC)LIMIT(5,5)");
	}

	@Override
	public String[] getLimitTitle() {
		return title.trim().toUpperCase().split(",");
	}

	@Override
	public String getLimitValue() {
		return QueryParser.escape(value.replaceAll("\\(", "").replaceAll("\\)", ""));
	}

	@Override
	public int getMaxTopNum() {
		return LUCENE_QUERY_MAXNUM;
	}

	@Override
	public Sort getSortTitle() {
		// ORDER_BY(dddd:int ASC,dddd:string ASC)
		// 注意被排序的字段必须被存储索引
		Sort sort = new Sort();
		if (group == null || group.trim().length() <= 0) {
			return sort;
		}
		String[] sortstr = group.split(",");
		SortField[] fields = new SortField[sortstr.length];
		try {
			for (int i = 0; i < sortstr.length; i++) {
				String sortString = sortstr[i];
				String[] para = sortString.trim().split(" ");
				String[] para2 = para[0].trim().split(":");
				String title = para2[0].trim().toUpperCase();
				String type = para2[1].trim().toUpperCase();
				String sortType = para[1].trim().toUpperCase();
				int typeInt = 0;
				if (title == null || title.length() <= 0 || type == null || type.length() <= 0 || sortType == null
						|| sortType.length() <= 0) {
					throw new RuntimeException("排序语句错误" + group);
				}
				if (type.equals("DOUBLE")) {
					typeInt = SortField.DOUBLE;
				}
				if (type.equals("STRING")) {
					typeInt = SortField.STRING;
				}
				if (type.equals("LONG")) {
					typeInt = SortField.LONG;
				}

				fields[i] = new SortField(title, typeInt, sortType.toUpperCase().equals("DESC"));
			}
			sort.setSort(fields);
		} catch (Exception e) {
			log.error("排序条件解析错误" + e);
			throw new RuntimeException("排序语句错误" + group + "/" + e);
		}
		return sort;
	}

	@Override
	public ScoreDoc[] subDoc(ScoreDoc[] allDoc) {
		int curentSize = allDoc.length - ((currentPage - 1) * pageSize);
		if (curentSize <= 0) {
			curentSize = 0;
		}
		if (curentSize > pageSize) {
			curentSize = pageSize;
		}
		ScoreDoc[] newScore = new ScoreDoc[curentSize];
		int m = 0;
		for (int i = ((currentPage - 1) * pageSize); i < ((currentPage - 1) * pageSize) + curentSize; i++) {
			if (allDoc.length < i) {
				break;
			}
			newScore[m] = allDoc[i];
			m++;
		}
		return newScore;
	}

	@Override
	public List<ScoreDoc> subDoc(List<ScoreDoc> allDoc) {
		int curentSize = allDoc.size() - ((currentPage - 1) * pageSize);
		if (curentSize <= 0) {
			curentSize = 0;
		}
		if (curentSize > pageSize) {
			curentSize = pageSize;
		}
		List<ScoreDoc> newScore = new ArrayList<>();
		for (int i = ((currentPage - 1) * pageSize); i < ((currentPage - 1) * pageSize) + curentSize; i++) {
			if (allDoc.size() < i) {
				break;
			}
			newScore.add(allDoc.get(i));
		}
		return newScore;
	}

	@Override
	public String getIQL() {
		return iql;
	}

}
