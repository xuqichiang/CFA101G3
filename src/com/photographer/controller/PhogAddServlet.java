package com.photographer.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
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
import com.photographer.model.PhogService;
import com.photographer.model.PhogVO;
import com.workphoto.model.WorVO;


@WebServlet("/photographer/phogAddServlet")
public class PhogAddServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//頭設置
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		//獲取會員id
		HttpSession session = request.getSession();
		MemVO memVO = new MemVO();
		memVO = (MemVO)session.getAttribute("user");
		Integer smem_id = memVO.getMem_id();
//		System.out.println(smem_id);
		
		String phog_name = request.getParameter("phog_name").trim();//攝影師姓名
		Integer phog_status = new Integer (request.getParameter("phog_status"));//狀態
		
		PhogVO phogVO = new PhogVO();
		phogVO.setPhog_smem_id(smem_id);//店家id
		phogVO.setPhog_name(phog_name);//攝影師名字
		phogVO.setPhog_status(phog_status);//狀態
		
		PhogService phogSvc = new PhogService();//調用service
		phogSvc.addPhog(phogVO);//新增
		
		PrintWriter out = response.getWriter();//輸出
		ObjectMapper mapper = new ObjectMapper();//JSON
		Map hashMap = new HashMap();
		hashMap.put("msg", "新增成功");
		String json = mapper.writeValueAsString(hashMap);
		out.println(json);
		
	}

}
