package com.photoorder.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class PhooVO implements Serializable{
	//婚紗攝影訂單table
	private Integer phoo_id;//訂單ID
	private Integer phoo_bmem_id;//買家ID
	private Integer phoo_smem_id;//店家ID
	private Integer phoo_phog_id;//攝影師ID
	private Integer phoo_phop_id;//方案ID
	private String phoo_place;//拍攝地點
	private Integer phoo_deposit;//訂金
	private Integer phoo_totalprice;//總金額
	private Integer phoo_paytype;//付款方式
	private Integer phoo_order_status;//訂單狀態
	private Integer phoo_pay_status;//付款狀態
	private Timestamp phoo_order_time;//訂單產生日
	private Date phoo_reserve_time;//預約時間
	private String phoo_note;//備註
	
	public Integer getPhoo_id() {
		return phoo_id;
	}
	public void setPhoo_id(Integer phoo_id) {
		this.phoo_id = phoo_id;
	}
	public Integer getPhoo_bmem_id() {
		return phoo_bmem_id;
	}
	public void setPhoo_bmem_id(Integer phoo_bmem_id) {
		this.phoo_bmem_id = phoo_bmem_id;
	}
	public Integer getPhoo_smem_id() {
		return phoo_smem_id;
	}
	public void setPhoo_smem_id(Integer phoo_smem_id) {
		this.phoo_smem_id = phoo_smem_id;
	}
	public Integer getPhoo_phog_id() {
		return phoo_phog_id;
	}
	public void setPhoo_phog_id(Integer phoo_phog_id) {
		this.phoo_phog_id = phoo_phog_id;
	}
	public Integer getPhoo_phop_id() {
		return phoo_phop_id;
	}
	public void setPhoo_phop_id(Integer phoo_phop_id) {
		this.phoo_phop_id = phoo_phop_id;
	}
	public String getPhoo_place() {
		return phoo_place;
	}
	public void setPhoo_place(String phoo_place) {
		this.phoo_place = phoo_place;
	}
	public Integer getPhoo_deposit() {
		return phoo_deposit;
	}
	public void setPhoo_deposit(Integer phoo_deposit) {
		this.phoo_deposit = phoo_deposit;
	}
	public Integer getPhoo_totalprice() {
		return phoo_totalprice;
	}
	public void setPhoo_totalprice(Integer phoo_totalprice) {
		this.phoo_totalprice = phoo_totalprice;
	}
	public Integer getPhoo_paytype() {
		return phoo_paytype;
	}
	public void setPhoo_paytype(Integer phoo_paytype) {
		this.phoo_paytype = phoo_paytype;
	}
	public Integer getPhoo_order_status() {
		return phoo_order_status;
	}
	public void setPhoo_order_status(Integer phoo_order_status) {
		this.phoo_order_status = phoo_order_status;
	}
	public Integer getPhoo_pay_status() {
		return phoo_pay_status;
	}
	public void setPhoo_pay_status(Integer phoo_pay_status) {
		this.phoo_pay_status = phoo_pay_status;
	}
	
	public Timestamp getPhoo_order_time() {
		return phoo_order_time;
	}
	public void setPhoo_order_time(Timestamp phoo_order_time) {
		this.phoo_order_time = phoo_order_time;
	}
	public Date getPhoo_reserve_time() {
		return phoo_reserve_time;
	}
	public void setPhoo_reserve_time(Date phoo_reserve_time) {
		this.phoo_reserve_time = phoo_reserve_time;
	}
	public String getPhoo_note() {
		return phoo_note;
	}
	public void setPhoo_note(String phoo_note) {
		this.phoo_note = phoo_note;
	}
}
