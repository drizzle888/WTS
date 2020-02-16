package com.farm.parameter.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * AloneDictionaryType entity. @author MyEclipse Persistence Tools
 */
@Entity(name = "AloneDictionaryType")
@Table(name = "alone_dictionary_type")
public class AloneDictionaryType implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 4237493054556172783L;
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
	@Column(name = "STATE", length = 1, nullable = false)
	private String state;
	@Column(name = "NAME", length = 128, nullable = false)
	private String name;
	@Column(name = "COMMENTS", length = 128)
	private String comments;
	@Column(name = "ENTITYTYPE", length = 128, nullable = false)
	private String entitytype;
	@Column(name = "ENTITY", length = 32, nullable = false)
	private String entity;
	@Column(name = "SORT", length = 11, nullable = false)
	private BigDecimal sort;
	@Column(name = "PARENTID", length = 32)
	private String parentid;
	@Column(name = "TREECODE", length = 256)
	private String treecode;

	// Constructors

	/** default constructor */
	public AloneDictionaryType() {
	}

	/** minimal constructor */
	public AloneDictionaryType(String ctime, String utime, String cuser,
			String muser, String state, String name, String entitytype,
			String entity, BigDecimal sort) {
		this.ctime = ctime;
		this.utime = utime;
		this.cuser = cuser;
		this.muser = muser;
		this.state = state;
		this.name = name;
		this.entitytype = entitytype;
		this.entity = entity;
		this.sort = sort;
	}

	/** full constructor */
	public AloneDictionaryType(String ctime, String utime, String cuser,
			String muser, String state, String name, String comments,
			String entitytype, String entity, BigDecimal sort) {
		this.ctime = ctime;
		this.utime = utime;
		this.cuser = cuser;
		this.muser = muser;
		this.state = state;
		this.name = name;
		this.comments = comments;
		this.entitytype = entitytype;
		this.entity = entity;
		this.sort = sort;
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

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getEntitytype() {
		return this.entitytype;
	}

	public void setEntitytype(String entitytype) {
		this.entitytype = entitytype;
	}

	public String getEntity() {
		return this.entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public BigDecimal getSort() {
		return this.sort;
	}

	public void setSort(BigDecimal sort) {
		this.sort = sort;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getTreecode() {
		return treecode;
	}

	public void setTreecode(String treecode) {
		this.treecode = treecode;
	}
}