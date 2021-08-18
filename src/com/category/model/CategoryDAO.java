package com.category.model;

import java.util.*;

interface CategoryDAO {
          void insert(CategoryVO category);
          void update(CategoryVO category);
          void delete(Integer cat_id);
          CategoryVO findByCatId(Integer cat_id);       
          List<CategoryVO> getAll();     
      	//查詢總共幾篇文章(Join post表格)
      	Integer getPostCountByCatId(Integer cat_id);
          
}