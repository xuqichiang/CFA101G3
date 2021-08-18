package com.member.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.model.MemService;
import com.member.model.MemVO;


@WebServlet("/member/registerSellerServlet")
public class RegisterSellerServlet extends HttpServlet {
    
	//註冊賣家帳號
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		MemService service = new MemService();
		MemVO member = new MemVO();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String phone = request.getParameter("phone");
		Integer role = Integer.parseInt(request.getParameter("role"));
		String shopname = request.getParameter("shopname");
		String city = request.getParameter("city");
		String cityarea = request.getParameter("cityarea");
		String street = request.getParameter("street");
		String usernameReg = "^\\w{1,63}@[a-zA-Z0-9]{2,63}\\.[a-zA-Z]{2,63}(\\.[a-zA-Z]{2,63})?$";
		String passwordReg = "^[0-9A-Za-z]{6,20}$";
		String phoneReg = "^09[0-9]{8}$";
		if(username.trim().isEmpty()||password.trim().isEmpty()||name.trim().isEmpty()||phone.trim().isEmpty()||shopname.trim().isEmpty()||city.trim().isEmpty()||cityarea.trim().isEmpty()||street.trim().isEmpty()||!username.matches(usernameReg)||!password.matches(passwordReg)||!phone.matches(phoneReg)) {
			response.getWriter().print("0");
			return;
		}
		
		if(!(role == 20 || role == 30 || role == 40)) {
			response.getWriter().print("0");
			return;
		}		
		member.setMem_username(username);
		member.setMem_password(password);
		member.setMem_name(name);
		member.setMem_role(role);
		member.setMem_phone(phone);
		member.setMem_shop_name(shopname);
		member.setMem_city(city);
		member.setMem_cityarea(cityarea);
		member.setMem_street(street);
		
		String logoPath = getServletContext().getRealPath("/front_end/member/images/logo.jpg");
		File logofile = new File(logoPath);
		InputStream logoIn = new FileInputStream(logofile);
		byte[] logo = new byte[logoIn.available()];
		logoIn.read(logo);
		logoIn.close();
		
		String bannerPath = getServletContext().getRealPath("/front_end/member/images/banner.jpg");
		File bannerfile = new File(bannerPath);
		InputStream bannerfileIn = new FileInputStream(bannerfile);
		byte[] banner = new byte[bannerfileIn.available()];
		bannerfileIn.read(banner);
		bannerfileIn.close();

		member.setMem_shop_logo(logo);//新增預設logo圖片
		member.setMem_shop_banner(banner);//新增預設banner圖片
		
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
