package com.post_tag_ref.model;

//文章標籤明細POST_TAG_REF
public class PostTagRefVO implements java.io.Serializable{

	private Integer ptr_id; //文章標籤明細ID
	private Integer ptr_post_id; //文章ID
	private Integer ptr_tag_id; //標籤ID
	
	public PostTagRefVO() {}
	
	public PostTagRefVO(Integer ptr_id, Integer ptr_post_id, Integer ptr_tag_id) {
		this.ptr_id = ptr_id;
		this.ptr_post_id = ptr_post_id;
		this.ptr_tag_id = ptr_tag_id;
	}


	public Integer getPtr_id() {
		return ptr_id;
	}
	public void setPtr_id(Integer ptr_id) {
		this.ptr_id = ptr_id;
	}
	public Integer getPtr_post_id() {
		return ptr_post_id;
	}
	public void setPtr_post_id(Integer ptr_post_id) {
		this.ptr_post_id = ptr_post_id;
	}
	public Integer getPtr_tag_id() {
		return ptr_tag_id;
	}
	public void setPtr_tag_id(Integer ptr_tag_id) {
		this.ptr_tag_id = ptr_tag_id;
	}

	public String toString() {
		return "PostTagRefVO [ptr_id=" + ptr_id + ", ptr_post_id=" + ptr_post_id 
				+ ", ptr_tag_id=" + ptr_tag_id + "]";
	}
	

}
