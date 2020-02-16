package com.wts.exam.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/* *
 *功能：问卷答案类
 *详细：
 *
 *版本：v2.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Entity(name = "CardAnswer")
@Table(name = "wts_card_answer")
public class CardAnswer implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
	@Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
	private String id;
	@Column(name = "PSTATE", length = 2, nullable = false)
	private String pstate;
	@Column(name = "PCONTENT", length = 256)
	private String pcontent;
	@Column(name = "CTIME", length = 16, nullable = false)
	private String ctime;
	@Column(name = "VALSTR", length = 65535, nullable = false)
	private String valstr;
	@Column(name = "CUSER", length = 32, nullable = false)
	private String cuser;
	@Column(name = "VERSIONID", length = 32, nullable = false)
	private String versionid;
	@Column(name = "ANSWERID", length = 32)
	private String answerid;
	@Column(name = "CARDID", length = 32, nullable = false)
	private String cardid;

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

	public String getCtime() {
		return this.ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public String getValstr() {
		return this.valstr;
	}

	public void setValstr(String valstr) {
		this.valstr = valstr;
	}

	public String getCuser() {
		return this.cuser;
	}

	public void setCuser(String cuser) {
		this.cuser = cuser;
	}

	public String getVersionid() {
		return this.versionid;
	}

	public void setVersionid(String versionid) {
		this.versionid = versionid;
	}

	public String getAnswerid() {
		return this.answerid;
	}

	public void setAnswerid(String answerid) {
		this.answerid = answerid;
	}

	public String getCardid() {
		return this.cardid;
	}

	public void setCardid(String cardid) {
		this.cardid = cardid;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
}