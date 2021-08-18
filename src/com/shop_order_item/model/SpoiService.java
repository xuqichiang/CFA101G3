package com.shop_order_item.model;

import java.util.List;

public class SpoiService {
	
	SpoiDAOImpl dao = new SpoiDAOImpl();
	//獲取全部商品訂單明細
	public List<SpoiVO> getAll(){
		return dao.getAll();
	}
	//利用spo_id獲取商品訂單明細
	public List<SpoiVO> getBySpo_id(Integer spo_id) {
		return dao.getBySpo_id(spo_id);
	}
}
