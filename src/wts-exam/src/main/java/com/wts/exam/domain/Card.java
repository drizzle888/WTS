package com.wts.exam.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/* *
 *功能：答题卡类
 *详细：
 *
 *版本：v2.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Entity(name = "Card")
@Table(name = "wts_card")
public class Card implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
	@Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
	private String id;
	@Column(name = "PCONTENT", length = 256)
	private String pcontent;
	@Column(name = "ENDTIME", length = 16)
	private String endtime;
	@Column(name = "PSTATE", length = 2, nullable = false)
	private String pstate;
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
	@Column(name = "ROOMID", length = 32, nullable = false)
	private String roomid;
	@Column(name = "PAPERID", length = 32, nullable = false)
	private String paperid;
	@Column(name = "COMPLETENUM", length = 10)
	private Integer completenum;
	@Column(name = "ALLNUM", length = 10)
	private Integer allnum;

	public Integer getCompletenum() {
		return completenum;
	}

	public void setCompletenum(Integer completenum) {
		this.completenum = completenum;
	}

	public Integer getAllnum() {
		return allnum;
	}

	public void setAllnum(Integer allnum) {
		this.allnum = allnum;
	}

	public String getPcontent() {
		return this.pcontent;
	}

	public void setPcontent(String pcontent) {
		this.pcontent = pcontent;
	}

	public String getAdjudgeusername() {
		return adjudgeusername;
	}

	public void setAdjudgeusername(String adjudgeusername) {
		this.adjudgeusername = adjudgeusername;
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

	public String getPaperid() {
		return this.paperid;
	}

	public void setPaperid(String paperid) {
		this.paperid = paperid;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
}