package com.locationprogram.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.locationprogram.model.LocpService;
import com.locationprogram.model.LocpVO;

@WebServlet("/locationprogram/getOneByLocpnameServlet")
public class GetOneByLocpnameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String locpname = request.getParameter("locpname");
		LocpService locpsvc = new LocpService();
		LocpVO locpVO = locpsvc.getOneLocpByLocpname(locpname);
		
		ObjectMapper mapper = new ObjectMapper(); 
		String data = mapper.writeValueAsString(locpVO);
		
		out.println(data);
	}

}
