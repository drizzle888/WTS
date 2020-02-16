package com.farm.parameter.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * AloneParameter entity. @author MyEclipse Persistence Tools
 */
@Entity(name = "AloneParameter")
@Table(name = "alone_parameter")
public class AloneParameter implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6930414361030223374L;
	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
	@Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
	private String id;
	@Column(name = "CTIME", length = 12, nullable = false)
	private String ctime;
	@Column(name = "UTIME", length = 12, nullable = false)
	private String utime;
	@Column(name = "CUSER", length = 32, nullable = false)
	private String cuser;
	@Column(name = "MUSER", length = 32, nullable = false)
	private String muser;
	@Column(name = "NAME", length = 128, nullable = false)
	private String name;
	@Column(name = "STATE", length = 1, nullable = false)
	private String state;
	@Column(name = "PKEY", length = 64, nullable = false)
	private String pkey;
	@Column(name = "PVALUE", length = 2048, nullable = false)
	private String pvalue;
	@Column(name = "RULES", length = 256)
	private String rules;
	@Column(name = "DOMAIN", length = 64)
	private String domain;
	@Column(name = "COMMENTS", length = 256)
	private String comments;
	@Column(name = "VTYPE", length = 1, nullable = false)
	private String vtype;
	@Column(name = "USERABLE", length = 1, nullable = false)
	private String userable;

	// Constructors

	/** default constructor */
	public AloneParameter() {
	}

	/** minimal constructor */
	public AloneParameter(String ctime, String utime, String cuser,
			String muser, String name, String state, String pkey,
			String pvalue, String vtype) {
		this.ctime = ctime;
		this.utime = utime;
		this.cuser = cuser;
		this.muser = muser;
		this.name = name;
		this.state = state;
		this.pkey = pkey;
		this.pvalue = pvalue;
		this.vtype = vtype;
	}

	/** full constructor */
	public AloneParameter(String ctime, String utime, String cuser,
			String muser, String name, String state, String pkey,
			String pvalue, String rules, String domain, String comments,
			String vtype) {
		this.ctime = ctime;
		this.utime = utime;
		this.cuser = cuser;
		this.muser = muser;
		this.name = name;
		this.state = state;
		this.pkey = pkey;
		this.pvalue = pvalue;
		this.rules = rules;
		this.domain = domain;
		this.comments = comments;
		this.vtype = vtype;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCtime() {
		return this.ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public String getUtime() {
		return this.utime;
	}

	public void setUtime(String utime) {
		this.utime = utime;
	}

	public String getCuser() {
		return this.cuser;
	}

	public void setCuser(String cuser) {
		this.cuser = cuser;
	}

	public String getMuser() {
		return this.muser;
	}

	public void setMuser(String muser) {
		this.muser = muser;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPkey() {
		return this.pkey;
	}

	public void setPkey(String pkey) {
		this.pkey = pkey;
	}

	public String getPvalue() {
		return this.pvalue;
	}

	public void setPvalue(String pvalue) {
		this.pvalue = pvalue;
	}

	public String getRules() {
		return this.rules;
	}

	public void setRules(String rules) {
		this.rules = rules;
	}

	public String getDomain() {
		return this.domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getVtype() {
		return this.vtype;
	}

	public void setVtype(String vtype) {
		this.vtype = vtype;
	}

	public String getUserable() {
		return userable;
	}

	public void setUserable(String userable) {
		this.userable = userable;
	}
	
}