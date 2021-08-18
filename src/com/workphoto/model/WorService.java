package com.workphoto.model;

import java.util.List;
import java.util.Map;

import com.weddingphoto.model.WedVO;

public class WorService {
	
	private WorDAO dao;
	
	public WorService() {
		dao = new WorDAOImpl();
	} 
	//新增作品集
	public void addWor(WorVO worVO) {
		dao.insert(worVO);
	}
	//查詢一筆作品集
	public WorVO getWorId(Integer wor_id) {
		return dao.findPrimaryKey(wor_id);
	}
	//查詢所有作品集
	public List<WorVO> getAll(){
		return dao.getAll();
	}
	//找到作品集的攝影師FK
	public List<WorVO> findByForeignKey(Integer wor_phog_id){
		return dao.findByForeignKey(wor_phog_id);
	}
	//同時新增品集與圖片
	public void addWorImg(WorVO worVO, List<WedVO> list) {
		 dao.insertList(worVO, list);
	}
	//更換作品集封面
	public void updataLogo(WorVO worVO) {
		dao.updateLogo(worVO);
	}
	//找到作品集店家LOGO & CITY
	public Map getShopInfo(Integer wor_id) {
		return dao.getShopInfo(wor_id);
	}
	//查詢作品名
	public List<WorVO> findByWork(String mem_shop_name){
		return dao.findByWork(mem_shop_name);
	}
}
