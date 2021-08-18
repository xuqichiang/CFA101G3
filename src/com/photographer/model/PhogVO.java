package com.photographer.model;

import java.io.Serializable;

public class PhogVO implements Serializable {
	
	//攝影師Table
	private Integer phog_id;
	private String phog_name;//攝影師名
	private Integer phog_status;//狀態
	private Integer phog_smem_id;//婚攝商家會員
	public PhogVO() {
		
	}
	public PhogVO(Integer phog_id, String phog_name, Integer phog_status, Integer phog_smem_id) {
		super();
		this.phog_id = phog_id;
		this.phog_name = phog_name;
		this.phog_status = phog_status;
		this.phog_smem_id = phog_smem_id;
	}
	public Integer getPhog_id() {
		return phog_id;
	}
	public void setPhog_id(Integer phog_id) {
		this.phog_id = phog_id;
	}
	public String getPhog_name() {
		return phog_name;
	}
	public void setPhog_name(String phog_name) {
		this.phog_name = phog_name;
	}
	public Integer getPhog_status() {
		return phog_status;
	}
	public void setPhog_status(Integer phog_status) {
		this.phog_status = phog_status;
	}
	public Integer getPhog_smem_id() {
		return phog_smem_id;
	}
	public void setPhog_smem_id(Integer phog_smem_id) {
		this.phog_smem_id = phog_smem_id;
	}
	@Override
	public String toString() {
		return "PhotographerVO [phog_id=" + phog_id + ", phog_name=" + phog_name + ", phog_status=" + phog_status
				+ ", phog_smem_id=" + phog_smem_id + "]";
	}
}