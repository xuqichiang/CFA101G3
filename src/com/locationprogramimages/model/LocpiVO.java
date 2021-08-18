package com.locationprogramimages.model;

import java.io.Serializable;
import java.util.Arrays;

public class LocpiVO implements Serializable {
	
	private Integer locpi_id;
	private Integer locpi_locp_id;
	private byte[] locpi_images;
	
	public LocpiVO() {
		super();
	}
	public LocpiVO(Integer locpi_id, Integer locpi_locp_id, byte[] locpi_images) {
		super();
		this.locpi_id = locpi_id;
		this.locpi_locp_id = locpi_locp_id;
		this.locpi_images = locpi_images;
	}
	public Integer getLocpi_id() {
		return locpi_id;
	}
	public void setLocpi_id(Integer locpi_id) {
		this.locpi_id = locpi_id;
	}
	public Integer getLocpi_locp_id() {
		return locpi_locp_id;
	}
	public void setLocpi_locp_id(Integer locpi_locp_id) {
		this.locpi_locp_id = locpi_locp_id;
	}
	public byte[] getLocpi_images() {
		return locpi_images;
	}
	public void setLocpi_images(byte[] locpi_images) {
		this.locpi_images = locpi_images;
	}
	@Override
	public String toString() {
		return "LocpiVO [locpi_id=" + locpi_id + ", locpi_locp_id=" + locpi_locp_id + ", locpi_images="
				+ Arrays.toString(locpi_images) + "]";
	}
	
	

}
