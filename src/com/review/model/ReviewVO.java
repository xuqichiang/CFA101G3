package com.review.model;

import java.sql.Date;
import java.sql.Timestamp;

//評價 表格
public class ReviewVO implements java.io.Serializable {
//	private Integer rev_id; //評價ID  無法新增到資料庫
	private Integer smem_id; //賣家ID PK
	private Integer bmem_id; //買家ID PK
	private String rev_content; //評價內容
	private Timestamp rev_time; //發送時間
	private Integer rev_score; //評分
	
	public ReviewVO() {
		super();
	}

	public Integer getSmem_id() {
		return smem_id;
	}

	public void setSmem_id(Integer smem_id) {
		this.smem_id = smem_id;
	}

	public Integer getBmem_id() {
		return bmem_id;
	}

	public void setBmem_id(Integer bmem_id) {
		this.bmem_id = bmem_id;
	}

	public String getRev_content() {
		return rev_content;
	}

	public void setRev_content(String rev_content) {
		this.rev_content = rev_content;
	}

	public Timestamp getRev_time() {
		return rev_time;
	}

	public void setRev_time(Timestamp rev_time) {
		this.rev_time = rev_time;
	}

	public Integer getRev_score() {
		return rev_score;
	}

	public void setRev_score(Integer rev_score) {
		this.rev_score = rev_score;
	}

	@Override
	public String toString() {
		return "ReviewVO [smem_id=" + smem_id + ", bmem_id=" + bmem_id + ", rev_content=" + rev_content + ", rev_time="
				+ rev_time + ", rev_score=" + rev_score + "]";
	}
	
	
	
}
