package com.photoprogramimages.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.photoprogramimages.model.PhopiService;
import com.photoprogramimages.model.PhopiVO;

@WebServlet("/photoprogramimages/phopFKservlet")
public class PhopFKservlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		Integer phopi_phop_id =Integer.parseInt(request.getParameter("phop_id")) ;
		
		PhopiService Service = new PhopiService();
		List<PhopiVO> list = Service.findByForeignkey(phopi_phop_id);
//		System.out.println(list);
		
		ObjectMapper mapper = new ObjectMapper();
		String data = mapper.writeValueAsString(list);
		response.getWriter().print(data);
		
	}

}
