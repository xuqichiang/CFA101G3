package com.locationroom.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.locationimages.model.LociService;
import com.locationimages.model.LociVO;
import com.locationroom.model.LocrService;
import com.locationroom.model.LocrVO;
import com.member.model.MemVO;


@WebServlet("/locationroom/locationRoomServlet")
public class LocationRoomServlet extends HttpServlet {
	
   	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		
		//獲取會員廳房資料
		if("locationRoomGetAll".equals(action)) {
			//從session獲取會員VO
			HttpSession session = request.getSession();
			MemVO memVO = (MemVO)session.getAttribute("user");
			//獲取會員ID
			Integer mem_id = memVO.getMem_id();
			//獲取所有廳房資料
			LocrService LocrSvc = new LocrService();
			List<LocrVO> allList = LocrSvc.getAll();
			//廳房fk與會員id作join並加入新的list
			List<LocrVO> list = new ArrayList();
			for(LocrVO locrVO : allList){
				if(locrVO.getLOCR_SMEM_ID().equals(mem_id)) {
					list.add(locrVO);
				}
			}
			//把join後的list存到request並forword回去jsp
			request.setAttribute("list", list);
			request.getRequestDispatcher("/front_end/locationroom/locationRoomGetAll.jsp").forward(request, response);
		}
		if("locationRoomDelete".equals(action)) {
			//從session獲取會員VO
			HttpSession session = request.getSession();
			MemVO memVO = (MemVO)session.getAttribute("user");
			//獲取會員ID
			Integer mem_id = memVO.getMem_id();
			//獲取所有廳房資料
			LocrService LocrSvc = new LocrService();
			List<LocrVO> allList = LocrSvc.getAll();
			//廳房fk與會員id作join並加入新的list
			List<LocrVO> list = new ArrayList();
			for(LocrVO locrVO : allList){
				if(locrVO.getLOCR_SMEM_ID().equals(mem_id)) {
					list.add(locrVO);
				}
			}
			//把join後的list存到request並forword回去jsp
			request.setAttribute("list", list);
			request.getRequestDispatcher("/front_end/locationroom/locationRoomDelete.jsp").forward(request, response);
		}
		//編輯廳房資料
		if("locationRoomUpdate".equals(action)) {
			String Strlocr_id = request.getParameter("locr_id");
			if(Strlocr_id!=null) {
				int locr_id = Integer.parseInt(Strlocr_id);
				LocrService locrService = new LocrService();
				LocrVO locrVO = locrService.getOneLocr(locr_id);//取得廳房資料
				LociService lociService = new LociService();
				List<LociVO> lociList = lociService.findByForeignKey(locr_id);//取的廳房照片list
				request.setAttribute("locrVO", locrVO);//傳送廳房資料到jsp
				request.setAttribute("lociList", lociList);//傳送廳房照片到jsp
				request.getRequestDispatcher("/front_end/locationroom/locationRoomUpdate.jsp").forward(request, response);
			}
		}

		//觀看廳房單筆資料LocrServlet.java
		if("locationRoomGetOne".equals(action)) {
			String Strlocr_id = request.getParameter("locr_id");
			
			
			
			
			if(Strlocr_id!=null) {
				int locr_id = Integer.parseInt(Strlocr_id);
				LocrService locrService = new LocrService();
				LocrVO locrVO = locrService.getOneLocr(locr_id);//取得廳房資料
				
				LocrService locrService2 = new LocrService();
				int smem_id = locrVO.getLOCR_SMEM_ID();
				MemVO   memVO =   locrService2.getSmem(smem_id);//取得店家資料 方法額外呼叫
						
				LociService lociService = new LociService();
				List<LociVO> lociList = lociService.findByForeignKey(locr_id);//取的廳房照片list
				request.setAttribute("locrVO", locrVO);//傳送廳房資料到jsp
				request.setAttribute("lociList", lociList);//傳送廳房照片到jsp
				request.setAttribute("memVO", memVO);//傳送會員資料
				
				request.getRequestDispatcher("/front_end/locationroom/locrBmemGetOnee.jsp").forward(request, response);
			}
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
