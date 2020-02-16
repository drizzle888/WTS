package com.farm.doc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/* *
 *功能：消息抄送人类
 *详细：
 *
 *版本：v2.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Entity(name = "MessageSender")
@Table(name = "farm_message_sender")
public class Messagesender implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        @GenericGenerator(name = "systemUUID", strategy = "uuid")
        @GeneratedValue(generator = "systemUUID")
        @Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
        private String id;
        @Column(name = "MODELID", length = 32, nullable = false)
        private String modelid;
        @Column(name = "APPID", length = 32, nullable = false)
        private String appid;
        @Column(name = "TYPE", length = 1, nullable = false)
        private String type;

        public String  getModelid() {
          return this.modelid;
        }
        public void setModelid(String modelid) {
          this.modelid = modelid;
        }
        public String  getAppid() {
          return this.appid;
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
        public String  getId() {
          return this.id;
        }
        public void setId(String id) {
          this.id = id;
        }
}