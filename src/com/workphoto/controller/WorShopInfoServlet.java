package com.workphoto.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workphoto.model.WorService;

@WebServlet("/workphoto/worShopInfoServlet")
public class WorShopInfoServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//設置頭
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		//取得參數
		Integer wor_id = new Integer (request.getParameter("wor_id"));//作品集ID
		WorService worSvc = new WorService();
		Map shopInfo = worSvc.getShopInfo(wor_id);
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(shopInfo);//轉成JSON格式
		PrintWriter out = response.getWriter();
		out.print(json);
		
		
		
	}

}
