package com.locationprogram.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.locationprogram.model.LocpService;
import com.locationroom.model.LocrVO;
import com.member.model.MemVO;

@WebServlet("/locationprogram/getRoomBySmemidServlet")
public class GetRoomBySmemidServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		
		MemVO memVo = (MemVO)session.getAttribute("user");
		Integer locr_smem_id = memVo.getMem_id();
		
		LocpService locpsvc = new LocpService();
		List<LocrVO> list = locpsvc.getAllLocrBySmemid(locr_smem_id);
		
		
		ObjectMapper mapper = new ObjectMapper(); 
		String data = mapper.writeValueAsString(list);
		
		out.println(data);
	}

}
