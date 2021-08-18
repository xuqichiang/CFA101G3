package com.workphoto.model;

import java.io.Serializable;
import java.util.Arrays;

public class WorVO  implements Serializable{
	
	//婚攝作品集
	private Integer wor_id;
	private String wor_name;//作品名稱
	private Integer wor_phog_id;
	private String phog_name;//攝影師
	private Integer phog_status;//攝影師狀態
	private String mem_shop_name;//婚攝店家名稱
	private byte[] wor_logo;//封面圖片
	public Integer getWor_id() {
		return wor_id;
	}
	public void setWor_id(Integer wor_id) {
		this.wor_id = wor_id;
	}
	public String getWor_name() {
		return wor_name;
	}
	public void setWor_name(String wor_name) {
		this.wor_name = wor_name;
	}
	public Integer getWor_phog_id() {
		return wor_phog_id;
	}
	public void setWor_phog_id(Integer wor_phog_id) {
		this.wor_phog_id = wor_phog_id;
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
	public String getMem_shop_name() {
		return mem_shop_name;
	}
	public void setMem_shop_name(String mem_shop_name) {
		this.mem_shop_name = mem_shop_name;
	}
	public byte[] getWor_logo() {
		return wor_logo;
	}
	public void setWor_logo(byte[] wor_logo) {
		this.wor_logo = wor_logo;
	}
	@Override
	public String toString() {
		return "WorVO [wor_id=" + wor_id + ", wor_name=" + wor_name + ", wor_phog_id=" + wor_phog_id + ", phog_name="
				+ phog_name + ", phog_status=" + phog_status + ", mem_shop_name=" + mem_shop_name + ", wor_logo="
				+ Arrays.toString(wor_logo) + "]";
	}
	
}
