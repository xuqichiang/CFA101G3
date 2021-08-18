package com.locationprogram.controller;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.locationprogram.model.LocpService;
import com.locationprogram.model.LocpVO;
import com.member.model.MemVO;

@WebServlet("/locationprogram/updateLocpServlet")
public class UpdateLocpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html;charset=utf-8");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date starttime = null;
		java.util.Date endtime = null;
		
		Integer locp_id = Integer.parseInt(request.getParameter("locp_id").trim()); 
		String locp_name = request.getParameter("locp_name").trim();
		Integer locp_price = Integer.parseInt(request.getParameter("locp_price").trim());
		
		try {
			starttime = sdf.parse(request.getParameter("locp_start_time"));
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date locp_start_time = new java.sql.Date(starttime.getTime());
		
		try {
			endtime = sdf.parse(request.getParameter("locp_end_time"));
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date locp_end_time = new java.sql.Date(endtime.getTime());
		
		String locp_content = request.getParameter("locp_content");
		Integer locp_status = Integer.parseInt(request.getParameter("locp_status").trim());
		LocpVO locpVO = new LocpVO();
		
		locpVO.setLocp_id(locp_id);
		locpVO.setLocp_name(locp_name);
		locpVO.setLocp_price(locp_price);
		locpVO.setLocp_start_time(locp_start_time);
		locpVO.setLocp_end_time(locp_end_time);
		locpVO.setLocp_content(locp_content);
		locpVO.setLocp_status(locp_status);
		
		LocpService locpsvc = new LocpService();
		locpsvc.updateLocp(locpVO);
		
		try {
			locpsvc.updateLocp(locpVO);
			response.getWriter().print("1");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print("0");
		}
	}

}
