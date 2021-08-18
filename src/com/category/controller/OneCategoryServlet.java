package com.category.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.category.model.CategoryService;
import com.category.model.CategoryVO;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/category/OneCategoryServelt")
public class OneCategoryServlet extends HttpServlet {

//目前無使用
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8"); 
		CategoryService categoryService = new CategoryService();
	    Integer  cat_id = Integer.parseInt(request.getParameter("cat_id"));
		CategoryVO one = categoryService.getOneCategory(cat_id);
		
		ObjectMapper mapper = new ObjectMapper();
		String categoryOne = mapper.writeValueAsString(one);//轉成JSON格式
		response.getWriter().print(categoryOne);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
