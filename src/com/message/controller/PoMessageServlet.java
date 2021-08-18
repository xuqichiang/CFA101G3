package com.message.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.model.MemVO;
import com.message.model.MessageService;

@WebServlet("/message/poMessageServlet")
public class PoMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
// 接收從show-article-message.html 的留言訊息

	public PoMessageServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		// 接收參數(ajax傳來的)
		Integer mes_post_id = Integer.parseInt(request.getParameter("mes_post_id"));
		String mes_content = request.getParameter("mes_content").trim();
		Date mes_time = new Date(new java.util.Date().getTime());

		// 須先取得會員身份
		HttpSession session = request.getSession();
		MemVO user = (MemVO) session.getAttribute("user");
		Integer mes_mem_id = user.getMem_id();
		
		//調用Service方法
		MessageService messageService = new MessageService();
		messageService.addMessage(mes_post_id, mes_mem_id, mes_content, mes_time);
		response.getWriter().print("留言成功");//成功送到資料庫會出現
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
