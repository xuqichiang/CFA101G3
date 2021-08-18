package com.locationroom.controller;

import java.io.*;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.locationroom.model.*;
import com.member.model.MemVO;


//找單筆頁面
@WebServlet("/locationroom/getonelocrservletfront")
public class GetoneLocrServletfront extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		
		LocrService locrsvc = new LocrService();
		
		
		Integer LOCR_ID = new Integer(request.getParameter("LOCR_ID"));
		
		LocrVO oneLocrVO = locrsvc.getOneLocr(LOCR_ID);
		

		
		ObjectMapper mapper = new ObjectMapper(); 
		String data = mapper.writeValueAsString(oneLocrVO);
		

		
		PrintWriter out = response.getWriter();

			out.print(data);

		
	
	}

}
