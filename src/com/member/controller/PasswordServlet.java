package com.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.model.MemService;
import com.member.model.MemVO;

@WebServlet("/member/passwordServlet")
public class PasswordServlet extends HttpServlet {
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String action = request.getParameter("action");
		//忘記密碼
		if("forget".equals(action)) {
			String email = request.getParameter("email");
			MemService service = new MemService();
			MemVO user = service.findByUsername(email);
			if(user != null) {
				SendEmailPassword sep = new SendEmailPassword(email,user.getMem_password());
				sep.start();
				sep = null;
			}
		}
		//修改密碼
		if("update".equals(action)) {
			HttpSession session = request.getSession();
			MemVO user = (MemVO)session.getAttribute("user");
			if(user != null) {
				try {
					String newPassword = request.getParameter("newPassword");
					String username = user.getMem_username();
					MemService service = new MemService();
					service.updatePassword(username, newPassword);
					response.getWriter().print("success");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
