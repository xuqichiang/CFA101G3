package com.photocategory.model;

import java.io.Serializable;

public class PhocVO implements Serializable{
	
	private Integer phoc_id;//婚攝方案類別
	private String phoc_name;//婚攝方案名稱
	
	public Integer getPhoc_id() {
		return phoc_id;
	}
	public void setPhoc_id(Integer phoc_id) {
		this.phoc_id = phoc_id;
	}
	public String getPhoc_name() {
		return phoc_name;
	}
	public void setPhoc_name(String phoc_name) {
		this.phoc_name = phoc_name;
	}
	@Override
	public String toString() {
		return "PhocVO [phoc_id=" + phoc_id + ", phoc_name=" + phoc_name + "]";
	}
}	
	