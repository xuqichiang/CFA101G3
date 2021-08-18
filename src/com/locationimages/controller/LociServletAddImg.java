package com.locationimages.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.locationimages.model.*;
import com.locationroom.model.*;
/**
 * Servlet implementation class LociServlet
 */
// 重點 一定要
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
@WebServlet("/locationimages/LociServletAddImg")
public class LociServletAddImg extends HttpServlet {
  
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		List<String> errorMsgs = new LinkedList<String>();
		ObjectMapper mapper = new ObjectMapper(); 
			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				Integer LOCI_LOCR_ID =Integer.parseInt(request.getParameter("LOCR_ID"));
				System.out.println(LOCI_LOCR_ID);
				Part part = request.getPart("file1");
				InputStream in = part.getInputStream();
				LociService locisvc = new LociService();
				LociVO lociVO = new LociVO();
				byte[] buf = null;
				buf = new byte[in.available()];
				in.read(buf);
				in.close();

				if (!errorMsgs.isEmpty()) {
					response.setStatus(401);
					String Msg = mapper.writeValueAsString(errorMsgs);
					response.getWriter().print(Msg);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				lociVO.setLOCI_LOCR_ID(LOCI_LOCR_ID);
				lociVO.setLOCI_IMAGES(buf);
				locisvc.addLoci(LOCI_LOCR_ID, buf);
				
				Map map = new HashMap();
				map.put("msg","success" );
				String msg = mapper.writeValueAsString(map);
				response.getWriter().print(msg);
			/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				e.printStackTrace();
				response.setStatus(401);
				String Msg = mapper.writeValueAsString(errorMsgs);
				response.getWriter().print(Msg);
			}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}


}
