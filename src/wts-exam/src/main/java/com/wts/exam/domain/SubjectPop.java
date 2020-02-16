package com.wts.exam.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/* *
 *功能：题库权限类
 *详细：
 *
 *版本：v2.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Entity(name = "SubjectPop")
@Table(name = "wts_subject_pop")
public class SubjectPop implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        @GenericGenerator(name = "systemUUID", strategy = "uuid")
        @GeneratedValue(generator = "systemUUID")
        @Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
        private String id;
        @Column(name = "TYPEID", length = 32, nullable = false)
        private String typeid;
        @Column(name = "USERNAME", length = 64, nullable = false)
        private String username;
        @Column(name = "USERID", length = 32, nullable = false)
        private String userid;
        @Column(name = "FUNTYPE", length = 1, nullable = false)
        private String funtype;

        public String  getTypeid() {
          return this.typeid;
        }
        public void setTypeid(String typeid) {
          this.typeid = typeid;
        }
        public String  getUsername() {
          return this.username;
        }
        public void setUsername(String username) {
          this.username = username;
        }
        public String  getUserid() {
          return this.userid;
        }
        public void setUserid(String userid) {
          this.userid = userid;
        }
        public String  getFuntype() {
          return this.funtype;
        }
        public void setFuntype(String funtype) {
          this.funtype = funtype;
        }
        public String  getId() {
          return this.id;
        }
        public void setId(String id) {
          this.id = id;
        }
}