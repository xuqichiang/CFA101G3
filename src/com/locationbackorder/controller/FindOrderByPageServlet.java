package com.locationbackorder.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.locationbackorder.model.LocoService;
import com.locpage.utils.PageVO;

@WebServlet("/locationorder/findOrderByPageServlet")
public class FindOrderByPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		String action = request.getParameter("action");
		Integer currentPage = Integer.parseInt(request.getParameter("currentPage"));//當前頁碼
		Integer rows = Integer.parseInt(request.getParameter("rows"));//每頁顯示訂單數
		
		PageVO pageVO = null;
		LocoService locosvc = new LocoService();
		
		if("infowithstatus".equals(action)) {
			Integer loco_status = Integer.parseInt(request.getParameter("loco_status"));
			pageVO = locosvc.findByPageWithStatus(currentPage, rows, loco_status);
		}
		
		if("infowithnostatus".equals(action)) {
			pageVO = locosvc.findByPage(currentPage, rows);
		}
		
		ObjectMapper mapper = new ObjectMapper(); 
		String data = mapper.writeValueAsString(pageVO);//轉JSON格式
		out.print(data);
	}

}
