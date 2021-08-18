package com.locationprogram.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.locationroom.model.LocrVO;
import com.member.model.MemVO;

public class LocpService {
	
	private LocpDAO dao;
	
	public LocpService() {
		
		dao = new LocpDAOImpl();
	}
	
	//新增方案
	public void addLocp(LocpVO locpvo) {
		dao.insert(locpvo);
	}
	
	//用方案的PK來搜尋方案
	public LocpVO getOneLocpByLocpid(Integer locp_id) {
		return dao.getOneByLocpid(locp_id);
	}
	
	//搜尋一個場地賣家的所有方案
	public List<LocpVO> getOneLocpBySmemid(Integer locp_smem_id) {
		return dao.getOneLocpBySmemid(locp_smem_id);
	}
	
	//用方案名稱來搜尋
	public LocpVO getOneLocpByLocpname(String locp_name) {
		return dao.getOneLocpByLocpname(locp_name);
	}
	
	//更新方案
	public void updateLocp(LocpVO locpvo) {
		dao.update(locpvo); 
	}
	
	//刪除方案
	public void deletelocp(Integer locp_id) {
		dao.delete(locp_id);
	}
	
	//得到最後一個方案的PK
	public LocpVO getLastID() {
		return dao.getLastID();
	}
	
	//搜尋全部的方案
	public List<LocpVO> getAll(){
		return dao.getAll();
	}
	
	//搜尋全部場地賣家
	public List<MemVO> getAllLoc(){
		return dao.getAllLoc();
	}
	
	//搜尋全部方案價格由高到低排列
	public List<LocpVO> getAllHighToLow(){
		return dao.getAllHighToLow();
	}
	
	//搜尋全部方案價格由低到高排列
	public List<LocpVO> getAllLowToHigh(){
		return dao.getAllLowToHigh();
	}
	
	//搜尋全部方案的logo價格由低到高
	public List<LocpVO> getAllLogoLowToHigh(){
		return dao.getAllLogoLowToHigh();
	}
	
	//搜尋全部方案的logo價格由高到低
	public List<LocpVO> getAllLogoHighToLow(){
		return dao.getAllLogoHighToLow();
	}

	//搜尋一個場地賣家的所有廳房
	public List<LocrVO> getAllLocrBySmemid(Integer locr_smem_id){
		return dao.getAllLocrBySmemid(locr_smem_id);
	}
	
	//取得所有方案狀態為上架，方案時間在期限內
	public Set<LocpVO> getAll_by_status_endtime(){
		List<LocpVO> all = dao.getAll();
		Set<LocpVO> set = new HashSet<LocpVO>();
		for(LocpVO locpVO:all) {
			if(locpVO.getLocp_end_time().getTime() > new java.util.Date().getTime() && locpVO.getLocp_status().equals(1)) {
				set.add(locpVO);
			}
		}
		return set;
	}
}
