package com.member.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.member.model.MemService;
import com.member.model.MemVO;
import com.review.model.ReviewService;


@WebServlet("/member/memServlet")
public class MemServlet extends HttpServlet {
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
    	response.setContentType("text/html;charset=UTF-8");
    	String action = request.getParameter("action");
    	//取得買家會員個人資料(分頁顯示)
    	if("getMemberVO".equals(action)) {
    		String mem_idStr = request.getParameter("mem_id");
    		System.out.println(mem_idStr);
    		if(mem_idStr != null) {
    			int mem_id = Integer.parseInt(mem_idStr);
    			MemService memService = new MemService();
    			MemVO memVO = memService.getOne(mem_id);
    			ObjectMapper mapper = new ObjectMapper();
    			String json = mapper.writeValueAsString(memVO);
    			response.getWriter().print(json);
    		}
    	}
    	
    	//取得聊天室會員id陣列轉會員名稱map
    	if("getMemberMap".equals(action)) {
    		String users = request.getParameter("users");
    		ObjectMapper mapper = new ObjectMapper();
    		MemService memService = new MemService();
    		if(users != null) {
    			HashSet<Integer> usersSet = mapper.readValue(users,new TypeReference<HashSet<Integer>>() {});
    			Map<Integer, String> usersMap = new HashMap<Integer,String>();
    			for(Integer mem_id:usersSet) {
    				MemVO memVO = memService.getOne(mem_id);
    				usersMap.put(mem_id, memVO.getMem_name());
    			}
    			String writeValueAsString = mapper.writeValueAsString(usersMap);
    			response.getWriter().print(writeValueAsString);
    		}
    	}
    	
    	//取得會員資料含評價數
    	if("getOne_review".equals(action)) {
    		String mem_idStr = request.getParameter("mem_id");
    		if(mem_idStr != null) {
    			Integer mem_id = Integer.parseInt(mem_idStr);
    			MemService memService = new MemService();
    			MemVO memVO = memService.getOne(mem_id);
    			ReviewService reviewService = new ReviewService();
    			List list = reviewService.get_all_by_smem_id(mem_id);
    			System.out.println(list);
    			System.out.println(list.size());
    			memVO.setMem_review_count(list.size());
    			Integer SCORE = 0;
    			for (int i = 0; i < list.size(); i++) {
    				Map map = (Map)list.get(i);
    				SCORE += (Integer)map.get("REV_SCORE");
				}
    			memVO.setMem_review_score(SCORE);
    			ObjectMapper mapper = new ObjectMapper();
    			String json = mapper.writeValueAsString(memVO);
    			response.getWriter().print(json);
    		}

    	}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		doGet(request, response);
	}

}
