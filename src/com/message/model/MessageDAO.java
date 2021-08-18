package com.message.model;

import java.util.List;

interface MessageDAO {
	// 新增留言
	void insert(MessageVO messageVO);
	// 修改留言狀態
	void updateMesStatus(MessageVO messageVO);

	MessageVO findOneByMesID(Integer mes_id);

	// 用文章ID找底下的留言們
	List<MessageVO> getBy_mes_post_id(Integer mes_post_id);
	
	//依文章留言數排序
	List message_count_sort();
}
