package com.locationprogramimages.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.locationprogramimages.model.LocpiService;
import com.locationprogramimages.model.LocpiVO;

@WebServlet("/locationprogramimages/getImageFKServlet")
public class GetImageFKServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		Integer locpi_locp_id = Integer.parseInt(request.getParameter("locpid"));
		LocpiService locpisvc = new LocpiService();
		List<LocpiVO> list = locpisvc.findForeignKey(locpi_locp_id);
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(list);
		response.getWriter().print(json);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
