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

@WebServlet("/workphoto/worGetFKServlet")
public class WorGetFKServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		
		Integer phog_id = new Integer (request.getParameter("phog_id"));
		WorService worSvc = new WorService();
		List<WorVO> list = worSvc.findByForeignKey(phog_id);
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(list);
		
		PrintWriter out = response.getWriter();
		out.print(json);
	}
}
