package com.weddingphoto.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.weddingphoto.model.WedService;
import com.weddingphoto.model.WedVO;


@WebServlet("/weddingphoto/wedPhotoServlet")
public class WedPhotoServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		Integer wed_id = null;
		
		try {
			wed_id = new Integer (request.getParameter("wed_id"));//取得照片ID
			WedService wedSvc = new WedService();
			WedVO wedVO = wedSvc.findByPrimaryKey(wed_id);//ID取出對應的VO
			if(wedVO!=null) {
				byte[] wed_images = wedVO.getWed_images();
				ServletOutputStream out = response.getOutputStream();//輸出
				out.write(wed_images);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
