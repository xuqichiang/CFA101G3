package com.locationprogram.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.locationprogram.model.LocpService;
import com.locationprogram.model.LocpVO;
import com.locationroom.model.LocrVO;

@WebServlet("/locationprogram/getOneLocSellerInfoServlet")
public class GetOneLocSellerInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		String action = request.getParameter("action");
		LocpService locpsvc = new LocpService();
		List list = null;
		Integer memid = Integer.parseInt(request.getParameter("memid"));
		
		if("getLocationProgram".equals(action)) {
			list = locpsvc.getOneLocpBySmemid(memid);
		}
		
		if("getLocationRoom".equals(action)) {
			list = locpsvc.getAllLocrBySmemid(memid);
		}
		
		ObjectMapper mapper = new ObjectMapper(); 
		String data = mapper.writeValueAsString(list);
		
		out.println(data);
		
		
	}

}
