package com.workphoto.model;//婚攝作品集

import java.util.List;
import java.util.Map;

import com.weddingphoto.model.WedVO;

public interface WorDAO {
	
	//新增作品集
	void insert(WorVO worVO);
	//修改作品集封面
	void updateLogo(WorVO worVO);
	//查詢作品集id
	WorVO findPrimaryKey(Integer wor_id);
	//瀏覽婚攝作品
	List<WorVO> getAll();
	//找出作品集的攝影師
	List<WorVO> findByForeignKey(Integer wor_phog_id);
	//同時新增作品集與作品集圖片
	void insertList(WorVO worVO ,List<WedVO> list);
	//取得此作品集的店家LOGO及CITY
	Map getShopInfo (Integer wor_id);
	//搜尋作品
	List<WorVO> findByWork(String mem_shop_name);
}
