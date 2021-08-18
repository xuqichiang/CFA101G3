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

@WebServlet("/photographer/phogGetShopServlet")
public class PhogGetShopServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//設置頭
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String action = request.getParameter("action");
		
		//請求買家會員
		if("getBmemId".equals(action)) {
			//獲取會員ID
			HttpSession session = request.getSession();
			MemVO memVO = (MemVO)session.getAttribute("user");
						
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("MEM_ID", memVO.getMem_id());//會員ID
			map.put("MEM_NAME", memVO.getMem_name());//會員名
//			System.out.println(map);
			//輸出		
			PrintWriter out = response.getWriter();			
			ObjectMapper mapper = new ObjectMapper(); 
			String json = mapper.writeValueAsString(map);
			out.print(json);
		}
				
		//請求店家會員
		if("getSmemId".equals(action)) {
			//取前端參數
			Integer phog_smem_id = new Integer(request.getParameter("phog_smem_id"));
			//調用方法
			PhogService phogSvc = new PhogService();
			Map shopInfo = phogSvc.getShopInfo(phog_smem_id);
			//輸出
			PrintWriter out = response.getWriter();
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(shopInfo);
			out.print(json);
		}
	}

}
