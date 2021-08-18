package com.photographer.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.photographer.model.PhogService;
import com.photographer.model.PhogVO;

@WebServlet("/photographer/phogGetOneSetvlet")
public class PhogGetOneSetvlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//設置頭
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		//取參數
		Integer phog_id = new Integer (request.getParameter("phog_id"));//攝影師ID
		PhogService phogSvc = new PhogService();
		PhogVO phogVO = phogSvc.getOnePhog(phog_id);//查一位攝影師

		//輸出
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(phogVO);
		out.println(json);
		
	}

}
