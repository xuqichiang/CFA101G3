package com.photoorder.model;

import java.util.List;
import java.util.Map;

import com.member.model.MemVO;

public class PhooService {

	private PhooDAO dao;

	public PhooService() {
		dao = new PhooDAOImpl();
	}
	// 新增預約訂單
	public void addPhoo(PhooVO phooVO) {
		dao.insert(phooVO);
	}
	// 買家查詢預約訂單
	public List getBmemReserveForm(Integer phoo_bmem_id) {
		return dao.getBmemReserveForm(phoo_bmem_id);
	}
	// 買家預約訂單查詢詳細資料
	public Map getBmemOneForm(Integer phoo_id) {
		return dao.getBmemOneForm(phoo_id);
	}
	// 婚攝賣家查詢預約訂單
	public List getSmemReserveForm(Integer phoo_smem_id) {
		return dao.getSmemReserveForm(phoo_smem_id);
	}
	// 婚攝預約訂單查詢詳細資料
	public Map getSmemOneForm(Integer phoo_id) {
		return dao.getSmemOneForm(phoo_id);
	}
	// 抓婚攝店家已預約時間
	public List<PhooVO> getTime(Integer phoo_phog_id) {
		return dao.getTime(phoo_phog_id);
	}
	// 修改訂單狀態
	public void updateOrder(Integer phoo_order_status, Integer phoo_id) {
		dao.updateOrder(phoo_order_status, phoo_id);
	}
	// 修改付狀態
	public void updatePay(Integer phoo_pay_status, Integer phoo_id) {
		dao.updatePay(phoo_pay_status, phoo_id);
	}
	// 取消預約
	public void cancelReserve(Integer phoo_id) {
		PhooVO phooVO = dao.findByPhooId(phoo_id);
		Integer phoo_pay_status = phooVO.getPhoo_pay_status();
		if (phoo_pay_status.equals(3)) {// 判斷是否為已付款
			dao.updateOrder(4, phoo_id);
			dao.updatePay(5, phoo_id);// 若已付款修改為退款中
		} else {
			dao.updateOrder(4, phoo_id);// 若未付款僅取消預約
		}
	}
	//以PK查詢訂單
	public PhooVO findByPhooId(Integer phoo_id) {
		return dao.findByPhooId(phoo_id);
	}
}
