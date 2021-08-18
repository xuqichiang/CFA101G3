package com.locationprogramimages.model;

import java.util.List;

public class LocpiService {
	
	LocpiDAO dao = new LocpiDAOImpl();
	
	public void insertLocpiImages(LocpiVO locpiVO) {
		dao.insertLocpiImages(locpiVO);
	}

	public LocpiVO findByPrimaryKey(Integer locpi_id) {
		return dao.findByPrimaryKey(locpi_id);
	}
	
	public List<LocpiVO> findForeignKey(Integer locpi_locp_id) {
		return dao.findForeignKey(locpi_locp_id);
	}
	
	public void deleteLocpiImages(Integer locpi_id) {
		dao.deleteLocpiImages(locpi_id);
	}
	
	public void updateLocpiImages(LocpiVO locpiVO) {
		dao.updateLocpiImages(locpiVO);
	}
	
	public List<LocpiVO> getAllimages() {
		return dao.getAllimages();
	}
	
	public LocpiVO findByForeignKey(Integer locpi_locp_id) {
		return dao.findByForeignKey(locpi_locp_id);
	}
}
