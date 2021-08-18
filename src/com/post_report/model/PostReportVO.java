package com.post_report.model;
import java.sql.Date;

//文章檢舉POST_REPORT
public class PostReportVO implements java.io.Serializable{

	private Integer prep_id; //檢舉ID
	private Integer prep_post_id; //文章ID
	private Integer prep_adm_id; //管理員ID
	private String prep_content; //檢舉內容
	private Date prep_time; //檢舉時間
	private Short prep_status; //檢舉狀態
	
	
	public PostReportVO() {}
	
	
	
	public PostReportVO(Integer prep_id, Integer prep_post_id, Integer prep_adm_id, String prep_content, Date prep_time,
			Short prep_status) {
		this.prep_id = prep_id;
		this.prep_post_id = prep_post_id;
		this.prep_adm_id = prep_adm_id;
		this.prep_content = prep_content;
		this.prep_time = prep_time;
		this.prep_status = prep_status;
	}


	public Integer getPrep_id() {
		return prep_id;
	}
	public void setPrep_id(Integer prep_id) {
		this.prep_id = prep_id;
	}
	public Integer getPrep_post_id() {
		return prep_post_id;
	}
	public void setPrep_post_id(Integer prep_post_id) {
		this.prep_post_id = prep_post_id;
	}
	public Integer getPrep_adm_id() {
		return prep_adm_id;
	}
	public void setPrep_adm_id(Integer prep_adm_id) {
		this.prep_adm_id = prep_adm_id;
	}
	public String getPrep_content() {
		return prep_content;
	}
	public void setPrep_content(String prep_content) {
		this.prep_content = prep_content;
	}
	public Date getPrep_time() {
		return prep_time;
	}
	public void setPrep_time(Date prep_time) {
		this.prep_time = prep_time;
	}
	public Short getPrep_status() {
		return prep_status;
	}
	public void setPrep_status(Short prep_status) {
		this.prep_status = prep_status;
	}

	public String toString() {
		return "PostReportVO [prep_id=" + prep_id + ", prep_post_id=" + prep_post_id 
				+ ", prep_adm_id=" + prep_adm_id+ ", prep_content=" + prep_content + ""
				+ ", prep_time=" + prep_time + ", prep_status=" + prep_status + "]";
	}
	
	
	
}
