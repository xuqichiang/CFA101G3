package com.photoorder.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
import com.photoorder.model.PhooService;
import com.photoorder.model.PhooVO;

//買家會員預約訂單
@WebServlet("/photoorder/phooByBmemServlet")
public class PhooByBmemServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//設置頭
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String action = request.getParameter("action");
		PrintWriter out = response.getWriter();//輸出
		
		//買家查詢預約all
		if("getBmemId".equals(action)) {
			//獲取會員ID
			HttpSession session = request.getSession();
			MemVO memVO = (MemVO)session.getAttribute("user");
			Integer bmem_id = memVO.getMem_id();
//			System.out.println(bmem_id);
			
			PhooService phooSvc = new PhooService();
			List list = phooSvc.getBmemReserveForm(bmem_id);
//			System.out.println(list);
			
			//輸出
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(list);
			out.print(json);
		}
		//買家單筆查詢
		if("getOneBmem".equals(action)) {
			//取得參數
			Integer phoo_id = new Integer(request.getParameter("phoo_id"));
//			System.out.println(phoo_id);
			//調用方法
			PhooService phooSvc = new PhooService();
			Map map = phooSvc.getBmemOneForm(phoo_id);
			//輸出
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(map);
			out.print(json);
		}
		//取消訂單
		if ("deleteorder".equals(action)) {
			Integer phoo_id = new Integer(request.getParameter("phoo_id"));
			Integer phoo_order_status = 4;
			PhooService phoosvc = new PhooService();
			phoosvc.updateOrder(phoo_order_status, phoo_id);
			out.print("1");
		}
		//付訂金
		if ("paydeposit".equals(action)) {
			Integer phoo_id = new Integer(request.getParameter("phoo_id"));
			PhooService phoosvc = new PhooService();
			Integer phoo_pay_status = 3;
			phoosvc.updatePay(phoo_pay_status, phoo_id);
			out.print("1");
		}
		//付尾款
		if ("finishtotal".equals(action)) {
			Integer phoo_id = new Integer(request.getParameter("phoo_id"));
			PhooService phoosvc = new PhooService();
			Integer phoo_pay_status = 4;
			phoosvc.updatePay(phoo_pay_status, phoo_id);
			out.print("1");
		}
		//退款中
//		if ("rebackcheck".equals(action)) {
//			Integer phoo_id = new Integer(request.getParameter("phoo_id"));
//			PhooService phoosvc = new PhooService();
//			Integer phoo_pay_status = 6;
//			phoosvc.updatePay(phoo_pay_status, phoo_id);
//			out.print("1");
//		}
		//訂金
		if ("checkoutdeposit".equals(action)) {
			String phoo_idStr = request.getParameter("phoo_id");
			String phoopaytypeStr = request.getParameter("phoo_paytype");
			
			if (phoo_idStr != null && phoopaytypeStr != null) {
				Integer PHOO_ID = Integer.parseInt(phoo_idStr);
				Integer PHOO_PAYTYPE = Integer.parseInt(phoopaytypeStr);
				
				PhooService phoosvc = new PhooService();
				PhooVO phooVO = phoosvc.findByPhooId(PHOO_ID);// 取得訂單VO
				Integer PHOO_DEPOSIT = phooVO.getPhoo_deposit();// 取得訂金付款金額
				request.setAttribute("PHOO_DEPOSIT", PHOO_DEPOSIT);// 將付款金額set在request scope
				List<Integer> phoo_idList = new ArrayList<Integer>();// 創建訂單ID的list 後續結帳servlet用
				phoo_idList.add(PHOO_ID);// 將訂單ID加到list
				HttpSession session = request.getSession();
				session.setAttribute("phoo_idList", phoo_idList);// 將訂單ID List設定在session scope

				if (PHOO_PAYTYPE.equals(0)) {
					request.getRequestDispatcher("/front_end/photoorder/CreditCard.jsp").forward(request, response);
				} else if (PHOO_PAYTYPE.equals(1)) {
					request.getRequestDispatcher("/front_end/photoorder/WebATM.jsp").forward(request, response);
				}
			}
		}
		//尾款
		if ("checkoutTotal".equals(action)) {
			String phoo_idStr = request.getParameter("phoo_id_total");
			String phoopaytypeStr = request.getParameter("phoo_paytype_total");

			if (phoo_idStr != null && phoopaytypeStr != null) {
				Integer PHOO_ID = Integer.parseInt(phoo_idStr);
				Integer PHOO_PAYTYPE = Integer.parseInt(phoopaytypeStr);
				PhooService phoosvc = new PhooService();
				PhooVO phooVO = phoosvc.findByPhooId(PHOO_ID);// 取得訂單VO
				Integer PHOO_DEPOSIT = phooVO.getPhoo_deposit();// 取得訂金付款金額
				Integer PHOO_TOTALPRICE = phooVO.getPhoo_totalprice();// 取得總付款金額
				Integer PHOO_lastPRICE = PHOO_TOTALPRICE-PHOO_DEPOSIT;
				
				request.setAttribute("PHOO_DEPOSIT", PHOO_lastPRICE);// 將付款金額set在request scope
				List<Integer> phoo_idList = new ArrayList<Integer>();// 創建訂單ID的list 後續結帳servlet用
				phoo_idList.add(PHOO_ID);// 將訂單ID加到list
				HttpSession session = request.getSession();
				session.setAttribute("phoo_idList", phoo_idList);// 將訂單ID List設定在session scope

				if (PHOO_PAYTYPE.equals(0)) {
					request.getRequestDispatcher("/front_end/photoorder/CreditCardtotal.jsp").forward(request, response);
				} else if (PHOO_PAYTYPE.equals(1)) {
					request.getRequestDispatcher("/front_end/photoorder/WebATMtotal.jsp").forward(request, response);
				}
			}
		}
		//退款
