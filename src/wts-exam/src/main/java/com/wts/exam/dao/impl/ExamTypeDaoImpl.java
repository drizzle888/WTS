package com.wts.exam.dao.impl;

import java.math.BigInteger;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.wts.exam.domain.ExamType;
import com.wts.exam.dao.ExamTypeDaoInter;
import com.farm.authority.domain.Organization;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.utils.HibernateSQLTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;
import javax.annotation.Resource;

/* *
 *功能：考试分类持久层实现
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Repository
public class ExamTypeDaoImpl extends HibernateSQLTools<ExamType>implements ExamTypeDaoInter {
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFatory;

	@Override
	public void deleteEntity(ExamType examtype) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		session.delete(examtype);
	}

	@Override
	public int getAllListNum() {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session.createSQLQuery("select count(*) from WTS_EXAM_TYPE");
		BigInteger num = (BigInteger) sqlquery.list().get(0);
		return num.intValue();
	}

	@Override
	public ExamType getEntity(String examtypeid) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		return (ExamType) session.get(ExamType.class, examtypeid);
	}

	@Override
	public ExamType insertEntity(ExamType examtype) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		session.save(examtype);
		return examtype;
	}

	@Override
	public void editEntity(ExamType examtype) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		session.update(examtype);
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
	public List<ExamType> selectEntitys(List<DBRule> rules) {
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
		return ExamType.class;
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFatory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ExamType> getAllSubNodes(String orgId) {
		Session session = sessionFatory.getCurrentSession();
		Query sqlquery = session.createQuery("from ExamType where TREECODE like ? order by ctime ");
		sqlquery.setString(0, getEntity(orgId).getTreecode() + "%");
		return sqlquery.list();
	}

	@Override
	public List<String> getAllSubType(List<String> typeIds) {
		String whereSql = null;
		for (String typeid : typeIds) {
			if (whereSql == null) {
				whereSql = "TREECODE like '%" + typeid + "%'";
			} else {
				whereSql = whereSql + " or TREECODE like '%" + typeid + "%'";
			}
		}
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session.createSQLQuery("select ID from WTS_EXAM_TYPE where " + whereSql);
		return sqlquery.list();
	}

	@Override
	public Set<String> getUserPopTypeids(String userId, String... funtypes) {
		String sql = "SELECT DISTINCT a.id FROM WTS_EXAM_TYPE a LEFT JOIN WTS_EXAM_POP b ON b.TYPEID = a.ID  ";
		String where = "";
		for (String fun : funtypes) {
			// 1:管理权限.
			if (fun.equals("1")) {
				where = where + " OR a.MNGPOP = '1' OR ( b.FUNTYPE = '1' AND b.USERID = '" + userId + "' )";
			}
			// 2:判卷权限.
			if (fun.equals("2")) {
				where = where + " OR a.ADJUDGEPOP = '1' OR ( b.FUNTYPE = '2' AND b.USERID = '" + userId + "' )";
			}
			// 3:查询权限.
			if (fun.equals("3")) {
				where = where + " OR a.QUERYPOP = '1' OR ( b.FUNTYPE = '3' AND b.USERID = '" + userId + "' )";
			}
			// 4:超级权限
			if (fun.equals("4")) {
				where = where + " OR a.SUPERPOP = '1' OR ( b.FUNTYPE = '4' AND b.USERID = '" + userId + "' )";
			}
		}

		Set<String> idset = new HashSet<>();
		if (StringUtils.isBlank(where)) {
			return idset;
		} else {
			sql = sql + " where 1=2 " + where;
		}
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session.createSQLQuery(sql);
		// sqlquery.setString(0, userId);
		for (String id : (List<String>) sqlquery.list()) {
			idset.add(id);
		}
		return idset;
	}

	@Override
	public List<Map<String, Object>> getTypePopUsers(String examtypeid, String funtype) {
		// TODO获得分类权限的用户<USERID,USERNAME,ORGID,ORGNAME,TYPEID,TYPENAME>
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session.createSQLQuery(
				"SELECT A.ID AS TYPEID, A. NAME AS TYPENAME, B.USERID AS USERID, C. NAME AS USERNAME, E.ID AS ORGID, E. NAME AS ORGNAME "
						+ "FROM WTS_EXAM_TYPE A LEFT JOIN WTS_EXAM_POP B ON A.ID = B.TYPEID AND FUNTYPE = ? LEFT JOIN ALONE_AUTH_USER C ON C.ID = B.USERID LEFT JOIN ALONE_AUTH_USERORG D ON D.USERID = C.ID LEFT JOIN ALONE_AUTH_ORGANIZATION E ON E.ID = D.ORGANIZATIONID"
						+ " WHERE A.ID=?");
		sqlquery.setString(0, funtype);
		sqlquery.setString(1, examtypeid);
		List<Map<String, Object>> results = new ArrayList<>();
		for (Object resultnode : sqlquery.list()) {
			Object[] node = (Object[]) resultnode;
			Map<String, Object> nodemap = new HashMap<>();
			nodemap.put("TYPEID", node[0]);
			nodemap.put("TYPENAME", node[1]);
			nodemap.put("USERID", node[2]);
			nodemap.put("USERNAME", node[3]);
			nodemap.put("ORGID", node[4]);
			nodemap.put("ORGNAME", node[5]);
			results.add(nodemap);
		}
		return results;
	}
}
