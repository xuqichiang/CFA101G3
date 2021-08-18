package com.calendar.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.calendar.model.CalendarService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.member.model.MemVO;

@WebServlet("/calendar/getAllStrokeServlet")
public class GetAllStrokeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		List list = new ArrayList();
		CalendarService calsvc = new CalendarService();
		MemVO memVO = new MemVO();
		
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		memVO = (MemVO)session.getAttribute("user");
		
		if("location".equals(action)) {
			
			list = calsvc.getLocmemStroke(memVO.getMem_id());
		}
		
		if("photography".equals(action)) {
			
			list = calsvc.getPhomemStroke(memVO.getMem_id());
		}
		
		ObjectMapper mapper = new ObjectMapper(); 
		String data = mapper.writeValueAsString(list);
		out.print(data);
	}

}
