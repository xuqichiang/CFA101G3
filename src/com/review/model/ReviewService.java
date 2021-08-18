package com.review.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;



public class ReviewService {
	private ReviewDAO dao = new ReviewDAOImpl();

	
	public void addReview (Integer smem_id, Integer bmem_id, String rev_content, Timestamp rev_time, Integer rev_score) {
		ReviewVO reviewVO = new ReviewVO();
		reviewVO.setSmem_id(smem_id);
		reviewVO.setBmem_id(bmem_id);
		reviewVO.setRev_content(rev_content);
		reviewVO.setRev_time(rev_time);
		reviewVO.setRev_score(rev_score);
		dao.insert(reviewVO);
	}
	
	
	//用smem_id找出該則賣家的留言們
	public List get_all_by_smem_id(Integer smem_id) {
		return dao.get_all_by_smem_id(smem_id);
	}
	
	//評價數最多排到最少
		public List review_count_sort() {
			return dao.review_count_sort();
		}
	
	
}
