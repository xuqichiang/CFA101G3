package com.locationprogramimages.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.locationprogramimages.model.LocpiService;
import com.locationprogramimages.model.LocpiVO;

@WebServlet("/locationprogramimages/getImagesByFKservlet")
public class GetImagesByFKservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		Integer locp_id = Integer.parseInt(request.getParameter("locp_id"));
		LocpiService locpisvc = new LocpiService();
		LocpiVO locpiVO = locpisvc.findByForeignKey(locp_id);
		
		if(locpiVO != null) {
			byte[] locpi_images = locpiVO.getLocpi_images();
			response.getOutputStream().write(locpi_images);
		 }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
