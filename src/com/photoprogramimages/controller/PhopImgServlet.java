package com.photoprogramimages.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.photoprogramimages.model.PhopiService;
import com.photoprogramimages.model.PhopiVO;

@WebServlet("/photoprogramimages/phopImgServlet")
public class PhopImgServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		Integer phopi_id = null;
		
		String phopi_idStr = request.getParameter("phopi_id");
		if(phopi_idStr!=null) {
			phopi_id = new Integer(phopi_idStr);//圖片ID
			PhopiService phopiSvc = new PhopiService();
			PhopiVO phopiVO = phopiSvc.findByPrimaryKey(phopi_id);
			byte[] img = phopiVO.getPhopi_images();
			response.getOutputStream().write(img);
		}
	}
}
