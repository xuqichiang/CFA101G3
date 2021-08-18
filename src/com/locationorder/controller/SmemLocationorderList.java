package com.locationorder.controller;

import java.io.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.locationroom.model.*;
import com.locationprogram.model.*;
import com.locationorder.model.*;
import com.member.model.MemVO;

@WebServlet("/locationorder/SmemLocationorderList")
public class SmemLocationorderList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		String action = request.getParameter("action");
		PrintWriter out = response.getWriter();

		if ("getSmemOrderID".equals(action)) {
			//從session獲取會員VO
			HttpSession session = request.getSession();
			MemVO memVO = (MemVO)session.getAttribute("user");
			//獲取會員ID
			Integer mem_id = memVO.getMem_id();
						
			LocoService locoService = new LocoService();
			List<LocoVO> allList = locoService.getAll();
			List<LocoVO> list = new ArrayList();
			
			for(LocoVO locoVO : allList){
				if(locoVO.getLOCO_SMEM_ID().equals(mem_id)) {
					list.add(locoVO);
					
				}
			}
			
			ObjectMapper mapper = new ObjectMapper();
			String data = mapper.writeValueAsString(list);
			out.print(data);

		}
		
		if ("getSmemOrderOneID".equals(action)) {
			Integer LOCO_ID = new Integer(request.getParameter("LOCO_ID"));
						
			LocoService locoService = new LocoService();
			LocoVO PKLoco = locoService.findByPrimaryKeyLoco(LOCO_ID);

			
			ObjectMapper mapper = new ObjectMapper();
			String data = mapper.writeValueAsString(PKLoco);
			out.print(data);

		}
		
		if ("getBmemOrderID".equals(action)) {
			//從session獲取會員VO
			HttpSession session = request.getSession();
			MemVO memVO = (MemVO)session.getAttribute("user");
			//獲取會員ID
			Integer mem_id = memVO.getMem_id();
						
			LocoService locoService = new LocoService();
			List<LocoVO> allList = locoService.getAll();
			List<LocoVO> list = new ArrayList();
			
			for(LocoVO locoVO : allList){
				if(locoVO.getLOCO_BMEM_ID().equals(mem_id)) {
					list.add(locoVO);
					
				}
			}
			
			ObjectMapper mapper = new ObjectMapper();
			String data = mapper.writeValueAsString(list);
			out.print(data);

		}
		
		if ("getmemname".equals(action)) {
			//從memID獲取會員VO
			Integer mem_ID = new Integer(request.getParameter("Mem_ID"));
			
			LocrService locrService = new LocrService();
			MemVO memVO = locrService.getSmem(mem_ID);// 取得店家資料 方法額外呼叫
			
			ObjectMapper mapper = new ObjectMapper();
			String data = mapper.writeValueAsString(memVO);
			out.print(data);

		}
		if ("getSmemname".equals(action)) {
			//從memID獲取會員VO
			Integer mem_ID = new Integer(request.getParameter("Mem_ID"));
			
			LocrService locrService = new LocrService();
			MemVO memVO = locrService.getSmem(mem_ID);// 取得店家資料 方法額外呼叫
			
			ObjectMapper mapper = new ObjectMapper();
			String data = mapper.writeValueAsString(memVO);
			out.print(data);

		}
		if ("getlocrname".equals(action)) {
			//從memID獲取會員VO
			Integer LOCR_ID = new Integer(request.getParameter("LOCR_ID"));
			
			LocrService locrService = new LocrService();
			LocrVO oneLocr = locrService.getOneLocr(LOCR_ID);// 取得店家資料 方法額外呼叫
			
			ObjectMapper mapper = new ObjectMapper();
			String data = mapper.writeValueAsString(oneLocr);
			out.print(data);

		}
		if ("getlocpname".equals(action)) {
			//從memID獲取會員VO
			Integer LOCP_ID = new Integer(request.getParameter("LOCP_ID"));
			
			LocpService locpService = new LocpService();
			LocpVO oneLocp = locpService.getOneLocpByLocpid(LOCP_ID);
			
			
			ObjectMapper mapper = new ObjectMapper();
			String data = mapper.writeValueAsString(oneLocp);
			out.print(data);

		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
