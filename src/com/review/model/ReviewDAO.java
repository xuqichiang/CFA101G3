package com.review.model;
import java.util.List;


interface ReviewDAO {

	// 新增評價
	void insert(ReviewVO reviewVO);

	//用賣家smem_id找留言們
	List get_all_by_smem_id(Integer smem_id);
	
	//評價數最多排到最少
	List review_count_sort();
	
	
	
//	ReviewVO findOneByRevID(Integer rev_id);
	
}
