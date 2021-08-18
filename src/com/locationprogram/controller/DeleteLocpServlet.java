package com.locationprogram.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.locationprogram.model.LocpService;
import com.locationprogramimages.model.LocpiService;

@WebServlet("/locationprogram/deleteLocpServlet")
public class DeleteLocpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		Integer locpid = Integer.parseInt(request.getParameter("locpid"));
		LocpiService locpisvc = new LocpiService();
		locpisvc.deleteLocpiImages(locpid);
		LocpService locpsvc = new LocpService();
		locpsvc.deletelocp(locpid);
		
		
		try {
			locpsvc.deletelocp(locpid);
			response.getWriter().print("1");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print("0");
		}
	}

}
