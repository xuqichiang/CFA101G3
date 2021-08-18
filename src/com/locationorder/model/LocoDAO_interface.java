package com.locationorder.model;

import java.util.List;

public interface LocoDAO_interface {


	public void insert(LocoVO LocoVO);
	public void update(LocoVO LocoVO);
	public void delete(Integer LOCO_ID);
	public LocoVO findByPrimaryKey(Integer LOCO_ID);
	public List<LocoVO> getAll() ;
	
	public List<LocoVO> findSmemOrder(Integer LOCO_SMEM_ID);
	public List<LocoVO> findBmemOrder(Integer LOCO_BMEM_ID);
	
	
	public List<LocoVO> getTime(Integer LOCO_LOCR_ID);
	
	
	public  void updateorder(Integer LOCO_ORDER_STATUS,Integer LOCO_ID);
	
	public  void updatepay(Integer LOCO_PAY_STATUS,Integer LOCO_ID);
	
	
	
	
	
}
