package com.farm.authority.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/* *
 *功能：业务权限类
 *详细：
 *
 *版本：v2.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Entity(name = "Pop")
@Table(name = "alone_auth_pop")
public class Pop implements java.io.Serializable {
        private static final long serialVersionUID = 1L;
        
        @Id
        @GenericGenerator(name = "systemUUID", strategy = "uuid")
        @GeneratedValue(generator = "systemUUID")
        @Column(name = "ID", length = 32, nullable = false)
        private String id;
        @Column(name = "PCONTENT", length = 128)
        private String pcontent;
        @Column(name = "CUSERNAME", length = 64, nullable = false)
        private String cusername;
        @Column(name = "PSTATE", length = 2, nullable = false)
        private String pstate;
        @Column(name = "CUSER", length = 32, nullable = false)
        private String cuser;
        @Column(name = "CTIME", length = 16, nullable = false)
        private String ctime;
        @Column(name = "POPTYPE", length = 1, nullable = false)
        private String poptype;
        @Column(name = "OID", length = 32, nullable = false)
        private String oid;
        @Column(name = "ONAME", length = 128, nullable = false)
        private String oname;
        @Column(name = "TARGETTYPE", length = 1, nullable = false)
        private String targettype;
        @Column(name = "TARGETID", length = 32, nullable = false)
        private String targetid;
        @Column(name = "TARGETNAME", length = 128)
        private String targetname;

        public String  getPcontent() {
          return this.pcontent;
        }
        public void setPcontent(String pcontent) {
          this.pcontent = pcontent;
        }
        public String  getCusername() {
          return this.cusername;
        }
        public void setCusername(String cusername) {
          this.cusername = cusername;
        }
        public String  getPstate() {
          return this.pstate;
        }
        public void setPstate(String pstate) {
          this.pstate = pstate;
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
        public String  getPoptype() {
          return this.poptype;
        }
        public void setPoptype(String poptype) {
          this.poptype = poptype;
        }
        public String  getOid() {
          return this.oid;
        }
        public void setOid(String oid) {
          this.oid = oid;
        }
        public String  getOname() {
          return this.oname;
        }
        public void setOname(String oname) {
          this.oname = oname;
        }
        public String  getTargettype() {
          return this.targettype;
        }
        public void setTargettype(String targettype) {
          this.targettype = targettype;
        }
        public String  getTargetid() {
          return this.targetid;
        }
        public void setTargetid(String targetid) {
          this.targetid = targetid;
        }
        public String  getTargetname() {
          return this.targetname;
        }
        public void setTargetname(String targetname) {
          this.targetname = targetname;
        }
        public String  getId() {
          return this.id;
        }
        public void setId(String id) {
          this.id = id;
        }
}