package com.product_images.model;

import java.util.List;

import com.product.model.ProVO;
public interface ProImgDAO {
	
	//捷哥流找PK
	public ProImgVO findByPrimaryKey(Integer proi_id);
	//捷哥流找FK
	public List<ProImgVO> findByForeignKey(Integer proi_pro_id);
	//主頁秀圖
	List<ProImgVO> getall();
	//商品秀圖
	List<ProImgVO> findByFKlist(Integer proi_pro_id);
	//新增商品圖片
	void insertProImg(ProImgVO proImgVO);
	//刪除商品圖片
	void deleteImg(Integer proi_id);
	//商品分類找FK
	public List<ProImgVO> findByCateList(Integer pro_proc_id);
	//根據價格排序
	public List<ProImgVO> findByCateCheapList(Integer pro_proc_id);
	public List<ProImgVO> findByCateExpList(Integer pro_proc_id);
}
