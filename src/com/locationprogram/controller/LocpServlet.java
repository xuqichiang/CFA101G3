package com.locationprogram.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.locationprogram.model.LocpService;
import com.locationprogram.model.LocpVO;
import com.locationprogramimages.model.LocpiService;
import com.locationprogramimages.model.LocpiVO;


@WebServlet("/locationprogram/locpServlet")
public class LocpServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		
		//取得所有方案狀態為上架，方案時間在期限內
		if("getAll_by_status_endtime".equals(action)) {
			response.setContentType("text/html;charset=utf-8");
			LocpService locpService = new LocpService();
			Set<LocpVO> set = locpService.getAll_by_status_endtime();
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(response.getWriter(), set);
		}
		
		//讀取一張方案圖片當封面
		if("getLocpImg".equals(action)) {
			Integer locp_id = Integer.parseInt(request.getParameter("locp_id"));
			LocpiService locpisvc = new LocpiService();
			LocpiVO locpiVO = locpisvc.findByForeignKey(locp_id);
			byte[] buf = locpiVO.getLocpi_images();
			if(buf!=null) {
				ServletOutputStream out = response.getOutputStream();
				out.write(buf);
				out.close();
			}else {
				String filePath = getServletContext().getRealPath("/front_end/locationprogram/images/Location_Banner1.jpg");
				File file = new File(filePath);
				InputStream in = new FileInputStream(file);
				byte[] bs = new byte[in.available()];
				in.read(bs);
				in.close();
				ServletOutputStream out = response.getOutputStream();
				out.write(bs);
				out.close();
			}
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
