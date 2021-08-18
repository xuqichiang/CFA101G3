package com.workphoto.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workphoto.model.WorService;
import com.workphoto.model.WorVO;

@WebServlet("/workphoto/worGetOneServlet")
public class WorGetOneServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 設置請求頭
		request.setCharacterEncoding("UTF-8");
		try {
			// 取參數
			Integer wor_id = new Integer(request.getParameter("wor_id"));// 作品集ID
			WorService worSvc = new WorService();
			WorVO worVO = worSvc.getWorId(wor_id);
			
			String action = request.getParameter("action");
			if("getOne".equals(action)) {
				if(worVO != null) {
			    	response.setContentType("text/html;charset=UTF-8");
			    	ObjectMapper mapper = new ObjectMapper();
			    	mapper.writeValue(response.getWriter(), worVO);
			    	return;
				}
			}
			
			if (worVO != null) {
				byte[] wor_img = worVO.getWor_logo();//LOGO
				ServletOutputStream out = response.getOutputStream();// 輸出
				out.write(wor_img);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
