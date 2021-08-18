package com.photographer.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.photographer.model.PhogService;
import com.photographer.model.PhogVO;

@WebServlet("/photographer/phogOneSmemIdServlet")
public class PhogOneSmemIdServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//設置頭
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF8");
		
		//取參數
		Integer phog_smem_id = new Integer(request.getParameter("phog_smem_id"));
		//調用方法
		PhogService phogSvc = new PhogService();
		List<PhogVO> phogVO = phogSvc.findByForeignKey(phog_smem_id);
			
		//輸出
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(phogVO);
		out.print(json);
		
		
	}

}
