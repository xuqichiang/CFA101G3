package com.photographer.model;

import java.util.List;
import java.util.Map;

public class PhogService {
	
	private PhogDAO dao;
	
	public PhogService() {
		dao = new PhogDAOImpl();
	}
	//新增攝影師
	public void addPhog(PhogVO phogVO) {
		dao.insert(phogVO);
	}
	//修改攝影師狀態
	public void updateStatus(PhogVO phogVO){
		dao.update(phogVO);
	}
	//查詢一位攝影師
	public PhogVO getOnePhog(Integer phog_id) {
		return dao.findByPhogId(phog_id);
	}
	//查詢全部攝影師
	public List<PhogVO> getAll(){
		return dao.getAll();
	}
	//查詢攝影師店家
	public List<PhogVO> findByForeignKey(Integer phog_smem_id) {
		return dao.findByForeignKey(phog_smem_id);
	}
	//取得店家資訊
	public Map getShopInfo(Integer phog_smem_id) {
		return dao.getShopInfo(phog_smem_id);
	}
	//店家找作品集
	public List getShopWork(Integer phog_smem_id) {
		return dao.findWorkCount(phog_smem_id);
	}
}
