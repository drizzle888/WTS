package com.farm.authority.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;



/* *
 *功能：组织机构类
 *详细：
 *
 *版本：v0.1
 *作者：Farm代码工程自动生成
 *日期：20141122211253
 *说明：
 */
@Entity(name = "Organization")
@Table(name = "ALONE_AUTH_ORGANIZATION")
public class Organization implements java.io.Serializable {
        private static final long serialVersionUID = 1L;
        
        @Column(name = "TYPE", length = 1, nullable = false)
        private String type;
        @Column(name = "SORT", nullable = false)
        private int sort;
        @Column(name = "PARENTID", length = 32, nullable = false)
        private String parentid;
        @Column(name = "MUSER", length = 32, nullable = false)
        private String muser;
        @Column(name = "CUSER", length = 32, nullable = false)
        private String cuser;
        @Column(name = "STATE", length = 1, nullable = false)
        private String state;
        @Column(name = "UTIME", length = 16, nullable = false)
        private String utime;
        @Column(name = "CTIME", length = 61, nullable = false)
        private String ctime;
        @Column(name = "COMMENTS", length = 128)
        private String comments;
        @Column(name = "NAME", length = 64, nullable = false)
        private String name;
        @Column(name = "TREECODE", length = 256, nullable = false)
        private String treecode;
        @Id
    	@GenericGenerator(name = "systemUUID", strategy = "uuid")
    	@GeneratedValue(generator = "systemUUID")
    	@Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
        private String id;
        @Column(name = "APPID", length = 32)
        private String appid;
        
        public String getAppid() {
			return appid;
		}
		public void setAppid(String appid) {
			this.appid = appid;
		}
		public String  getType() {
          return this.type;
        }
        public void setType(String type) {
          this.type = type;
        }
        public int  getSort() {
          return this.sort;
        }
        public void setSort(int sort) {
          this.sort = sort;
        }
        public String  getParentid() {
          return this.parentid;
        }
        public void setParentid(String parentid) {
          this.parentid = parentid;
        }
        public String  getMuser() {
          return this.muser;
        }
        public void setMuser(String muser) {
          this.muser = muser;
        }
        public String  getCuser() {
          return this.cuser;
        }
        public void setCuser(String cuser) {
          this.cuser = cuser;
        }
        public String  getState() {
          return this.state;
        }
        public void setState(String state) {
          this.state = state;
        }
        public String  getUtime() {
          return this.utime;
        }
        public void setUtime(String utime) {
          this.utime = utime;
        }
        public String  getCtime() {
          return this.ctime;
        }
        public void setCtime(String ctime) {
          this.ctime = ctime;
        }
        public String  getComments() {
          return this.comments;
        }
        public void setComments(String comments) {
          this.comments = comments;
        }
        public String  getName() {
          return this.name;
        }
        public void setName(String name) {
          this.name = name;
        }
        public String  getTreecode() {
          return this.treecode;
        }
        public void setTreecode(String treecode) {
          this.treecode = treecode;
        }
        public String  getId() {
          return this.id;
        }
        public void setId(String id) {
          this.id = id;
        }
}