package com.admin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.admin.model.AdmService;
import com.admin.model.AdmVO;
import com.member.model.MemService;
import com.member.model.MemVO;

@WebServlet("/admin/adminServlet")
public class AdminServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		response.setHeader("Cache-Control","no-store");
		response.setHeader("Pragma","no-cache");
		response.setDateHeader("Expires",0); 
		
		String action = request.getParameter("action");
		//管理員登入驗證
		if("login".equals(action)) {
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			request.setAttribute("errorMsgs", errorMsgs);
			try {
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				String usernameReg = "^\\w{1,63}@[a-zA-Z0-9]{2,63}\\.[a-zA-Z]{2,63}(\\.[a-zA-Z]{2,63})?$";
				String passwordReg = "^[0-9A-Za-z]{6,20}$";
				if(username == null || username.trim().isEmpty()) {
					errorMsgs.put("username", "帳號請勿空白");
				}else if(!username.trim().matches(usernameReg)) {
					errorMsgs.put("username", "請輸入有效的電子郵件地址");
				}
				if(password == null || password.trim().isEmpty()) {
					errorMsgs.put("password", "密碼請勿空白");
				}else if(!password.trim().matches(passwordReg)) {
					errorMsgs.put("password", "密碼長度限制6-20");
				}
				if(!errorMsgs.isEmpty()) {
					request.getRequestDispatcher("/back_end/admin/login.jsp").forward(request, response);
					return;
				}
				
				AdmService service = new AdmService();
				MemService memService = new MemService();
				AdmVO admin = service.findByUsernameAndPassword(username, password);
				MemVO user = memService.login(username, password);
				if(admin != null) {
					HttpSession session = request.getSession();
					session.setAttribute("admin", admin);
					if(user != null) {
						session.setAttribute("user", user);
					}
					response.sendRedirect(request.getContextPath()+"/member/memberServlet?action=buyMember");
				}else {
					errorMsgs.put("username", "帳號或密碼錯誤");
					request.getRequestDispatcher("/back_end/admin/login.jsp").forward(request, response);
				}
			} catch (Exception e) {
				errorMsgs.put("Exception", e.getMessage());
				request.getRequestDispatcher("/back_end/admin/login.jsp").forward(request, response);
			}
		}
		
		//確認是否為管理員登入
		if("check".equals(action)) {
			HttpSession session = request.getSession();
			AdmVO admin = (AdmVO)session.getAttribute("admin");
			MemVO user = (MemVO)session.getAttribute("user");
			if(admin!=null && user != null  && user.getMem_username().equals(admin.getAdm_username())) {
				response.getWriter().print(true);
			}else {
				response.getWriter().print(false);
			}
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
