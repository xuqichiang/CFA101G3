package com.post.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.member.model.MemService;
import com.member.model.MemVO;

@WebServlet("/post/postImgServlet")
public class PostImgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PostImgServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		MemService memService = new MemService();
		String action = request.getParameter("action");
		if ("getMemHeadShot".equals(action)) {
			String mem_id = request.getParameter("mem_id");
			MemVO one = memService.getOne(Integer.parseInt(mem_id));
			if (one != null) {
				byte[] mem_headshot = one.getMem_headshot();
				response.getOutputStream().write(mem_headshot);
			}
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
