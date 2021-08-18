package com.weddingphoto.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weddingphoto.model.WedService;
import com.weddingphoto.model.WedVO;

@WebServlet("/weddingphoto/wedDeleteServlet")
public class WedDeleteServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		//取值
		Integer wed_id = new Integer(request.getParameter("wed_id"));//作品集圖片ID
		
		//刪除
		WedService wedSvc = new WedService();
		wedSvc.deleteImg(wed_id);
		
		try {
			response.getWriter().print("1");
		} catch (IOException e) {
			response.getWriter().print("0");
			e.printStackTrace();
		}

	}

}
