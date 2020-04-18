package com.wts.exam.dao.impl;

import java.math.BigInteger;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.wts.exam.domain.Material;
import com.wts.exam.dao.MaterialDaoInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.utils.HibernateSQLTools;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import javax.annotation.Resource;

/* *
 *功能：引用材料持久层实现
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Repository
public class MaterialDaoImpl extends HibernateSQLTools<Material>implements MaterialDaoInter {
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFatory;

	@Override
	public void deleteEntity(Material material) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		session.delete(material);
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
	public Material getEntity(String materialid) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		return (Material) session.get(Material.class, materialid);
	}

	@Override
	public Material insertEntity(Material material) {
		if (StringUtils.isBlank(material.getUuid())) {
			material.setUuid("");
		}
		Session session = sessionFatory.getCurrentSession();
		session.save(material);
		if (StringUtils.isBlank(material.getUuid())) {
			material.setUuid(material.getId());
			session.save(material);
		}
		return material;
	}

	@Override
	public void editEntity(Material material) {
		// TODO 自动生成代码,修改后请去除本注释
		Session session = sessionFatory.getCurrentSession();
		session.update(material);
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
	public List<Material> selectEntitys(List<DBRule> rules) {
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
		return Material.class;
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFatory;
	}

	@Override
	public List<Material> getMaterialsByPaperId(String paperId) {
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session.createSQLQuery(
				"select distinct c.ID as ID,C.UUID as UUID,c.CTIME as CTIME,c.ETIME as ETIME,c.CUSER as CUSER,c.EUSER as EUSER,c.PSTATE as PSTATE,c.PCONTENT as PCONTENT,c.TEXT as TEXT,c.TITLE as TITLE from WTS_PAPER_SUBJECT a left join WTS_SUBJECT b on a.SUBJECTID=b.ID left join WTS_MATERIAL c on c.id=b.MATERIALID where a.paperid=? and c.id is not null");
		sqlquery.setString(0, paperId);
		@SuppressWarnings("unchecked")
		List<Material> list = (List<Material>) sqlquery.addEntity(Material.class).list();
		return list;
	}

	@Override
	public String getIdByUuid(String uuid) {
		Session session = sessionFatory.getCurrentSession();
		SQLQuery sqlquery = session.createSQLQuery("select ID from WTS_MATERIAL where uuid=?");
		sqlquery.setString(0, uuid);
		@SuppressWarnings("unchecked")
		List<String> list = (List<String>) sqlquery.list();
		if (list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
}
