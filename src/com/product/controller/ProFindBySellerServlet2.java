package com.product.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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

/**
 * Servlet implementation class ProFindByPKServlet
 */
@WebServlet("/product/ProFindBySellerServlet2")
public class ProFindBySellerServlet2 extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//設置頭
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		//取得參數
		Integer pro_smem_id = Integer.parseInt(request.getParameter("memid"));
		System.out.println(pro_smem_id);
		//從DB找到資料
		ProService service = new ProService();
		List<ProVO> list = service.getOneProBySeller(pro_smem_id);
		//開始輸出
		PrintWriter out = response.getWriter(); //servlet輸出
		ObjectMapper mapper = new ObjectMapper(); //JSON
		String data = mapper.writeValueAsString(list); //JSON
		out.println(data);//讓我看看!!
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
