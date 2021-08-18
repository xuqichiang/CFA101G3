package com.admin.model;

import java.io.Serializable;

public class AdmVO implements Serializable{
	private Integer adm_id;
	private String adm_username;
	private String adm_password;
	private Integer adm_status;
	public Integer getAdm_id() {
		return adm_id;
	}
	public void setAdm_id(Integer adm_id) {
		this.adm_id = adm_id;
	}
	public String getAdm_username() {
		return adm_username;
	}
	public void setAdm_username(String adm_username) {
		this.adm_username = adm_username;
	}
	public String getAdm_password() {
		return adm_password;
	}
	public void setAdm_password(String adm_password) {
		this.adm_password = adm_password;
	}
	public Integer getAdm_status() {
		return adm_status;
	}
	public void setAdm_status(Integer adm_status) {
		this.adm_status = adm_status;
	}
}
