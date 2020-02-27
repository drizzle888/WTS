package com.wts.exam.service.impl;

import com.wts.exam.domain.CardAnswer;
import com.farm.core.time.TimeTool;
import org.apache.log4j.Logger;
import com.wts.exam.dao.CardAnswerDaoInter;
import com.wts.exam.service.CardAnswerServiceInter;
import com.farm.core.sql.query.DataQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import com.farm.core.auth.domain.LoginUser;
/* *
 *功能：问卷答案服务层实现类
 *详细：
 *
 *版本：v0.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Service
public class CardAnswerServiceImpl implements CardAnswerServiceInter{
  @Resource
  private CardAnswerDaoInter  cardAnswerDaoImpl;

  private static final Logger log = Logger.getLogger(CardAnswerServiceImpl.class);
  @Override
  @Transactional
  public CardAnswer insertCardAnswerEntity(CardAnswer entity,LoginUser user) {
    // TODO 自动生成代码,修改后请去除本注释
    //entity.setCuser(user.getId());
    //entity.setCtime(TimeTool.getTimeDate14());
    //entity.setCusername(user.getName());
    //entity.setEuser(user.getId()); 
    //entity.setEusername(user.getName());
    //entity.setEtime(TimeTool.getTimeDate14());
    //entity.setPstate("1");
    return cardAnswerDaoImpl.insertEntity(entity);
  }
  @Override
  @Transactional
  public CardAnswer editCardAnswerEntity(CardAnswer entity,LoginUser user) {
    // TODO 自动生成代码,修改后请去除本注释
    CardAnswer entity2 = cardAnswerDaoImpl.getEntity(entity.getId());
    //entity2.setEuser(user.getId());
    //entity2.setEusername(user.getName());
    //entity2.setEtime(TimeTool.getTimeDate14()); 
    entity2.setPstate(entity.getPstate());
    entity2.setPcontent(entity.getPcontent());
    entity2.setCtime(entity.getCtime());
    entity2.setValstr(entity.getValstr());
    entity2.setCuser(entity.getCuser());
    entity2.setVersionid(entity.getVersionid());
    entity2.setAnswerid(entity.getAnswerid());
    entity2.setCardid(entity.getCardid());
    entity2.setId(entity.getId());
    cardAnswerDaoImpl.editEntity(entity2);
    return entity2;
  }
  @Override
  @Transactional
  public void deleteCardAnswerEntity(String id,LoginUser user) {
    // TODO 自动生成代码,修改后请去除本注释
	  cardAnswerDaoImpl.deleteEntity(cardAnswerDaoImpl.getEntity(id));
  }
  @Override
  @Transactional
  public CardAnswer getCardAnswerEntity(String id) {
    // TODO 自动生成代码,修改后请去除本注释
    if (id == null){return null;}
    return cardAnswerDaoImpl.getEntity(id);
  }
  @Override
  @Transactional
  public DataQuery createCardAnswerSimpleQuery(DataQuery query) {
    // TODO 自动生成代码,修改后请去除本注释
    DataQuery dbQuery = DataQuery
        .init(
            query,
            "WTS_CARD_ANSWER",
            "ID,PSTATE,PCONTENT,CTIME,VALSTR,CUSER,VERSIONID,ANSWERID,CARDID");
    return dbQuery;
  }

}
