package com.photographer.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.member.model.MemVO;
import com.photographer.model.PhogService;
import com.photographer.model.PhogVO;

@WebServlet("/photographer/phogUpdateServlet")
public class PhogUpdateServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//設置頭
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		
		//取得會員ID
		HttpSession session = request.getSession();
		MemVO memVO = (MemVO)session.getAttribute("user");//會員ID
		Integer smem_id = memVO.getMem_id();
		
		//取得參數
		Integer phog_id = new Integer (request.getParameter("phog_id"));//攝影師ID
		Integer phog_status = new Integer (request.getParameter("phog_status"));//攝影師狀態
		
		PhogVO phogVO = new PhogVO();
		phogVO.setPhog_status(phog_status);
		phogVO.setPhog_id(phog_id);
		phogVO.setPhog_smem_id(smem_id);
		
		PhogService phogSvc = new PhogService();
		phogSvc.updateStatus(phogVO);//修改
		
		//輸出
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();//JSON格式
		Map hashMap = new HashMap();
		hashMap.put("msg", "更新成功");
		String json = mapper.writeValueAsString(hashMap);
		out.println(json);
	}

}
