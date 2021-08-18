package com.message_report.model;

import java.sql.Date;
//留言檢舉MESSAGE_REPORT
public class MessageReportVO implements java.io.Serializable {

	private Integer mrep_id;  //檢舉ID
	private Integer mrep_mes_id;  //留言ID
	private Integer mrep_adm_id;  //管理員ID
	private String mrep_content;  //檢舉內容
	private Date mrep_time;  //檢舉時間
	private Short mrep_status;  //檢舉狀態

	public MessageReportVO() {}
	
	

	public MessageReportVO(Integer mrep_id, Integer mrep_mes_id, Integer mrep_adm_id, String mrep_content,
			Date mrep_time, Short mrep_status) {
		this.mrep_id = mrep_id;
		this.mrep_mes_id = mrep_mes_id;
		this.mrep_adm_id = mrep_adm_id;
		this.mrep_content = mrep_content;
		this.mrep_time = mrep_time;
		this.mrep_status = mrep_status;
	}

	public Integer getMrep_id() {
		return mrep_id;
	}

	public void setMrep_id(Integer mrep_id) {
		this.mrep_id = mrep_id;
	}

	public Integer getMrep_mes_id() {
		return mrep_mes_id;
	}

	public void setMrep_mes_id(Integer mrep_mes_id) {
		this.mrep_mes_id = mrep_mes_id;
	}

	public Integer getMrep_adm_id() {
		return mrep_adm_id;
	}

	public void setMrep_adm_id(Integer mrep_adm_id) {
		this.mrep_adm_id = mrep_adm_id;
	}

	public String getMrep_content() {
		return mrep_content;
	}

	public void setMrep_content(String mrep_content) {
		this.mrep_content = mrep_content;
	}

	public Date getMrep_time() {
		return mrep_time;
	}

	public void setMrep_time(Date mrep_time) {
		this.mrep_time = mrep_time;
	}

	public Short getMrep_status() {
		return mrep_status;
	}

	public void setMrep_status(Short mrep_status) {
		this.mrep_status = mrep_status;
	}

	public String toString() {
		return "MessageReportVO [mrep_id=" + mrep_id 
				+ ", mrep_mes_id=" + mrep_mes_id + ", mrep_adm_id=" + mrep_adm_id
				+ ", mrep_content=" + mrep_content + ", mrep_time=" + mrep_time 
				+ ", mrep_status=" + mrep_status + "]";
	}

}
