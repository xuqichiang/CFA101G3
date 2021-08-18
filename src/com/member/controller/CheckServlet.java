package com.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.model.*;


@WebServlet("/member/checkServlet")
public class CheckServlet extends HttpServlet {
       
	//驗證是否為登入狀態
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();
		MemVO user = (MemVO)session.getAttribute("user");
		if(user!= null) {
			Integer mem_role = user.getMem_role();
			if(mem_role.equals(10)) {
				response.sendRedirect(request.getContextPath()+"/front_end/member/memberBuyProfile.html?action=profile");
			}else if(mem_role.equals(20)) {
				response.sendRedirect(request.getContextPath()+"/front_end/product/ProSellerProfile.html?action=profile");
			}else if(mem_role.equals(30)) {
				response.sendRedirect(request.getContextPath()+"/front_end/member/LocSellerProfile.html?action=profile");
			}else if(mem_role.equals(40)) {
				response.sendRedirect(request.getContextPath()+"/front_end/photographer/wedSellerProfile.html?action=profile");
			}else if(mem_role.equals(50)) {
				response.sendRedirect(request.getContextPath()+"/front_end/member/memberBuyProfile.html?action=profile");
			}
		}else {
			response.sendRedirect(request.getContextPath()+"/front_end/member/login.html");
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
