package com.member.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.member.model.MemService;
import com.member.model.MemVO;

@WebServlet("/member/GetOneServlet")
public class GetOneServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		Integer mem_id= Integer.parseInt(request.getParameter("memid"));
		System.out.println(mem_id);
		MemService service = new MemService();
		MemVO vo = service.getOne(mem_id);
		
		PrintWriter writer = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		String data = mapper.writeValueAsString(vo);
		writer.print(data);
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