//		if ("reback".equals(action)) {
//			String phoo_idStr = request.getParameter("phoo_id");
//			String phoopaytypeStr = request.getParameter("phoo_paytype");
//					
//			if (phoo_idStr != null && phoopaytypeStr != null) {
//				Integer PHOO_ID = Integer.parseInt(phoo_idStr);
//				Integer PHOO_PAYTYPE = Integer.parseInt(phoopaytypeStr);
//
//				PhooService phoosvc = new PhooService();
//				PhooVO phooVO = phoosvc.findByPhooId(PHOO_ID);// 取得訂單VO
//				Integer PHOO_DEPOSIT = phooVO.getPhoo_deposit();// 取得訂金付款金額
//				request.setAttribute("PHOO_DEPOSIT", PHOO_DEPOSIT);// 將付款金額set在request scope
//				List<Integer> phoo_idList = new ArrayList<Integer>();// 創建訂單ID的list 後續結帳servlet用
//				phoo_idList.add(PHOO_ID);// 將訂單ID加到list
//				HttpSession session = request.getSession();
//				session.setAttribute("phoo_idList", phoo_idList);// 將訂單ID List設定在session scope
//
//				if (PHOO_PAYTYPE.equals(0)) {
//					request.getRequestDispatcher("/front_end/photoorder/CreditCardreback.jsp").forward(request, response);
//				} else if (PHOO_PAYTYPE.equals(1)) {
//					request.getRequestDispatcher("/front_end/photoorder/WebATMreback.jsp").forward(request, response);
//				}
//			}
//		}		
		// WebATM付款確認 訂金
		if ("webATM".equals(action)) {
			HttpSession session = request.getSession();
			List<Integer> phoo_idList = (List<Integer>) session.getAttribute("phoo_idList");// 取得訂單ID List
			Integer phooidList = phoo_idList.get(0);
			PhooService phoosvc = new PhooService();

			request.removeAttribute("PHOO_DEPOSIT");
			session.removeAttribute("phoo_idList");
			try {
				Integer PHOO_DEPOSIT = Integer.parseInt(request.getParameter("PHOO_DEPOSIT").trim());
				Integer pay = Integer.parseInt(request.getParameter("pay").trim());
				if (pay.equals(PHOO_DEPOSIT)) {
					phoosvc.updatePay(3, phooidList);// 更新為已付訂金
					request.getRequestDispatcher("/front_end/photoorder/Success.jsp").forward(request, response);
				} else {
					phoosvc.updatePay(1, phooidList);// 更新為付款失敗
					request.getRequestDispatcher("/front_end/photoorder/Error.jsp").forward(request, response);
				}
			} catch (Exception e) {
				System.out.println("WebATM輸入異常:" + e.getMessage());
				phoosvc.updatePay(1, phooidList);// 更新為付款失敗
				request.getRequestDispatcher("/front_end/photoorder/Error.jsp").forward(request, response);
			}
		}
		// 信用卡付款確認 訂金
		if ("creditcard".equals(action)) {
			request.removeAttribute("PHOO_DEPOSIT");
			String cardnumber = request.getParameter("cardnumber");
			String expirationdate = request.getParameter("expirationdate");
			String securitycode = request.getParameter("securitycode");

			HttpSession session = request.getSession();
			List<Integer> phoo_idList = (List<Integer>) session.getAttribute("phoo_idList");// 取得訂單ID List
			session.removeAttribute("phoo_idList");

			Integer phooidList = phoo_idList.get(0);
			PhooService phoosvc = new PhooService();

			if ("4321 4321 4321 4321".equals(cardnumber) && "10/25".equals(expirationdate)
					&& "333".equals(securitycode)) {
				phoosvc.updatePay(3, phooidList);// 更新為已付訂金
				request.getRequestDispatcher("/front_end/photoorder/Success.jsp").forward(request, response);
			} else {
				phoosvc.updatePay(1, phooidList);// 更新為付款失敗
				request.getRequestDispatcher("/front_end/photoorder/Error.jsp").forward(request, response);
			}
		}
		// WebATM付款確認 尾款
		if ("webATMtotal".equals(action)) {
			HttpSession session = request.getSession();
			List<Integer> phoo_idList = (List<Integer>) session.getAttribute("phoo_idList");// 取得訂單ID List
			Integer phooidList = phoo_idList.get(0);
			PhooService phoosvc = new PhooService();

			request.removeAttribute("PHOO_DEPOSIT");
			session.removeAttribute("phoo_idList");
			try {
				Integer PHOO_DEPOSIT = Integer.parseInt(request.getParameter("PHOO_DEPOSIT").trim());
				Integer pay = Integer.parseInt(request.getParameter("pay").trim());
				if (pay.equals(PHOO_DEPOSIT)) {
					phoosvc.updatePay(4, phooidList);// 更新為已付尾款
					request.getRequestDispatcher("/front_end/photoorder/Success.jsp").forward(request, response);
				} else {
					phoosvc.updatePay(1, phooidList);// 更新為付款失敗
					request.getRequestDispatcher("/front_end/photoorder/Error.jsp").forward(request, response);
				}
			} catch (Exception e) {
				System.out.println("WebATM輸入異常:" + e.getMessage());
				phoosvc.updatePay(1, phooidList);// 更新為付款失敗
				request.getRequestDispatcher("/front_end/photoorder/Error.jsp").forward(request, response);
			}
		}
		// 信用卡付款確認 尾款
		if ("creditcardtotal".equals(action)) {
			request.removeAttribute("PHOO_DEPOSIT");
			String cardnumber = request.getParameter("cardnumber");
			String expirationdate = request.getParameter("expirationdate");
			String securitycode = request.getParameter("securitycode");

			HttpSession session = request.getSession();
			List<Integer> phoo_idList = (List<Integer>) session.getAttribute("phoo_idList");// 取得訂單ID List
			session.removeAttribute("phoo_idList");

			Integer phooidList = phoo_idList.get(0);
			PhooService phoosvc = new PhooService();

			if ("4321 4321 4321 4321".equals(cardnumber) && "10/25".equals(expirationdate)
					&& "333".equals(securitycode)) {
				phoosvc.updatePay(4, phooidList);// 更新為已付尾款
				request.getRequestDispatcher("/front_end/photoorder/Success.jsp").forward(request, response);
			} else {
				phoosvc.updatePay(1, phooidList);// 更新為付款失敗
				request.getRequestDispatcher("/front_end/photoorder/Error.jsp").forward(request, response);
			}
		}
		// WebATM付款確認 退款
