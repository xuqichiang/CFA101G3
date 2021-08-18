package com.review.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.message.model.MessageService;
import com.review.model.ReviewService;
import com.review.model.ReviewVO;

@WebServlet("/review/reviewActionServlet")
public class ReviewActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

//抓出該賣家的評價  送到評價頁面
	public ReviewActionServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String action = request.getParameter("action");
		
		//秀出買家留的評價內容
		if ("showBmemReview".equals(action)) {
			Integer smem_id = Integer.parseInt(request.getParameter("smem_id"));
			ReviewService reviewService = new ReviewService();
			List reviewList = reviewService.get_all_by_smem_id(smem_id);

			ObjectMapper mapper = new ObjectMapper();
			String writeValueAsString = mapper.writeValueAsString(reviewList);
			PrintWriter out = response.getWriter();
			out.print(writeValueAsString);
		}
//抓右側熱門商家
		if ("hotStore".equals(action)) {
			ReviewService reviewService = new ReviewService();
			List storeList = reviewService.review_count_sort();

			ObjectMapper mapper = new ObjectMapper();
			String writeValueAsString = mapper.writeValueAsString(storeList); // 轉JSON格式
			PrintWriter out = response.getWriter();
			out.print(writeValueAsString);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
