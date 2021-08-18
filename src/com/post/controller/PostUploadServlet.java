package com.post.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/post/postUploadServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)

public class PostUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//PO文的頁面 讓會員可上傳圖片到編輯框
	String saveDirectory = "/images_uploaded";
       
//讀圖片
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); 
		String data = request.getParameter("data");//檔案名稱
		if(data != null) {
			String realPath = getServletContext().getRealPath(saveDirectory);
			File fsaveDirectory = new File(realPath);
			File file = new File(fsaveDirectory,data);
			InputStream in = new FileInputStream(file);
			byte[] buf = new byte[in.available()];
			in.read(buf);
			in.close();
			response.getOutputStream().write(buf);
		}
	}
//上傳圖片
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); 
		response.setContentType("text/html; charset=UTF-8");
		String realPath = getServletContext().getRealPath(saveDirectory);
		File fsaveDirectory = new File(realPath);
		if (!fsaveDirectory.exists()) {
			fsaveDirectory.mkdirs(); 
		}
		
		Collection<Part> parts = request.getParts();
		for (Part part : parts) {
			String filename = "postImg" + new Date().getTime() + "." + getFileExtension(part);
			File file = new File(fsaveDirectory,filename);
			part.write(file.toString());//把圖片寫出去
			response.getWriter().print(filename);
		}
	}
	
	//取出檔案副檔名
	public String getFileExtension(Part part) {
		String partName = part.getName(); //取得key值(fileName)
		String fe = "";
		int i = partName.lastIndexOf('.');
		if (i > 0) {
		    fe = partName.substring(i+1);
		}
		return fe;
	}
}
