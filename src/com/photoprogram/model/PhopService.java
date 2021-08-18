package com.photoprogram.model;

import java.util.List;
import java.util.Map;

import com.photoprogramimages.model.PhopiVO;

public class PhopService {
	
	private PhopDAO dao = new PhopDAOImpl();
	
	//新增方案
	public void insert(PhopVO phopVO) {
		dao.insert(phopVO);
	}
	//修改方案
	public void updatePhop(PhopVO phopVO) {
		dao.update(phopVO);
	}
	//查詢全部方案
	public List<PhopVO> getAll(){
		return dao.getAll();
	}
	//下架
	public void delete(Integer phop_id) {
		 dao.delete(phop_id);
	}
	//以ID尋找方案
	public PhopVO findByPhopId(Integer phop_id) {
		return dao.findByPhopId(phop_id);
	}
	public List getContent() {
		return dao. getContent();
	}

	public PhopVO findByForeignkey(Integer phop_phoc_id) {
		return dao.findByPhopId(phop_phoc_id);
	}
//--------------------//
	
	//瀏覽所有方案頁面
	public List getimages(){
		return dao.getimages();
	}
	//瀏覽方案頁面
	public Map getOneContent(Integer phop_id) {
		return dao.getOneContent(phop_id);
	}
	//同時新增方案及圖片
	public void addPhopImg(PhopVO phopVO, List<PhopiVO> list) {
		dao.insertList(phopVO, list);
	}
	//查詢方案店家
	public List<PhopVO> findByFK (Integer phop_smem_id){
		return dao.findByForeignKey(phop_smem_id);
	}
	//搜尋方案名
	public List findByPhotoProgram(String phop_name) {
		return dao.findByPhotoProgram(phop_name);
	}
	//搜尋類別
	public List findBySearchCategory(Integer phop_phoc_id) {
		return dao.findBySearchCategory(phop_phoc_id);
	}
	//複合式搜尋
	public List findBySearchList(Integer phop_phoc_id, Integer phop_price_1, Integer phop_price_2, String city) {
		return dao.findBySearchList(phop_phoc_id, phop_price_1, phop_price_2, city);
	}
}