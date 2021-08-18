package com.locationprogramimages.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.locationprogramimages.model.LocpiService;
import com.locationprogramimages.model.LocpiVO;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
@WebServlet("/locationprogramimages/updateLocpImagesServlet")
public class UpdateLocpImagesServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8"); 
		response.setContentType("text/html; charset=UTF-8");
		LocpiVO locpiVO = new LocpiVO();
		LocpiService locpisvc = new LocpiService();
		byte[] buf = null;
		List<LocpiVO> list = new ArrayList<LocpiVO>();
		
		Integer locpi_locp_id = Integer.parseInt(request.getParameter("locp_id"));
		Part part = request.getPart("file1");
		InputStream in = part.getInputStream();
		buf = new byte[in.available()];
		in.read(buf);
		in.close();
		list = locpisvc.findForeignKey(locpi_locp_id);
		locpiVO.setLocpi_locp_id(locpi_locp_id);
		locpiVO.setLocpi_images(buf);
		
		if((list.size()) != 0) {
			
			locpisvc.updateLocpiImages(locpiVO);
				
		}else{
			
			locpisvc.insertLocpiImages(locpiVO);
			
		}
		
		try {
			response.getWriter().print("1");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print("0");
		}
	}
}


