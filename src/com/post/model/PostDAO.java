package com.post.model;

import java.util.*;

import com.tag.model.TagVO;

interface PostDAO {
	void insert(PostVO post, List<TagVO> addTag);

	void update(PostVO post);

	void delete(Integer post_id);

	Map findByPostId(Integer post_id);

	// 獲取文章標題.內容.時間.分類.作者.狀態
	List<PostVO> getAll();

	// 更新文章狀態
	void updatePostStatus(PostVO postVO);

	// 獲取首頁文章區塊資訊
	List getPost();

	// 用文章分類id找文章
	List findByCatId(Integer cat_id);

	// 查詢總共幾篇文章
	Integer findTotalCount();

	// 每頁顯示的文章放在這
	List findByPage(Integer start, Integer rows);

	// 根據作者ID尋找文章們
	List findByWriter(Integer post_mem_id);

}
