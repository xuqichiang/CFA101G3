package com.product.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.member.model.MemVO;
import com.product.model.ProDAO;
import com.product.model.ProDAOimpl;
import com.product.model.ProService;
import com.product.model.ProVO;


@WebServlet("/product/ProInsertServlet2")
public class ProInsertServlet2 extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//設置頭
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		//取得參數
//		HttpSession session = request.getSession();
//		System.out.println(session);
//		MemVO memVo = (MemVO)session.getAttribute("user");
//		System.out.println(memVo);
		System.out.println("我進來了");
		//命名用
		for(int i=231;i<300;i++) {
		Integer pro_smem_id =10;
		StringBuilder sb = new StringBuilder();
		for(int j=0;j<6;j++) {
			sb.append((char)((Math.random() * 26) + 97));
		} 
		String str = sb.toString();
		System.out.println(str);
		String upper = str.toUpperCase();
		String pro_name =upper+":WeddingGoods";
		Integer pro_price =(int)(Math.random()*1000);
		String pro_content ="小物"+i;
		Integer pro_proc_id =4;
		Integer pro_status =1;
		// set它!!
		ProVO proVO = new ProVO();
		proVO.setPro_smem_id(pro_smem_id);
		proVO.setPro_name(pro_name);
		proVO.setPro_price(pro_price);
		proVO.setPro_content(pro_content);
		proVO.setPro_proc_id(pro_proc_id);
		proVO.setPro_status(pro_status);
		// 新增資料
		ProService proSvc = new ProService();
		proVO = proSvc.addPro(pro_smem_id,pro_name, pro_price, pro_content, pro_proc_id, pro_status);
		}
		//輸出
		try {
			response.getWriter().print(1);
		} catch (Exception e) {
			response.getWriter().print(0);
		}
	}
}
