package com.photoprogramimages.controller;

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

import com.photoprogramimages.model.PhopiService;
import com.photoprogramimages.model.PhopiVO;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
@WebServlet("/photoprogramimages/phopiUpdateServlet")
public class PhopiUpdateServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		Integer phopi_phop_id = new Integer(request.getParameter("phop_id"));
//		System.out.println(phopi_phop_id);
		
		// 新增方案圖片
		Collection<Part> parts = request.getParts();
		for (Part partImg : parts) {
			if ("file_img".equals(partImg.getName())) {
				InputStream in = partImg.getInputStream();
				byte[] buf = new byte[in.available()];
				in.read(buf);
				if (buf.length != 0) {
					PhopiVO phopiVO = new PhopiVO();
					PhopiService phopiSvc = new PhopiService();
					phopiVO.setPhopi_phop_id(phopi_phop_id);
					phopiVO.setPhopi_images(buf);
					phopiSvc.addImg(phopiVO);

					in.close();
				}

			}
		}
		try {
			response.getWriter().print("1");
		} catch (IOException e) {
			response.getWriter().print("0");
			e.printStackTrace();
		}
	}

}
