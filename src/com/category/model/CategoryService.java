package com.category.model;

import java.util.List;

public class CategoryService {
	private CategoryDAO dao = new CategoryDAOImpl();
	
	public void addCategory (CategoryVO category) {
		dao.insert(category);
	}
	public void updateCategory(CategoryVO category) {
		dao.update(category);
	}
	public void deleteCategory(Integer cat_id) {
		dao.delete(cat_id);
	}
	public CategoryVO getOneCategory(Integer cat_id) {
		return dao.findByCatId(cat_id);
	}
	public List<CategoryVO> getAll() {
		return dao.getAll();
	}
	public Integer getPostCountByCatId(Integer cat_id) {
		return dao.getPostCountByCatId(cat_id);
		
	
	}

}
