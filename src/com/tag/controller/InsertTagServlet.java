package com.tag.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.tag.model.TagService;
import com.tag.model.TagVO;

//搭配po_article.html  接收會員輸入的標籤 並存回資料庫
@WebServlet("/tag/insertTagServlet")
public class InsertTagServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		// 取得參數(ajax傳送來的需求)
		String tag_name = request.getParameter("tag_name").trim();
		
//		HttpSession session = request.getSession();
//		MemVO user = (MemVO) session.getAttribute("user");
//		Integer post_mem_id = user.getMem_id();
		//會員??//
//		Integer post_mem_id = 2; 


		// 調用Service與方法
		TagService tagService = new TagService();
//		TagVO tagVo = tagService.addTag(tag_name);
		response.getWriter().print("標籤OK");//若成功送到資料庫會出現的字

	}
	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
