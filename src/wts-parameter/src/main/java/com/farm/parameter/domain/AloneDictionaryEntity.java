package com.farm.parameter.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * AloneDictionaryEntity entity. @author MyEclipse Persistence Tools
 */
@Entity(name = "AloneDictionaryEntity")
@Table(name = "alone_dictionary_entity")
public class AloneDictionaryEntity implements java.io.Serializable {

	private static final long serialVersionUID = 3910748672120707433L;
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
	@Column(name = "ENTITYINDEX", length = 128, nullable = false)
	private String entityindex;
	@Column(name = "COMMENTS", length = 128)
	private String comments;
	@Column(name = "TYPE", length = 1, nullable = false)
	private String type;

	// Constructors

	/** default constructor */
	public AloneDictionaryEntity() {
	}

	/** minimal constructor */
	public AloneDictionaryEntity(String ctime, String utime, String cuser,
			String muser, String state, String name, String entityindex) {
		this.ctime = ctime;
		this.utime = utime;
		this.cuser = cuser;
		this.muser = muser;
		this.state = state;
		this.name = name;
		this.entityindex = entityindex;
	}

	/** full constructor */
	public AloneDictionaryEntity(String ctime, String utime, String cuser,
			String muser, String state, String name, String entityindex,
			String comments) {
		this.ctime = ctime;
		this.utime = utime;
		this.cuser = cuser;
		this.muser = muser;
		this.state = state;
		this.name = name;
		this.entityindex = entityindex;
		this.comments = comments;
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

	public String getEntityindex() {
		return this.entityindex;
	}

	public void setEntityindex(String entityindex) {
		this.entityindex = entityindex;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}