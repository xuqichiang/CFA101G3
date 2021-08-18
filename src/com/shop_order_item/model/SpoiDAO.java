package com.shop_order_item.model;

import java.sql.Connection;
import java.util.List;

public interface SpoiDAO {
	//新增商品訂單明細(產生商品訂單時用)
	public void insertByShopOrder(SpoiVO spoiVO,Connection con);
	//獲取全部商品訂單明細
	public List<SpoiVO> getAll();
	//利用spo_id獲取商品訂單明細
	public List<SpoiVO> getBySpo_id(Integer spo_id);
}
