package com.product.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.model.ProDAO;
import com.product.model.ProDAOimpl;
import com.product.model.ProService;
import com.product.model.ProVO;

@WebServlet("/product/ProFindBySqlServlet")
public class ProFindBySqlServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		try {
			String pro_name=request.getParameter("pro_name");
			ProService service = new ProService();
			List<ProVO> list = service.findBySQLList(pro_name);
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(list);
			response.getWriter().print(json);
//			System.out.println(json);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
