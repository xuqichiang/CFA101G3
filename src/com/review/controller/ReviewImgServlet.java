package com.review.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.model.MemService;
import com.member.model.MemVO;


@WebServlet("/review/reviewImgServlet")
public class ReviewImgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
  
    public ReviewImgServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		MemService memService = new MemService();
		String action = request.getParameter("action");
		//獲取買家大頭照
		if ("getBmemHeadShot".equals(action)) {
			String mem_id = request.getParameter("mem_id");
			MemVO one = memService.getOne(Integer.parseInt(mem_id));
			if (one != null) {
				byte[] mem_headshot = one.getMem_headshot();
				response.getOutputStream().write(mem_headshot);
			}
		}
       //獲取商家logo
		if ("getShopLogo".equals(action)) {
			String mem_id = request.getParameter("mem_id");
			MemVO one = memService.getOne(Integer.parseInt(mem_id));
			if (one != null) {
				byte[] mem_shop_logo = one.getMem_shop_logo();
				response.getOutputStream().write(mem_shop_logo);
			}
		}
	
	
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
