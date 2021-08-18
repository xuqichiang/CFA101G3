package com.locationorder.model;

import java.sql.Date;
import java.util.List;



public class LocoService {
	
	private LocoDAO_interface dao;
	
	public LocoService() {
		dao = new LocoDAO();
	}
	
	public LocoVO addLoco(Integer LOCO_SMEM_ID,Integer LOCO_BMEM_ID,Integer LOCO_LOCR_ID,Integer LOCO_LOCP_ID,Integer LOCO_TOTALPRICE,
			Integer LOCO_DEPOSIT	,Integer LOCO_PAYTYPE,Integer LOCO_ORDER_STATUS,Integer LOCO_PAY_STATUS,Date LOCO_ORDER_TIME,Date LOCO_RESERVE_TIME,
			Integer LOCO_TABLE_NUM,String LOCO_NOTE) {		
		LocoVO LocoVO = new LocoVO();
		LocoVO.setLOCO_SMEM_ID(LOCO_SMEM_ID);
		LocoVO.setLOCO_BMEM_ID(LOCO_BMEM_ID);
		LocoVO.setLOCO_LOCR_ID(LOCO_LOCR_ID);
		LocoVO.setLOCO_LOCP_ID(LOCO_LOCP_ID);
		LocoVO.setLOCO_TOTALPRICE(LOCO_TOTALPRICE);
		LocoVO.setLOCO_DEPOSIT(LOCO_DEPOSIT);
		LocoVO.setLOCO_PAYTYPE(LOCO_PAYTYPE);
		LocoVO.setLOCO_ORDER_STATUS(LOCO_ORDER_STATUS);
		LocoVO.setLOCO_PAY_STATUS(LOCO_PAY_STATUS);
		LocoVO.setLOCO_ORDER_TIME(LOCO_ORDER_TIME);
		LocoVO.setLOCO_RESERVE_TIME(LOCO_RESERVE_TIME);
		LocoVO.setLOCO_TABLE_NUM(LOCO_TABLE_NUM);
		LocoVO.setLOCO_NOTE(LOCO_NOTE);
		
		dao.insert(LocoVO);				
		return LocoVO; 
	}
	public LocoVO updateLoco(Integer LOCO_ID,Integer LOCO_SMEM_ID,Integer LOCO_BMEM_ID,Integer LOCO_LOCR_ID,Integer LOCO_LOCP_ID,Integer LOCO_TOTALPRICE,
			Integer LOCO_DEPOSIT	,Integer LOCO_PAYTYPE,Integer LOCO_ORDER_STATUS,Integer LOCO_PAY_STATUS,Date LOCO_ORDER_TIME,Date LOCO_RESERVE_TIME,
			Integer LOCO_TABLE_NUM,String LOCO_NOTE) {		
		LocoVO LocoVO = new LocoVO();
		LocoVO.setLOCO_ID(LOCO_ID);
		LocoVO.setLOCO_SMEM_ID(LOCO_SMEM_ID);
		LocoVO.setLOCO_BMEM_ID(LOCO_BMEM_ID);
		LocoVO.setLOCO_LOCR_ID(LOCO_LOCR_ID);
		LocoVO.setLOCO_LOCP_ID(LOCO_LOCP_ID);
		LocoVO.setLOCO_TOTALPRICE(LOCO_TOTALPRICE);
		LocoVO.setLOCO_DEPOSIT(LOCO_DEPOSIT);
		LocoVO.setLOCO_PAYTYPE(LOCO_PAYTYPE);
		LocoVO.setLOCO_ORDER_STATUS(LOCO_ORDER_STATUS);
		LocoVO.setLOCO_PAY_STATUS(LOCO_PAY_STATUS);
		LocoVO.setLOCO_ORDER_TIME(LOCO_ORDER_TIME);
		LocoVO.setLOCO_RESERVE_TIME(LOCO_RESERVE_TIME);
		LocoVO.setLOCO_TABLE_NUM(LOCO_TABLE_NUM);
		LocoVO.setLOCO_NOTE(LOCO_NOTE);
		
		dao.insert(LocoVO);				
		return LocoVO; 

	}
	public void deleteLoco(Integer LOCO_ID) {		
		dao.delete(LOCO_ID);				
	}	
	public  LocoVO findByPrimaryKeyLoco(Integer LOCO_ID) {
		return dao.findByPrimaryKey(LOCO_ID);
	}
	public List<LocoVO> getAll() {
		return dao.getAll();
	}
	public List<LocoVO> findSmemOrder(Integer LOCO_SMEM_ID){
		return dao.findSmemOrder(LOCO_SMEM_ID);
	}
	public List<LocoVO> findBmemOrder(Integer LOCO_BMEM_ID){
		return dao.findBmemOrder(LOCO_BMEM_ID);
	}
	
	//抓廳房訂單時間
	public List<LocoVO> getTime(Integer LOCO_LOCR_ID){
		
		return dao.getTime(LOCO_LOCR_ID);
	}
	
	public void updateorder(Integer LOCO_ORDER_STATUS,Integer LOCO_ID){
		 dao.updateorder(LOCO_ORDER_STATUS,LOCO_ID);
	}
	
	public void updatepay(Integer LOCO_PAY_STATUS,Integer LOCO_ID){
		dao.updatepay(LOCO_PAY_STATUS,LOCO_ID);
	}
	
	
	
	
	
	

}
