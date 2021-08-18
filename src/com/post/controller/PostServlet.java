package com.post.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.category.model.CategoryService;
import com.category.model.CategoryVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.post.model.PostService;
import com.post_tag_ref.model.PostTagRefService;
import com.post_tag_ref.model.PostTagRefVO;
import com.tag.model.TagService;



@WebServlet("/post/postServlet")
public class PostServlet extends HttpServlet {     
//搭配forumindex  撈出後端的文章資訊送到專欄討論區的首頁 一段貼文一個區塊
	
//	getPost指令：
//	"select p.POST_ID,p.POST_TITLE,p.POST_CONTENT,p.POST_TIME, c.CAT_NAME,m.MEM_NAME,m.MEM_HEADSHOT,p.POST_STATUS from MEMBER m "
//	+ "join POST p on m.MEM_ID = p.POST_MEM_ID "
//	+ "join CATEGORY c on p.POST_CAT_ID = c.CAT_ID";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8"); 
		PostService postService = new PostService();
		List post = postService.getPost();
		
		ObjectMapper mapper = new ObjectMapper();
		String writeValueAsString = mapper.writeValueAsString(post); //轉JSON格式
		PrintWriter out = response.getWriter();
		out.print(writeValueAsString);
		
		
	
//		PostTagRefService postTagRefService = new PostTagRefService();
//		Integer  ptr_post_id = Integer.parseInt(request.getParameter("ptr_post_id"));
//		List<PostTagRefVO> postTagReflist = postTagRefService.findBy_PTR_Post_Id(ptr_post_id);
	
//		
//		for(int i=0; i< postTagReflist.size(); i++) {
//			PostTagRefVO postTagRefVO = postTagReflist.get(i);
//
//		
		
		
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
