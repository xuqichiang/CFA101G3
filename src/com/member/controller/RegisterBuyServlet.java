package com.member.controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.model.MemService;
import com.member.model.MemVO;


@WebServlet("/member/registerBuyServlet")
public class RegisterBuyServlet extends HttpServlet {
    
	//註冊買家帳號
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		MemService service = new MemService();
		MemVO member = new MemVO();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		String usernameReg = "^\\w{1,63}@[a-zA-Z0-9]{2,63}\\.[a-zA-Z]{2,63}(\\.[a-zA-Z]{2,63})?$";
		String passwordReg = "^[0-9A-Za-z]{6,20}$";
		String phoneReg = "^09[0-9]{8}$";
		if(username.trim().isEmpty()||password.trim().isEmpty()||name.trim().isEmpty()||phone.trim().isEmpty()||!username.matches(usernameReg)||!password.matches(passwordReg)||!phone.matches(phoneReg)) {
			response.getWriter().print("0");
			return;
		}
		member.setMem_username(username);
		member.setMem_password(password);
		member.setMem_name(name);
		member.setMem_role(10);
		member.setMem_phone(phone);
		int count = service.register(member);
		if(count == 1) {
			SendEmail se = new SendEmail(username);
			se.start();
			se = null;
			response.getWriter().print("1");
		}else {
			response.getWriter().print("0");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
