package com.proJOINmem.model;

import com.member.model.MemVO;

public interface ProJoinMemDAO {

	//從商品名抓會員
	public ProJoinMemVO findByPid(Integer pro_id);
}
