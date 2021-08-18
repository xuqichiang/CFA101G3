package com.post.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.post.model.PostService;

//fourmindex.html  透過文章分類抓出所有文章
@WebServlet("/post/showPostByCatIdServlet")
public class ShowPostByCatIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ShowPostByCatIdServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PostService postService = new PostService();
		Integer cat_id = Integer.parseInt(request.getParameter("cat_id"));
		List showPostByCatId = postService.findByCatId(cat_id);

		ObjectMapper mapper = new ObjectMapper();
		String writeValueAsString = mapper.writeValueAsString(showPostByCatId); // 轉JSON格式
		System.out.println(writeValueAsString);
		PrintWriter out = response.getWriter();
		out.print(writeValueAsString);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
