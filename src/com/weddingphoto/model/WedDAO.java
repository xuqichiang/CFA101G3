package com.weddingphoto.model;

import java.sql.Connection;
import java.util.List;

import com.workphoto.model.WorVO;

public interface WedDAO {
	//作品集照片
	void insert(WedVO wedVO);//新稱
	void update(WedVO wedVO);//修改
	void delete(Integer wed_id);//刪除
	WedVO findByPrimaryKey(Integer wed_id);//查詢
	
	//找出作品集的名稱
	List<WedVO> findByForeignKey(Integer wed_wor_id);
	//同時新增作品集與作品集圖片
	void insert2 (WedVO wedVO, Connection con);
	
}
