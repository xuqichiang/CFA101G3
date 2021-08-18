package com.photoprogramimages.model;

import java.util.List;

public class PhopiService {
	PhopiDAOImpl dao = new PhopiDAOImpl();
	public List<PhopiVO> findByForeignkey(Integer PHOPI_PHOP_ID){
		return dao.findByForeignkey(PHOPI_PHOP_ID);
	}
	public PhopiVO findByPrimaryKey(Integer phopi_id) {
		return dao.findByPrimaryKey(phopi_id);
	}
	//刪除圖片
	public void delImg(Integer phopi_id) {
		dao.delete(phopi_id);
	}
	//新增
	public void addImg(PhopiVO phopiVO) {
		dao.insert(phopiVO);
	}
}
