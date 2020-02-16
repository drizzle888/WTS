package com.farm.doc.dao;

import com.farm.doc.domain.Docfiletext;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;

/* *
 *功能：附件文本内容数据库持久层接口
 *详细：
 *
 *版本：v0.1
 *作者：Farm代码工程自动生成
 *日期：20150707114057
 *说明：
 */
public interface DocfiletextDaoInter {
	/**
	 * 删除一个附件文本内容实体
	 * 
	 * @param entity
	 *            实体
	 */
	public void deleteEntity(Docfiletext docfiletext);

	/**
	 * 由附件文本内容id获得一个附件文本内容实体
	 * 
	 * @param id
	 * @return
	 */
	public Docfiletext getEntity(String docfiletextid);

	/**
	 * 插入一条附件文本内容数据
	 * 
	 * @param entity
	 */
	public Docfiletext insertEntity(Docfiletext docfiletext);

	/**
	 * 获得记录数量
	 * 
	 * @return
	 */
	public int getAllListNum();

	/**
	 * 修改一个附件文本内容记录
	 * 
	 * @param entity
	 */
	public void editEntity(Docfiletext docfiletext);

	/**
	 * 获得一个session
	 */
	public Session getSession();

	/**
	 * 执行一条附件文本内容查询语句
	 */
	public DataResult runSqlQuery(DataQuery query);

	/**
	 * 条件删除附件文本内容实体，依据对象字段值(一般不建议使用该方法)
	 * 
	 * @param rules
	 *            删除条件
	 */
	public void deleteEntitys(List<DBRule> rules);

	/**
	 * 条件查询附件文本内容实体，依据对象字段值,当rules为空时查询全部(一般不建议使用该方法)
	 * 
	 * @param rules
	 *            查询条件
	 * @return
	 */
	public List<Docfiletext> selectEntitys(List<DBRule> rules);

	/**
	 * 条件修改附件文本内容实体，依据对象字段值(一般不建议使用该方法)
	 * 
	 * @param values
	 *            被修改的键值对
	 * @param rules
	 *            修改条件
	 */
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules);

	/**
	 * 条件合计附件文本内容:count(*)
	 * 
	 * @param rules
	 *            统计条件
	 */
	public int countEntitys(List<DBRule> rules);

	/**
	 * 件文本内容通過文件id查询
	 * 
	 * @param fileId
	 * @return
	 */
	public Docfiletext getEntityByFileId(String fileId);

	/**删除附件的文本描述
	 * @param fileId
	 */
	public void deleteFileTextByFileid(String fileId);
}