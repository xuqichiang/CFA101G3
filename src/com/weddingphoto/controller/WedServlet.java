package com.weddingphoto.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.weddingphoto.model.WedService;
import com.weddingphoto.model.WedVO;

@WebServlet("/weddingphoto/wedServlet")
public class WedServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		Integer wed_wor_id = new Integer(request.getParameter("wed_wor_id"));
		WedService wedSvc = new WedService();
		List<WedVO> list = wedSvc.findByForeignKey(wed_wor_id);
		//System.out.println(list);
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(list);
		PrintWriter out = response.getWriter();
		out.print(json);
	}
}
