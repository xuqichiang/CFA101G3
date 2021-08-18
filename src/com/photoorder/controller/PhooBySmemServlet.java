package com.photoorder.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
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

//賣家會員預約訂單
@WebServlet("/photoorder/phooBySmemServlet")
public class PhooBySmemServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 設置頭
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String action = request.getParameter("action");
		PhooService phooSvc = new PhooService();
		PrintWriter out = response.getWriter();//輸出
		
		//全部訂單
		if ("getSmemId".equals(action)) {
			// 獲取會員ID
			HttpSession session = request.getSession();
			MemVO memVO = (MemVO) session.getAttribute("user");
			Integer smem_id = memVO.getMem_id();
//			System.out.println(smem_id);

			List list = phooSvc.getSmemReserveForm(smem_id);
//			System.out.println(list);

			// 輸出
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(list);
			out.print(json);
		}
		//單筆訂單
		if ("getOneSmem".equals(action)) {
			try {
				// 取得參數
				Integer phoo_id = new Integer(request.getParameter("phoo_id"));
//				System.out.println(phoo_id);
				
				// 調用方法
				Map map = phooSvc.getSmemOneForm(phoo_id);
				
				// 輸出
				ObjectMapper mapper = new ObjectMapper();
				String json = mapper.writeValueAsString(map);
				out.print(json);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//婚攝賣家確認預約
		if("confirm".equals(action)){
			Integer phoo_id = new Integer (request.getParameter("phoo_id"));
			Integer phoo_order_status = 1;//狀態確認預約
			phooSvc.updateOrder(phoo_order_status, phoo_id);
			out.print("1");//成功
		}
		//婚攝賣家婉拒預約
		if("cancel".equals(action)){
			Integer phoo_id = new Integer (request.getParameter("phoo_id"));
			Integer phoo_order_status = 2;//狀態婉拒預約
			phooSvc.updateOrder(phoo_order_status, phoo_id);
			out.print("1");//成功
		}
		//婚攝賣家完成預約買家已付尾款
		if("finish".equals(action)) {
			Integer phoo_id = new Integer (request.getParameter("phoo_id"));
			Integer phoo_order_status = 3;//狀態訂單已完成
			phooSvc.updateOrder(phoo_order_status, phoo_id);
			out.print("1");//成功
		}
		//婚攝店家已被預約時間
		if("getTime".equals(action)) {
			Integer phoo_phog_id = new Integer(request.getParameter("phoo_phog_id"));
			List<PhooVO> reserveTime = phooSvc.getTime(phoo_phog_id);//取得預約時間
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");//轉換格式(SQL顯示是"-" datetimepicker "/")
			
			List<String> list = new ArrayList();
			for(PhooVO phooVO : reserveTime) {
				Date phoo_reserve_time = phooVO.getPhoo_reserve_time();
				String format = sdf.format(phoo_reserve_time);
				list.add(format);
			}
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(list);
			out.print(json);
		}
	}

}
