package com.locationroom.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.locationroom.model.*;
import com.member.model.MemVO;

@WebServlet("/locationroom/locrServletUp2")
public class LocrServletUp2 extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=utf-8");

			
			List<String> errorMsgs = new LinkedList<String>();


			ObjectMapper mapper = new ObjectMapper(); 	
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				Integer LOCR_ID = new Integer(req.getParameter("LOCR_ID").trim());
				
		Integer LOCR_SMEM_ID=null;
//		try {
//			LOCR_SMEM_ID = new Integer(req.getParameter("LOCR_SMEM_ID").trim());
//		} catch (NumberFormatException e) {
//			LOCR_SMEM_ID = 0;
//			errorMsgs.add("代號請填數字.");
//		}
		//獲取會員ID
		HttpSession session = req.getSession();
		MemVO memVO = (MemVO)session.getAttribute("user");
		LOCR_SMEM_ID =memVO.getMem_id();
		
		String LOCR_NAME = req.getParameter("LOCR_NAME");
		String LOCR_NAMEReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
		if (LOCR_NAME == null || LOCR_NAME.trim().length() == 0) {
			errorMsgs.add("廳房名稱: 請勿空白");
		} else if(!LOCR_NAME.trim().matches(LOCR_NAMEReg)) { //以下練習正則(規)表示式(regular-expression)
			errorMsgs.add("廳房名稱: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
        }
		
		Integer LOCR_MAX_TABLE=null;
		try {
			LOCR_MAX_TABLE = new Integer(req.getParameter("LOCR_MAX_TABLE").trim());
		} catch (NumberFormatException e) {
			LOCR_MAX_TABLE = 0;
			errorMsgs.add("最大桌數請填數字.");
		}
		
		Integer LOCR_MIN_TABLE = null;
		try {
			LOCR_MIN_TABLE = new Integer(req.getParameter("LOCR_MIN_TABLE").trim());
		} catch (NumberFormatException e) {
			LOCR_MIN_TABLE = 0;
			errorMsgs.add("最小桌數請填數字.");
		}
		Integer LOCR_MAIN_TABLE = null;
		try {
			LOCR_MAIN_TABLE = new Integer(req.getParameter("LOCR_MAIN_TABLE").trim());
		} catch (NumberFormatException e) {
			LOCR_MAIN_TABLE = 0;
			errorMsgs.add("主桌人數請填數字.");
		}
		Integer LOCR_GUEST_TABLE = null;
		try {
			LOCR_GUEST_TABLE = new Integer(req.getParameter("LOCR_GUEST_TABLE").trim());
		} catch (NumberFormatException e) {
			LOCR_GUEST_TABLE = 0;
			errorMsgs.add("客桌人數請填數字.");
		}
		Integer LOCR_FLOOR =null;
		try {
			LOCR_FLOOR = new Integer(req.getParameter("LOCR_FLOOR").trim());
		} catch (NumberFormatException e) {
			LOCR_FLOOR = 0;
			errorMsgs.add("最小桌數請填數字.");
		}
		String LOCR_CONTENT = req.getParameter("LOCR_CONTENT") ;
		if (LOCR_CONTENT == null || LOCR_CONTENT.trim().length() == 0) {
			errorMsgs.add("介紹請勿空白");
		}
		Integer LOCR_STATUS = null;
		try {
			LOCR_STATUS = new Integer(req.getParameter("LOCR_STATUS").trim());
		} catch (NumberFormatException e) {
			LOCR_STATUS = 0;
			errorMsgs.add("最小桌數請填數字.");
		}
		
		LocrVO LocrVO = new LocrVO();

		LocrVO.setLOCR_SMEM_ID(LOCR_SMEM_ID);
		LocrVO.setLOCR_NAME(LOCR_NAME);
		LocrVO.setLOCR_MAX_TABLE(LOCR_MAX_TABLE);
		LocrVO.setLOCR_MIN_TABLE(LOCR_MIN_TABLE);
		LocrVO.setLOCR_MAIN_TABLE(LOCR_MAIN_TABLE);
		LocrVO.setLOCR_GUEST_TABLE(LOCR_GUEST_TABLE);
		LocrVO.setLOCR_FLOOR(LOCR_FLOOR);
		LocrVO.setLOCR_CONTENT(LOCR_CONTENT);
		LocrVO.setLOCR_STATUS(LOCR_STATUS);
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					res.setStatus(401);
					String Msg = mapper.writeValueAsString(errorMsgs);
					
					res.getWriter().print(Msg);
					return;
				}
				
				/***************************2.開始修改資料*****************************************/
				LocrService LocrSvc = new LocrService();
				LocrVO = LocrSvc.updateLocr(LOCR_ID,LOCR_SMEM_ID,LOCR_NAME,  LOCR_MAX_TABLE,
						LOCR_MIN_TABLE,  LOCR_MAIN_TABLE, LOCR_GUEST_TABLE,  LOCR_FLOOR,
						LOCR_CONTENT,LOCR_STATUS);
				
				Map map = new HashMap();
				map.put("msg","success" );
				String msg = mapper.writeValueAsString(map);
System.out.println(msg);
				res.getWriter().print(msg);
								
				
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
//				req.setAttribute("LocrVO", LocrVO); // 資料庫update成功後,正確的的empVO物件,存入req
//				String url = "/locationroom/locationRoomServlet?action=locationRoomGetAll";
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
//				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());

				e.printStackTrace();
				res.setStatus(401);
				String Msg = mapper.writeValueAsString(errorMsgs);
				
				res.getWriter().print(Msg);
				
			}
		}


}
