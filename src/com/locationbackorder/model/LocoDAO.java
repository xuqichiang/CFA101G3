package com.locationbackorder.model;

import java.util.List;

import com.member.model.MemVO;

public interface LocoDAO {
	
	public void insertLocorder(LocoVO locoVO);
	public void updateLocorder(LocoVO locoVO);
	public void deleteLocorder(Integer loco_id);
	//用訂單編號來查詢訂單資訊
	public LocoVO getOneLocorderByPrimaryKey(Integer loco_id);
	//撈所有場地訂單
	public List getAllLocorder();
	//用訂單狀態來撈訂單資訊
	public List getOrderByStatus(Integer loco_order_status);
	//用訂單的ID來撈取姓名
	public MemVO getMemVOByPrimaryKey(Integer mem_id);
	//查詢所有訂單數
	public Integer findTotalCount();
	//用頁數來查詢每頁顯示的訂單數
	public List findByPage(Integer start, Integer rows);
	//用訂單狀態來查詢所有的訂單數
	public Integer findTotalCountWithStatus(Integer loco_status);
	//用訂單狀態來查詢每頁顯示的訂單數
	public List findByPageWithStatus(Integer start, Integer rows, Integer loco_status);
}
