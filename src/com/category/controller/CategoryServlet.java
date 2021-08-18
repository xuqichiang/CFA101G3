package com.category.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.category.model.CategoryService;
import com.category.model.CategoryVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.post.model.PostService;


@WebServlet("/category/categoryServlet")
public class CategoryServlet extends HttpServlet {

	  //搭配fourmindex.html  抓出所有文章分類 於精選文章下方區塊
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
//String action = request.getParameter("action");
			
			CategoryService categoryService = new CategoryService();
			List<CategoryVO> all = categoryService.getAll();
			
//CategoryService categoryService2 = new CategoryService();
//Integer cat_id = Integer.parseInt(request.getParameter("cat_id"));
//Integer postCountByCatId = categoryService2.getPostCountByCatId(cat_id);
//			
			
			
			ObjectMapper mapper = new ObjectMapper();
			String categoryAll = mapper.writeValueAsString(all);//轉成JSON格式
//String postCount = mapper.writeValueAsString(postCountByCatId);//轉成JSON格式
			PrintWriter out = response.getWriter();
			out.print(categoryAll);
//out.print(postCount);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
