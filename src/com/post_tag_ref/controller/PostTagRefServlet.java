package com.post_tag_ref.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.post_tag_ref.model.PostTagRefService;


@WebServlet("/post/postTagRefServlet")
public class PostTagRefServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public PostTagRefServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String action = request.getParameter("action");
		if ("getTags".equals(action)) {
//			findBy_PTR_Post_Id 獲得 PTR_ID PTR_POST_ID PTR_TAG_ID TAG_ID TAG_NAME
			PostTagRefService postTagRefService = new PostTagRefService();
			Integer ptr_post_id = Integer.parseInt(request.getParameter("ptr_post_id"));
			List tags = postTagRefService.findBy_PTR_Post_Id(ptr_post_id);
			
			ObjectMapper mapper = new ObjectMapper();
			String writeValueAsString = mapper.writeValueAsString(tags); // 轉JSON格式
			System.out.println(writeValueAsString);
			PrintWriter out = response.getWriter();
			out.print(writeValueAsString);
					
		}
	
	
	
	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
