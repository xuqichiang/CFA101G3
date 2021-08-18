package com.locationroom.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.locationroom.model.LocrService;
import com.locationroom.model.LocrVO;



@WebServlet("/locationroom/getAllLocrServlet")
public class GetAllLocrServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		
		
		LocrService locrsvc = new LocrService();
		List<LocrVO> allList = locrsvc.getAll();
		//篩選上架的資料
		List<LocrVO> list = new ArrayList();
		for(LocrVO locrVO : allList){
			if(locrVO.getLOCR_STATUS().equals(1)) {
				list.add(locrVO);
			}
		}
		
		ObjectMapper mapper = new ObjectMapper(); 
		String data = mapper.writeValueAsString(list);
		
		
		PrintWriter out = response.getWriter();
		out.println(data);
		
		
	}

}
