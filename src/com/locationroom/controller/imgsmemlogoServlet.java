package com.locationroom.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import com.locationroom.model.LocrService;
import com.member.model.MemVO;

/**
 * Servlet implementation class LociServlet
 */
@WebServlet("/locationroom/imgsmemlogoServlet")
public class imgsmemlogoServlet extends HttpServlet {

    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");

	

			try {
				Integer SMEM_ID = Integer.parseInt(request.getParameter("LOCI_SMEM_ID"));					
				
				LocrService locrService2 = new LocrService();
				
				MemVO smemVO = locrService2.getSmem(SMEM_ID);
				

//				ObjectMapper mapper2 = new ObjectMapper(); 
//				String data2 = mapper2.writeValueAsString(smemVO);		
//				PrintWriter out2 = response.getWriter();	
//				out2.print(data2);
								
				if(smemVO != null) {
					byte[] Smem_shop_logo = smemVO.getMem_shop_logo();
					response.getOutputStream().write(Smem_shop_logo);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
	
	
	
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
