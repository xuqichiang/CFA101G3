package com.tag.model;

public class TagVO implements java.io.Serializable{

	private Integer tag_id; //標籤ID
	private String tag_name; //標籤名稱
	
	public TagVO() {}
	
	public TagVO(Integer tag_id, String tag_name) {
		this.tag_id = tag_id;
		this.tag_name = tag_name;
	}

	public Integer getTag_id() {
		return tag_id;
	}
	public void setTag_id(Integer tag_id) {
		this.tag_id = tag_id;
	}
	public String getTag_name() {
		return tag_name;
	}
	public void setTag_name(String tag_name) {
		this.tag_name = tag_name;
	}

	public String toString() {
		return "TagVO [tag_id=" + tag_id + ", tag_name=" + tag_name + "]";
	}

}
