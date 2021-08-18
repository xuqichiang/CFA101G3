package com.locationbackorder.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

@WebServlet("/locationorder/getOrderBySessionServlet")
public class GetOrderBySessionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String action = request.getParameter("action");
		
		
		if("getorderbysession".equals(action)) {
			
			HttpSession session = request.getSession();
			Object map = session.getAttribute("map");
			
			if(map != null) {
				
				ObjectMapper mapper = new ObjectMapper(); 
				String data = mapper.writeValueAsString(map);
				out.print(data);
			}
			
		}
		
	}

}
