package com.farm.doc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/* *
 *功能：附件文本内容类
 *详细：
 *
 *版本：v2.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Entity(name = "DocFileText")
@Table(name = "farm_docfile_text")
public class Docfiletext implements java.io.Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GenericGenerator(name = "systemUUID", strategy = "uuid")
	@GeneratedValue(generator = "systemUUID")
	@Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
	private String id;
	@Column(name = "DOCID", length = 32, nullable = false)
	private String docid;
	@Column(name = "DESCRIBESMIN", length = 128, nullable = false)
	private String describesmin;
	@Column(name = "FILEID", length = 32, nullable = false)
	private String fileid;
	@Column(name = "DESCRIBES", length = 65535, nullable = false)
	private String describes;

	public String getDocid() {
		return this.docid;
	}

	public void setDocid(String docid) {
		this.docid = docid;
	}

	public String getDescribesmin() {
		return this.describesmin;
	}

	public void setDescribesmin(String describesmin) {
		this.describesmin = describesmin;
	}

	public String getFileid() {
		return this.fileid;
	}

	public void setFileid(String fileid) {
		this.fileid = fileid;
	}

	public String getDescribes() {
		return this.describes;
	}

	public void setDescribes(String describes) {
		this.describes = describes;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}
}