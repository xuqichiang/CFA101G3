package com.photoprogram.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.photoprogram.model.PhopService;
import com.photoprogram.model.PhopVO;

@WebServlet("/photoprogram/phopGetImagesServlet")
public class PhopGetImagesServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 瀏覽攝影方案首頁(資料庫撈圖片&資料 無上傳圖片)
		response.setContentType("text/html;charset=UTF-8");
		PhopService phopService = new PhopService();

		List list = phopService.getimages();
//		System.out.println(list);
		// 取得方案資料
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(list); // 轉換方法：Object轉成JSNO格式
		PrintWriter out = response.getWriter();
		out.print(json);

	}
}
