package com.proJOINmem.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proJOINmem.model.ProJoinMemService;
import com.proJOINmem.model.ProJoinMemVO;

@WebServlet("/ProJoinMemServlet/ProJoinMemServlet")
public class ProJoinMemServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//設置頭
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		//取得參數
		Integer pro_id = Integer.parseInt(request.getParameter("pro_id"));
		System.out.println(pro_id);
		
		//從DB找到資料
		ProJoinMemService service = new ProJoinMemService();
		ProJoinMemVO vo = service.findByPid(pro_id);
		
		//開始輸出
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper(); //JSON
		String data = mapper.writeValueAsString(vo); //JSON
		out.println(data);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
