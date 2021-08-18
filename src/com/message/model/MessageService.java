package com.message.model;
import java.sql.Date;
import java.util.List;

import com.post.model.PostVO;
import com.tag.model.TagVO;


public class MessageService {
	private MessageDAO dao = new MessageDAOImpl();
	
	public void addMessage (Integer mes_post_id, Integer mes_mem_id, String mes_content, Date mes_time) {
		MessageVO messageVO = new MessageVO();
		messageVO.setMes_post_id(mes_post_id);
		messageVO.setMes_mem_id(mes_mem_id);
		messageVO.setMes_content(mes_content);
		messageVO.setMes_time(mes_time);
		dao.insert(messageVO);
	}
	

	//用mes_post_id找出該則文章下的留言
	public List<MessageVO> getBy_mes_post_id(Integer mes_post_id) {
		return dao.getBy_mes_post_id(mes_post_id);
	}
	
	//mes join post&category表格 以文章的留言數做排序
	public List message_count_sort() {
		return dao.message_count_sort();
	}
	
	

}
