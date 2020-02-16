package com.farm.doc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/* *
 *功能：消息模板类
 *详细：
 *
 *版本：v2.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Entity(name = "MessageModel")
@Table(name = "farm_message_model")
public class Messagemodel implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        @GenericGenerator(name = "systemUUID", strategy = "uuid")
        @GeneratedValue(generator = "systemUUID")
        @Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
        private String id;
        @Column(name = "CONTENTMODEL", length = 512, nullable = false)
        private String contentmodel;
        @Column(name = "TYPEKEY", length = 128, nullable = false)
        private String typekey;
        @Column(name = "OVERER", length = 128, nullable = false)
        private String overer;
        @Column(name = "TITLEMODEL", length = 512, nullable = false)
        private String titlemodel;
        @Column(name = "PCONTENT", length = 128)
        private String pcontent;
        @Column(name = "TITLE", length = 512, nullable = false)
        private String title;
        @Column(name = "PSTATE", length = 2, nullable = false)
        private String pstate;
        @Column(name = "EUSER", length = 32, nullable = false)
        private String euser;
        @Column(name = "EUSERNAME", length = 64, nullable = false)
        private String eusername;
        @Column(name = "CUSER", length = 32, nullable = false)
        private String cuser;
        @Column(name = "CUSERNAME", length = 64, nullable = false)
        private String cusername;
        @Column(name = "CTIME", length = 16, nullable = false)
        private String ctime;
        @Column(name = "ETIME", length = 16, nullable = false)
        private String etime;

        public String  getContentmodel() {
          return this.contentmodel;
        }
        public void setContentmodel(String contentmodel) {
          this.contentmodel = contentmodel;
        }
        public String  getTypekey() {
          return this.typekey;
        }
        public void setTypekey(String typekey) {
          this.typekey = typekey;
        }
        public String  getOverer() {
          return this.overer;
        }
        public void setOverer(String overer) {
          this.overer = overer;
        }
        public String  getTitlemodel() {
          return this.titlemodel;
        }
        public void setTitlemodel(String titlemodel) {
          this.titlemodel = titlemodel;
        }
        public String  getPcontent() {
          return this.pcontent;
        }
        public void setPcontent(String pcontent) {
          this.pcontent = pcontent;
        }
        public String  getTitle() {
          return this.title;
        }
        public void setTitle(String title) {
          this.title = title;
        }
        public String  getPstate() {
          return this.pstate;
        }
        public void setPstate(String pstate) {
          this.pstate = pstate;
        }
        public String  getEuser() {
          return this.euser;
        }
        public void setEuser(String euser) {
          this.euser = euser;
        }
        public String  getEusername() {
          return this.eusername;
        }
        public void setEusername(String eusername) {
          this.eusername = eusername;
        }
        public String  getCuser() {
          return this.cuser;
        }
        public void setCuser(String cuser) {
          this.cuser = cuser;
        }
        public String  getCusername() {
          return this.cusername;
        }
        public void setCusername(String cusername) {
          this.cusername = cusername;
        }
        public String  getCtime() {
          return this.ctime;
        }
        public void setCtime(String ctime) {
          this.ctime = ctime;
        }
        public String  getEtime() {
          return this.etime;
        }
        public void setEtime(String etime) {
          this.etime = etime;
        }
        public String  getId() {
          return this.id;
        }
        public void setId(String id) {
          this.id = id;
        }
}