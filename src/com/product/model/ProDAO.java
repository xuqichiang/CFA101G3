package com.product.model;

import java.util.List;

import com.mysql.cj.x.protobuf.MysqlxCrud.Find;

public interface ProDAO {
	
	//查詢商品
	List<ProVO> getAll();
	//商品上架
	void insert(ProVO proVO);
	//商品下架
	void delete(Integer pro_id);
	//尋找單筆商品PK
	ProVO findByPK(Integer pro_id);
	//尋找單筆商品PK
	ProVO findBySmemID(Integer pro_smem_id);
	//修改商品
	void update(ProVO proVO);
	//根據會員店家ID尋找商品
	List<ProVO> findBySeller(Integer pro_smem_id);
	//搜尋商品分頁
	List<ProVO> findByCateList(Integer pro_proc_id);
	//依價格搜尋
	List<ProVO> findByCateCheapList(Integer pro_proc_id);
	List<ProVO> findByCateExpList(Integer pro_proc_id);
	//查詢功能
	List<ProVO> findBySQLList(String pro_name);
	//複合查詢功能
	List<ProVO> findBySQLList2(Integer pro_price,Integer pro_price2 , Integer pro_proc_id,String pro_name);
	//複合店家查詢功能
	List<ProVO> findBySQLList3(Integer pro_price,Integer pro_price2 , Integer pro_proc_id, Integer pro_smem_id , String pro_name );
	//商品主頁隨機秀圖
	List<ProVO> ProMain(Integer pro_proc_id);
}
