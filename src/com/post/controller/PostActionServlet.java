package com.post.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.member.model.MemVO;
import com.message.model.MessageService;
import com.post.model.PostService;
import com.post_tag_ref.model.PostTagRefService;
import com.tag.model.TagService;
import com.tag.model.TagVO;

@WebServlet("/post/postActionServlet")
public class PostActionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PostActionServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String action = request.getParameter("action");

		// 抓出右側熱門文章(依照留言數)
		if ("hotPost".equals(action)) {
			MessageService messageService = new MessageService();
			List postList = messageService.message_count_sort();

			ObjectMapper mapper = new ObjectMapper();
			String writeValueAsString = mapper.writeValueAsString(postList); // 轉JSON格式
			PrintWriter out = response.getWriter();
			out.print(writeValueAsString);
		}

		// 抓作者PO的文章 做後續修改
		if ("post_from_writer".equals(action)) {
			// 取得session中的會員資料
			HttpSession session = request.getSession();
			MemVO memVo = (MemVO) session.getAttribute("user");
			Integer post_mem_id = memVo.getMem_id();

			// 調用Service
			PostService postService = new PostService();
			List post_list = postService.findByWriter(post_mem_id);

			// 開始輸出
			ObjectMapper mapper = new ObjectMapper();
			String writeValueAsString = mapper.writeValueAsString(post_list); // 轉JSON格式
			PrintWriter out = response.getWriter();
			out.print(writeValueAsString);
		}

		// 編輯文章內容
		if ("edit_post".equals(action)) {
			// 取得session
			HttpSession session = request.getSession();
			MemVO memVo = (MemVO) session.getAttribute("user");
			Integer post_mem_id = memVo.getMem_id();

			// 取得前端傳來的參數
			Integer post_id = Integer.parseInt(request.getParameter("post_id"));
			String post_title = request.getParameter("post_title").trim();
			String post_content = request.getParameter("post_content").trim();
			Timestamp post_time = new Timestamp(new java.util.Date().getTime());
			Integer post_cat_id = Integer.parseInt(request.getParameter("post_cat_id"));
			Integer post_status = Integer.parseInt(request.getParameter("post_status"));
			String tag_name = request.getParameter("tag_name");
			String[] tagArray = tag_name.split(",");// 將tag_name字串轉成tagArray

			// 調用service
			PostService postService = new PostService();
			postService.updatePost(post_title, post_content, post_time, post_cat_id, post_mem_id, post_status, post_id);
			TagService tagService = new TagService();
			List<TagVO> addTag = tagService.addTag(tagArray);// 把接到的tagArray丟回此方法，讓他判斷標籤是否與舊標籤重複
			PostTagRefService postTagRefService = new PostTagRefService();
			postTagRefService.UpdateTags(post_id, addTag);

			// 輸出
			PrintWriter out = response.getWriter(); // servlet輸出
			ObjectMapper mapper = new ObjectMapper(); // JSON
			String msg = "更新成功";
			Map hashMap = new HashMap();// 把字串放進map
			hashMap.put("msg", msg);
			String data = mapper.writeValueAsString(hashMap); // JSON
			out.println(data);

		}
		
		
		if ("mem_post".equals(action)) {
			// 取得session
			HttpSession session = request.getSession();
			MemVO memVo = (MemVO) session.getAttribute("user");
			

			if(memVo == null ) {
				response.getWriter().print(0);
			}else {
				Integer post_mem_id = memVo.getMem_id();
				response.getWriter().print(1); 
			}
		

		}
		
		

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
