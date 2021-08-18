package com.shop_order.model;


import java.util.HashMap;
import java.util.List;

import com.product.model.ProVO;
import com.shop_order_item.model.SpoiVO;

public class SpoService {
	
	SpoDAOImpl dao = new SpoDAOImpl();
	//新增多筆商品訂單(返回實付金額)
	public List<Integer> insertMultiple(HashMap<Integer, List<SpoiVO>> smemMap,String name,String phone,Integer paytype,HashMap<Integer,Integer> postageMap ,String address,Integer bmem_id) {
		return dao.insertMultiple(smemMap, name, phone, paytype, postageMap, address, bmem_id);
	}
	
	//更新多筆訂單付款狀態
	public void updateAllSpo_pay_status(List<Integer> spo_idList,Integer spo_pay_status) {
		dao.updateAllSpo_pay_status(spo_idList, spo_pay_status);
	}
	
	//查詢多筆訂單
	public List<SpoVO> getOrdall(Integer spo_smem_id){
		return dao.getOrdall(spo_smem_id);
	}
	
	//查詢貨物狀態
	public List<SpoVO> getOrdReturn(Integer spo_smem_id,Integer spo_cargo_status){
		return dao.getOrdReturn(spo_smem_id, spo_cargo_status);
	}
	
	//查詢付款狀態
	public List<SpoVO> getOrdRefund(Integer spo_smem_id,Integer spo_pay_status){
		return dao.getOrdRefund(spo_smem_id, spo_pay_status);
	}
	
	
	//查詢單筆訂單
	public SpoVO findByPK(Integer spo_id) {
		return dao.findByPK(spo_id);
	}
	
	//更新訂單狀態
	public void update_spo_status(Integer spo_id,Integer spo_status) {
		dao.update_spo_status(spo_id, spo_status);
	}
	//更新付款狀態
	public void update_spo_pay_status(Integer spo_id,Integer spo_pay_status) {
		dao.update_spo_pay_status(spo_id, spo_pay_status);
	}
	//更新發貨狀態
	public void update_spo_cargo_status(Integer spo_id,Integer spo_cargo_status) {
		dao.update_spo_cargo_status(spo_id, spo_cargo_status);
	}
	//取消訂單
	public void cancelOrder(Integer spo_id) {
		SpoVO spoVO = dao.getOne(spo_id);
		Integer spo_pay_status = spoVO.getSpo_pay_status();
		if(spo_pay_status.equals(3)) {//判斷是否為已付款
			dao.update_spo_status(spo_id, 3);
			dao.update_spo_pay_status(spo_id, 4);//若已付款修改為退款中
		}else {
			dao.update_spo_status(spo_id, 3);//若未付款僅取消訂單
		}
	}
	
	//獲取所有訂單
	public List<SpoVO> getAll(){
		return dao.getAll();
	}
	
	//獲取單筆訂單
	public SpoVO getOne(Integer spo_id) {
		return dao.getOne(spo_id);
	}
	
	
	//更新付款方式
	public void update_spo_paytype(Integer spo_id, Integer spo_paytype) {
		dao.update_spo_paytype(spo_id, spo_paytype);
	}
	
	//獲取訂單藉由買家會員ID
	public List<SpoVO> findByForeignKey(Integer spo_bmem_id){
		return dao.findByForeignKey(spo_bmem_id);
	}
}