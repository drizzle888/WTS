package com.wts.exam.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/* *
 *功能：答题卡历史记录类
 *详细：
 *
 *版本：v2.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Entity(name = "CardHis")
@Table(name = "wts_card_his")
public class CardHis implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
	private String id;
	@Column(name = "ALLNUM", length = 10)
	private Integer allnum;
	@Column(name = "COMPLETENUM", length = 10)
	private Integer completenum;
	@Column(name = "ENDTIME", length = 16)
	private String endtime;
	@Column(name = "PSTATE", length = 2, nullable = false)
	private String pstate;
	@Column(name = "OVERTIME", length = 2, nullable = false)
	private String overtime;
	@Column(name = "PCONTENT", length = 256)
	private String pcontent;
	@Column(name = "STARTTIME", length = 16)
	private String starttime;
	@Column(name = "ADJUDGETIME", length = 16)
	private String adjudgetime;
	@Column(name = "ADJUDGEUSERNAME", length = 64)
	private String adjudgeusername;
	@Column(name = "ADJUDGEUSER", length = 32)
	private String adjudgeuser;
	@Column(name = "POINT", length = 12, nullable = false)
	private Float point;
	@Column(name = "USERID", length = 32, nullable = false)
	private String userid;
	@Column(name = "USERNAME", length = 64, nullable = false)
	private String username;
	@Column(name = "ROOMID", length = 32, nullable = false)
	private String roomid;
	@Column(name = "ROOMNAME", length = 64, nullable = false)
	private String roomname;
	@Column(name = "PAPERID", length = 32, nullable = false)
	private String paperid;
	@Column(name = "PAPERNAME", length = 128, nullable = false)
	private String papername;

	public String getOvertime() {
		return overtime;
	}

	public void setOvertime(String overtime) {
		this.overtime = overtime;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getAllnum() {
		return this.allnum;
	}

	public void setAllnum(Integer allnum) {
		this.allnum = allnum;
	}

	public Integer getCompletenum() {
		return this.completenum;
	}

	public void setCompletenum(Integer completenum) {
		this.completenum = completenum;
	}

	public String getEndtime() {
		return this.endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getPstate() {
		return this.pstate;
	}

	public void setPstate(String pstate) {
		this.pstate = pstate;
	}

	public String getPcontent() {
		return this.pcontent;
	}

	public void setPcontent(String pcontent) {
		this.pcontent = pcontent;
	}

	public String getStarttime() {
		return this.starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getAdjudgetime() {
		return this.adjudgetime;
	}

	public void setAdjudgetime(String adjudgetime) {
		this.adjudgetime = adjudgetime;
	}

	public String getAdjudgeusername() {
		return this.adjudgeusername;
	}

	public void setAdjudgeusername(String adjudgeusername) {
		this.adjudgeusername = adjudgeusername;
	}

	public String getAdjudgeuser() {
		return this.adjudgeuser;
	}

	public void setAdjudgeuser(String adjudgeuser) {
		this.adjudgeuser = adjudgeuser;
	}

	public Float getPoint() {
		return this.point;
	}

	public void setPoint(Float point) {
		this.point = point;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getRoomid() {
		return this.roomid;
	}

	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}

	public String getRoomname() {
		return this.roomname;
	}

	public void setRoomname(String roomname) {
		this.roomname = roomname;
	}

	public String getPaperid() {
		return this.paperid;
	}

	public void setPaperid(String paperid) {
		this.paperid = paperid;
	}

	public String getPapername() {
		return this.papername;
	}

	public void setPapername(String papername) {
		this.papername = papername;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
}