package com.wts.exam.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/* *
 *功能：参考人类
 *详细：
 *
 *版本：v2.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Entity(name = "RoomUser")
@Table(name = "wts_room_user")
public class RoomUser implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        @GenericGenerator(name = "systemUUID", strategy = "uuid")
        @GeneratedValue(generator = "systemUUID")
        @Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
        private String id;
        @Column(name = "USERID", length = 32, nullable = false)
        private String userid;
        @Column(name = "ROOMID", length = 32, nullable = false)
        private String roomid;

        public String  getId() {
          return this.id;
        }
        public void setId(String id) {
          this.id = id;
        }
        public String  getUserid() {
          return this.userid;
        }
        public void setUserid(String userid) {
          this.userid = userid;
        }
        public String  getRoomid() {
          return this.roomid;
        }
        public void setRoomid(String roomid) {
          this.roomid = roomid;
        }
}