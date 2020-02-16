package com.farm.doc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/* *
 *功能：用户消息类
 *详细：
 *
 *版本：v2.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Entity(name = "Usermessage")
@Table(name = "farm_usermessage")
public class Usermessage implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        @GenericGenerator(name = "systemUUID", strategy = "uuid")
        @GeneratedValue(generator = "systemUUID")
        @Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
        private String id;
        @Column(name = "READUSERID", length = 32, nullable = false)
        private String readuserid;
        @Column(name = "CONTENT", length = 10000, nullable = false)
        private String content;
        @Column(name = "TITLE", length = 64, nullable = false)
        private String title;
        @Column(name = "READSTATE", length = 2, nullable = false)
        private String readstate;
        @Column(name = "PCONTENT", length = 128)
        private String pcontent;
        @Column(name = "PSTATE", length = 2, nullable = false)
        private String pstate;
        @Column(name = "CUSERNAME", length = 64)
        private String cusername;
        @Column(name = "CUSER", length = 32)
        private String cuser;
        @Column(name = "CTIME", length = 16, nullable = false)
        private String ctime;

        public String  getReaduserid() {
          return this.readuserid;
        }
        public void setReaduserid(String readuserid) {
          this.readuserid = readuserid;
        }
        public String  getContent() {
          return this.content;
        }
        public void setContent(String content) {
          this.content = content;
        }
        public String  getTitle() {
          return this.title;
        }
        public void setTitle(String title) {
          this.title = title;
        }
        public String  getReadstate() {
          return this.readstate;
        }
        public void setReadstate(String readstate) {
          this.readstate = readstate;
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
        public String  getCusername() {
          return this.cusername;
        }
        public void setCusername(String cusername) {
          this.cusername = cusername;
        }
        public String  getCuser() {
          return this.cuser;
        }
        public void setCuser(String cuser) {
          this.cuser = cuser;
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