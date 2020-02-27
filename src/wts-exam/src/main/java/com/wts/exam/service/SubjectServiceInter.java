package com.wts.exam.service;

import com.wts.exam.domain.Subject;
import com.wts.exam.domain.SubjectVersion;
import com.wts.exam.domain.ex.SubjectUnit;
import com.wts.exam.domain.ex.TipType;
import com.farm.core.sql.query.DataQuery;

import java.util.List;

import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：考题服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface SubjectServiceInter {

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 */
	public SubjectVersion editSubjectEntity(SubjectVersion entity, String tipanalysis, LoginUser currentUser);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteSubjectEntity(String subjectId, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public Subject getSubjectEntity(String subjectId);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createSubjectSimpleQuery(DataQuery query);

	/**
	 * 获得一个试题对象
	 * 
	 * @param tiptype
	 *            试题类型
	 * @param subjectTypeid
	 *            试题分类
	 * @param user
	 *            当前用户
	 * @return
	 */
	public SubjectUnit initSubjectUnit(TipType tiptype, String subjectTypeid, LoginUser user);

	/**
	 * 获得一个试题对象
	 * 
	 * @param versionId
	 *            试题版本
	 * @param type
	 *            试题类型
	 * @param user
	 *            当前用户
	 * @return
	 */
	public SubjectUnit getSubjectUnit(String subjectVersionId);

	/**
	 * 设置考题分类
	 * 
	 * @param subjectId
	 * @param typeId
	 * @param currentUser
	 */
	public void subjectTypeSetting(String subjectId, String typeId, LoginUser currentUser);

	/**
	 * 通过大文本添加一批考题
	 * 
	 * @param typeid
	 * @param texts
	 * @param currentUser
	 * @return
	 */
	public List<SubjectUnit> addTextSubjects(String typeid, String texts, LoginUser currentUser);

	/**
	 * 清空临时数据（主要是创建题目后没有保存的题目，会形成状态为0的临时数据）
	 * 
	 * @param currentUser
	 * @return
	 */
	public int clearSubject(LoginUser currentUser);

	/**
	 * 更新題是否已經有了正确答案，該功能目的是幫助用戶判斷題是否設置答案，以便進行答案配置的工作
	 * 
	 * @param subjectId
	 */
	public void updateAnswered(String subjectId);

	/**
	 * 绑定材料到问题
	 * 
	 * @param subjectId
	 * @param materialId
	 * @param currentUser
	 */
	public void bindMaterial(String subjectId, String materialId, LoginUser currentUser);

	/**
	 * 清空綁定的材料
	 * 
	 * @param subjectId
	 * @param currentUser
	 */
	public void clearMaterial(String subjectId, LoginUser currentUser);

	/**
	 * 解析一個回答json字符串為subjectUnit對象
	 * 
	 * @param jsons
	 *            {"versionid":"2c902a8d6887dfc2016887ff390d007d","answerid":
	 *            "2c902a8d6887dfc2016887ff390d007f","value":true}
	 * @return
	 */
	public SubjectUnit parseSubjectJsonVal(String jsons);

	/**
	 * 刷新试题解析数量
	 * 
	 * @param subjectid
	 */
	public int refrashAnalysisnum(String subjectid);

	/**
	 * 刷新试题评论数量
	 * 
	 * @param subjectid
	 */
	public int refrashCommentnum(String subjectid);

	/**
	 * 獲得題目的最新版本id
	 * 
	 * @param subjectId
	 * @return
	 */
	public String getSubjectVersionId(String subjectId);

	/**
	 * 点赞（第二次点是取消）
	 * 
	 * @param subjectId
	 *            题id
	 * @param isJoin
	 *            赞，还是踩
	 * @param user
	 */
	public int doPraise(String subjectId, LoginUser user);

	/**
	 * 增加题完成次数
	 * 
	 * @param subjectid
	 */
	public void addDoNum(String subjectid);

	/**
	 * 增加題答對次數
	 * 
	 * @param subjectid
	 */
	public void addRightNum(String subjectid);
}