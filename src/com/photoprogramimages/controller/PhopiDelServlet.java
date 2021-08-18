package com.photoprogramimages.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.photoprogramimages.model.PhopiService;
import com.photoprogramimages.model.PhopiVO;


@WebServlet("/photoprogramimages/phopiDelServlet")
public class PhopiDelServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//設置頭
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		//取參數
		Integer phopi_id =new Integer(request.getParameter("phopi_id"));
		
		//調用方法
		PhopiService phopiSvc = new PhopiService();
		phopiSvc.delImg(phopi_id);//刪除
		
		try {
			response.getWriter().print("1");
		} catch (IOException e) {
			response.getWriter().print("0");
			e.printStackTrace();
		}
	}

}
