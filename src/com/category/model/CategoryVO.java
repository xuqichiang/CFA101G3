package com.category.model;
//import java.sql.Date;


public class CategoryVO implements java.io.Serializable{

	private Integer cat_id; 
	private String cat_name; 
	
	
	public CategoryVO() {}

	
	public CategoryVO(Integer cat_id, String cat_name) {
		this.cat_id = cat_id;
		this.cat_name = cat_name;
	}


	public Integer getCat_id() {
		return cat_id;
	}
	public void setCat_id(Integer cat_id) {
		this.cat_id = cat_id;
	}
	public String getCat_name() {
		return cat_name;
	}
	public void setCat_name(String cat_name) {
		this.cat_name = cat_name;
	}
	
	public String toString() {
		return "CategoryVO [cat_id=" + cat_id + ", cat_name=" + cat_name + "]";
	}
	
	

}

	


