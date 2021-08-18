package com.photoprogram.model;

import java.io.Serializable;
import java.sql.Date;

public class PhopVO implements Serializable{
	
	//婚攝方案
	
	private Integer phop_id;
	private Integer phop_phoc_id;//婚攝方案類別
	private Integer phop_smem_id;//婚攝商家會員
	private String phop_name;//名稱
	private String phop_content;//內容
	private Date phop_start_time;//開始時間
	private Date phop_end_time;//結束時間
	private Integer phop_status;//狀態
	private Integer phop_price;//金額
	
	
	public Integer getPhop_id() {
		return phop_id;
	}

	public void setPhop_id(Integer phop_id) {
		this.phop_id = phop_id;
	}

	public Integer getPhop_phoc_id() {
		return phop_phoc_id;
	}

	public void setPhop_phoc_id(Integer phop_phoc_id) {
		this.phop_phoc_id = phop_phoc_id;
	}

	public Integer getPhop_smem_id() {
		return phop_smem_id;
	}

	public void setPhop_smem_id(Integer phop_smem_id) {
		this.phop_smem_id = phop_smem_id;
	}

	public String getPhop_name() {
		return phop_name;
	}

	public void setPhop_name(String phop_name) {
		this.phop_name = phop_name;
	}

	public String getPhop_content() {
		return phop_content;
	}

	public void setPhop_content(String phop_content) {
		this.phop_content = phop_content;
	}

	public Date getPhop_start_time() {
		return phop_start_time;
	}

	public void setPhop_start_time(Date phop_start_time) {
		this.phop_start_time = phop_start_time;
	}

	public Date getPhop_end_time() {
		return phop_end_time;
	}

	public void setPhop_end_time(Date phop_end_time) {
		this.phop_end_time = phop_end_time;
	}

	public Integer getPhop_status() {
		return phop_status;
	}

	public void setPhop_status(Integer phop_status) {
		this.phop_status = phop_status;
	}

	public Integer getPhop_price() {
		return phop_price;
	}

	public void setPhop_price(Integer phop_price) {
		this.phop_price = phop_price;
	}

	@Override
	public String toString() {
		return "PhopVO [phop_id=" + phop_id + ", phop_phoc_id=" + phop_phoc_id + ", phop_smem_id=" + phop_smem_id
				+ ", phop_name=" + phop_name + ", phop_content=" + phop_content + ", phop_start_time=" + phop_start_time
				+ ", phop_end_time=" + phop_end_time + ", phop_status=" + phop_status + ", phop_price=" + phop_price
				+ "]";
	}

}
