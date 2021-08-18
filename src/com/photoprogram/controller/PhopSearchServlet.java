package com.photoprogram.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.photoprogram.model.PhopService;

//搜尋方案名
@WebServlet("/photoprogram/phopSearchServlet")
public class PhopSearchServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String action = request.getParameter("action");
		PhopService phopSvc = new PhopService();
		//搜尋方案名
		if("phopName".equals(action)) {
			try {
				String phop_name = request.getParameter("phop_name");
//				System.out.println(phop_name);
				List list = phopSvc.findByPhotoProgram(phop_name);
				
				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(list);
				PrintWriter out = response.getWriter();
				out.print(json);
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//類別搜尋
		if("searchCategory".equals(action)) {
			Integer phop_phoc_id = new Integer(request.getParameter("phop_phoc_id"));//類別
			List list = phopSvc.findBySearchCategory(phop_phoc_id);
			
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(list);
			PrintWriter out = response.getWriter();
			out.print(json);
		}
		
		//複合式查詢
		if("searchList".equals(action)) {
			String city = request.getParameter("city");//地區
			Integer phop_price_1 = new Integer(request.getParameter("phop_price_1"));//價錢1
			Integer phop_price_2 = new Integer(request.getParameter("phop_price_2"));//價錢2
			Integer phop_phoc_id = new Integer(request.getParameter("phop_phoc_id"));//類別
			List list = phopSvc.findBySearchList(phop_phoc_id, phop_price_1, phop_price_2, city);
			
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(list);
			PrintWriter out = response.getWriter();
			out.print(json);
			
		}
		
	}

}
