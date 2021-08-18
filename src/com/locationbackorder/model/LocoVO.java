package com.locationbackorder.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class LocoVO implements Serializable{

		private Integer loco_id;
		private Integer loco_smem_id;
		private Integer loco_bmem_id;
		private Integer loco_locr_id;
		private Integer loco_locp_id;
		private Integer loco_totalprice;
		private Integer loco_deposit;
		private Integer loco_paytype;
		private Integer loco_order_status;
		private Integer loco_pay_status;
		private Timestamp loco_order_time;
		private Timestamp loco_reserve_time;
		private Integer loco_table_num;
		private String loco_note;
		
		public Integer getLoco_id() {
			return loco_id;
		}
		public void setLoco_id(Integer loco_id) {
			this.loco_id = loco_id;
		}
		public Integer getLoco_smem_id() {
			return loco_smem_id;
		}
		public void setLoco_smem_id(Integer loco_smem_id) {
			this.loco_smem_id = loco_smem_id;
		}
		public Integer getLoco_bmem_id() {
			return loco_bmem_id;
		}
		public void setLoco_bmem_id(Integer loco_bmem_id) {
			this.loco_bmem_id = loco_bmem_id;
		}
		public Integer getLoco_locr_id() {
			return loco_locr_id;
		}
		public void setLoco_locr_id(Integer loco_locr_id) {
			this.loco_locr_id = loco_locr_id;
		}
		public Integer getLoco_locp_id() {
			return loco_locp_id;
		}
		public void setLoco_locp_id(Integer loco_locp_id) {
			this.loco_locp_id = loco_locp_id;
		}
		public Integer getLoco_totalprice() {
			return loco_totalprice;
		}
		public void setLoco_totalprice(Integer loco_totalprice) {
			this.loco_totalprice = loco_totalprice;
		}
		public Integer getLoco_deposit() {
			return loco_deposit;
		}
		public void setLoco_deposit(Integer loco_deposit) {
			this.loco_deposit = loco_deposit;
		}
		public Integer getLoco_paytype() {
			return loco_paytype;
		}
		public void setLoco_paytype(Integer loco_paytype) {
			this.loco_paytype = loco_paytype;
		}
		public Integer getLoco_order_status() {
			return loco_order_status;
		}
		public void setLoco_order_status(Integer loco_order_status) {
			this.loco_order_status = loco_order_status;
		}
		public Integer getLoco_pay_status() {
			return loco_pay_status;
		}
		public void setLoco_pay_status(Integer loco_pay_status) {
			this.loco_pay_status = loco_pay_status;
		}
		public Timestamp getLoco_order_time() {
			return loco_order_time;
		}
		public void setLoco_order_time(Timestamp loco_order_time) {
			this.loco_order_time = loco_order_time;
		}
		public Timestamp getLoco_reserve_time() {
			return loco_reserve_time;
		}
		public void setLoco_reserve_time(Timestamp loco_reserve_time) {
			this.loco_reserve_time = loco_reserve_time;
		}
		public Integer getLoco_table_num() {
			return loco_table_num;
		}
		public void setLoco_table_num(Integer loco_table_num) {
			this.loco_table_num = loco_table_num;
		}
		public String getLoco_note() {
			return loco_note;
		}
		public void setLoco_note(String loco_note) {
			this.loco_note = loco_note;
		}
		@Override
		public String toString() {
			return "LocoVO [loco_id=" + loco_id + ", loco_smem_id=" + loco_smem_id + ", loco_bmem_id=" + loco_bmem_id
					+ ", loco_locr_id=" + loco_locr_id + ", loco_locp_id=" + loco_locp_id + ", loco_totalprice="
					+ loco_totalprice + ", loco_deposit=" + loco_deposit + ", loco_paytype=" + loco_paytype
					+ ", loco_order_status=" + loco_order_status + ", loco_pay_status=" + loco_pay_status
					+ ", loco_order_time=" + loco_order_time + ", loco_reserve_time=" + loco_reserve_time
					+ ", loco_table_num=" + loco_table_num + ", loco_note=" + loco_note + "]";
		}
		
		
		
		
}
