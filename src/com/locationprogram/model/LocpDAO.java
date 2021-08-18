package com.locationprogram.model;

import java.util.List;

import com.locationroom.model.LocrVO;
import com.member.model.MemVO;

public interface LocpDAO {
	
	//新增方案
	public void insert(LocpVO locpVO);
	//更新方案
	public void update(LocpVO locpVO);
	//刪除方案
	public void delete(Integer locp_id);
	//用方案的PK來搜尋方案
	public LocpVO getOneByLocpid(Integer locp_id);
	//搜尋一個場地賣家的所有方案
	public List<LocpVO> getOneLocpBySmemid(Integer locp_smem_id);
	//用方案名稱來搜尋
	public LocpVO getOneLocpByLocpname(String locp_name);
	//得到最後一個方案的PK
	public LocpVO getLastID();
	//搜尋全部的方案
	public List<LocpVO> getAll();
	//搜尋全部場地賣家
	public List<MemVO> getAllLoc();
	//搜尋全部方案價格由高到低排列
	public List<LocpVO> getAllHighToLow();
	//搜尋全部方案價格由低到高排列
	public List<LocpVO> getAllLowToHigh();
	//搜尋全部方案的logo價格由低到高
	public List<LocpVO> getAllLogoLowToHigh();
	//搜尋全部方案的logo價格由高到低
	public List<LocpVO> getAllLogoHighToLow();
	//搜尋一個場地賣家的所有廳房
	public List<LocrVO> getAllLocrBySmemid(Integer locr_smem_id);
}
