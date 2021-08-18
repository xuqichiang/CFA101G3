package com.locationprogram.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.locationprogram.model.LocpService;
import com.locationprogram.model.LocpVO;
import com.locationprogramimages.model.LocpiService;
import com.locationprogramimages.model.LocpiVO;
import com.member.model.MemVO;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
@WebServlet("/locationprogram/insertLocpServlet")
public class InsertLocpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date starttime = null;
		java.util.Date endtime = null;
		HttpSession session = request.getSession();
		MemVO memVo = new MemVO();
		LocpService locpsvc = new LocpService();
		LocpVO locpVO = new LocpVO();
		LocpiService locpisvc = new LocpiService();
		LocpiVO locpiVO = new LocpiVO();
		byte[] buf = null;
		
		String locp_name = request.getParameter("locp_name");
		Integer locp_price = Integer.parseInt(request.getParameter("locp_price"));
		try {
			starttime = sdf.parse(request.getParameter("start_date"));
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date locp_start_time = new java.sql.Date(starttime.getTime());
		
		try {
			endtime = sdf.parse(request.getParameter("end_date"));
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date locp_end_time = new java.sql.Date(endtime.getTime());
		
		String locp_content = request.getParameter("locp_content");
		Integer locp_status = Integer.parseInt(request.getParameter("locp_status").trim());
		
		locpVO.setLocp_name(locp_name);
		locpVO.setLocp_price(locp_price);
		locpVO.setLocp_start_time(locp_start_time);
		locpVO.setLocp_end_time(locp_end_time);
		locpVO.setLocp_content(locp_content);
		memVo = (MemVO)session.getAttribute("user");
		Integer locp_smem_id = memVo.getMem_id();
		locpVO.setLocp_smem_id(locp_smem_id);
		locpVO.setLocp_status(locp_status);
		
		locpsvc.addLocp(locpVO);  
		
		LocpVO locpID = locpsvc.getLastID();  //抓取上方新增的方案id,搭配sql語句
		Part part = request.getPart("file1");
		InputStream in = part.getInputStream();
		
			if(in.available() != 0) {
				
				buf = new byte[in.available()];
				in.read(buf);
				in.close();
				
				locpiVO.setLocpi_locp_id(locpID.getLocp_id());
				locpiVO.setLocpi_images(buf);
				locpisvc.insertLocpiImages(locpiVO);
			}
			
		
		
		try {
			response.getWriter().print("1");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print("0");
		}
		
		
		
	}

}
