package com.photoprogram.controller;

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
import com.photoprogram.model.PhopService;
import com.photoprogram.model.PhopVO;

@WebServlet("/photoprogram/listAllPhotoProgram")
public class ListAllPhotoProgram extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//頭設置
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		//取得會員ID
		HttpSession session = request.getSession();
		MemVO memVO = (MemVO) session.getAttribute("user");
		Integer phop_smem_id = memVO.getMem_id();
		
		//呼叫方法
		PhopService phopService = new PhopService();
		List<PhopVO> getAll = phopService.getAll();//全部資料
		List<PhopVO> arrayList = new ArrayList<PhopVO>();//存放FK與會員 join
		for(PhopVO phopVO : getAll) {
			if(phopVO.getPhop_smem_id().equals(phop_smem_id)) {//判斷會員ID與婚攝方案會員是否相同
				arrayList.add(phopVO);//相同做存入
			}
		}
		ObjectMapper mapper = new ObjectMapper();
		String data = mapper.writeValueAsString(arrayList);//轉換方法：Object轉成JSNO格式
		PrintWriter out = response.getWriter();
		out.print(data);
	}

}
