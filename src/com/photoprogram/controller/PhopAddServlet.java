package com.photoprogram.controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.member.model.MemVO;
import com.photoprogram.model.PhopService;
import com.photoprogram.model.PhopVO;
import com.photoprogramimages.model.PhopiVO;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
@WebServlet("/photoprogram/phopAddServlet")
public class PhopAddServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//設置頭
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		//取得會員ID
		HttpSession session = request.getSession();
		MemVO memVO = new MemVO();
		memVO = (MemVO) session.getAttribute("user");
		Integer phop_smem_id = memVO.getMem_id();
//		System.out.println(phop_smem_id);

		//時間
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date startTime = null;
		java.util.Date endTime = null;
		
		//取得前端的參數(ajax)
		String phop_name = request.getParameter("phop_name").trim();//方案名
		Integer phop_price = new Integer(request.getParameter("phop_price").trim());//價錢
		Integer phop_phoc_id = new Integer(request.getParameter("phop_phoc_id"));//方案類別
		try {
			startTime = sdf.parse(request.getParameter("phop_start_time"));//開始時間
			endTime = sdf.parse(request.getParameter("phop_end_time"));//結束時間
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Date phop_start_time = new java.sql.Date(startTime.getTime());
		Date phop_end_time = new java.sql.Date(endTime.getTime());
		String phop_content = request.getParameter("phop_content").trim();//方案內容
		Integer phop_status = new Integer(request.getParameter("phop_status"));//狀態
		
		PhopVO phopVO = new PhopVO();//方案VO
		phopVO.setPhop_name(phop_name);
		phopVO.setPhop_price(phop_price);
		phopVO.setPhop_phoc_id(phop_phoc_id);
		phopVO.setPhop_start_time(phop_start_time);
		phopVO.setPhop_end_time(phop_end_time);
		phopVO.setPhop_content(phop_content);
		phopVO.setPhop_status(phop_status);
		phopVO.setPhop_smem_id(phop_smem_id);
		
		//調用Service與方法
		PhopService phopSvc = new PhopService();
//		phopSvc.insert(phopVO);
//		System.out.println(phopVO);
		
		//取得方案ID，新增方案圖片
		ArrayList<PhopiVO> arrayList = new ArrayList<PhopiVO>();//new新的陣列存放圖片
		Collection<Part> parts = request.getParts();
		for(Part partImg :parts) {
			if("file_img".equals(partImg.getName())) {//取part的name
				InputStream in = partImg.getInputStream();//寫入
				int available = in.available();
				byte[] buf = new byte[available];
				in.read(buf);//讀
				
				if(buf.length!=0) {
					PhopiVO phopiVO = new PhopiVO();//new方案圖片VO
					phopiVO.setPhopi_images(buf);//圖片
					arrayList.add(phopiVO);//新增圖片存放到List
					in.close();
				}else {
					response.setStatus(401);//壓力測試，擋下空值
					return;
				}
			}
		}
		//呼叫方法
		phopSvc.addPhopImg(phopVO, arrayList);
		try {
			response.getWriter().print("1");//t
		} catch (IOException e) {
			e.printStackTrace();
			response.getWriter().print("0");//f
		}
	}

}
