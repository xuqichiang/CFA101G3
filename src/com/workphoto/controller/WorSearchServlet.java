package com.workphoto.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workphoto.model.WorService;
import com.workphoto.model.WorVO;

//瀏覽+搜尋功能
@WebServlet("/workphoto/worSearchServlet")
public class WorSearchServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		try {
			String mem_shop_name = request.getParameter("mem_shop_name");
			System.out.println(mem_shop_name);
			
			WorService worSvc = new WorService();
			List<WorVO> list = worSvc.findByWork(mem_shop_name);
			
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(list);
			PrintWriter out = response.getWriter();
			out.print(json);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
