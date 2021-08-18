package com.post_tag_ref.model;

import com.tag.model.TagVO;

public class PostTagRefJoinVO implements java.io.Serializable {

	private Integer ptr_id; //文章標籤明細ID
	private Integer ptr_post_id; //文章ID
	private TagVO tagVO; //文章標籤
	
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
	public TagVO getTagVO() {
		return tagVO;
	}
	public void setTagVO(TagVO tagVO) {
		this.tagVO = tagVO;
	}
	@Override
	public String toString() {
		return "PostTagRefJoinVO [ptr_id=" + ptr_id + ", ptr_post_id=" + ptr_post_id + ", tagVO=" + tagVO + "]";
	}
	
	
	
	
	
}
