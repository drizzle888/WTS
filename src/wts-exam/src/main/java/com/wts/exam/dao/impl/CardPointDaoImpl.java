package com.wts.exam.dao.impl;

import java.math.BigInteger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.wts.exam.domain.CardPoint;
import com.wts.exam.dao.CardPointDaoInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.utils.HibernateSQLTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;

/* *
 *功能：用户答题持久层实现
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Repository
public class CardPointDaoImpl extends HibernateSQLTools<CardPoint>implements CardPointDaoInter {
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFatory;

	@Override
	public void deleteEntity(CardPoint cardPoint) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		session.delete(cardPoint);
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
	public CardPoint getEntity(String cardPointid) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		return (CardPoint) session.get(CardPoint.class, cardPointid);
	}

	@Override
	public CardPoint insertEntity(CardPoint cardPoint) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		session.save(cardPoint);
		return cardPoint;
	}

	@Override
	public void editEntity(CardPoint cardPoint) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		session.update(cardPoint);
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
	public List<CardPoint> selectEntitys(List<DBRule> rules) {
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
		return CardPoint.class;
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFatory;
	}

	@Override
	public List<Map<String, Object>> getAllRoomCardPoints(String roomid) {
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session.createSQLQuery(
				"SELECT B.POINT AS POINT,B.MPOINT AS MPOINT,C.SUBJECTID AS SUBJECTID,A.USERID AS USERID FROM WTS_CARD A LEFT JOIN WTS_CARD_POINT B ON A.ID=B.CARDID LEFT JOIN WTS_SUBJECT_VERSION C ON C.ID=B.VERSIONID "
						+ "where a.ROOMID=? and b.POINT is not null");
		sqlquery.setString(0, roomid);
		List<Map<String, Object>> results = new ArrayList<>();
		for (Object resultnode : sqlquery.list()) {
			Object[] node = (Object[]) resultnode;
			Map<String, Object> nodemap = new HashMap<>();
			nodemap.put("POINT", node[0]);
			nodemap.put("MPOINT", node[1]);
			nodemap.put("SUBJECTID", node[2]);
			nodemap.put("USERID", node[3]);
			results.add(nodemap);
		}
		return results;
	}
}
