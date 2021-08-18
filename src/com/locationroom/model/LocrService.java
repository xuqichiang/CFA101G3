package com.locationroom.model;

import java.util.List;

import com.member.model.MemVO;

public class LocrService {

	private LocrDAO_interface dao;

	public LocrService() {
		dao = new LocrDAO();
	}

	public LocrVO addLocr(Integer LOCR_SMEM_ID, String LOCR_NAME, Integer LOCR_MAX_TABLE, Integer LOCR_MIN_TABLE,
			Integer LOCR_MAIN_TABLE, Integer LOCR_GUEST_TABLE, Integer LOCR_FLOOR, String LOCR_CONTENT,
			Integer LOCR_STATUS) {

		LocrVO LocrVO = new LocrVO();

		LocrVO.setLOCR_SMEM_ID(LOCR_SMEM_ID);
		LocrVO.setLOCR_NAME(LOCR_NAME);
		LocrVO.setLOCR_MAX_TABLE(LOCR_MAX_TABLE);
		LocrVO.setLOCR_MIN_TABLE(LOCR_MIN_TABLE);
		LocrVO.setLOCR_MAIN_TABLE(LOCR_MAIN_TABLE);
		LocrVO.setLOCR_GUEST_TABLE(LOCR_GUEST_TABLE);
		LocrVO.setLOCR_FLOOR(LOCR_FLOOR);
		LocrVO.setLOCR_CONTENT(LOCR_CONTENT);
		LocrVO.setLOCR_STATUS(LOCR_STATUS);
		dao.insert(LocrVO);

		return LocrVO;
	}

	public LocrVO updateLocr(Integer LOCR_ID, Integer LOCR_SMEM_ID, String LOCR_NAME, Integer LOCR_MAX_TABLE,
			Integer LOCR_MIN_TABLE, Integer LOCR_MAIN_TABLE, Integer LOCR_GUEST_TABLE, Integer LOCR_FLOOR,
			String LOCR_CONTENT, Integer LOCR_STATUS) {

		LocrVO LocrVO = new LocrVO();

		LocrVO.setLOCR_ID(LOCR_ID);
		LocrVO.setLOCR_SMEM_ID(LOCR_SMEM_ID);
		LocrVO.setLOCR_NAME(LOCR_NAME);
		LocrVO.setLOCR_MAX_TABLE(LOCR_MAX_TABLE);
		LocrVO.setLOCR_MIN_TABLE(LOCR_MIN_TABLE);
		LocrVO.setLOCR_MAIN_TABLE(LOCR_MAIN_TABLE);
		LocrVO.setLOCR_GUEST_TABLE(LOCR_GUEST_TABLE);
		LocrVO.setLOCR_FLOOR(LOCR_FLOOR);
		LocrVO.setLOCR_CONTENT(LOCR_CONTENT);
		LocrVO.setLOCR_STATUS(LOCR_STATUS);
		dao.update(LocrVO);
		return LocrVO;
	}

	public void deleteLocr(Integer LOCR_ID) {
		dao.delete(LOCR_ID);
	}

	public LocrVO getOneLocr(Integer LOCR_ID) {
		return dao.findByPrimaryKey(LOCR_ID);
	}
	
//	public List<LocrVO> getSmemOneLocr(Integer LOCR_SMEM_ID) {
//		return dao.GET_SMEM_LOCR(LOCR_SMEM_ID);
//	}

	public LocrVO getLastID() {
		
		return dao.getLastID();
	}
	
	public List<LocrVO> getAll() {
		return dao.getAll();
	}
	public List<LocrVO> findbysqlList(String locr_name) {
		return dao.findbysqlList(locr_name);
	}
	
	public MemVO getSmem(Integer MEM_ID) {
		return dao.getSmem(MEM_ID);
	}
	public MemVO getBmem(Integer MEM_ID) {
		return dao.getBmem(MEM_ID);
	}
}
