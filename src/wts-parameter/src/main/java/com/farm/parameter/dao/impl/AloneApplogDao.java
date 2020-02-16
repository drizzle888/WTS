package  com.farm.parameter.dao.impl;

import java.math.BigInteger;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.utils.HibernateSQLTools;
import com.farm.parameter.dao.AloneApplogDaoInter;
import com.farm.parameter.domain.AloneApplog;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**系统日志
 * @author MAC_wd
 * 
 */
@Repository
public class AloneApplogDao implements  AloneApplogDaoInter {
	@Resource(name = "sessionFactory")
	private SessionFactory sessionFatory;
	private HibernateSQLTools<AloneApplog> sqlTools ;

	public void deleteEntity(AloneApplog entity) {
		Session session=sessionFatory.getCurrentSession();
		session.delete(entity);
	}
	public int getAllListNum(){
		Session session= sessionFatory.getCurrentSession();
		SQLQuery sqlquery= session.createSQLQuery("select count(*) from alone_applog");
		BigInteger num=(BigInteger)sqlquery.list().get(0);
		return num.intValue() ;
	}
	public AloneApplog getEntity(String id) {
		Session session= sessionFatory.getCurrentSession();
		return (AloneApplog)session.get(AloneApplog.class, id);
	}
	public AloneApplog insertEntity(AloneApplog entity) {
		Session session= sessionFatory.getCurrentSession();
		session.save(entity);
		return entity;
	}
	public void editEntity(AloneApplog entity) {
		Session session= sessionFatory.getCurrentSession();
		session.update(entity);
	}
	
	@Override
	public Session getSession() {
		return sessionFatory.getCurrentSession();
	}
	public DataResult runSqlQuery(DataQuery query){
		try {
			return query.search(sessionFatory.getCurrentSession());
		} catch (Exception e) {
			return null;
		}
	}
	@Override
	public void deleteEntitys(List<DBRule> rules) {
		sqlTools.deleteSqlFromFunction(sessionFatory.getCurrentSession(), rules);
	}

	@Override
	public List<AloneApplog> selectEntitys(List<DBRule> rules) {
		return sqlTools.selectSqlFromFunction(
				sessionFatory.getCurrentSession(), rules);
	}

	@Override
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules) {
		sqlTools.updataSqlFromFunction(sessionFatory.getCurrentSession(),
				values, rules);
	}
	
	
	public SessionFactory getSessionFatory() {
		return sessionFatory;
	}

	public void setSessionFatory(SessionFactory sessionFatory) {
		this.sessionFatory = sessionFatory;
	}
	public HibernateSQLTools<AloneApplog> getSqlTools() {
		return sqlTools;
	}
	public void setSqlTools(HibernateSQLTools<AloneApplog> sqlTools) {
		this.sqlTools = sqlTools;
	}
}
