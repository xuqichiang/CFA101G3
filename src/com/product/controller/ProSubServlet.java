package com.product.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.model.ProService;
import com.product.model.ProVO;

@WebServlet("/product/ProSubServlet")
public class ProSubServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String action = request.getParameter("action");
		if("getall".equals(action)) {
			Integer pro_proc_id= Integer.parseInt(request.getParameter("pro_proc_id"));
			ProService Service = new ProService();
			List<ProVO> list = Service.findByCateList(pro_proc_id);
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(list);
			response.getWriter().print(json);
		}
		if("cheap".equals(action)) {
			Integer pro_proc_id= Integer.parseInt(request.getParameter("pro_proc_id"));
			ProService Service = new ProService();
			List<ProVO> list = Service.findByCateCheapList(pro_proc_id);
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(list);
			response.getWriter().print(json);
		}
		if("expensive".equals(action)) {
			Integer pro_proc_id= Integer.parseInt(request.getParameter("pro_proc_id"));
			ProService Service = new ProService();
			List<ProVO> list = Service.findByCateExpList(pro_proc_id);
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(list);
			response.getWriter().print(json);
		}
		
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
