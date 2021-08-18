package com.photocategory.model;

import java.util.List;

import com.photoprogram.model.PhopVO;

public class PhocService {

	private PhocDAO  dao = new PhocDAOImpl();
	//所有方案類別	
	public List<PhocVO> getAll() {
		return dao.getAll();
	}
	
}
