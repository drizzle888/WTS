package com.wts.exam.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/* *
 *功能：考试试卷类
 *详细：
 *
 *版本：v2.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Entity(name = "RoomPaper")
@Table(name = "wts_room_paper")
public class RoomPaper implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        @GenericGenerator(name = "systemUUID", strategy = "uuid")
        @GeneratedValue(generator = "systemUUID")
        @Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
        private String id;
        @Column(name = "PAPERID", length = 32, nullable = false)
        private String paperid;
        @Column(name = "ROOMID", length = 32, nullable = false)
        private String roomid;
        @Column(name = "NAME", length = 512)
        private String name;
        
        public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String  getPaperid() {
          return this.paperid;
        }
        public void setPaperid(String paperid) {
          this.paperid = paperid;
        }
        public String  getRoomid() {
          return this.roomid;
        }
        public void setRoomid(String roomid) {
          this.roomid = roomid;
        }
        public String  getId() {
          return this.id;
        }
        public void setId(String id) {
          this.id = id;
        }
}