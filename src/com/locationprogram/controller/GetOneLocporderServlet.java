package com.locationprogram.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.locationprogram.model.LocpService;
import com.locationprogram.model.LocpVO;
import com.locationprogramimages.model.LocpiService;
import com.locationprogramimages.model.LocpiVO;

@WebServlet("/locationprogram/getOneLocporderServlet")
public class GetOneLocporderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		Integer locp_id = Integer.parseInt(request.getParameter("locpid"));
		
		//撈方案資訊
		LocpService locpsvc = new LocpService();
		LocpVO locpVO = locpsvc.getOneLocpByLocpid(locp_id);

		
		//用Map存取往前端送
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("locp_id", locpVO.getLocp_id());
		map.put("locp_name", locpVO.getLocp_name());
		map.put("locp_price", locpVO.getLocp_price());
		map.put("locp_start_time", locpVO.getLocp_start_time());
		map.put("locp_end_time()", locpVO.getLocp_end_time());
		map.put("locp_content", locpVO.getLocp_content());
		
		
		ObjectMapper mapper = new ObjectMapper(); 
		String data = mapper.writeValueAsString(map);
		
		out.println(data);
	}

}
