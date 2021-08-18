package com.photographer.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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

@WebServlet("/photographer/phogServlet")
public class PhogServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		HttpSession session = request.getSession();
		MemVO memVO = (MemVO) session.getAttribute("user");//從session獲取memVO
		//System.out.println(memVO);
		Integer phog_smem_id = memVO.getMem_id();//取得會員id
		
		//System.out.println(phog_smem_id);
		
		PhogService phogSvc = new PhogService();
		List<PhogVO> getAll = phogSvc.getAll();//攝影師全部資料
		List<PhogVO> list = new ArrayList<PhogVO>();//fk與會員id join加入新的list
		for(PhogVO phogVO : getAll) {
			if(phogVO.getPhog_smem_id().equals(phog_smem_id)) {//判斷攝影師店家id是否與買家id相同
				list.add(phogVO);//相同就存入新的list
			}
		}
		//System.out.println(list);
		
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(list);//新的list轉換json
		PrintWriter out = response.getWriter();//輸出
		out.print(json);
	
	}

}
