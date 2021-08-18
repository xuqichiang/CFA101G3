package com.post.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import com.category.model.CategoryVO;
import com.tag.model.TagVO;

public class PostService {

	private PostDAO dao = new PostDAOImpl();

	//新增文章
	public void addPost(String post_title, String post_content, Timestamp post_time, Integer post_cat_id,
			Integer post_mem_id, List<TagVO> addTag) {
		PostVO post = new PostVO();
		post.setPost_title(post_title);
		post.setPost_content(post_content);
		post.setPost_time(post_time);
		post.setPost_cat_id(post_cat_id);
		post.setPost_mem_id(post_mem_id);
		dao.insert(post, addTag);
		// addTag:經過判斷的標籤List，已在TagService setTag_name
	}

	// 編輯文章 含狀態
	public PostVO updatePost(String post_title,String post_content,Timestamp post_time,
			Integer post_cat_id,Integer post_mem_id,Integer post_status,Integer post_id) {
		PostVO postVO = new PostVO();
		postVO.setPost_title(post_title);
		postVO.setPost_content(post_content);
		postVO.setPost_time(post_time);
		postVO.setPost_cat_id(post_cat_id);
		postVO.setPost_mem_id(post_mem_id);
		postVO.setPost_status(post_status);
		postVO.setPost_id(post_id);
		dao.update(postVO);
		return postVO;
	}

	// 修改文章狀態
	public void updatePostStatus(PostVO postVO) {
		dao.updatePostStatus(postVO);
	}

	public void deletePost(Integer post_id) {
		dao.delete(post_id);
	}

	public Map findByPostId(Integer post_id) {
		return dao.findByPostId(post_id);
	}

	public List findByCatId(Integer cat_id) {
		return dao.findByCatId(cat_id);
	}

	public List<PostVO> getAll() {
		return dao.getAll();
	}

	public List getPost() {
		return dao.getPost();
	}

	public PostPageVO findByPage(Integer currentPage, Integer rows) {
		PostPageVO postPageVO = new PostPageVO();
		postPageVO.setCurrentPage(currentPage);
		postPageVO.setRows(rows);
		// 查詢所有文章數
		Integer totalCount = dao.findTotalCount();
		postPageVO.setTotalCount(totalCount);
		// 當頁的開始
		Integer start = (currentPage - 1) * rows;
		List list = dao.findByPage(start, rows);
		postPageVO.setList(list);
		// 總頁數
		Integer totalPage = (totalCount % rows) == 0 ? totalCount / rows : (totalCount / rows) + 1;
		postPageVO.setTotalPage(totalPage);
		return postPageVO;
	}

	// 用作者找文章
	public List findByWriter(Integer post_mem_id) {
		return dao.findByWriter(post_mem_id);
	}

}
