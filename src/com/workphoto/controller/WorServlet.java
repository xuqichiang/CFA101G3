package com.workphoto.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workphoto.model.WorService;
import com.workphoto.model.WorVO;

@WebServlet("/workphoto/worServlet")//專案路徑CFA1101G3/Workphoto/WorServlet
public class WorServlet extends HttpServlet {
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		WorService worSvc = new WorService();
		List<WorVO> list = worSvc.getAll();//調用方法
//		System.out.println(list);
		
		ObjectMapper mapper = new ObjectMapper();//創建
		String json = mapper.writeValueAsString(list);//轉換方法：Object轉成JSNO字串格式
		PrintWriter out = response.getWriter();//Servlet輸出
		out.print(json);
	}
}
