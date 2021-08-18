package com.locationimages.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.locationimages.model.*;

@WebServlet("/locationimages/getLocImageServlet")
public class getLocImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String action = request.getParameter("action");
		if("getImgListByFK".equals(action)) {
			try {
				Integer LOCI_LOCR_ID = Integer.parseInt(request.getParameter("LOCI_LOCR_ID"));
				LociService LociService = new LociService();
				List<LociVO> list = LociService.findByForeignKey(LOCI_LOCR_ID);
				ObjectMapper mapper = new ObjectMapper(); 
				mapper.writeValue(response.getWriter(), list);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if("getAll".equals(action)) {
			PrintWriter out = response.getWriter();
			LociService locisvc = new LociService();
			List<LociVO> allList = locisvc.getAll();
			ObjectMapper mapper = new ObjectMapper(); 
			String data = mapper.writeValueAsString(allList);
			System.out.println(data);
			out.println(data);
		}
		
		
	}

}