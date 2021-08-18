package com.locationprogram.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Arrays;

public class LocpVO implements Serializable {
	
	private String loc_name;
	private byte[] loc_logo;
	private Integer locp_id;
	private String locp_name;
	private Integer locp_price;
	private Date locp_start_time;
	private Date locp_end_time;
	private String locp_content;
	private Integer locp_smem_id;
	private Integer locp_status;
	private byte[] locp_images;
	
	
	public String getLoc_name() {
		return loc_name;
	}
	public void setLoc_name(String loc_name) {
		this.loc_name = loc_name;
	}
	public byte[] getLoc_logo() {
		return loc_logo;
	}
	public void setLoc_logo(byte[] loc_logo) {
		this.loc_logo = loc_logo;
	}
	public Integer getLocp_id() {
		return locp_id;
	}
	public void setLocp_id(Integer locp_id) {
		this.locp_id = locp_id;
	}
	public String getLocp_name() {
		return locp_name;
	}
	public void setLocp_name(String locp_name) {
		this.locp_name = locp_name;
	}
	public Integer getLocp_price() {
		return locp_price;
	}
	public void setLocp_price(Integer locp_price) {
		this.locp_price = locp_price;
	}
	public Date getLocp_start_time() {
		return locp_start_time;
	}
	public void setLocp_start_time(Date locp_start_time) {
		this.locp_start_time = locp_start_time;
	}
	public Date getLocp_end_time() {
		return locp_end_time;
	}
	public void setLocp_end_time(Date locp_end_time) {
		this.locp_end_time = locp_end_time;
	}
	public String getLocp_content() {
		return locp_content;
	}
	public void setLocp_content(String locp_content) {
		this.locp_content = locp_content;
	}
	public Integer getLocp_smem_id() {
		return locp_smem_id;
	}
	public void setLocp_smem_id(Integer locp_smem_id) {
		this.locp_smem_id = locp_smem_id;
	}
	public Integer getLocp_status() {
		return locp_status;
	}
	public void setLocp_status(Integer locp_status) {
		this.locp_status = locp_status;
	}
	public byte[] getLocp_images() {
		return locp_images;
	}
	public void setLocp_images(byte[] locp_images) {
		this.locp_images = locp_images;
	}
	@Override
	public String toString() {
		return "LocpVO [loc_name=" + loc_name + ", loc_logo=" + Arrays.toString(loc_logo) + ", locp_id=" + locp_id
				+ ", locp_name=" + locp_name + ", locp_price=" + locp_price + ", locp_start_time=" + locp_start_time
				+ ", locp_end_time=" + locp_end_time + ", locp_content=" + locp_content + ", locp_smem_id="
				+ locp_smem_id + ", locp_status=" + locp_status + ", locp_images=" + Arrays.toString(locp_images) + "]";
	}
	
	
	
	
	

}
