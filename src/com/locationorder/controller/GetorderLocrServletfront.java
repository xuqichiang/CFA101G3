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
import com.locationorder.model.*;
import com.member.model.MemVO;



//找單筆頁面
@WebServlet("/locationorder/GetorderLocrServletfront")
public class GetorderLocrServletfront extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		String action = request.getParameter("action");
		PrintWriter out = response.getWriter();

		if ("getSmemID".equals(action)) {
			LocrService locrsvc = new LocrService();
			Integer LOCR_ID = new Integer(request.getParameter("LOCR_ID"));
			LocrVO oneLocrVO = locrsvc.getOneLocr(LOCR_ID);// 取得廳房資料
			
			int smem_id = oneLocrVO.getLOCR_SMEM_ID();
			LocrService locrService2 = new LocrService();
			MemVO memVO = locrService2.getSmem(smem_id);// 取得店家資料 方法額外呼叫
			
			
			Date ORDER_TIME = new java.sql.Date(new java.util.Date().getTime());
			
			
			// 用Map存取往前端送
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("LOCR_NAME", oneLocrVO.getLOCR_NAME());
			map.put("LOCR_ID", oneLocrVO.getLOCR_ID());
			map.put("LOCR_MAX_TABLE", oneLocrVO.getLOCR_MAX_TABLE());
			map.put("LOCR_MIN_TABLE", oneLocrVO.getLOCR_MIN_TABLE());
			map.put("LOCR_MIN_TABLE", oneLocrVO.getLOCR_MIN_TABLE());
			map.put("LOCR_SMEM_ID", oneLocrVO.getLOCR_SMEM_ID());
			map.put("LOCR_NAME", oneLocrVO.getLOCR_NAME());
			map.put("Mem_shop_name", memVO.getMem_shop_name());
			map.put("ORDER_TIME", ORDER_TIME);
			

			ObjectMapper mapper = new ObjectMapper();
			String data = mapper.writeValueAsString(map);
			out.print(data);
		}
		if ("getTime".equals(action)) {
		
			Integer LOCR_ID = new Integer(request.getParameter("LOCR_ID"));

			LocoService locoService = new LocoService();
			List<LocoVO> time = locoService.getTime(LOCR_ID);// 取得廳房時間
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");//轉換格式
			
			
			
			List<String> list = new ArrayList();
			
			for(LocoVO locoVO :time ) {
				Date loco_RESERVE_TIME = locoVO.getLOCO_RESERVE_TIME();
					String format = sdf.format(loco_RESERVE_TIME);
					list.add(format);
			}
			
			
			
			
				
			ObjectMapper mapper = new ObjectMapper();
			String data = mapper.writeValueAsString(list);
			out.print(data);
		}
		if ("check".equals(action)) {
			
			Integer LOCO_ID = new Integer(request.getParameter("LOCO_ID"));
			Integer LOCO_ORDER_STATUS =1;
			
			
			LocoService locoService = new LocoService();
			locoService.updateorder( LOCO_ORDER_STATUS,LOCO_ID);
			out.print("1");			
			

			}
		if ("cancel".equals(action)) {
			
			Integer LOCO_ID = new Integer(request.getParameter("LOCO_ID"));
			Integer LOCO_ORDER_STATUS =2;
			
			
			LocoService locoService = new LocoService();
			locoService.updateorder( LOCO_ORDER_STATUS,LOCO_ID);
			out.print("1");			
			

			}
		if ("finish".equals(action)) {
			
			Integer LOCO_ID = new Integer(request.getParameter("LOCO_ID"));
			Integer LOCO_ORDER_STATUS =3;
			
			
			LocoService locoService = new LocoService();
			locoService.updateorder( LOCO_ORDER_STATUS,LOCO_ID);
			out.print("1");			
			

			}

		if ("paydeposit".equals(action)) {
			
			Integer LOCO_ID = new Integer(request.getParameter("LOCO_ID"));
			
			
			
			LocoService locoService = new LocoService();
			
			Integer LOCO_PAY_STATUS =3;
			locoService.updatepay(LOCO_PAY_STATUS, LOCO_ID);
			
			out.print("1");			
			

			}
		if ("deleteorder".equals(action)) {
			
			Integer LOCO_ID = new Integer(request.getParameter("LOCO_ID"));
			Integer LOCO_ORDER_STATUS =4;
			
			
			LocoService locoService = new LocoService();
			locoService.updateorder( LOCO_ORDER_STATUS,LOCO_ID);
			out.print("1");			
			

			}
		if ("finishtotal".equals(action)) {
			
			Integer LOCO_ID = new Integer(request.getParameter("LOCO_ID"));
			
			
			
			LocoService locoService = new LocoService();
			Integer LOCO_PAY_STATUS =4;
			locoService.updatepay(LOCO_PAY_STATUS, LOCO_ID);
			
			out.print("1");			
			
			
		}
		if ("rebackcheck".equals(action)) {
			
			Integer LOCO_ID = new Integer(request.getParameter("LOCO_ID"));
			
			
			
			LocoService locoService = new LocoService();
			Integer LOCO_PAY_STATUS =6;
			locoService.updatepay(LOCO_PAY_STATUS, LOCO_ID);
			
			out.print("1");			
			
			
		}
		
		if("checkoutdeposit".equals(action)) {
			String loco_idStr = request.getParameter("LOCO_ID");
			
			String locopaytypeStr = request.getParameter("LOCO_PAYTYPE");
			
			if(loco_idStr != null && locopaytypeStr != null) {
				
				Integer LOCO_ID = Integer.parseInt(loco_idStr);
				Integer LOCO_PAYTYPE = Integer.parseInt(locopaytypeStr);
				
				LocoService locoService = new LocoService();
				LocoVO locoVO = locoService.findByPrimaryKeyLoco(LOCO_ID);//取得訂單VO
				Integer loco_DEPOSIT = locoVO.getLOCO_DEPOSIT();//取得訂金付款金額
				request.setAttribute("loco_DEPOSIT", loco_DEPOSIT);//將付款金額set在request scope
				List<Integer> loco_idList = new ArrayList<Integer>();//創建訂單ID的list 後續結帳servlet用
				loco_idList.add(LOCO_ID);//將訂單ID加到list
				HttpSession session = request.getSession();
				session.setAttribute("loco_idList",loco_idList);//將訂單ID List設定在session scope
		
				if(LOCO_PAYTYPE.equals(0)) {
					request.getRequestDispatcher("/front_end/locationorder/CreditCard.jsp").forward(request, response);
				}else if(LOCO_PAYTYPE.equals(1)){
					request.getRequestDispatcher("/front_end/locationorder/WebATM.jsp").forward(request, response);
				}
			}
		}
		if("reback".equals(action)) {
			String loco_idStr = request.getParameter("LOCO_ID");
			
			String locopaytypeStr = request.getParameter("LOCO_PAYTYPE");
			
			if(loco_idStr != null && locopaytypeStr != null) {
				
				Integer LOCO_ID = Integer.parseInt(loco_idStr);
				Integer LOCO_PAYTYPE = Integer.parseInt(locopaytypeStr);
				
				LocoService locoService = new LocoService();
				LocoVO locoVO = locoService.findByPrimaryKeyLoco(LOCO_ID);//取得訂單VO
				Integer loco_DEPOSIT = locoVO.getLOCO_DEPOSIT();//取得訂金付款金額
				request.setAttribute("loco_DEPOSIT", loco_DEPOSIT);//將付款金額set在request scope
				List<Integer> loco_idList = new ArrayList<Integer>();//創建訂單ID的list 後續結帳servlet用
				loco_idList.add(LOCO_ID);//將訂單ID加到list
				HttpSession session = request.getSession();
				session.setAttribute("loco_idList",loco_idList);//將訂單ID List設定在session scope
		
				if(LOCO_PAYTYPE.equals(0)) {
					request.getRequestDispatcher("/front_end/locationorder/CreditCardreback.jsp").forward(request, response);
				}else if(LOCO_PAYTYPE.equals(1)){
					request.getRequestDispatcher("/front_end/locationorder/WebATMreback.jsp").forward(request, response);
				}
			}
		}
		if ("checkoutTotal".equals(action)) {
			String loco_idStr = request.getParameter("LOCO_IDtotal");
			System.out.println(loco_idStr);
			String locopaytypeStr = request.getParameter("LOCO_PAYTYPEtotal");
			System.out.println(locopaytypeStr);
			if (loco_idStr != null && locopaytypeStr != null) {
				System.out.println(locopaytypeStr);
				Integer LOCO_ID = Integer.parseInt(loco_idStr);
				Integer LOCO_PAYTYPE = Integer.parseInt(locopaytypeStr);

				LocoService locoService = new LocoService();
				LocoVO locoVO = locoService.findByPrimaryKeyLoco(LOCO_ID);// 取得訂單VO
				
				Integer loco_DEPOSIT = locoVO.getLOCO_DEPOSIT();// 取得訂金付款金額
				Integer loco_TOTALPRICE = locoVO.getLOCO_TOTALPRICE();// 取得總付款金額
				Integer loco_lastPRICE = loco_TOTALPRICE-loco_DEPOSIT;
				
				
				request.setAttribute("loco_DEPOSIT", loco_lastPRICE);// 將付款金額set在request scope
				List<Integer> loco_idList = new ArrayList<Integer>();// 創建訂單ID的list 後續結帳servlet用
				loco_idList.add(LOCO_ID);// 將訂單ID加到list
				HttpSession session = request.getSession();
				session.setAttribute("loco_idList", loco_idList);// 將訂單ID List設定在session scope

				if (LOCO_PAYTYPE.equals(0)) {
					request.getRequestDispatcher("/front_end/locationorder/CreditCardtotal.jsp").forward(request, response);
				} else if (LOCO_PAYTYPE.equals(1)) {
					request.getRequestDispatcher("/front_end/locationorder/WebATMtotal.jsp").forward(request, response);
				}
			}
		}
		//WebATM付款確認
		if("webATM".equals(action)) {
			HttpSession session = request.getSession();
			List<Integer> loco_idList = (List<Integer>)session.getAttribute("loco_idList");//取得訂單ID List
			Integer locoidList = loco_idList.get(0);
			LocoService locoService = new LocoService();
			
			request.removeAttribute("loco_DEPOSIT");
			session.removeAttribute("loco_idList");
			try {
				Integer loco_DEPOSIT = Integer.parseInt(request.getParameter("loco_DEPOSIT").trim());
				Integer pay = Integer.parseInt(request.getParameter("pay").trim());
				if(pay.equals(loco_DEPOSIT)) {
					locoService.updatepay(3, locoidList);//更新為付款成功
					request.getRequestDispatcher("/front_end/locationorder/Success.jsp").forward(request, response);
				}else {
					locoService.updatepay(1, locoidList);//更新為付款失敗
					request.getRequestDispatcher("/front_end/locationorder/Error.jsp").forward(request, response);
				}
			} catch (Exception e) {
				System.out.println("WebATM輸入異常:"+e.getMessage());
				locoService.updatepay(1, locoidList);//更新為付款失敗
				request.getRequestDispatcher("/front_end/locationorder/Error.jsp").forward(request, response);
			} 
		}
		//信用卡付款確認
		if("creditcard".equals(action)) {
			request.removeAttribute("loco_DEPOSIT");
			String cardnumber = request.getParameter("cardnumber");
			String expirationdate = request.getParameter("expirationdate");
			String securitycode = request.getParameter("securitycode");
			
			HttpSession session = request.getSession();
			List<Integer> loco_idList = (List<Integer>)session.getAttribute("loco_idList");//取得訂單ID List
			session.removeAttribute("loco_idList");
			
			Integer locoidList = loco_idList.get(0);
			LocoService locoService = new LocoService();
			
			if("4321 4321 4321 4321".equals(cardnumber) && "10/25".equals(expirationdate) && "333".equals(securitycode)) {
				locoService.updatepay(3, locoidList);//更新為付款成功
				request.getRequestDispatcher("/front_end/locationorder/Success.jsp").forward(request, response);
			}else {
				locoService.updatepay(1, locoidList);//更新為付款失敗
				request.getRequestDispatcher("/front_end/locationorder/Error.jsp").forward(request, response);
			}
		}
		//WebATM付款確認
		if("webATMtotal".equals(action)) {
			HttpSession session = request.getSession();
			List<Integer> loco_idList = (List<Integer>)session.getAttribute("loco_idList");//取得訂單ID List
			Integer locoidList = loco_idList.get(0);
			LocoService locoService = new LocoService();
			
			request.removeAttribute("loco_DEPOSIT");
			session.removeAttribute("loco_idList");
			try {
				Integer loco_DEPOSIT = Integer.parseInt(request.getParameter("loco_DEPOSIT").trim());
				Integer pay = Integer.parseInt(request.getParameter("pay").trim());
				if(pay.equals(loco_DEPOSIT)) {
					locoService.updatepay(4, locoidList);//更新為付款成功
					request.getRequestDispatcher("/front_end/locationorder/Success.jsp").forward(request, response);
				}else {
					locoService.updatepay(1, locoidList);//更新為付款失敗
					request.getRequestDispatcher("/front_end/locationorder/Error.jsp").forward(request, response);
				}
			} catch (Exception e) {
				System.out.println("WebATM輸入異常:"+e.getMessage());
				locoService.updatepay(1, locoidList);//更新為付款失敗
				request.getRequestDispatcher("/front_end/locationorder/Error.jsp").forward(request, response);
			} 
		}
		//信用卡付款確認
		if("creditcardtotal".equals(action)) {
			request.removeAttribute("loco_DEPOSIT");
			String cardnumber = request.getParameter("cardnumber");
			String expirationdate = request.getParameter("expirationdate");
			String securitycode = request.getParameter("securitycode");
			
			HttpSession session = request.getSession();
			List<Integer> loco_idList = (List<Integer>)session.getAttribute("loco_idList");//取得訂單ID List
			session.removeAttribute("loco_idList");
			
			Integer locoidList = loco_idList.get(0);
			LocoService locoService = new LocoService();
			
			if("4321 4321 4321 4321".equals(cardnumber) && "10/25".equals(expirationdate) && "333".equals(securitycode)) {
				locoService.updatepay(4, locoidList);//更新為付款成功
				request.getRequestDispatcher("/front_end/locationorder/Success.jsp").forward(request, response);
			}else {
				locoService.updatepay(1, locoidList);//更新為付款失敗
				request.getRequestDispatcher("/front_end/locationorder/Error.jsp").forward(request, response);
			}
		}
		//WebATM付款確認
		if("webATMreback".equals(action)) {
			HttpSession session = request.getSession();
			List<Integer> loco_idList = (List<Integer>)session.getAttribute("loco_idList");//取得訂單ID List
			Integer locoidList = loco_idList.get(0);
			LocoService locoService = new LocoService();
			
			request.removeAttribute("loco_DEPOSIT");
			session.removeAttribute("loco_idList");
			try {
				Integer loco_DEPOSIT = Integer.parseInt(request.getParameter("loco_DEPOSIT").trim());
				Integer pay = Integer.parseInt(request.getParameter("pay").trim());
				if(pay.equals(loco_DEPOSIT)) {
					locoService.updatepay(5, locoidList);//更新為付款成功
					request.getRequestDispatcher("/front_end/locationorder/Success.jsp").forward(request, response);
				}else {
					locoService.updatepay(1, locoidList);//更新為付款失敗
					request.getRequestDispatcher("/front_end/locationorder/Error.jsp").forward(request, response);
				}
			} catch (Exception e) {
				System.out.println("WebATM輸入異常:"+e.getMessage());
				locoService.updatepay(1, locoidList);//更新為付款失敗
				request.getRequestDispatcher("/front_end/locationorder/Error.jsp").forward(request, response);
			} 
		}
		//信用卡付款確認
		if("creditcardreback".equals(action)) {
			request.removeAttribute("loco_DEPOSIT");
			String cardnumber = request.getParameter("cardnumber");
			String expirationdate = request.getParameter("expirationdate");
			String securitycode = request.getParameter("securitycode");
			
			HttpSession session = request.getSession();
			List<Integer> loco_idList = (List<Integer>)session.getAttribute("loco_idList");//取得訂單ID List
			session.removeAttribute("loco_idList");
			
			Integer locoidList = loco_idList.get(0);
			LocoService locoService = new LocoService();
			
			if("4321 4321 4321 4321".equals(cardnumber) && "10/25".equals(expirationdate) && "333".equals(securitycode)) {
				locoService.updatepay(5, locoidList);//更新為付款成功
				request.getRequestDispatcher("/front_end/locationorder/Success.jsp").forward(request, response);
			}else {
				locoService.updatepay(1, locoidList);//更新為付款失敗
				request.getRequestDispatcher("/front_end/locationorder/Error.jsp").forward(request, response);
			}
		}
			
	}

}
