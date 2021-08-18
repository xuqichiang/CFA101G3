package com.post.model;
import java.sql.Date;
import java.sql.Timestamp;

//文章POST
public class PostVO implements java.io.Serializable{

	private Integer post_id; //文章ID
	private String post_title; //文章標題
	private String post_content; //文章內容
	private Timestamp post_time; //文章發表時間
	private Integer post_cat_id; //文章分類ID
	private Integer post_mem_id; //文章作者會員ID
	private Integer post_status; //文章狀態
	
	
	public PostVO() {}
	
	public PostVO(Integer post_id, String post_title, String post_content, Timestamp post_time, Integer post_cat_id,
			Integer post_mem_id, Integer post_status) {
		this.post_id = post_id;
		this.post_title = post_title;
		this.post_content = post_content;
		this.post_time = post_time;
		this.post_cat_id = post_cat_id;
		this.post_mem_id = post_mem_id;
		this.post_status = post_status;
	}




	public Integer getPost_id() {
		return post_id;
	}
	public void setPost_id(Integer post_id) {
		this.post_id = post_id;
	}
	public String getPost_title() {
		return post_title;
	}
	public void setPost_title(String post_title) {
		this.post_title = post_title;
	}
	public String getPost_content() {
		return post_content;
	}
	public void setPost_content(String post_content) {
		this.post_content = post_content;
	}
	public Timestamp getPost_time() {
		return post_time;
	}
	public void setPost_time(Timestamp post_time) {
		this.post_time = post_time;
	}
	public Integer getPost_cat_id() {
		return post_cat_id;
	}
	public void setPost_cat_id(Integer post_cat_id) {
		this.post_cat_id = post_cat_id;
	}
	public Integer getPost_mem_id() {
		return post_mem_id;
	}
	public void setPost_mem_id(Integer post_mem_id) {
		this.post_mem_id = post_mem_id;
	}
	public Integer getPost_status() {
		return post_status;
	}
	public void setPost_status(Integer post_status) {
		this.post_status = post_status;
	}
	@Override
	public String toString() {
		return "PostVO [post_id=" + post_id + ", post_title=" + post_title 
				+ ", post_content=" + post_content
				+ ", post_time=" + post_time + ", post_cat_id=" + post_cat_id 
				+ ", post_mem_id=" + post_mem_id
				+ ", post_status=" + post_status + "]";
	}
	

	
	

}
