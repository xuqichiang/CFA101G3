package com.post.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.post.model.PostService;
import com.post_tag_ref.model.PostTagRefJoinVO;
import com.post_tag_ref.model.PostTagRefService;

@WebServlet("/post/showOnePostBypostIdServlet")
public class ShowOnePostBypostIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

//在show-article-message.html show出文章資訊與標籤
	public ShowOnePostBypostIdServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String action = request.getParameter("action");
		if ("getPost".equals(action)) {
//			(1, post_id);
//				map.put("POST_ID", rs.getInt("post_id"));
//				map.put("POST_TITLE", rs.getString("post_title"));
//				map.put("POST_CONTENT", rs.getString("post_content"));
//				map.put("POST_TIME", rs.getTimestamp("post_time"));
//				map.put("CAT_NAME", rs.getString("cat_name"));
//				map.put("MEM_NAME", rs.getString("mem_name"));
//				map.put("MEM_HEADSHOT", rs.getBytes("mem_headshot"));
//				map.put("POST_STATUS", rs.getInt("post_status"));
				
			PostService postService = new PostService();
			Integer post_id = Integer.parseInt(request.getParameter("post_id"));
			Map showPost = postService.findByPostId(post_id);

			ObjectMapper mapper = new ObjectMapper();
			String writeValueAsString = mapper.writeValueAsString(showPost); // 轉JSON格式
			System.out.println(writeValueAsString);
			PrintWriter out = response.getWriter();
			out.print(writeValueAsString);
		}

		if ("getTag".equals(action)) {
			PostTagRefService postTagRefService = new PostTagRefService();
			Integer post_id = Integer.parseInt(request.getParameter("post_id"));
			List<PostTagRefJoinVO> findBy_PTR_Post_Id = postTagRefService.findBy_PTR_Post_Id(post_id);
			ObjectMapper mapper = new ObjectMapper();
			String writeValueAsString = mapper.writeValueAsString(findBy_PTR_Post_Id); // 轉JSON格式
			System.out.println(writeValueAsString);
			PrintWriter out = response.getWriter();
			out.print(writeValueAsString);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
