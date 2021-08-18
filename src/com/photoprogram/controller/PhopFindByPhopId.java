package com.photoprogram.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.photoprogram.model.PhopService;
import com.photoprogram.model.PhopVO;

@WebServlet("/photoprogram/phopFindByPhopId")

public class PhopFindByPhopId extends HttpServlet {
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html ;charset= utf-8");
		Integer phop_id = Integer.parseInt(request.getParameter("phop_id").trim());

		PhopService Service = new PhopService();
		
		PhopVO phopVO = Service.findByPhopId(phop_id);
//		System.out.println(phop_id);
		
		ObjectMapper mapper = new ObjectMapper();
		String data = mapper.writeValueAsString(phopVO);
		PrintWriter out = response.getWriter();
		out.print(data);
		
	}

}
