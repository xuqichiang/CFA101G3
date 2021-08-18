package com.product.model;
import java.util.List;
public class ProService {

	private ProDAO dao;
	
	public ProService() {
		dao = new ProDAOimpl();
	}
	
	//查詢所有商品
	public List<ProVO> getAll(){
		return dao.getAll();
	}
	//新增商品
	public ProVO addPro(Integer pro_smem_id,String pro_name,Integer pro_price,String pro_content,Integer pro_proc_id,Integer pro_status) {
		ProVO proVO = new ProVO();
//		proVO.setPro_id(pro_id);
		proVO.setPro_smem_id(pro_smem_id);
		proVO.setPro_name(pro_name);
		proVO.setPro_price(pro_price);
		proVO.setPro_content(pro_content);
		proVO.setPro_proc_id(pro_proc_id);
		proVO.setPro_status(pro_status);
		dao.insert(proVO);
		return proVO;
	}
	//尋找單筆商品PK
	public ProVO getOnePro(Integer pro_id) {
		return dao.findByPK(pro_id);
	}
	//尋找單筆商品Seller
	public List<ProVO> getOneProBySeller(Integer pro_smem_id) {
		return dao.findBySeller(pro_smem_id);
	}
	//尋找商品分頁
	public List<ProVO> findByCateList(Integer pro_proc_id){
		return dao.findByCateList(pro_proc_id);
	}
	
	//下架商品
	public void deletePro(Integer pro_id) {
		dao.delete(pro_id);
	}
	//修改商品
	public ProVO updatePro(Integer pro_id,String pro_name,Integer pro_price,
			String pro_content,Integer pro_smem_id,Integer pro_proc_id,Integer pro_status) {
		//newVO
		ProVO proVO = new ProVO();
		//SET它
		proVO.setPro_id(pro_id);
		proVO.setPro_name(pro_name);
		proVO.setPro_price(pro_price);
		proVO.setPro_content(pro_content);
		proVO.setPro_smem_id(pro_smem_id);
		proVO.setPro_proc_id(pro_proc_id);
		proVO.setPro_status(pro_status);
		//執行更新
		dao.update(proVO);
		return proVO;
	}
	//查詢商品
	public List<ProVO> findBySQLList(String pro_name){
		return dao.findBySQLList(pro_name); 
	}
	
	//價格查詢
	public List<ProVO> findByCateCheapList(Integer pro_proc_id){
		return dao.findByCateCheapList(pro_proc_id);
	}
	
	public List<ProVO> findByCateExpList(Integer pro_proc_id){
		return dao.findByCateExpList(pro_proc_id);
	}
	
	public List<ProVO> findBySQLList2(Integer pro_price,Integer pro_price2 , Integer pro_proc_id,String pro_name){
		return dao.findBySQLList2(pro_price, pro_price2, pro_proc_id, pro_name);
	}
	
	public List<ProVO> findBySQLList3(Integer pro_price,Integer pro_price2 , Integer pro_proc_id, Integer pro_smem_id , String pro_name ){
		return dao.findBySQLList3(pro_price, pro_price2, pro_proc_id, pro_smem_id, pro_name);
	}
	//隨機主頁秀圖
	public List<ProVO> ProMain(Integer pro_proc_id){
		return dao.ProMain(pro_proc_id);
	}
	
	public ProVO findBySmemID(Integer pro_smem_id) {
		return dao.findBySmemID(pro_smem_id); 
	}
}