//		if ("webATMreback".equals(action)) {
//			HttpSession session = request.getSession();
//			List<Integer> phoo_idList = (List<Integer>) session.getAttribute("phoo_idList");// 取得訂單ID List
//			Integer phooidList = phoo_idList.get(0);
//			PhooService phoosvc = new PhooService();
//
//			request.removeAttribute("PHOO_DEPOSIT");
//			session.removeAttribute("phoo_idList");
//			try {
//				Integer PHOO_DEPOSIT = Integer.parseInt(request.getParameter("PHOO_DEPOSIT").trim());
//				Integer pay = Integer.parseInt(request.getParameter("pay").trim());
//				if (pay.equals(PHOO_DEPOSIT)) {
//					phoosvc.updatePay(5, phooidList);// 更新為退款中
//					request.getRequestDispatcher("/front_end/photoorder/Success.jsp").forward(request, response);
//				} else {
//					phoosvc.updatePay(1, phooidList);// 更新為付款失敗
//					request.getRequestDispatcher("/front_end/photoorder/Error.jsp").forward(request, response);
//				}
//			} catch (Exception e) {
//				System.out.println("WebATM輸入異常:" + e.getMessage());
//				phoosvc.updatePay(1, phooidList);// 更新為付款失敗
//				request.getRequestDispatcher("/front_end/photoorder/Error.jsp").forward(request, response);
//			}
//		}
		// 信用卡付款確認 退款
//		if("creditcardreback".equals(action)) {
//			request.removeAttribute("PHOO_DEPOSIT");
//			String cardnumber = request.getParameter("cardnumber");
//			String expirationdate = request.getParameter("expirationdate");
//			String securitycode = request.getParameter("securitycode");
//			
//			HttpSession session = request.getSession();
//			List<Integer> phoo_idList = (List<Integer>)session.getAttribute("phoo_idList");//取得訂單ID List
//			session.removeAttribute("loco_idList");
//			
//			Integer phooidList = phoo_idList.get(0);
//			PhooService locoService = new PhooService();
//			
//			if("4321 4321 4321 4321".equals(cardnumber) && "10/25".equals(expirationdate) && "333".equals(securitycode)) {
//				locoService.updatePay(5, phooidList);// 更新為退款中
//				request.getRequestDispatcher("/front_end/locationorder/Success.jsp").forward(request, response);
//			}else {
//				locoService.updatePay(1, phooidList);// 更新為付款失敗
//				request.getRequestDispatcher("/front_end/locationorder/Error.jsp").forward(request, response);
//			}
//		}
	}
}
