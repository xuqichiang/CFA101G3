package com.member.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.model.MemService;
import com.member.model.MemVO;


@WebServlet("/member/memImgServlet")
public class MemImgServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		
		//取得大頭照
		if("headShot".equals(action)) {
			String mem_idStr = request.getParameter("mem_id");
			if(mem_idStr != null) {
				Integer mem_id = Integer.parseInt(mem_idStr);
				MemService memService = new MemService();
				MemVO memVO = memService.getOne(mem_id);
				byte[] headshot = memVO.getMem_headshot();
				if(headshot == null) {
					String filePath = getServletContext().getRealPath("/front_end/index/images/headshot.png");
					File file = new File(filePath);
					InputStream in = new FileInputStream(file);
					byte[] buf = new byte[in.available()];
					in.read(buf);
					in.close();
					ServletOutputStream out = response.getOutputStream();
					out.write(buf);
					out.close();
				}else {
					ServletOutputStream out = response.getOutputStream();
					out.write(headshot);
					out.close();
				}
			}
		}
		
		//取得會員logo圖片
		if("logoBlob".equals(action)) {
			HttpSession session = request.getSession();
			MemVO memVO = (MemVO)session.getAttribute("user");
			if(memVO != null) {
				byte[] shop_logo = memVO.getMem_shop_logo();
				if(shop_logo == null) {
					String filePath = getServletContext().getRealPath("/front_end/member/images/logo.jpg");
					File file = new File(filePath);
					InputStream in = new FileInputStream(file);
					byte[] buf = new byte[in.available()];
					in.read(buf);
					in.close();
					ServletOutputStream out = response.getOutputStream();
					out.write(buf);
					out.close();
				}else {
					ServletOutputStream out = response.getOutputStream();
					out.write(shop_logo);
					out.close();
				}
			}
		}
		
		//取得會員banner圖片
		if("bannerBlob".equals(action)) {
			HttpSession session = request.getSession();
			MemVO memVO = (MemVO)session.getAttribute("user");
			if(memVO != null) {
				byte[] shop_banner = memVO.getMem_shop_banner();
				if(shop_banner == null) {
					String filePath = getServletContext().getRealPath("/front_end/member/images/banner.jpg");
					File file = new File(filePath);
					InputStream in = new FileInputStream(file);
					byte[] buf = new byte[in.available()];
					in.read(buf);
					in.close();
					ServletOutputStream out = response.getOutputStream();
					out.write(buf);
					out.close();
				}else {
					ServletOutputStream out = response.getOutputStream();
					out.write(shop_banner);
					out.close();
				}
			}
		}
		
		//讀取賣家banner
		if("getBanner".equals(action)) {
			String mem_idStr = request.getParameter("mem_id");
			if(mem_idStr != null) {
				Integer mem_id = Integer.parseInt(mem_idStr);
				MemService memService = new MemService();
				MemVO memVO = memService.getOne(mem_id);
				if(memVO != null) {
					byte[] shop_banner = memVO.getMem_shop_banner();
					if(shop_banner == null) {
						String filePath = getServletContext().getRealPath("/front_end/member/images/banner.jpg");
						File file = new File(filePath);
						InputStream in = new FileInputStream(file);
						byte[] buf = new byte[in.available()];
						in.read(buf);
						in.close();
						ServletOutputStream out = response.getOutputStream();
						out.write(buf);
						out.close();
					}else {
						ServletOutputStream out = response.getOutputStream();
						out.write(shop_banner);
						out.close();
					}
				}
			}
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
