package com.locationorder.controller;

import java.io.IOException;
import java.sql.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.locationorder.model.*;
import com.locationroom.model.*;
import com.member.model.*;
import com.locationprogram.model.*;

@WebServlet("/Locationorder/LocationorderServlet")
public class LocationorderServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		String action = request.getParameter("action");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

//		System.out.println("8");
		List<String> errorMsgs = new LinkedList<String>();
		ObjectMapper mapper = new ObjectMapper();

		try {
			/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
			java.util.Date opentime = null;
			java.util.Date reservetime = null;
//					Integer LOCO_SMEM_ID;			

			Integer LOCO_SMEM_ID = null;
			
			LOCO_SMEM_ID = new Integer(request.getParameter("LOCR_SMEM_ID"));
			

//						Integer LOCO_BMEM_ID;		

			Integer LOCO_BMEM_ID = null;
			LOCO_BMEM_ID = new Integer(request.getParameter("Mem_id"));

//					Integer LOCO_LOCR_ID;
			Integer LOCO_LOCR_ID = null;
			LOCO_LOCR_ID = new Integer(request.getParameter("LOCO_LOCR_ID"));

//					Integer LOCO_LOCP_ID;

			Integer LOCO_LOCP_ID = null;
			LOCO_LOCP_ID = new Integer(request.getParameter("LOCO_LOCP_ID"));
//					Integer LOCO_TOTALPRICE;
			Integer LOCO_TOTALPRICE = null;
			System.out.println(request.getParameter("LOCO_TOTALPRICEshow"));
			LOCO_TOTALPRICE = new Integer(request.getParameter("LOCO_TOTALPRICEshow"));

//					Integer LOCO_DEPOSIT;
			Integer LOCO_DEPOSIT = null;
			LOCO_DEPOSIT = new Integer(request.getParameter("LOCO_DEPOSITshow"));
//					Integer LOCO_PAYTYPE;
			Integer LOCO_PAYTYPE = null;
			LOCO_PAYTYPE = new Integer(request.getParameter("LOCO_PAYTAPE"));
//					Integer LOCO_ORDER_STATUS;
			Integer LOCO_ORDER_STATUS = 0;

//					Integer LOCO_PAY_STATUS;
			Integer LOCO_PAY_STATUS = 0;

//					Date LOCO_ORDER_TIME;

			Date LOCO_ORDER_TIME = new java.sql.Date(new java.util.Date().getTime());
			System.out.println(LOCO_ORDER_TIME);

//					Date LOCO_RESERVE_TIME;

			try {
				reservetime = sdf.parse(request.getParameter("LOCO_RESERVE_TIME"));
				
				LocoService locoService = new LocoService();
				List<LocoVO> time = locoService.getTime(LOCO_LOCR_ID);
				for (LocoVO locoVO : time) {
					if(locoVO.getLOCO_RESERVE_TIME().getTime()==reservetime.getTime()) {
						response.getWriter().print("0");
						return;
					}
					
				}
				

			} catch (ParseException e) {
				e.printStackTrace();
			}
			Date LOCO_RESERVE_TIME = new java.sql.Date(reservetime.getTime());

//					Integer LOCO_TABLE_NUM;
			Integer LOCO_TABLE_NUM = null;
			try {
				LOCO_TABLE_NUM = new Integer(request.getParameter("LOCO_TABLE_NUM").trim());
			    LocrService locrService = new LocrService();
			    LocrVO oneLocr = locrService.getOneLocr(LOCO_LOCR_ID);
			    Integer locr_MAX_TABLE = oneLocr.getLOCR_MAX_TABLE();
			    Integer locr_MIN_TABLE = oneLocr.getLOCR_MIN_TABLE(); 
			    
			    if(LOCO_TABLE_NUM >locr_MAX_TABLE || LOCO_TABLE_NUM <locr_MIN_TABLE) {
			     errorMsgs.add("桌數請在範圍內");
			    }
			} catch (NumberFormatException e) {
				LOCO_TABLE_NUM = 0;
				errorMsgs.add("桌數請填數字.");
			}

//					String LOCO_NOTE;
			String LOCO_NOTE = request.getParameter("LOCO_NOTE");

			LocoVO LocoVO = new LocoVO();

			LocoVO.setLOCO_SMEM_ID(LOCO_SMEM_ID);
			LocoVO.setLOCO_BMEM_ID(LOCO_BMEM_ID);
			LocoVO.setLOCO_LOCR_ID(LOCO_LOCR_ID);
			LocoVO.setLOCO_LOCP_ID(LOCO_LOCP_ID);
			LocoVO.setLOCO_TOTALPRICE(LOCO_TOTALPRICE);
			LocoVO.setLOCO_DEPOSIT(LOCO_DEPOSIT);
			LocoVO.setLOCO_PAYTYPE(LOCO_PAYTYPE);
			LocoVO.setLOCO_ORDER_STATUS(LOCO_ORDER_STATUS);
			LocoVO.setLOCO_PAY_STATUS(LOCO_PAY_STATUS);
			LocoVO.setLOCO_ORDER_TIME(LOCO_ORDER_TIME);
			LocoVO.setLOCO_RESERVE_TIME(LOCO_RESERVE_TIME);
			LocoVO.setLOCO_TABLE_NUM(LOCO_TABLE_NUM);
			LocoVO.setLOCO_NOTE(LOCO_NOTE);

			// Send the use back to the form, if there were er nrors
			if (!errorMsgs.isEmpty()) {
//				System.out.println("10");
				response.setStatus(401);
				String Msg = mapper.writeValueAsString(errorMsgs);

				response.getWriter().print(Msg);
				return;
			}

			/*************************** 2.開始新增資料 ***************************************/
			LocoService LocoSvc = new LocoService();
			LocoVO = LocoSvc.addLoco(LOCO_SMEM_ID, LOCO_BMEM_ID, LOCO_LOCR_ID, LOCO_LOCP_ID, LOCO_TOTALPRICE,
					LOCO_DEPOSIT, LOCO_PAYTYPE, LOCO_ORDER_STATUS, LOCO_PAY_STATUS, LOCO_ORDER_TIME, LOCO_RESERVE_TIME,
					LOCO_TABLE_NUM, LOCO_NOTE);
//			System.out.println("20");
			Map map = new HashMap();
			map.put("msg", "success");
			String msg = mapper.writeValueAsString(map);
			System.out.println(msg);
			response.getWriter().print(msg);

			/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
//					String url = "/front_end/locationroom/locrBmemGetOnee.jsp";
//					RequestDispatcher successView = request.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
//					successView.forward(request, response);	

			/*************************** 其他可能的錯誤處理 **********************************/
		} catch (Exception e) {
			
			errorMsgs.add(e.getMessage());

			e.printStackTrace();
			response.setStatus(401);
			String Msg = mapper.writeValueAsString(errorMsgs);

			response.getWriter().print(Msg);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
