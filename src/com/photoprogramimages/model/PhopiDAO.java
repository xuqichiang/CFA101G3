package com.photoprogramimages.model;

import java.sql.Connection;
import java.util.List;

import com.weddingphoto.model.WedVO;

public interface PhopiDAO {
	
	//新增
	void insert(PhopiVO phopiVO);
	//修改
	void updtaephopi(PhopiVO phopiVO);
	//刪除
	void delete(Integer phopi_id);
	//查詢
	List<PhopiVO> getAll();
	//FK
	List<PhopiVO> findByForeignkey(Integer PHOPI_PHOP_ID);
	
	//查詢單筆
	PhopiVO findByPrimaryKey(Integer phopi_id);
	//同時新增方案及圖片
	void insert2(PhopiVO phopiVO, Connection con);
}
