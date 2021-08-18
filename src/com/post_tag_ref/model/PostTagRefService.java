package com.post_tag_ref.model;

import java.util.List;

import com.tag.model.TagVO;

public class PostTagRefService {

	private PostTagRefDAO dao = new PostTagRefDAOImpl();

	// 由PK 文章標籤明細找
	public PostTagRefVO find_One_By_PTRId(Integer ptr_id) {
		return dao.find_One_By_PTRId(ptr_id);
	}

	// 由FK 文章ID找PK
	public List<PostTagRefJoinVO> findBy_PTR_Post_Id(Integer ptr_post_id) {
		return dao.findBy_PTR_Post_Id(ptr_post_id);
	}

	public void UpdateTags(Integer post_id, List<TagVO> list) {
		dao.deleteByPostId(post_id); //先把文章的tag全刪除
		for(TagVO tagVO : list) {//再更新文章標籤
			PostTagRefVO postTagRefVO = new PostTagRefVO();
			postTagRefVO.setPtr_post_id(post_id);
			postTagRefVO.setPtr_tag_id(tagVO.getTag_id());
			dao.create(postTagRefVO);
		}
	}

}
