package com.photoprogramimages.model;

import java.io.Serializable;
import java.util.Arrays;

public class PhopiVO implements Serializable{
	
	//婚攝方案照片
	private Integer phopi_id;
	private Integer phopi_phop_id;//婚攝方案
	private byte[] phopi_images;//圖片
	
	public PhopiVO() {

	}

	public PhopiVO(Integer phopi_id, Integer phopi_phop_id, byte[] phopi_images) {
		super();
		this.phopi_id = phopi_id;
		this.phopi_phop_id = phopi_phop_id;
		this.phopi_images = phopi_images;
	}

	public Integer getPhopi_id() {
		return phopi_id;
	}

	public void setPhopi_id(Integer phopi_id) {
		this.phopi_id = phopi_id;
	}

	public Integer getPhopi_phop_id() {
		return phopi_phop_id;
	}

	public void setPhopi_phop_id(Integer phopi_phop_id) {
		this.phopi_phop_id = phopi_phop_id;
	}

	public byte[] getPhopi_images() {
		return phopi_images;
	}

	public void setPhopi_images(byte[] phopi_images) {
		this.phopi_images = phopi_images;
	}

	@Override
	public String toString() {
		return "PhopiVO [phopi_id=" + phopi_id + ", phopi_phop_id=" + phopi_phop_id + ", phopi_images="
				+ Arrays.toString(phopi_images) + "]";
	}
	
}
