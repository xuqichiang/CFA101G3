package com.photoprogram.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.photoprogram.model.PhopService;
import com.photoprogram.model.PhopVO;
import com.photoprogramimages.model.PhopiService;
import com.photoprogramimages.model.PhopiVO;


@WebServlet("/photoprogram/phopUpdateServlet")
public class PhopUpdateServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//設置頭
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		//時間
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date startTime = null;
		java.util.Date endTime = null;
		
		//取得參數
		Integer phop_id = new Integer(request.getParameter("phop_id"));//方案ID
		String phop_name = request.getParameter("phop_name");//方案名
		Integer price = new Integer(request.getParameter("phop_price"));//價錢
		try {
			startTime = sdf.parse(request.getParameter("phop_start_time"));//開始時間
			endTime = sdf.parse(request.getParameter("phop_end_time"));//結束時間
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date phop_start_time = new java.sql.Date(startTime.getTime());
		Date phop_end_time = new java.sql.Date(endTime.getTime());
		String phop_content = request.getParameter("phop_content");//內容
		Integer phop_status = new Integer(request.getParameter("phop_status"));//狀態
		
		//傳參數
		PhopVO phopVO = new PhopVO();
		phopVO.setPhop_name(phop_name);
		phopVO.setPhop_price(price);
		phopVO.setPhop_start_time(phop_start_time);
		phopVO.setPhop_end_time(phop_end_time);
		phopVO.setPhop_content(phop_content);
		phopVO.setPhop_status(phop_status);
		phopVO.setPhop_id(phop_id);
		
		//呼叫方法
		PhopService phopSvc = new PhopService();
		phopSvc.updatePhop(phopVO);//修改
//		System.out.println(phopVO);
		
		try {
			response.getWriter().print("1");
		} catch (IOException e) {
			response.getWriter().print("0");
			e.printStackTrace();
		}
	}

}
