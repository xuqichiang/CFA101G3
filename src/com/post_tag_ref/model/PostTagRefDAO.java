package com.post_tag_ref.model;

import java.sql.Connection;
import java.util.*;

interface PostTagRefDAO {
	
	//新增文章時產生的文章標籤明細
	void insert(PostTagRefVO postTagRefVO,Connection con);
	
	
	void create(PostTagRefVO postTagRefVO);
	
	//用文章標籤明細ID(PK) 找到對應的1個文章ID和1個標籤ID
	PostTagRefVO find_One_By_PTRId(Integer ptr_id); 
	
	//用文章ID(FK) 反查文章標籤明細ID
	List<PostTagRefJoinVO> findBy_PTR_Post_Id(Integer ptr_post_id);
	
	void deleteByPostId(Integer post_id);
	
//	//獲取所有文章標籤明細ID 文章ID和標籤ID們
//		List<PostTagRefVO> get_All();

}
