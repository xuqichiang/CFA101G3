package com.calendar.model;

import java.sql.Date;
import java.sql.Timestamp;

public class CalendarVO {
	
	private Integer loco_id;
	private Timestamp loco_reserve_time;
	private String locr_name;
	private Integer phoo_id;
	private Date phoo_reserve_time;
	private String phog_name;
	private String mem_name;
	
	public String getMem_name() {
		return mem_name;
	}
	public void setMem_name(String mem_name) {
		this.mem_name = mem_name;
	}
	public Integer getLoco_id() {
		return loco_id;
	}
	public void setLoco_id(Integer loco_id) {
		this.loco_id = loco_id;
	}
	public Timestamp getLoco_reserve_time() {
		return loco_reserve_time;
	}
	public void setLoco_reserve_time(Timestamp loco_reserve_time) {
		this.loco_reserve_time = loco_reserve_time;
	}
	public String getLocr_name() {
		return locr_name;
	}
	public void setLocr_name(String locr_name) {
		this.locr_name = locr_name;
	}
	public Integer getPhoo_id() {
		return phoo_id;
	}
	public void setPhoo_id(Integer phoo_id) {
		this.phoo_id = phoo_id;
	}
	public Date getPhoo_reserve_time() {
		return phoo_reserve_time;
	}
	public void setPhoo_reserve_time(Date phoo_reserve_time) {
		this.phoo_reserve_time = phoo_reserve_time;
	}
	public String getPhog_name() {
		return phog_name;
	}
	public void setPhog_name(String phog_name) {
		this.phog_name = phog_name;
	}
	@Override
	public String toString() {
		return "CalendarVO [loco_id=" + loco_id + ", loco_reserve_time=" + loco_reserve_time + ", locr_name="
				+ locr_name + ", phoo_id=" + phoo_id + ", phoo_reserve_time=" + phoo_reserve_time + ", phog_name="
				+ phog_name + "]";
	}
	
	
	
	
	
}
