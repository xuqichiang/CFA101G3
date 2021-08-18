package com.locationbackorder.model;

import java.util.List;

import com.locpage.utils.PageVO;
import com.member.model.MemVO;

public class LocoService {
	
private LocoDAO dao;
	
	public LocoService() {
		
		dao = new LocoDAOImpl();
	}
	
	//查詢全部的訂單
	public List getAllLocorder(){
		return dao.getAllLocorder();
	}
	
	//用訂單狀態來撈訂單資訊
	public List getOrderByStatus(Integer loco_order_status){
		return dao.getOrderByStatus(loco_order_status);
	}
	
	//用訂單編號來查詢訂單資訊
	public LocoVO getOneLocorderByPrimaryKey(Integer loco_id) {
		return dao.getOneLocorderByPrimaryKey(loco_id);
	}
	
	//用訂單的ID來撈取姓名
	public MemVO getMemVOByPrimaryKey(Integer mem_id) {
		return dao.getMemVOByPrimaryKey(mem_id);
	}
	
	//查詢所有訂單，用分頁的方式顯示
	public PageVO findByPage(Integer currentPage, Integer rows) {
		
		PageVO pageVO = new PageVO();
		pageVO.setCurrentPage(currentPage);
		pageVO.setRows(rows);
		//查詢所有訂單數
		Integer totalCount = dao.findTotalCount();
		pageVO.setTotalCount(totalCount);
		//當頁的開始
		Integer start = (currentPage - 1)* rows;
		List list = dao.findByPage(start, rows);
		pageVO.setList(list);
		//總頁數
		Integer totalPage= (totalCount % rows) == 0 ? totalCount/rows : (totalCount/rows)+1;
		pageVO.setTotalPage(totalPage);
		
		return pageVO;
	}

	//用訂單狀態查詢訂單，並用分頁的方式展示
	public PageVO findByPageWithStatus(Integer currentPage, Integer rows, Integer loco_status) {

		PageVO pageVO = new PageVO();
		pageVO.setCurrentPage(currentPage);
		pageVO.setRows(rows);
		
		Integer totalCount = dao.findTotalCountWithStatus(loco_status);
		pageVO.setTotalCount(totalCount);
		
		Integer start = (currentPage - 1)* rows;
		List list = dao.findByPageWithStatus(start, rows, loco_status);
		pageVO.setList(list);
		
		Integer totalPage= (totalCount % rows) == 0 ? totalCount/rows : (totalCount/rows)+1;
		pageVO.setTotalPage(totalPage);
		
		return pageVO;
	}
}
