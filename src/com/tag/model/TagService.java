package com.tag.model;

import java.util.ArrayList;
import java.util.List;

public class TagService {
	private TagDAO dao = new TagDAOImpl();

	public List<TagVO> addTag(String[] tagArray) {
//標籤list，已存在則返回創過的舊標籤VO，若不存在則新增標籤後返回VO(id+名稱)
		List<TagVO> list = new ArrayList<TagVO>(); 
		for (int i = 0; i < tagArray.length; i++) {
			TagVO tagVO = dao.findOneTagByTagName(tagArray[i]);
			if (tagVO != null) { //有找到舊的標籤
				list.add(tagVO);//在list加入舊標籤
			} else {
				TagVO addTagVO = new TagVO();//創一個新VO
				addTagVO.setTag_name(tagArray[i]);//透過他set新標籤
				TagVO resultTagVO = dao.insertTag(addTagVO);
				list.add(resultTagVO);//在list加入新標籤
			}
		}
		return list;//將判斷過的新舊混合list丟回poArticleServlet 讓他存到資料庫
	}

	public TagVO findOneTagByTagId(Integer tag_id) {
		return dao.findOneTagByTagId(tag_id);
	}

	public List<TagVO> getAllTag() {
		return dao.getAllTag();

	}

}
