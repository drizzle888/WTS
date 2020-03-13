package com.wts.exam.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/* *
 *功能：考试类
 *详细：
 *
 *版本：v2.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Entity(name = "Room")
@Table(name = "wts_room")
public class Room implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
	@Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
	private String id;
	@Column(name = "COUNTTYPE", length = 2, nullable = false)
	private String counttype;
	@Column(name = "ROOMNOTE", length = 65535)
	private String roomnote;
	@Column(name = "TIMELEN", length = 10, nullable = false)
	private Integer timelen;
	@Column(name = "WRITETYPE", length = 2, nullable = false)
	private String writetype;
	@Column(name = "STARTTIME", length = 16)
	private String starttime;
	@Column(name = "ENDTIME", length = 16)
	private String endtime;
	@Column(name = "TIMETYPE", length = 2, nullable = false)
	private String timetype;
	@Column(name = "EXAMTYPEID", length = 32)
	private String examtypeid;
	@Column(name = "CUSER", length = 32, nullable = false)
	private String cuser;
	@Column(name = "CUSERNAME", length = 64, nullable = false)
	private String cusername;
	@Column(name = "ETIME", length = 16, nullable = false)
	private String etime;
	@Column(name = "CTIME", length = 16, nullable = false)
	private String ctime;
	@Column(name = "EUSERNAME", length = 64, nullable = false)
	private String eusername;
	@Column(name = "EUSER", length = 32, nullable = false)
	private String euser;
	@Column(name = "PSTATE", length = 2, nullable = false)
	private String pstate;
	@Column(name = "DUSERNAME", length = 32)
	private String dusername;
	@Column(name = "PCONTENT", length = 128)
	private String pcontent;
	@Column(name = "DTIME", length = 14)
	private String dtime;
	@Column(name = "DUSER", length = 32)
	private String duser;
	@Column(name = "NAME", length = 32, nullable = false)
	private String name;
	@Column(name = "RESTARTTYPE", length = 2, nullable = false)
	private String restarttype;
	@Column(name = "IMGID", length = 32)
	private String imgid;
	@Column(name = "SSORTTYPE", length = 2, nullable = false)
	private String ssorttype;
	@Column(name = "OSORTTYPE", length = 2, nullable = false)
	private String osorttype;
	@Column(name = "PSHOWTYPE", length = 2, nullable = false)
	private String pshowtype;
	
	public String getSsorttype() {
		return ssorttype;
	}

	public void setSsorttype(String ssorttype) {
		this.ssorttype = ssorttype;
	}

	public String getOsorttype() {
		return osorttype;
	}

	public void setOsorttype(String osorttype) {
		this.osorttype = osorttype;
	}

	public String getPshowtype() {
		return pshowtype;
	}

	public void setPshowtype(String pshowtype) {
		this.pshowtype = pshowtype;
	}

	public String getRestarttype() {
		return restarttype;
	}
	
	public String getImgid() {
		return imgid;
	}

	public void setImgid(String imgid) {
		this.imgid = imgid;
	}

	public void setRestarttype(String restarttype) {
		this.restarttype = restarttype;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCounttype() {
		return this.counttype;
	}

	public void setCounttype(String counttype) {
		this.counttype = counttype;
	}

	public String getRoomnote() {
		return this.roomnote;
	}

	public void setRoomnote(String roomnote) {
		this.roomnote = roomnote;
	}

	public Integer getTimelen() {
		return this.timelen;
	}

	public void setTimelen(Integer timelen) {
		this.timelen = timelen;
	}

	public String getWritetype() {
		return this.writetype;
	}

	public void setWritetype(String writetype) {
		this.writetype = writetype;
	}

	public String getStarttime() {
		return this.starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return this.endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getTimetype() {
		return this.timetype;
	}

	public void setTimetype(String timetype) {
		this.timetype = timetype;
	}

	public String getExamtypeid() {
		return this.examtypeid;
	}

	public void setExamtypeid(String examtypeid) {
		this.examtypeid = examtypeid;
	}

	public String getCuser() {
		return this.cuser;
	}

	public void setCuser(String cuser) {
		this.cuser = cuser;
	}

	public String getCusername() {
		return this.cusername;
	}

	public void setCusername(String cusername) {
		this.cusername = cusername;
	}

	public String getEtime() {
		return this.etime;
	}

	public void setEtime(String etime) {
		this.etime = etime;
	}

	public String getCtime() {
		return this.ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public String getEusername() {
		return this.eusername;
	}

	public void setEusername(String eusername) {
		this.eusername = eusername;
	}

	public String getEuser() {
		return this.euser;
	}

	public void setEuser(String euser) {
		this.euser = euser;
	}

	public String getPstate() {
		return this.pstate;
	}

	public void setPstate(String pstate) {
		this.pstate = pstate;
	}

	public String getDusername() {
		return this.dusername;
	}

	public void setDusername(String dusername) {
		this.dusername = dusername;
	}

	public String getPcontent() {
		return this.pcontent;
	}

	public void setPcontent(String pcontent) {
		this.pcontent = pcontent;
	}

	public String getDtime() {
		return this.dtime;
	}

	public void setDtime(String dtime) {
		this.dtime = dtime;
	}

	public String getDuser() {
		return this.duser;
	}

	public void setDuser(String duser) {
		this.duser = duser;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
}