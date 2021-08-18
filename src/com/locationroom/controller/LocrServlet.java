package com.locationroom.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.locationimages.model.LociService;
import com.locationimages.model.LociVO;
import com.locationroom.model.*;
import com.member.model.MemVO;

@WebServlet("/locationroom/locrServletxx")
public class LocrServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		//請求會員名稱ID
		if("getname".equals(action)) {
				//獲取會員ID
			HttpSession session = req.getSession();
			MemVO memVO = (MemVO)session.getAttribute("user");		
			
			res.setContentType("text/html;charset=UTF-8");
			
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			
			map.put("Mem_id", memVO.getMem_id());
			map.put("Mem_name", memVO.getMem_name());
			map.put("Mem_shop_name", memVO.getMem_shop_name());
			map.put("Mem_role", memVO.getMem_role());
			
			
			ObjectMapper mapper = new ObjectMapper(); 
			String data2 = mapper.writeValueAsString(map);
			
			PrintWriter out = res.getWriter();
			out.print(data2);
	

		}
		
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("LOCR_ID");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入廳房編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front_end/locationroom/locationRoomSelect.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				Integer LOCR_ID = null;
				try {
					LOCR_ID = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("廳房編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front_end/locationroom/locationRoomSelect.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				LocrService LocrSvc = new LocrService();
				LocrVO LocrVO = LocrSvc.getOneLocr(LOCR_ID);
				if (LocrVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front_end/locationroom/locationRoomSelect.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("LocrVO", LocrVO); // 資料庫取出的LocrVO物件,存入req
				String url = "/front_end/locationroom/locationRoomGetOne.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front_end/locationroom/locationRoomSelect.jsp");
				failureView.forward(req, res);
			}
		}
		if ("getOne_For_Display2".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("LOCR_ID");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入廳房編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front_end/locationroom/locationRoomSelect.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				Integer LOCR_ID = null;
				try {
					LOCR_ID = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("廳房編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front_end/locationroom/locationRoomSelect.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				LocrService LocrSvc = new LocrService();
				LocrVO LocrVO = LocrSvc.getOneLocr(LOCR_ID);
				if (LocrVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front_end/locationroom/locationRoomSelect.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("LocrVO", LocrVO); // 資料庫取出的LocrVO物件,存入req
				String url = "/front_end/locationroom/locationRoomGetOne.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front_end/locationroom/locationRoomSelect.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				Integer LOCR_ID = new Integer(req.getParameter("LOCR_ID"));
				
				/***************************2.開始查詢資料****************************************/
				LocrService LocrSvc = new LocrService();
				LocrVO LocrVO = LocrSvc.getOneLocr(LOCR_ID);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("LocrVO", LocrVO);         // 資料庫取出的empVO物件,存入req
				String url = "/front_end/locationroom/locationRoomUpdateInput.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front_end/locationroom/locationRoomUpdate.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
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
		LocrVO.setLOCR_ID(LOCR_ID);
		LocrVO.setLOCR_SMEM_ID(LOCR_SMEM_ID);
		LocrVO.setLOCR_NAME(LOCR_NAME);
		LocrVO.setLOCR_MAX_TABLE(LOCR_MAX_TABLE);
		LocrVO.setLOCR_MIN_TABLE(LOCR_MIN_TABLE);
		LocrVO.setLOCR_MAIN_TABLE(LOCR_MAIN_TABLE);
		LocrVO.setLOCR_GUEST_TABLE(LOCR_GUEST_TABLE);
		LocrVO.setLOCR_FLOOR(LOCR_FLOOR);
		LocrVO.setLOCR_CONTENT(LOCR_CONTENT);
		LocrVO.setLOCR_STATUS(LOCR_STATUS);
		LociService lociService = new LociService();
		List<LociVO> lociList = lociService.findByForeignKey(LOCR_ID);
		
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("locrVO", LocrVO);
					req.setAttribute("lociList", lociList);// 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front_end/locationroom/locationRoomUpdate.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				LocrService LocrSvc = new LocrService();
				LocrVO = LocrSvc.updateLocr(LOCR_ID,LOCR_SMEM_ID,LOCR_NAME,  LOCR_MAX_TABLE,
						LOCR_MIN_TABLE,  LOCR_MAIN_TABLE, LOCR_GUEST_TABLE,  LOCR_FLOOR,
						LOCR_CONTENT,LOCR_STATUS);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("LocrVO", LocrVO); // 資料庫update成功後,正確的的empVO物件,存入req
				req.setAttribute("lociList", lociList);
				String url = "/locationroom/locationRoomServlet?action=locationRoomGetAll";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front_end/locationroom/locationRoomUpdateInput.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { // 來自addEmp.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
							
				Integer LOCR_SMEM_ID=null;
//				try {
//					LOCR_SMEM_ID = new Integer(req.getParameter("LOCR_SMEM_ID").trim());
//				} catch (NumberFormatException e) {
//					LOCR_SMEM_ID = 0;
//					errorMsgs.add("代號請填數字.");
//				}
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

				// Send the use back to the form, if there were er nrors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("LocrVO", LocrVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front_end/locationroom/locationRoomAddLocr.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				LocrService LocrSvc = new LocrService();
				LocrVO = LocrSvc.addLocr(LOCR_SMEM_ID,LOCR_NAME,LOCR_MAX_TABLE,
						LOCR_MIN_TABLE,LOCR_MAIN_TABLE,LOCR_GUEST_TABLE,LOCR_FLOOR,LOCR_CONTENT,LOCR_STATUS);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/locationroom/locationRoomServlet?action=locationRoomGetAll";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front_end/locationroom/locationRoomAddLocr.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				Integer LOCR_ID = new Integer(req.getParameter("LOCR_ID"));
				
				/***************************2.開始刪除資料***************************************/
				LocrService LocrSvc = new LocrService();
				LocrSvc.deleteLocr(LOCR_ID);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/locationroom/locationRoomServlet?action=locationRoomDelete";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/locationroom/locationRoomServlet?action=locationRoomDelete");
				failureView.forward(req, res);
			}
			
		}
		if ("delete2".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				Integer LOCR_ID = new Integer(req.getParameter("LOCR_ID"));
				
				/***************************2.開始刪除資料***************************************/
				
				LociService lociSvc = new LociService();
				lociSvc.deleteLoci(LOCR_ID);;
				
				
				LocrService LocrSvc = new LocrService();
				LocrSvc.deleteLocr(LOCR_ID);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/locationroom/locationRoomServlet?action=locationRoomDelete";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/locationroom/locationRoomServlet?action=locationRoomDelete");
				failureView.forward(req, res);
			}
			
		}
	}

}
