package com.proJOINmem.model;

import java.util.Arrays;

public class ProJoinMemVO {

	private Integer mem_id;
	private String mem_phone;
	private String mem_shop_name;
	private String mem_shop_content;
	private byte[] mem_shop_logo;
	private byte[] mem_shop_banner;

	private Integer mem_review_count;
	private Integer mem_review_score;
	private Integer pro_id;
	
	public Integer getMem_id() {
		return mem_id;
	}
	public String getMem_phone() {
		return mem_phone;
	}
	public String getMem_shop_name() {
		return mem_shop_name;
	}
	public String getMem_shop_content() {
		return mem_shop_content;
	}
	public byte[] getMem_shop_logo() {
		return mem_shop_logo;
	}
	public byte[] getMem_shop_banner() {
		return mem_shop_banner;
	}
	
	public Integer getMem_review_count() {
		return mem_review_count;
	}
	public Integer getMem_review_score() {
		return mem_review_score;
	}
	public Integer getPro_id() {
		return pro_id;
	}
	public void setMem_id(Integer mem_id) {
		this.mem_id = mem_id;
	}
	public void setMem_phone(String mem_phone) {
		this.mem_phone = mem_phone;
	}
	public void setMem_shop_name(String mem_shop_name) {
		this.mem_shop_name = mem_shop_name;
	}
	public void setMem_shop_content(String mem_shop_content) {
		this.mem_shop_content = mem_shop_content;
	}
	public void setMem_shop_logo(byte[] mem_shop_logo) {
		this.mem_shop_logo = mem_shop_logo;
	}
	public void setMem_shop_banner(byte[] mem_shop_banner) {
		this.mem_shop_banner = mem_shop_banner;
	}
	
	public void setMem_review_count(Integer mem_review_count) {
		this.mem_review_count = mem_review_count;
	}
	public void setMem_review_score(Integer mem_review_score) {
		this.mem_review_score = mem_review_score;
	}
	public void setPro_id(Integer pro_id) {
		this.pro_id = pro_id;
	}
	@Override
	public String toString() {
		return "ProJoinMemVO [mem_id=" + mem_id + ", mem_phone=" + mem_phone + ", mem_shop_name=" + mem_shop_name
				+ ", mem_shop_content=" + mem_shop_content + ", mem_shop_logo=" + Arrays.toString(mem_shop_logo)
				+ ", mem_shop_banner=" + Arrays.toString(mem_shop_banner) + ", mem_review_count=" + mem_review_count + ", mem_review_score="
				+ mem_review_score + ", pro_id=" + pro_id + "]";
	}

	
	
	//	SQL ="SELECT MEM_ID , MEM_PHONE , MEM_SHOP_NAME ,  MEM_SHOP_CONTENT , MEM_SHOP_LOGO ,MEM_SHOP_BANNER, MEM_HEADSHOT,MEM_REVIEW_COUNT , MEM_REVIEW_SCORE FROM MEMBER join PRODUCT ON MEM_ID = PRO_SMEM_ID WHERE PRO_ID =? ;"
}
