package com.workphoto.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.weddingphoto.model.WedService;
import com.weddingphoto.model.WedVO;
import com.workphoto.model.WorService;
import com.workphoto.model.WorVO;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
@WebServlet("/workphoto/worUpdataServlet")
public class WorUpdataServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		Integer wor_id = new Integer(request.getParameter("wor_id"));
		
		//修改作品集封面
		byte[] buf = null;
		Part part = request.getPart("file_logo");
		
		InputStream in = part.getInputStream();
		if(in.available()!=0) {
			
			buf = new byte[(in.available())];
			in.read(buf);
			in.close();
			
			WorVO worVO = new WorVO();
			WorService worSvc = new WorService();
			worVO.setWor_logo(buf);
			worVO.setWor_id(wor_id);
			worSvc.updataLogo(worVO);
		}
		
		//作品集新增圖片
		Collection<Part> parts = request.getParts();
		for (Part partImg : parts) {

			if ("file_img".equals(partImg.getName())) {//抓到part的name
				InputStream inImg = partImg.getInputStream();//寫入
				int available = inImg.available();
				byte[] bufImg = new byte[available];
				inImg.read(bufImg);// 讀
				
				if(bufImg.length!=0) {
					
					WedVO wedVO = new WedVO();//new作品集圖片VO
					WedService wedSvc = new WedService();
					wedVO.setWed_wor_id(wor_id);
					wedVO.setWed_images(bufImg);//多張圖片
					wedSvc.addWed(wedVO);
					
					inImg.close();
				}
			}
		}
		try {
			response.getWriter().print("1");
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print("0");
		}
	}

}
