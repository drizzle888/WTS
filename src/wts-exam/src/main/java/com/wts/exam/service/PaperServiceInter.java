package com.wts.exam.service;

import com.wts.exam.domain.Paper;
import com.wts.exam.domain.ex.ChapterUnit;
import com.wts.exam.domain.ex.PaperUnit;
import com.wts.exam.domain.ex.SubjectUnit;
import com.farm.core.sql.query.DataQuery;

import java.util.List;

import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：考卷服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface PaperServiceInter {
	/**
	 * 新增实体管理实体
	 * 
	 * @param entity
	 */
	public Paper insertPaperEntity(Paper entity, LoginUser user);

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 */
	public Paper editPaperEntity(Paper entity, LoginUser user);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deletePaperEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public Paper getPaperEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createPaperSimpleQuery(DataQuery query);

	/**
	 * 设置考试得分类
	 * 
	 * @param roomid
	 *            考试id
	 * @param examtypeId
	 *            考试分类id
	 * @param currentUser
	 */
	public void examTypeSetting(String roomid, String examtypeId, LoginUser currentUser);

	/**
	 * 修改考试状态
	 * 
	 * @param id
	 * @param string
	 * @param currentUser
	 */
	public void editState(String id, String string, LoginUser currentUser);

	/**
	 * 获得试卷的封装对象
	 * 
	 * @param paperId
	 * @return
	 */
	public PaperUnit getPaperUnit(String paperId);

	/**
	 * 刷新答卷题数量
	 * 
	 * @param paperid
	 */
	public void refreshSubjectNum(String paperid);

	/**
	 * 检查试卷是否所有的题目都配置了得分
	 * 
	 * @param paperid
	 * @return
	 */
	public boolean isAllSubjectHavePoint(String paperid);

	/**
	 * 检查试卷是否全部设置了客观题答案
	 * 
	 * @param id
	 * @return
	 */
	public boolean isAllHaveObjectiveSubjectAnswer(String paperid);

	/**答卷是否包含问题
	 * @param paperid
	 * @return
	 */
	public boolean isHaveSubjects(String paperid);

	/**抓取考卷中所有的題目(递归抓取其中考题，适用于直接传入paperUnit中的chapterUnits)
	 * @param list
	 * @return
	 */
	public List<SubjectUnit> getPaperSubjects(List<ChapterUnit> chapterUnits);
}