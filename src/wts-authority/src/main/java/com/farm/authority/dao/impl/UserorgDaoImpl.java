package  com.farm.authority.dao.impl;

import java.math.BigInteger;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.farm.authority.domain.Userorg;
import com.farm.authority.dao.UserorgDaoInter;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import com.farm.core.sql.utils.HibernateSQLTools;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/* *
 *功能：置顶文档持久层实现
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Repository
public class UserorgDaoImpl  extends HibernateSQLTools<Userorg> implements  UserorgDaoInter {
  @Resource(name = "sessionFactory")
  private SessionFactory sessionFatory;
  
  @Override
  public void deleteEntity(Userorg userorg) {
    
    Session session=sessionFatory.getCurrentSession();
    session.delete(userorg);
  }
  @Override
  public int getAllListNum(){
    
    Session session= sessionFatory.getCurrentSession();
    SQLQuery sqlquery= session.createSQLQuery("select count(*) from farm_code_field");
    BigInteger num=(BigInteger)sqlquery.list().get(0);
    return num.intValue() ;
  }
  @Override
  public Userorg getEntity(String userorgid) {
    
    Session session= sessionFatory.getCurrentSession();
    return (Userorg)session.get(Userorg.class, userorgid);
  }
  @Override
  public Userorg insertEntity(Userorg userorg) {
    
    Session session= sessionFatory.getCurrentSession();
    session.save(userorg);
    return userorg;
  }
  @Override
  public void editEntity(Userorg userorg) {
    
    Session session= sessionFatory.getCurrentSession();
    session.update(userorg);
  }
  
  @Override
  public Session getSession() {
    
    return sessionFatory.getCurrentSession();
  }
  @Override
  public DataResult runSqlQuery(DataQuery query){
    
    try {
      return query.search(sessionFatory.getCurrentSession());
    } catch (Exception e) {
      return null;
    }
  }
  @Override
  public void deleteEntitys(List<DBRule> rules) {
    
    deleteSqlFromFunction(sessionFatory.getCurrentSession(), rules);
  }

  @Override
  public List<Userorg> selectEntitys(List<DBRule> rules) {
    
    return selectSqlFromFunction(sessionFatory.getCurrentSession(), rules);
  }

  @Override
  public void updataEntitys(Map<String, Object> values, List<DBRule> rules) {
    
   updataSqlFromFunction(sessionFatory.getCurrentSession(), values, rules);
  }
  
  @Override
  public int countEntitys(List<DBRule> rules) {
    
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
		return Userorg.class;
	}

	@Override
	protected SessionFactory getSessionFactory() {
		return sessionFatory;
	}
	@Override
	public Userorg getEntityByUserId(String id) {
		Query query = getSession().createQuery("from UserOrg t where t.userid = ?");
		query.setString(0, id);
		return (Userorg) query.uniqueResult();
	}
}
