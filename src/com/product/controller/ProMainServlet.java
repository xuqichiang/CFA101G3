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

@WebServlet("/product/ProMainServlet")
public class ProMainServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String action = request.getParameter("action");
		if("shoes".equals(action)) {
			Integer pro_proc_id= 1;
			ProService Service = new ProService();
			List<ProVO> list = Service.ProMain(pro_proc_id);
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(list);
			response.getWriter().print(json);
		}
		if("ring".equals(action)) {
			Integer pro_proc_id= 2;
			ProService Service = new ProService();
			List<ProVO> list = Service.ProMain(pro_proc_id);
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(list);
			response.getWriter().print(json);
		}
		if("flower".equals(action)) {
			Integer pro_proc_id= 3;
			ProService Service = new ProService();
			List<ProVO> list = Service.ProMain(pro_proc_id);
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(list);
			response.getWriter().print(json);
		}
		if("goods".equals(action)) {
			Integer pro_proc_id= 4;
			ProService Service = new ProService();
			List<ProVO> list = Service.ProMain(pro_proc_id);
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
