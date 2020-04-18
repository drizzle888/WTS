package com.wts.exam.dao.impl;

import java.math.BigInteger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.wts.exam.domain.RoomPaper;
import com.wts.exam.dao.RoomPaperDaoInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.utils.HibernateSQLTools;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;

/* *
 *功能：考试试卷持久层实现
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Repository
public class RoomPaperDaoImpl extends HibernateSQLTools<RoomPaper>implements RoomPaperDaoInter {
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFatory;

	@Override
	public void deleteEntity(RoomPaper roompaper) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		session.delete(roompaper);
	}

	@Override
	public int getAllListNum() {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session.createSQLQuery("select count(*) from farm_code_field");
		BigInteger num = (BigInteger) sqlquery.list().get(0);
		return num.intValue();
	}

	@Override
	public RoomPaper getEntity(String roompaperid) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		return (RoomPaper) session.get(RoomPaper.class, roompaperid);
	}

	@Override
	public RoomPaper insertEntity(RoomPaper roompaper) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		session.save(roompaper);
		return roompaper;
	}

	@Override
	public void editEntity(RoomPaper roompaper) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		session.update(roompaper);
	}

	@Override
	public Session getSession() {
		// TODO 自动生成代码,修改后请去除本注释
		return sessionFatory.getCurrentSession();
	}

	@Override
	public DataResult runSqlQuery(DataQuery query) {
		// TODO 自动生成代码,修改后请去除本注释
		try {
			return query.search(sessionFatory.getCurrentSession());
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void deleteEntitys(List<DBRule> rules) {
		// TODO 自动生成代码,修改后请去除本注释
		deleteSqlFromFunction(sessionFatory.getCurrentSession(), rules);
	}

	@Override
	public List<RoomPaper> selectEntitys(List<DBRule> rules) {
		// TODO 自动生成代码,修改后请去除本注释
		return selectSqlFromFunction(sessionFatory.getCurrentSession(), rules);
	}

	@Override
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules) {
		// TODO 自动生成代码,修改后请去除本注释
		updataSqlFromFunction(sessionFatory.getCurrentSession(), values, rules);
	}

	@Override
	public int countEntitys(List<DBRule> rules) {
		// TODO 自动生成代码,修改后请去除本注释
		return countSqlFromFunction(sessionFatory.getCurrentSession(), rules);
	}

	public SessionFactory getSessionFatory() {
		return sessionFatory;
	}

	public void setSessionFatory(SessionFactory sessionFatory) {
		this.sessionFatory = sessionFatory;
	}

	@Override
	protected Class<?> getTypeClass() {
		return RoomPaper.class;
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFatory;
	}

	@Override
	public String getPaperAliasByCardId(String cardId) {
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session.createSQLQuery(
				"select NAME from WTS_CARD a left join WTS_ROOM_PAPER b on a.ROOMID=b.ROOMID and a.PAPERID=b.PAPERID where a.id=?");
		sqlquery.setString(0, cardId);
		List<String> names = (List<String>) sqlquery.list();
		if (names.size() > 0) {
			return names.get(0);
		}
		return null;
	}
}
