package com.message.model;
import java.sql.Date;

//留言MESSAGE
public class MessageVO implements java.io.Serializable{

	private Integer mes_id; //留言ID
	private Integer mes_post_id; //文章ID
	private Integer mes_mem_id; //會員ID
	private String mes_content; //留言內容
	private Date mes_time; //留言時間
	private Integer mes_status; //留言狀態
	
	
	public MessageVO() {}
	
	public MessageVO(Integer mes_id, Integer mes_post_id, Integer mes_mem_id, String mes_content, Date mes_time,
			Integer mes_status) {
		this.mes_id = mes_id;
		this.mes_post_id = mes_post_id;
		this.mes_mem_id = mes_mem_id;
		this.mes_content = mes_content;
		this.mes_time = mes_time;
		this.mes_status = mes_status;
	}


	public Integer getMes_id() {
		return mes_id;
	}
	public void setMes_id(Integer mes_id) {
		this.mes_id = mes_id;
	}
	public Integer getMes_post_id() {
		return mes_post_id;
	}
	public void setMes_post_id(Integer mes_post_id) {
		this.mes_post_id = mes_post_id;
	}
	public Integer getMes_mem_id() {
		return mes_mem_id;
	}
	public void setMes_mem_id(Integer mes_mem_id) {
		this.mes_mem_id = mes_mem_id;
	}
	public String getMes_content() {
		return mes_content;
	}
	public void setMes_content(String mes_content) {
		this.mes_content = mes_content;
	}
	public Date getMes_time() {
		return mes_time;
	}
	public void setMes_time(Date mes_time) {
		this.mes_time = mes_time;
	}
	public Integer getMes_status() {
		return mes_status;
	}
	public void setMes_status(Integer mes_status) {
		this.mes_status = mes_status;
	}
	@Override
	public String toString() {
		return "MessageVO [mes_id=" + mes_id + ", mes_post_id=" + mes_post_id 
				+ ", mes_mem_id=" + mes_mem_id
				+ ", mes_content=" + mes_content 
				+ ", mes_time=" + mes_time + ", mes_status=" 
				+ mes_status + "]";
	}

	

}
