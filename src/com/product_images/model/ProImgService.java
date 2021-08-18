package com.product_images.model;

import java.util.List;

import com.product.model.ProDAOimpl;

public class ProImgService {

	private ProImgDAO dao = null;
	
	public ProImgService() {
		dao = new ProImgDAOimpl();
	}
//	ProImgDAO dao = new ProImgDAOimpl(); 
	
//	public void insertProImg(ProImgVO proImgVO) {
//		
//	ProImgVO vo = new ProImgVO();
//	vo.setProi_pro_id(proi_pro_id);
//	
//}
	public List<ProImgVO> findByFKlist(Integer proi_pro_id){
		return dao.findByFKlist(proi_pro_id);
	}
	
	public ProImgVO findByPrimaryKey(Integer proi_id) {
		return dao.findByPrimaryKey(proi_id);
	}
	
	public List<ProImgVO> findByForeignKey(Integer proi_pro_id){
		return dao.findByForeignKey(proi_pro_id);
	}
	
	public List<ProImgVO> findByCateList(Integer pro_proc_id){
		return dao.findByCateList(pro_proc_id);
	}
	
	public List<ProImgVO> findByCateCheap(Integer pro_proc_id){
		return dao.findByCateCheapList(pro_proc_id);
	}
	
	public List<ProImgVO> findByCateExp(Integer pro_proc_id){
		return dao.findByCateExpList(pro_proc_id);
	}
	
	public void deleteImg(Integer proi_id) {
		dao.deleteImg(proi_id);
	}
}
