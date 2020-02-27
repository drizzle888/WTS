package com.wts.exam.dao.impl;

import java.math.BigInteger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.wts.exam.domain.RoomUser;
import com.wts.exam.dao.RoomUserDaoInter;
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
 *功能：参考人持久层实现
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Repository
public class RoomUserDaoImpl extends HibernateSQLTools<RoomUser>implements RoomUserDaoInter {
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFatory;

	@Override
	public void deleteEntity(RoomUser roomuser) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		session.delete(roomuser);
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
	public RoomUser getEntity(String roomuserid) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		return (RoomUser) session.get(RoomUser.class, roomuserid);
	}

	@Override
	public RoomUser insertEntity(RoomUser roomuser) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		session.save(roomuser);
		return roomuser;
	}

	@Override
	public void editEntity(RoomUser roomuser) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		session.update(roomuser);
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
	public List<RoomUser> selectEntitys(List<DBRule> rules) {
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
		return RoomUser.class;
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFatory;
	}

	@Override
	public List<Map<String, Object>> getRoomUsers(String roomid) {
		// TODO获得分类权限的用户<USERID,USERNAME,ORGID,ORGNAME,TYPEID,TYPENAME>
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session.createSQLQuery(
				"SELECT a.ID AS ROOMID, a. NAME AS ROOMNAME, b.USERID AS USERID, c. NAME AS USERNAME, e.ID AS orgid, e. NAME AS ORGNAME "
						+ "FROM WTS_ROOM a LEFT JOIN WTS_ROOM_USER b ON a.id = b.ROOMID LEFT JOIN ALONE_AUTH_USER c ON c.id = b.USERID LEFT JOIN ALONE_AUTH_USERORG d ON d.USERID = c.ID LEFT JOIN ALONE_AUTH_ORGANIZATION e ON e.ID = d.ORGANIZATIONID"
						+ " WHERE A.ID=? and b.USERID is not null");
		sqlquery.setString(0, roomid);
		List<Map<String, Object>> results = new ArrayList<>();
		for (Object resultnode : sqlquery.list()) {
			Object[] node = (Object[]) resultnode;
			Map<String, Object> nodemap = new HashMap<>();
			nodemap.put("ROOMID", node[0]);
			nodemap.put("ROOMNAME", node[1]);
			nodemap.put("USERID", node[2]);
			nodemap.put("USERNAME", node[3]);
			nodemap.put("ORGID", node[4]);
			nodemap.put("ORGNAME", node[5]);
			results.add(nodemap);
		}
		return results;
	}
}
