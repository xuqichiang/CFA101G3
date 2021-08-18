package com.product_images.controller;

import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.Out;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.product_images.model.ProImgDAO;
import com.product_images.model.ProImgDAOimpl;
import com.product_images.model.ProImgService;
import com.product_images.model.ProImgVO;

@WebServlet("/product/ProImgSubServlet")
public class ProImgSubServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String action = request.getParameter("action");
		if("getall".equals(action)) {
			Integer pro_proc_id= Integer.parseInt(request.getParameter("pro_proc_id"));
			ProImgService imgService = new ProImgService();
			List<ProImgVO> list = imgService.findByCateList(pro_proc_id);
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(list);
			response.getWriter().print(json);
		}
		if("cheap".equals(action)) {
			Integer pro_proc_id= Integer.parseInt(request.getParameter("pro_proc_id"));
			ProImgService imgService = new ProImgService();
			List<ProImgVO> list = imgService.findByCateCheap(pro_proc_id);
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(list);
			response.getWriter().print(json);
		}if("expensive".equals(action)) {
			Integer pro_proc_id= Integer.parseInt(request.getParameter("pro_proc_id"));
			ProImgService imgService = new ProImgService();
			List<ProImgVO> list = imgService.findByCateExp(pro_proc_id);
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(list);
			response.getWriter().print(json);
		}
		
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
