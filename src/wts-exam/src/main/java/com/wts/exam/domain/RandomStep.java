package com.wts.exam.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/* *
 *功能：答卷随机步骤类
 *详细：
 *
 *版本：v2.1
 *作者：FarmCode代码工程
 *日期：20150707114057
 *说明：
 */
@Entity(name = "RandomStep")
@Table(name = "wts_random_step")
public class RandomStep implements java.io.Serializable {
        private static final long serialVersionUID = 1L;

        @Id
        @GenericGenerator(name = "systemUUID", strategy = "uuid")
        @GeneratedValue(generator = "systemUUID")
        @Column(name = "ID", length = 32, insertable = true, updatable = true, nullable = false)
        private String id;
        @Column(name = "ITEMID", length = 32, nullable = false)
        private String itemid;
        @Column(name = "PCONTENT", length = 128)
        private String pcontent;
        @Column(name = "NAME", length = 64, nullable = false)
        private String name;
        @Column(name = "SORT", length = 10, nullable = false)
        private Integer sort;
        @Column(name = "SUBPOINT", length = 10, nullable = false)
        private Integer subpoint;
        @Column(name = "SUBNUM", length = 10, nullable = false)
        private Integer subnum;
        @Column(name = "TIPTYPE", length = 2, nullable = false)
        private String tiptype;
        @Column(name = "TYPEID", length = 32, nullable = false)
        private String typeid;

        public String  getItemid() {
          return this.itemid;
        }
        public void setItemid(String itemid) {
          this.itemid = itemid;
        }
        public String  getPcontent() {
          return this.pcontent;
        }
        public void setPcontent(String pcontent) {
          this.pcontent = pcontent;
        }
        public String  getName() {
          return this.name;
        }
        public void setName(String name) {
          this.name = name;
        }
        public Integer  getSort() {
          return this.sort;
        }
        public void setSort(Integer sort) {
          this.sort = sort;
        }
        public Integer  getSubpoint() {
          return this.subpoint;
        }
        public void setSubpoint(Integer subpoint) {
          this.subpoint = subpoint;
        }
        public Integer  getSubnum() {
          return this.subnum;
        }
        public void setSubnum(Integer subnum) {
          this.subnum = subnum;
        }
        public String  getTiptype() {
          return this.tiptype;
        }
        public void setTiptype(String tiptype) {
          this.tiptype = tiptype;
        }
        public String  getTypeid() {
          return this.typeid;
        }
        public void setTypeid(String typeid) {
          this.typeid = typeid;
        }
        public String  getId() {
          return this.id;
        }
        public void setId(String id) {
          this.id = id;
        }
}