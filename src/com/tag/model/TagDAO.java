package com.tag.model;

import java.util.*;


interface TagDAO {
	TagVO insertTag(TagVO tagVO);
    void updateTag(TagVO tagVO);
    void deleteTag(Integer tag_id);
    TagVO findOneTagByTagId(Integer tag_id);       
    TagVO findOneTagByTagName(String tag_name);  
    List<TagVO> getAllTag();      

}
