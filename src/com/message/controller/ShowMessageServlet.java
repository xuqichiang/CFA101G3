package com.message.controller;

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
import com.post.model.PostService;


@WebServlet("/message/showMessageServlet")
public class ShowMessageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public ShowMessageServlet() {
        super();
      
    }

//撈出該文章的留言  送到文章頁面下方
//join的表格
//getBy_mes_post_id = "select s.MES_ID,s.MES_POST_ID,s.MES_MEM_ID,m.MEM_NAME,s.MES_TIME,s.MES_CONTENT,m.MEM_HEADSHOT,\n"
//+ "s.MES_STATUS from MEMBER m join MESSAGE s on m.MEM_ID = s.MES_MEM_ID where MES_POST_ID=? order by MES_ID desc";
		
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8"); 
			Integer mes_post_id = Integer.parseInt(request.getParameter("mes_post_id"));
			MessageService messageService = new MessageService();
			List messageList = messageService.getBy_mes_post_id(mes_post_id);

			ObjectMapper mapper = new ObjectMapper();
			String writeValueAsString = mapper.writeValueAsString(messageList); //轉JSON格式
			PrintWriter out = response.getWriter();
			out.print(writeValueAsString);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
