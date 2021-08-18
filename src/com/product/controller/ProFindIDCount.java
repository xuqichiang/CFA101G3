package com.product.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.product.model.ProService;
import com.product.model.ProVO;


@WebServlet("/product/ProFindIDCount")
public class ProFindIDCount extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//設置頭
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		//取得參數
		Integer pro_id = Integer.parseInt(request.getParameter("pro_id"));
//		System.out.println(pro_id);
		//從DB找到資料
		ProService proSvc = new ProService();
		ProVO proVO = proSvc.getOnePro(pro_id);
//		System.out.println(proVO);
		Integer pro_smem_id = proVO.getPro_smem_id();
//		System.out.println(pro_smem_id);
		List<ProVO> list = proSvc.getOneProBySeller(pro_smem_id);
		int size = list.size();
		response.getWriter().print(size);
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
