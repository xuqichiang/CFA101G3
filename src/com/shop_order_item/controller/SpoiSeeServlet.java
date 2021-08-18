package com.shop_order_item.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop_order_item.model.SpoiService;
import com.shop_order_item.model.SpoiVO;


@WebServlet("/shop_order_item/SpoiSeeServlet")
public class SpoiSeeServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			response.setContentType("text/html;charset=utf-8");
			int spo_id = Integer.parseInt(request.getParameter("spo_id"));
			System.out.println(spo_id);
			SpoiService service = new SpoiService();
			List<SpoiVO> list = service.getBySpo_id(spo_id);
			System.out.println(list);
			ObjectMapper mapper = new ObjectMapper();
			String data = mapper.writeValueAsString(list);
			response.getWriter().print(data);
		} catch (NumberFormatException e) {
			
			response.getWriter().print(0);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
