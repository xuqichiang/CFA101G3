package com.product_images.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.product.model.ProService;
import com.product_images.model.ProImgDAO;
import com.product_images.model.ProImgDAOimpl;
import com.product_images.model.ProImgService;
import com.product_images.model.ProImgVO;


@WebServlet("/product/ProImgOutServlet3")
public class ProImgOutServlet3 extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
			Integer pro_id = Integer.parseInt(request.getParameter("pro_id"));
			ProImgDAO dao = new ProImgDAOimpl();
			List<ProImgVO> list = dao.findByFKlist(pro_id);
			if(list.size() != 0) {
				ProImgVO proImgVO = list.get(0);
				if(proImgVO != null) {
					byte[] proi_images = proImgVO.getProi_images();
					response.getOutputStream().write(proi_images);
				}
			}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
