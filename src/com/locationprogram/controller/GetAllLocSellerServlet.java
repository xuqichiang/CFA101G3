package com.locationprogram.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.locationprogram.model.LocpService;
import com.member.model.MemVO;
import com.review.model.ReviewService;

@WebServlet("/locationprogram/getAllLocSellerServlet")
public class GetAllLocSellerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		LocpService locpsvc = new LocpService();
		ReviewService reviewService = new ReviewService();//評價Svc

		List<MemVO> Loclist = locpsvc.getAllLoc();
		
		//對每個場地店家會員塞評價資料
		for(MemVO memVO : Loclist) {
			Integer mem_id = memVO.getMem_id();
			List reviewList = reviewService.get_all_by_smem_id(mem_id);
			memVO.setMem_review_count(reviewList.size());
			Integer SCORE = 0;
			for (int i = 0; i < reviewList.size(); i++) {
				Map map = (Map)reviewList.get(i);
				SCORE += (Integer)map.get("REV_SCORE");
			}
			memVO.setMem_review_score(SCORE);
		}
		
		ObjectMapper mapper = new ObjectMapper(); 
		String data = mapper.writeValueAsString(Loclist);
		out.println(data);
	}

}
