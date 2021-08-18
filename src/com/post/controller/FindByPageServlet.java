package com.post.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.post.model.PostPageVO;
import com.post.model.PostService;


@WebServlet("/post/findByPageServlet")
public class FindByPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public FindByPageServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		Integer currentPage = Integer.parseInt(request.getParameter("currentPage"));//當前頁碼
		Integer rows = Integer.parseInt(request.getParameter("rows"));//每頁顯示文章數

		PostService postService = new PostService();
		PostPageVO postPageVO = postService.findByPage(currentPage, rows);
		ObjectMapper mapper = new ObjectMapper();
		String writeValueAsString = mapper.writeValueAsString(postPageVO); //轉JSON格式
		PrintWriter out = response.getWriter();
		out.print(writeValueAsString);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
