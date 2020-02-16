package com.farm.authority.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/* *
 *功能：外部账户类
 *详细：
 *
 *版本：v2.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Entity(name = "OutUser")
@Table(name = "alone_auth_outuser")
public class Outuser implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        @GenericGenerator(name = "systemUUID", strategy = "uuid")
        @GeneratedValue(generator = "systemUUID")
        @Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
        private String id;
        @Column(name = "ACCOUNTNAME", length = 64, nullable = false)
        private String accountname;
        @Column(name = "ACCOUNTID", length = 64, nullable = false)
        private String accountid;
        @Column(name = "USERID", length = 32)
        private String userid;
        @Column(name = "PCONTENT", length = 128)
        private String pcontent;
        @Column(name = "PSTATE", length = 2, nullable = false)
        private String pstate;
        @Column(name = "CTIME", length = 16, nullable = false)
        private String ctime;

        public String  getAccountname() {
          return this.accountname;
        }
        public void setAccountname(String accountname) {
          this.accountname = accountname;
        }
        public String  getAccountid() {
          return this.accountid;
        }
        public void setAccountid(String accountid) {
          this.accountid = accountid;
        }
        public String  getUserid() {
          return this.userid;
        }
        public void setUserid(String userid) {
          this.userid = userid;
        }
        public String  getPcontent() {
          return this.pcontent;
        }
        public void setPcontent(String pcontent) {
          this.pcontent = pcontent;
        }
        public String  getPstate() {
          return this.pstate;
        }
        public void setPstate(String pstate) {
          this.pstate = pstate;
        }
        public String  getCtime() {
          return this.ctime;
        }
        public void setCtime(String ctime) {
          this.ctime = ctime;
        }
        public String  getId() {
          return this.id;
        }
        public void setId(String id) {
          this.id = id;
        }
}