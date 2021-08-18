package com.photoprogram.model;

import java.util.List;
import java.util.Map;
import com.photoprogramimages.model.PhopiVO;


public interface PhopDAO {

	//新增
	void insert(PhopVO phopVO);
	//修改
	void update(PhopVO phopVO);
	// FK
	//下架
	void delete(Integer phop_id);
	List getContent();
	
//---------------------//
	
	//查詢全部方案
	List<PhopVO> getAll();
	//查詢一筆方案
	PhopVO findByPhopId(Integer phop_id);
	//瀏覽方案頁面取得方案照片
	List getimages();
	//取得一個方案資訊
	Map getOneContent(Integer phop_id);
	//同時新增方案及圖片
	void insertList(PhopVO phopVO, List<PhopiVO> list);
	//找出方案店家
	List<PhopVO> findByForeignKey(Integer phop_smem_id);
	//條件搜尋方案
	List findByPhotoProgram(String phop_name);
	//複合選單搜尋
	List findBySearchList(Integer phop_phoc_id, Integer phop_price_1, Integer phop_price_2, String city);
	//類別搜尋
	List findBySearchCategory(Integer phop_phoc_id);
}
