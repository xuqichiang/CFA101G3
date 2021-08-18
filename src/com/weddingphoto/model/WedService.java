package com.weddingphoto.model;

import java.util.List;

public class WedService {
	
	private WedDAO dao;
	
	public WedService() {
		dao = new WedDAOImpl();
	}
	//新增作品集圖片
	public void addWed(WedVO wedVO) {
		dao.insert(wedVO);
	}
	//查詢
	public WedVO findByPrimaryKey(Integer wed_id) {
		return dao.findByPrimaryKey(wed_id);
	}
	//查詢作品集名稱
	public List<WedVO> findByForeignKey(Integer wed_wor_id) {
		return dao.findByForeignKey(wed_wor_id);
	}
	//刪除圖片
	public void deleteImg(Integer wed_id) {
		dao.delete(wed_id);
	}
}
