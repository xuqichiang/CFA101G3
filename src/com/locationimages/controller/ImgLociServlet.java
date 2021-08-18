package com.locationimages.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.locationimages.model.LociService;
import com.locationimages.model.LociVO;

/**
 * Servlet implementation class LociServlet
 */
@WebServlet("/locationimages/imgLociServlet")
public class ImgLociServlet extends HttpServlet {

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		Integer LOCI_ID = null;
	

			try {
				LOCI_ID = Integer.parseInt(request.getParameter("LOCI_ID"));
				LociService LociService = new LociService();
				LociVO LociVO = LociService.findByPrimaryKey(LOCI_ID);
				if(LociVO != null) {
					byte[] LOCI_IMAGES = LociVO.getLOCI_IMAGES();
					response.getOutputStream().write(LOCI_IMAGES);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	
	
	
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
