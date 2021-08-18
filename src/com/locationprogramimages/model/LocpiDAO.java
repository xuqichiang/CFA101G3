package com.locationprogramimages.model;

import java.util.List;

import com.member.model.MemVO;

public interface LocpiDAO {
	//新增圖片
	public void insertLocpiImages(LocpiVO locpiVO);
	//更新圖片
	public void updateLocpiImages(LocpiVO locpiVO);
	//刪除圖片
	public void deleteLocpiImages(Integer locpi_locp_id);
	//用主鍵搜尋圖片
	public LocpiVO findByPrimaryKey(Integer locpi_id);
	//用外來鍵找一張圖片
	public LocpiVO findByForeignKey(Integer locpi_locp_id);
	//找到全部的外來鍵
	public List<LocpiVO> findForeignKey(Integer locpi_locp_id);
	//得到全部的圖片
	public List<LocpiVO> getAllimages();
}
