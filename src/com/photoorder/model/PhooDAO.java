package com.photoorder.model;

import java.util.List;
import java.util.Map;

import com.member.model.MemVO;

public interface PhooDAO {
	//新增預約訂單
	void insert(PhooVO phooVO);
	//買家查詢預約訂單all
	List getBmemReserveForm(Integer phoo_bmem_id);
	//買家預約詳細資訊
	Map getBmemOneForm(Integer phoo_id);
	//婚攝賣家查詢預約訂單all
	List getSmemReserveForm(Integer phoo_smem_id);
	//婚攝賣家預約詳細資訊
	Map getSmemOneForm(Integer phoo_id);
	//婚攝店家攝影師預約時間
	List<PhooVO> getTime(Integer phoo_phog_id);
	//婚攝賣家修改訂單狀態
	void updateOrder(Integer phoo_order_status, Integer phoo_id);
	//買家修改付款狀態
	void updatePay(Integer phoo_pay_status, Integer phoo_id);
	//以PK查詢訂單
	PhooVO findByPhooId(Integer phoo_id);
}
