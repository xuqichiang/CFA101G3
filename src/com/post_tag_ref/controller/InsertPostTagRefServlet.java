package com.post_tag_ref.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.member.model.MemVO;
import com.post.model.PostVO;
import com.post_tag_ref.model.PostTagRefService;
import com.post_tag_ref.model.PostTagRefVO;
import com.tag.model.TagService;
import com.tag.model.TagVO;


@WebServlet("/post_tag_ref/InsertPostTagRefServlet")
public class InsertPostTagRefServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		
		//會員?
//		HttpSession session = request.getSession();
//		MemVO memVo = new MemVO();
		
		
		//接收前台發送來的請求
		Integer ptr_id = Integer.parseInt(request.getParameter("ptr_id"));
		Integer ptr_post_id = Integer.parseInt(request.getParameter("ptr_post_id"));
		Integer ptr_tag_id = Integer.parseInt(request.getParameter("ptr_tag_id"));
		
		//文章標籤明細 Service
		PostTagRefService postTagRefService = new PostTagRefService();
//		PostTagRefVO postTagRefVo = postTagRefService.addPostTagRef(ptr_post_id, ptr_tag_id);
		
		//標籤 Service?
//		TagService tagService = new TagService();
		
		
		
		
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
