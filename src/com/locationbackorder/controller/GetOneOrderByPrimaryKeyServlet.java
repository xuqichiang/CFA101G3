package com.locationbackorder.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.locationbackorder.model.LocoService;
import com.locationbackorder.model.LocoVO;
import com.locationprogram.model.LocpService;
import com.locationprogram.model.LocpVO;
import com.locationroom.model.LocrService;
import com.locationroom.model.LocrVO;
import com.member.model.MemVO;

@WebServlet("/locationorder/getOneOrderByPrimaryKeyServlet")
public class GetOneOrderByPrimaryKeyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		Map<String,Object> map = new HashMap<String, Object>();
		LocoVO locoVO = new LocoVO();
		MemVO smemVO = new MemVO();
		MemVO bmemVO = new MemVO();
		LocpVO locpVO = new LocpVO();
		LocrVO locrVO = new LocrVO();
		String loco_order_time = "";
		String loco_reserve_time = "";
		
		Integer loco_id = Integer.parseInt(request.getParameter("loco_id"));
		String action = request.getParameter("action");
		
		LocoService locosvc = new LocoService();
		locoVO = locosvc.getOneLocorderByPrimaryKey(loco_id);
		
		if(locoVO != null) {
			smemVO = locosvc.getMemVOByPrimaryKey(locoVO.getLoco_smem_id());
			bmemVO = locosvc.getMemVOByPrimaryKey(locoVO.getLoco_bmem_id());
			
			LocpService locpsvc = new LocpService();
			locpVO = locpsvc.getOneLocpByLocpid(locoVO.getLoco_locp_id());
			
			LocrService locrsvc = new LocrService();
			locrVO = locrsvc.getOneLocr(locoVO.getLoco_locr_id());
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			loco_order_time = sdf.format(locoVO.getLoco_order_time());
			loco_reserve_time = sdf.format(locoVO.getLoco_reserve_time());
			
			map.put("loco_id", locoVO.getLoco_id());
			map.put("loco_smem_id", smemVO.getMem_shop_name());
			map.put("loco_bmem_id", bmemVO.getMem_name());
			map.put("loco_locr_id", locrVO.getLOCR_NAME());
			map.put("loco_locp_id", locpVO.getLocp_name());
			map.put("loco_totalprice", locoVO.getLoco_totalprice());
			map.put("loco_deposit", locoVO.getLoco_deposit());
			map.put("loco_paytype", locoVO.getLoco_paytype());
			map.put("loco_order_status", locoVO.getLoco_order_status());
			map.put("loco_pay_status", locoVO.getLoco_pay_status());
			map.put("loco_order_time", loco_order_time);
			map.put("loco_reserve_time", loco_reserve_time);
			map.put("loco_table_num", locoVO.getLoco_table_num());
			map.put("loco_note", locoVO.getLoco_note());
		}
		
		if("getorderbyaddress".equals(action)) {
			
			ObjectMapper mapper = new ObjectMapper(); 
			String data = mapper.writeValueAsString(map);
			
			out.print(data);
		}
		
		if("getorderbyintput".equals(action)) {
			
			HttpSession session = request.getSession();
			if(map.size() != 0) {
				session.setAttribute("map", map);
				out.print("1");
			}else {
				out.print("0");
			}
		}
				
	}

}
