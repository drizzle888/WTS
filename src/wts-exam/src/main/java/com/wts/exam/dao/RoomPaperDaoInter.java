package com.wts.exam.dao;

import com.wts.exam.domain.RoomPaper;
import org.hibernate.Session;
import com.farm.core.sql.query.DBRule;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;
import java.util.List;
import java.util.Map;

/* *
 *功能：考试试卷数据库持久层接口
 *详细：
 *
 *版本：v0.1
 *作者：Farm代码工程自动生成
 *日期：20150707114057
 *说明：
 */
public interface RoomPaperDaoInter {
	/**
	 * 删除一个考试试卷实体
	 * 
	 * @param entity
	 *            实体
	 */
	public void deleteEntity(RoomPaper roompaper);

	/**
	 * 由考试试卷id获得一个考试试卷实体
	 * 
	 * @param id
	 * @return
	 */
	public RoomPaper getEntity(String roompaperid);

	/**
	 * 插入一条考试试卷数据
	 * 
	 * @param entity
	 */
	public RoomPaper insertEntity(RoomPaper roompaper);

	/**
	 * 获得记录数量
	 * 
	 * @return
	 */
	public int getAllListNum();

	/**
	 * 修改一个考试试卷记录
	 * 
	 * @param entity
	 */
	public void editEntity(RoomPaper roompaper);

	/**
	 * 获得一个session
	 */
	public Session getSession();

	/**
	 * 执行一条考试试卷查询语句
	 */
	public DataResult runSqlQuery(DataQuery query);

	/**
	 * 条件删除考试试卷实体，依据对象字段值(一般不建议使用该方法)
	 * 
	 * @param rules
	 *            删除条件
	 */
	public void deleteEntitys(List<DBRule> rules);

	/**
	 * 条件查询考试试卷实体，依据对象字段值,当rules为空时查询全部(一般不建议使用该方法)
	 * 
	 * @param rules
	 *            查询条件
	 * @return
	 */
	public List<RoomPaper> selectEntitys(List<DBRule> rules);

	/**
	 * 条件修改考试试卷实体，依据对象字段值(一般不建议使用该方法)
	 * 
	 * @param values
	 *            被修改的键值对
	 * @param rules
	 *            修改条件
	 */
	public void updataEntitys(Map<String, Object> values, List<DBRule> rules);

	/**
	 * 条件合计考试试卷:count(*)
	 * 
	 * @param rules
	 *            统计条件
	 */
	public int countEntitys(List<DBRule> rules);

	/**
	 * 通過答題卡id獲得答卷別名
	 * 
	 * @param cardId
	 * @return
	 */
	public String getPaperAliasByCardId(String cardId);
}