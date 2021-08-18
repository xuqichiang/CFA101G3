package com.post.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.member.model.MemVO;
import com.post.model.PostService;
import com.post.model.PostVO;
import com.post_tag_ref.model.PostTagRefService;
import com.post_tag_ref.model.PostTagRefVO;
import com.tag.model.TagService;
import com.tag.model.TagVO;

@WebServlet("/post/poArticleServlet")
public class PoArticleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

//搭配po_article.html  接收作者的發文(標題.內容.標籤..並存回資料庫
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");

		//接收參數(ajax傳來的)
		Integer post_cat_id = Integer.parseInt(request.getParameter("post_cat_id"));
		String post_title = request.getParameter("post_title").trim();
		String post_content = request.getParameter("post_content").trim();
		Timestamp post_time = new Timestamp(new java.util.Date().getTime());
		String tag_name = request.getParameter("tag_name");
		String[] tagArray = tag_name.split(",");//將tag_name字串轉成tagArray
		//須先取得會員身份
		HttpSession session = request.getSession();
		MemVO user = (MemVO) session.getAttribute("user");
		Integer post_mem_id = user.getMem_id();
		// *******測試 暫時將會員寫死*******//
        //Integer post_mem_id = 2;
		// *******測試 暫時將會員寫死*******//

		List<String> errorMsgs = new LinkedList<>();
		ObjectMapper mapper = new ObjectMapper(); 	
		request.setAttribute("errorMsgs",errorMsgs);
//		String postContentReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_.())]{1,25}$";
		
		if(post_title == null|| post_title.length() ==0) {
			errorMsgs.add("*請輸入標題");
//		}else if (!post_title.matches(postContentReg)) {
//			errorMsgs.add("*標題格式不正確");
		}
		
		if (post_content == null || post_content.length() == 0) {
			errorMsgs.add("*請輸入文章內容");
		}
		
		// Send the use back to the form, if there were er nrors
		if (!errorMsgs.isEmpty()) {
			response.setStatus(401);
			String Msg = mapper.writeValueAsString(errorMsgs);
			response.getWriter().print(Msg);
			return;
		}
		
		
		
		//調用Service與方法		
		TagService tagService = new TagService();
		List<TagVO> addTag = tagService.addTag(tagArray);//把接到的tagArray丟回此方法，讓他判斷標籤是否與舊標籤重複

		PostService postService = new PostService();
		postService.addPost(post_title, post_content, post_time, post_cat_id, post_mem_id, addTag);

		response.getWriter().print("success");// 文章成功送到資料庫會出現
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
