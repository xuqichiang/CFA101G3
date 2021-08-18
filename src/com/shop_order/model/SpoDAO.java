package com.shop_order.model;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

import com.product.model.ProVO;
import com.shop_order_item.model.SpoiVO;

public interface SpoDAO {
	//新增商品訂單(需藉由別人的連線)(單筆)
	public Integer insert(SpoVO spoVO,List<SpoiVO> spoiVOList,Connection con);
	//新增多筆商品訂單
	public List<Integer> insertMultiple(HashMap<Integer, List<SpoiVO>> smemMap,String name,String phone,Integer paytype,HashMap<Integer,Integer> postageMap,String address,Integer bmem_id);
	//更新多筆訂單付款狀態
	public void updateAllSpo_pay_status(List<Integer> spo_idList,Integer spo_pay_status);
	//依據店家查詢多筆訂單
	List<SpoVO> getOrdall(Integer spo_smem_id);
	//查詢未出貨訂單(取消用)
	List<SpoVO> getOrdReturn(Integer spo_smem_id,Integer spo_cargo_status);
	//查詢退款中訂單
	List<SpoVO> getOrdRefund(Integer spo_smem_id,Integer spo_pay_status);
	//查詢單筆訂單
	SpoVO findByPK(Integer spo_id);
	//取得所有訂單
	public List<SpoVO> getAll();
	//獲取單筆訂單
	public SpoVO getOne(Integer spo_id);
	//更新訂單狀態
	public void update_spo_status(Integer spo_id,Integer spo_status);
	//更新付款狀態
	public void update_spo_pay_status(Integer spo_id,Integer spo_pay_status);
	//更新發貨狀態
	public void update_spo_cargo_status(Integer spo_id,Integer spo_cargo_status);
	//更新付款方式
	public void update_spo_paytype(Integer spo_id,Integer spo_paytype);
	//獲取訂單藉由買家會員ID
	public List<SpoVO> findByForeignKey(Integer spo_bmem_id);
}
