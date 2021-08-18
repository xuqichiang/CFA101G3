package com.shop_order.controller;

import java.io.IOException;
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
import com.shop_order.model.SpoService;
import com.shop_order.model.SpoVO;
import com.shop_order_item.model.SpoiService;
import com.shop_order_item.model.SpoiVO;

@WebServlet("/shop_order/buyerSpoServlet")
public class BuyerSpoServlet extends HttpServlet {
	
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		response.setHeader("Cache-Control","no-store");//清快取
		response.setHeader("Pragma","no-cache");//清快取
		response.setDateHeader("Expires",0);//清快取
		String action = request.getParameter("action");
		
		//獲取所有訂單
		if("getAll".equals(action)) {
			HttpSession session = request.getSession();
			MemVO memVO = (MemVO)session.getAttribute("user");
			Integer bmem_id = memVO.getMem_id();
			SpoService spoService = new SpoService();
			List<SpoVO> list = spoService.findByForeignKey(bmem_id);
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(response.getWriter(), list);
		}
		
		//獲取單筆訂單
		if("getOne".equals(action)) {
			String spo_idStr = request.getParameter("spo_id");
			if(spo_idStr != null) {
				Integer spo_id = Integer.parseInt(spo_idStr);
				SpoService spoService = new SpoService();
				SpoVO spoVO = spoService.getOne(spo_id);
				ObjectMapper mapper = new ObjectMapper();
				mapper.writeValue(response.getWriter(), spoVO);
			}
		}
		
		//獲取待付款訂單
		if("outstanding".equals(action)) {
			HttpSession session = request.getSession();
			MemVO memVO = (MemVO)session.getAttribute("user");
			Integer bmem_id = memVO.getMem_id();
			SpoService spoService = new SpoService();
			List<SpoVO> list = spoService.findByForeignKey(bmem_id);
			List<SpoVO> buyerSpoList = new ArrayList<SpoVO>();
			for (SpoVO spoVO : list) {
				if(spoVO.getSpo_pay_status().equals(0)||spoVO.getSpo_pay_status().equals(1)) {
					buyerSpoList.add(spoVO);
				}
			}
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(response.getWriter(), buyerSpoList);
		}
		
		//獲取待出貨訂單
		if("unshipped".equals(action)) {
			HttpSession session = request.getSession();
			MemVO memVO = (MemVO)session.getAttribute("user");
			Integer bmem_id = memVO.getMem_id();
			SpoService spoService = new SpoService();
			List<SpoVO> list = spoService.findByForeignKey(bmem_id);
			List<SpoVO> buyerSpoList = new ArrayList<SpoVO>();
			for (SpoVO spoVO : list) {
				if(spoVO.getSpo_cargo_status().equals(0)) {
					buyerSpoList.add(spoVO);
				}
			}
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(response.getWriter(), buyerSpoList);
		}
		
		//獲取待收貨訂單
		if("shipped".equals(action)) {
			HttpSession session = request.getSession();
			MemVO memVO = (MemVO)session.getAttribute("user");
			Integer bmem_id = memVO.getMem_id();
			SpoService spoService = new SpoService();
			List<SpoVO> list = spoService.findByForeignKey(bmem_id);
			List<SpoVO> buyerSpoList = new ArrayList<SpoVO>();
			for (SpoVO spoVO : list) {
				if(spoVO.getSpo_cargo_status().equals(1)) {
					buyerSpoList.add(spoVO);
				}
			}
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(response.getWriter(), buyerSpoList);
		}
		
		//獲取已完成訂單
		if("finish".equals(action)) {
			HttpSession session = request.getSession();
			MemVO memVO = (MemVO)session.getAttribute("user");
			Integer bmem_id = memVO.getMem_id();
			SpoService spoService = new SpoService();
			List<SpoVO> list = spoService.findByForeignKey(bmem_id);
			List<SpoVO> buyerSpoList = new ArrayList<SpoVO>();
			for (SpoVO spoVO : list) {
				if(spoVO.getSpo_status().equals(2)) {
					buyerSpoList.add(spoVO);
				}
			}
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(response.getWriter(), buyerSpoList);
		}
		
		
		//獲取商品訂單明細by Spo_id
		if("getBySpo_id".equals(action)) {
			String spo_idStr = request.getParameter("spo_id");
			if(spo_idStr != null) {
				SpoiService spoiService = new SpoiService();
				List<SpoiVO> list = spoiService.getBySpo_id(Integer.parseInt(spo_idStr));
				ObjectMapper mapper = new ObjectMapper();
				mapper.writeValue(response.getWriter(), list);
			}
		}
		
		//獲取單筆訂單
		if("getSpoVO".equals(action)) {
			String spo_idStr = request.getParameter("spo_id");
			if(spo_idStr!=null) {
				Integer spo_id = Integer.parseInt(spo_idStr);
				SpoService spoService = new SpoService();
				SpoVO spoVO = spoService.getOne(spo_id);
				ObjectMapper mapper = new ObjectMapper();
				mapper.writeValue(response.getWriter(), spoVO);
			}
		}
		
		//取消訂單
		if("cancelOrder".equals(action)) {
			String spo_idStr = request.getParameter("spo_id");
			if(spo_idStr != null) {
				Integer spo_id = Integer.parseInt(spo_idStr);
				SpoService spoService = new SpoService();
				spoService.cancelOrder(spo_id);
				response.getWriter().print(true);
			}
		}
		
		//支付訂單款項
		if("checkout".equals(action)) {
			String spo_idStr = request.getParameter("spo_id");
			String paytypeStr = request.getParameter("paytype");
			if(spo_idStr != null && paytypeStr != null) {
				Integer spo_id = Integer.parseInt(spo_idStr);
				Integer paytype = Integer.parseInt(paytypeStr);
				SpoService spoService = new SpoService();
				spoService.update_spo_paytype(spo_id, paytype);//更新付款方式
				SpoVO spoVO = spoService.getOne(spo_id);//取得訂單VO
				Integer payment = spoVO.getSpo_payment();//取得付款金額
				request.setAttribute("payment", payment);//將付款金額set在request scope
				List<Integer> spo_idList = new ArrayList<Integer>();//創建訂單ID的list 後續結帳servlet用
				spo_idList.add(spo_id);//將訂單ID加到list
				HttpSession session = request.getSession();
				session.setAttribute("spo_idList",spo_idList);//將訂單ID List設定在session scope
				if(paytype.equals(0)) {
					request.getRequestDispatcher("/front_end/product/CreditCard.jsp").forward(request, response);
				}else if(paytype.equals(1)){
					request.getRequestDispatcher("/front_end/product/WebATM.jsp").forward(request, response);
				}
			}
		}
		
		
		//確定收貨
		if("Receipt".equals(action)) {
			String spo_idStr = request.getParameter("spo_id");
			if(spo_idStr != null) {
				Integer spo_id = Integer.parseInt(spo_idStr);
				SpoService spoService = new SpoService();
				spoService.update_spo_cargo_status(spo_id, 2);
				spoService.update_spo_status(spo_id, 2);
				response.getWriter().print(true);
			}
		}
		
		//申請退貨
		if("Return".equals(action)) {
			String spo_idStr = request.getParameter("spo_id");
			if(spo_idStr != null) {
				Integer spo_id = Integer.parseInt(spo_idStr);
				SpoService spoService = new SpoService();
				spoService.update_spo_cargo_status(spo_id, 3);
				spoService.update_spo_pay_status(spo_id, 4);
				spoService.update_spo_status(spo_id, 3);
				response.getWriter().print(true);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
