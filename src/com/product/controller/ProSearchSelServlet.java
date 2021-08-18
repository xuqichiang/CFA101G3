package com.product.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.model.ProDAOimpl;
import com.product.model.ProService;
import com.product.model.ProVO;


@WebServlet("/product/ProSearchSelServlet")
public class ProSearchSelServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		
		try {
		int pro_price = Integer.parseInt(request.getParameter("searchPriceH").trim());
		int pro_price2 = Integer.parseInt(request.getParameter("searchPriceL").trim());
		String pro_name = request.getParameter("searchName").trim();
		int pro_proc_id = Integer.parseInt(request.getParameter("searchSel"));
		int pro_smem_id = Integer.parseInt(request.getParameter("memid"));
		
		
		
			ProService service = new ProService();
			List<ProVO> list = service.findBySQLList3(pro_price, pro_price2, pro_proc_id, pro_smem_id, pro_name);
			ObjectMapper mapper = new ObjectMapper();
			String data = mapper.writeValueAsString(list);
			response.getWriter().print(data);
		} catch (Exception e) {
			response.getWriter().print("0");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
