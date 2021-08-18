package com.locationimages.model;

import java.util.List;

public class LociService {

	private LociDAO_interface dao;

	public LociService() {
		dao = new LociDAO();
	}

	public LociVO addLoci(Integer LOCI_LOCR_ID, byte[] LOCI_IMAGES) {
		System.out.println("8");
		LociVO LociVO = new LociVO();

		LociVO.setLOCI_LOCR_ID(LOCI_LOCR_ID);
		LociVO.setLOCI_IMAGES(LOCI_IMAGES);
		dao.insert(LociVO);

		return LociVO;
	}

	public LociVO updateLoci(Integer LOCI_ID,Integer LOCI_LOCR_ID, byte[] LOCI_IMAGES) {

		LociVO LociVO = new LociVO();

		LociVO.setLOCI_ID(LOCI_ID);
		LociVO.setLOCI_LOCR_ID(LOCI_LOCR_ID);
		LociVO.setLOCI_IMAGES(LOCI_IMAGES);
	
		dao.update(LociVO);
		return LociVO;
	}

	public void deleteLoci(Integer LOCI_LOCR_ID) {
		dao.delete(LOCI_LOCR_ID);
	}
	public void deleteLociID(Integer LOCI_ID) {
		dao.deleteById(LOCI_ID);
	}

	public LociVO findByPrimaryKey(Integer LOCI_ID) {
		return dao.findByPrimaryKey(LOCI_ID);
	}

	public List<LociVO> getAll() {
		return dao.getAll();
	}
	public List<LociVO> findByForeignKey(Integer LOCI_LOCR_ID) {
		return dao.findByForeignKey(LOCI_LOCR_ID);
	}


	
}
