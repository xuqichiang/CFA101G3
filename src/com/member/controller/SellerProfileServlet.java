package com.member.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.member.model.MemService;
import com.member.model.MemVO;

@MultipartConfig
@WebServlet("/member/sellerProfileServlet")
public class SellerProfileServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			request.setCharacterEncoding("UTF-8");
			String action = request.getParameter("action");
			
			//更新商店資料
			if("update".equals(action)) {
				String shopName = request.getParameter("shopName");
				String shopContentForm = request.getParameter("shopContentForm");
				Part logoBlob = request.getPart("logoBlob");
				Part bannerBlob = request.getPart("bannerBlob");
				HttpSession session = request.getSession();
				MemVO memVO = (MemVO)session.getAttribute("user");
				Integer mem_id = memVO.getMem_id();
				
				if(shopName != null && shopContentForm !=null && !shopName.trim().isEmpty() && !shopContentForm.trim().isEmpty() && mem_id != null && logoBlob.getSize() > 0 && bannerBlob.getSize() > 0) {
					InputStream logoIn = logoBlob.getInputStream();
					 byte[] logoBuf = new byte[logoIn.available()];
					 logoIn.read(logoBuf);
					 logoIn.close();
					 
					 InputStream bannerIn = bannerBlob.getInputStream();
					 byte[] bannerBuf = new byte[bannerIn.available()];
					 bannerIn.read(bannerBuf);
					 bannerIn.close();
					 
					MemService memService = new MemService();
					memService.updateSellerShop(shopName, shopContentForm, logoBuf, bannerBuf,mem_id);
					session.setAttribute("user", memService.getOne(mem_id));
					response.getWriter().print(true);
				}else {
					response.getWriter().print(false);
				}
			}
			
			//取得商店資料
			if("getSellerShop".equals(action)) {
				response.setContentType("text/html;charset=utf-8");
				HttpSession session = request.getSession();
				MemVO user = (MemVO)session.getAttribute("user");
				Integer mem_id = user.getMem_id();
				String mem_shop_name = user.getMem_shop_name();
				String mem_shop_content = user.getMem_shop_content();
				MemVO memVO = new MemVO();
				memVO.setMem_id(mem_id);
				memVO.setMem_shop_name(mem_shop_name);
				memVO.setMem_shop_content(mem_shop_content);
				ObjectMapper mapper = new ObjectMapper();
				mapper.writeValue(response.getWriter(), memVO);
			}
			

		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(401);
			response.getWriter().print("error");
		} 		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
