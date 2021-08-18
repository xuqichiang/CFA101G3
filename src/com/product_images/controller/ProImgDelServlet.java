package com.product_images.controller;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.product_images.model.ProImgService;
import com.product_images.model.ProImgVO;
import com.sun.mail.handlers.text_html;


@WebServlet("/product/ProImgDelServlet")
public class ProImgDelServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//取值
		response.setContentType("text/html; charset=UTF-8");
		Integer proi_id = Integer.parseInt(request.getParameter("proi_id"));
		//調用dao
		ProImgService service = new ProImgService();
		service.deleteImg(proi_id);
		//回應
		try {
			response.getWriter().print("1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			response.getWriter().print("0");
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
