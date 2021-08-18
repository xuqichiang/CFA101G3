package com.photographer.model;

import java.util.List;
import java.util.Map;

import com.member.model.MemVO;

public interface PhogDAO {
	
	//新增攝影師
	void insert(PhogVO phogVO);
	//修改攝影師狀態
	void update(PhogVO phogVO);
	//查詢一位攝影師
	PhogVO findByPhogId(Integer phog_id);
	//攝影師所有資料
	List<PhogVO> getAll();
	//找出攝影師店家
	List<PhogVO> findByForeignKey(Integer phog_smem_id);
	//找出攝影師店家資訊
	Map getShopInfo(Integer phog_smem_id);
	//店家所有作品集
	List findWorkCount(Integer phog_smem_id);
	
}
