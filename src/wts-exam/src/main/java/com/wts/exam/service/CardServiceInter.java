package com.wts.exam.service;

import com.wts.exam.domain.Card;
import com.wts.exam.domain.ex.CardInfo;
import com.wts.exam.domain.ex.PaperUnit;
import com.wts.exam.domain.ex.RoomUnit;
import com.wts.exam.domain.ex.SubjectUnit;
import com.farm.core.sql.query.DataQuery;
import com.farm.core.sql.result.DataResult;

import java.util.List;
import java.util.Map;

import com.farm.core.auth.domain.LoginUser;

/* *
 *功能：答题卡服务层接口
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
public interface CardServiceInter {

	/**
	 * 修改实体管理实体
	 * 
	 * @param entity
	 */
	public Card editCardEntity(Card entity);

	/**
	 * 删除实体管理实体
	 * 
	 * @param entity
	 */
	public void deleteCardEntity(String id, LoginUser user);

	/**
	 * 获得实体管理实体
	 * 
	 * @param id
	 * @return
	 */
	public Card getCardEntity(String id);

	/**
	 * 创建一个基本查询用来查询当前实体管理实体
	 * 
	 * @param query
	 *            传入的查询条件封装
	 * @return
	 */
	public DataQuery createCardSimpleQuery(DataQuery query);

	/**
	 * 初始化一个答题卡，创建或者获得（第一次访问就创建）
	 * 
	 * @param paperid
	 *            试卷id
	 * @param roomId
	 *            答题室id
	 * @param currentUser
	 *            当前用户
	 * @return
	 */
	public Card creatOrGetCard(String paperid, String roomId, LoginUser currentUser);

	/**
	 * 获得人员的答题卡信息(同时会把超时未交卷的状态做标记)
	 * 
	 * @param paperid
	 * @param roomid
	 * @param userid
	 * @return
	 */
	public Card loadCard(String paperid, String roomid, String userid);

	/**
	 * 保存一道考题的答案
	 * 
	 * @param paperid
	 * @param roomid
	 * @param versionid
	 * @param answerid
	 * @param value
	 * @param currentUser
	 * @return 是否有答案（如果用户提交一个空答案，则认为用户没有做该题）
	 */
	public boolean saveCardVal(Card card, String versionid, String answerid, String value);

	/**
	 * 汇总答題卡總分
	 * 
	 * @param card
	 * @return
	 */
	public float getCardPointSum(Card card);

	/**
	 * 从答题卡中加载试卷的值(用于试卷展示)
	 * 
	 * @param paper
	 *            试卷对象
	 * @param card
	 *            答题卡
	 */
	public void loadCardVal(PaperUnit paper, Card card);

	/**
	 * 答题卡是否可以答题（在答题时间内）
	 * 
	 * @param card
	 * @return
	 */
	public boolean isAnswerAble(Card card);

	/**
	 * 裝載考場考卷答題人數和縂人數
	 * 
	 * @param roomunit
	 */
	public void loadPaperUserNum(RoomUnit roomunit);

	/**
	 * 獲得某試卷的用戶答案和題的封裝
	 * 
	 * @param card
	 * @return
	 */
	public List<SubjectUnit> loadUserSubjects(Card card);

	/**
	 * 计算用户答卷的得分(将得分插入到数据库中WTS_CARD_POINT)
	 * 
	 * @param userSubjects
	 *            用户再答题卡中的题目，可通过loadUserSubjects方法获得
	 * @param paperId
	 * @return 是否全部完成计算（如果全部都是选择题或判断题就可以全部完成计算）
	 */
	public boolean runPointCount(List<SubjectUnit> userSubjects, Card card);

	/**
	 * 加载考卷的得分到试卷中，判卷的时候会用
	 * 
	 * @param paper
	 * @param card
	 */
	public void loadCardPoint(PaperUnit paper, Card card);

	/**
	 * 计算并合计试卷分数
	 * 
	 * @param cardid
	 * @param loginUser
	 */
	public CardInfo autoCountCardPoint(String cardid);

	/**
	 * 结束判卷
	 * 
	 * @param card
	 * @param points
	 *            map<題版本id,得分>
	 * @param currentUser
	 */
	public void finishAdjudge(Card card, Map<String, Integer> points, LoginUser currentUser);

	/**
	 * 发布答题卡分数
	 * 
	 * @param cardid
	 * @param currentUser
	 */
	public void publicPoint(String cardid, LoginUser currentUser);

	/**
	 * 获得房间内试卷的用户信息
	 * 
	 * @param roomid
	 * @param paperId
	 * @return
	 */
	public PaperUnit getRoomPaperUserNums(String roomid, String paperid);

	/**
	 * 清空房间中某试卷的所有用户答题卡
	 * 
	 * @param roomid
	 * @param paperid
	 * @param currentUser
	 */
	public void clearRoomCard(String roomid, String paperid, LoginUser currentUser);

	/**
	 * 计算一道题的得分权重
	 * 
	 * @param versionid
	 *            题目版本id
	 * @param value
	 *            用户答案
	 * @return （0~100）
	 */
	public int countSubjectPoint(SubjectUnit subjectUnit);

	/**
	 * 清空用户在某场考试下的试卷答题卡
	 * 
	 * @param roomid
	 * @param paperid
	 * @param userid
	 */
	public void clearPaperUserCard(String roomid, String paperid, LoginUser user);

	/**
	 * 删除考场答卷
	 * 
	 * @param roomid
	 * @param user
	 */
	public void deleteCardsByRoom(String roomid, LoginUser user);

	/**
	 * 获得用户在某个房间答过的卷子(当前房间的答卷，历史的不算)
	 * 
	 * @param roomid
	 * @param userid
	 * @return
	 */
	public List<String> getUserPaperidsByRoom(String roomid, String userid);

	/**
	 * 获得考场试卷的人员信息
	 * 
	 * @param roomId
	 *            房间id
	 * @param paperid
	 *            试卷id
	 * @param query
	 *            查询条件
	 * @return
	 */
	public DataResult getRoomPaperUsers(String roomId, String paperid, DataQuery query);

	/**
	 * 获得考场的人员信息（合并考場中全部試卷）
	 * 
	 * @param roomId
	 * @param query
	 * @return
	 */
	public DataResult getRoomUsers(String roomId, DataQuery query);

	/**
	 * 获得房间的所有答题卡
	 * 
	 * @param roomid
	 * @return
	 */
	public List<Card> getRoomCards(String roomid);

	/**
	 * 结束考试(用户主动结束考试，或判卷人强制收卷)(不判断权限)
	 * 
	 * @param id
	 * @param currentUser
	 */
	public void finishExamNoPop(String id, LoginUser currentUser);

	/**
	 * 结束考试(用户主动结束考试，或判卷人强制收卷)(判断权限，只有判卷人和本人可以收卷或交卷)
	 * 
	 * @param cardId
	 * @param currentUser
	 */
	public void finishExam(String cardId, LoginUser currentUser);

	/**
	 * 删除答题室的所有答题卡
	 * 
	 * @param roomid
	 * @param currentUser
	 */
	public void clearRoomCard(String roomid, LoginUser currentUser);
}