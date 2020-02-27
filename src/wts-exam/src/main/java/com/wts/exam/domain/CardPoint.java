package com.wts.exam.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/* *
 *功能：用户答题类
 *详细：
 *
 *版本：v2.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Entity(name = "CardPoint")
@Table(name = "wts_card_point")
public class CardPoint implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        @GenericGenerator(name = "systemUUID", strategy = "uuid")
        @GeneratedValue(generator = "systemUUID")
        @Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
        private String id;
        
        //是否已经是最终得分
        @Column(name = "COMPLETE", length = 1, nullable = false)
        private String complete;
        @Column(name = "VERSIONID", length = 32, nullable = false)
        private String versionid;
        @Column(name = "CARDID", length = 32, nullable = false)
        private String cardid;
        @Column(name = "POINT", length = 10)
        private Integer point;

        public String  getComplete() {
          return this.complete;
        }
        public void setComplete(String complete) {
          this.complete = complete;
        }
        public String  getVersionid() {
          return this.versionid;
        }
        public void setVersionid(String versionid) {
          this.versionid = versionid;
        }
        public String  getCardid() {
          return this.cardid;
        }
        public void setCardid(String cardid) {
          this.cardid = cardid;
        }
        public Integer  getPoint() {
          return this.point;
        }
        public void setPoint(Integer point) {
          this.point = point;
        }
        public String  getId() {
          return this.id;
        }
        public void setId(String id) {
          this.id = id;
        }
}