package com.weddingphoto.model;

import java.io.Serializable;
import java.util.Arrays;

public class WedVO implements Serializable{
	
	//婚攝作品集照片
	private Integer wed_id;
	private Integer wed_wor_id;//作品集
	private byte[] wed_images;//圖
	public WedVO() {

	}
	public WedVO(Integer wed_id, Integer wed_wor_id, byte[] wed_images) {
		super();
		this.wed_id = wed_id;
		this.wed_wor_id = wed_wor_id;
		this.wed_images = wed_images;
	}
	public Integer getWed_id() {
		return wed_id;
	}
	public void setWed_id(Integer wed_id) {
		this.wed_id = wed_id;
	}
	public Integer getWed_wor_id() {
		return wed_wor_id;
	}
	public void setWed_wor_id(Integer wed_wor_id) {
		this.wed_wor_id = wed_wor_id;
	}
	
	public byte[] getWed_images() {
		return wed_images;
	}
	
	public void setWed_images(byte[] wed_images) {
		this.wed_images = wed_images;
	}
	@Override
	public String toString() {
		return "WedVO [wed_id=" + wed_id + ", wed_wor_id=" + wed_wor_id + ", wed_images=" + Arrays.toString(wed_images)
				+ "]";
	}
}
