package com.locationbackorder.controller;

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
import com.locationbackorder.model.LocoService;
import com.locationbackorder.model.LocoVO;

@WebServlet("/locationorder/getOrderByStatusServlet")
public class GetOrderByStatusServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		List list = new ArrayList();
		LocoService locosvc = new LocoService();
		Integer loco_order_status = Integer.parseInt(request.getParameter("loco_status"));
		
		if(loco_order_status == 0) {
			list = locosvc.getAllLocorder();
		}else {
			list = locosvc.getOrderByStatus(loco_order_status);
		}
		
		ObjectMapper mapper = new ObjectMapper(); 
		String data = mapper.writeValueAsString(list);
		
		out.print(data);
	}

}
