package com.shop_order.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.member.model.MemVO;
import com.shop_order.model.SpoService;
import com.shop_order.model.SpoVO;


@WebServlet("/shop_order/SpoSelCanServlet")
public class SpoSelCanServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//設置頭
	response.setContentType("text/html; charset=utf-8");
	request.setCharacterEncoding("UTF-8");
	//取得參數
	HttpSession session = request.getSession();
	MemVO memVo = (MemVO)session.getAttribute("user");
	Integer spo_smem_id = memVo.getMem_id();
	System.out.println(spo_smem_id);
	Integer spo_cargo_status = 0; //未發貨
	
	SpoService service = new SpoService();
	List<SpoVO> list = service.getOrdReturn(spo_smem_id, spo_cargo_status);
	ObjectMapper mapper = new ObjectMapper();
	String data = mapper.writeValueAsString(list);
	PrintWriter out = response.getWriter();
	out.print(data);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
