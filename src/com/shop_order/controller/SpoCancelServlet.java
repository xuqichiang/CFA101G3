package com.shop_order.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.Soundbank;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shop_order.model.SpoService;
import com.shop_order.model.SpoVO;


@WebServlet("/shop_order/SpoCancelServlet")
public class SpoCancelServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//設置頭
		response.setContentType("text/html; charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		//取得參數
		Integer spo_id = Integer.parseInt(request.getParameter("spo_id"));
		Integer spo_status = 3; // 處理中 已確認 > 已取消
		//從DB找到資料
		SpoService service = new SpoService();
		service.cancelOrder(spo_id);
		
		//開始輸出
		try {
			response.getWriter().print(1);
		} catch (Exception e) {
			response.getWriter().print(0);
			e.printStackTrace();
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
