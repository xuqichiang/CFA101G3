package com.workphoto.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
@WebServlet("/workphoto/worAddServlet")
public class WorAddServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//設置頭
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		//取得攝影師ID，作品集名稱
		String wor_name = request.getParameter("wor_name").trim();//作品集名稱
		Integer phog_id = new Integer(request.getParameter("phog_id"));//攝影師ID

		WorVO worVO = new WorVO();
		WorService worSvc = new WorService();

		//新增作品集封面
		byte[] buf = null;
		Part part = request.getPart("file_wor");//getPart()方法上傳單張
		InputStream in = part.getInputStream();

		//圖片匯入資料庫
		if (in.available() != 0) {
			buf = new byte[in.available()];
			in.read(buf);

			worVO.setWor_name(wor_name);//攝影師名字
			worVO.setWor_phog_id(phog_id);//攝影師ID
			worVO.setWor_logo(buf);//封面圖片

			in.close();
		} else {
			response.setStatus(401);//壓力測試，擋下空值
			return;
		}

		//取到作品集ID，新增作品集圖片
		List<WedVO> arrayList = new ArrayList<WedVO>();//new新的陣列存放多張圖片
		Collection<Part> parts = request.getParts();//getPart()方法上傳多張圖片
		for (Part partImg : parts) {

			if ("file_wed".equals(partImg.getName())) {//抓到part的name
				InputStream inImg = partImg.getInputStream();//寫入
				int available = inImg.available();
				byte[] bufImg = new byte[available];
				inImg.read(bufImg);// 讀
				
				if(bufImg.length!=0) {
					WedVO wedVO = new WedVO();//new作品集圖片VO
					wedVO.setWed_images(bufImg);//多張圖片
					arrayList.add(wedVO);//新增圖片存放到List
					inImg.close();
				}else {
					response.setStatus(401);//壓力測試，擋下空值
					return;
				}
			}
		}
		//呼叫方法，同時新增作品集及圖片
		worSvc.addWorImg(worVO, arrayList);

		try {
			response.getWriter().print("1");//t
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().print("0");//f
		}
	}
}
