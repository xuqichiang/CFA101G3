package com.photoorder.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.photoorder.model.PhooService;
import com.photoorder.model.PhooVO;
//婚紗攝影預約表單新增
@WebServlet("/photoorder/phooAddFormServlet")
public class PhooAddFormServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//設置頭
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		//時間
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date reserveTime = null;
		
		//取得前端參數
		Integer phoo_bmem_id = new Integer(request.getParameter("phoo_bmem_id"));//買家ID
		Integer phoo_smem_id = new Integer(request.getParameter("phoo_smem_id"));//賣家ID
		Integer phoo_phop_id = new Integer(request.getParameter("phoo_phop_id"));//方案ID
		Integer phoo_phog_id = new Integer(request.getParameter("phoo_phog_id"));//攝影師ID
		Integer phoo_totalprice = new Integer(request.getParameter("phoo_totalprice"));//總金額
		Integer phoo_deposit = new Integer(request.getParameter("phoo_deposit"));//訂金
		try {
			reserveTime = sdf.parse(request.getParameter("phoo_reserve_time"));//拍攝日期
			System.out.println(reserveTime);
			
			PhooService phooSvc = new PhooService();
			List<PhooVO> time = phooSvc.getTime(phoo_phog_id);
			for(PhooVO phooVO : time) {
				//判斷選擇的攝影師時間是已被預約
				if(phooVO.getPhoo_reserve_time().getTime()==reserveTime.getTime()) {
					response.getWriter().print("0");
					return;
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date phoo_reserve_time = new java.sql.Date(reserveTime.getTime());
		Timestamp phoo_order_time = new java.sql.Timestamp(new java.util.Date().getTime());//訂單成立時間
		String phoo_place = request.getParameter("phoo_place");//地點
		Integer phoo_paytype = new Integer(request.getParameter("phoo_paytype"));//付款方式
		String phoo_note = request.getParameter("phoo_note");//備註
		Integer phoo_order_status = 0;//訂單狀態 預約中
		Integer phoo_pay_status = 0;//付款狀態 未付款
		
		PhooVO phooVO = new PhooVO();
		phooVO.setPhoo_bmem_id(phoo_bmem_id);
		phooVO.setPhoo_smem_id(phoo_smem_id);
		phooVO.setPhoo_phop_id(phoo_phop_id);
		phooVO.setPhoo_phog_id(phoo_phog_id);
		phooVO.setPhoo_totalprice(phoo_totalprice);
		phooVO.setPhoo_deposit(phoo_deposit);
		phooVO.setPhoo_reserve_time(phoo_reserve_time);
		phooVO.setPhoo_order_time(phoo_order_time);
		phooVO.setPhoo_place(phoo_place);
		phooVO.setPhoo_paytype(phoo_paytype);
		phooVO.setPhoo_note(phoo_note);
		phooVO.setPhoo_order_status(phoo_order_status);
		phooVO.setPhoo_pay_status(phoo_pay_status);
		
		//調用方法
		PhooService phooSvc = new PhooService();
		phooSvc.addPhoo(phooVO);
		
		//輸出
		PrintWriter out = response.getWriter();
		ObjectMapper mapper = new ObjectMapper();//JSON格式
		Map map = new HashMap();
		map.put("msg", "預約成功");
		String json = mapper.writeValueAsString(map);
		out.println(json);
	}
}
