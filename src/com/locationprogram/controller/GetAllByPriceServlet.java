package com.locationprogram.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.locationprogram.model.LocpService;
import com.locationprogram.model.LocpVO;

@WebServlet("/locationprogram/getAllByPriceServlet")
public class GetAllByPriceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		List<LocpVO> list = new ArrayList<LocpVO>();
		
		String condition = request.getParameter("action");
		
		if("hightolow".equals(condition)) {
			
			LocpService locpsvc = new LocpService();
			list = locpsvc.getAllHighToLow();
			
		}
		
		if("lowtohigh".equals(condition)) {
			
			LocpService locpsvc = new LocpService();
			list = locpsvc.getAllLowToHigh();
			
		}
		
		ObjectMapper mapper = new ObjectMapper(); 
		String data = mapper.writeValueAsString(list);
		
		out.println(data);
		
	}

}
