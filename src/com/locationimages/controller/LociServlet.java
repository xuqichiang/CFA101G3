package com.locationimages.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.locationimages.model.*;
import com.locationroom.model.*;
/**
 * Servlet implementation class LociServlet
 */
@WebServlet("/LociServlet")
public class LociServlet extends HttpServlet {
  
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
	
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String action = request.getParameter("action");
	
		if("jsp".equals(action)) {
			try {
				Integer LOCI_LOCR_ID = Integer.parseInt(request.getParameter("LOCI_LOCR_ID"));
				LociService LociService = new LociService();
				List<LociVO> list = LociService.findByForeignKey(LOCI_LOCR_ID);
				request.setAttribute("list", list);
				RequestDispatcher rd = request.getRequestDispatcher("/front_end/locationimages/locationImagesShow.jsp");
				rd.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if("locr".equals(action)) {
			try {
				Integer LOCI_LOCR_ID = Integer.parseInt(request.getParameter("LOCI_LOCR_ID"));
				LociService LociService = new LociService();
				List<LociVO> list = LociService.findByForeignKey(LOCI_LOCR_ID);
				request.setAttribute("list2", list);
				RequestDispatcher rd = request.getRequestDispatcher("/front_end/locationroom/locationRoomGetAll.jsp");
				rd.forward(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			request.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				Integer LOCI_ID = new Integer(request.getParameter("LOCI_ID"));

				/*************************** 2.開始刪除資料 ***************************************/
				LociService LociSvc = new LociService();
				LociVO lociVO = LociSvc.findByPrimaryKey(LOCI_ID);
				Integer LOCI_LOCR_ID = lociVO.getLOCI_LOCR_ID();
				LociSvc.deleteLociID(LOCI_ID);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/locationroom/locationRoomServlet?action=locationRoomUpdate&locr_id=" + LOCI_LOCR_ID;
				// 刪除成功後,轉交回送出刪除的來源網頁
				response.sendRedirect(request.getContextPath()+url);
				
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
//				Integer LOCI_ID = new Integer(request.getParameter("LOCI_ID"));
//				LociService LociSvc = new LociService();
//				LociVO lociVO = LociSvc.findByPrimaryKey(LOCI_ID);
//				Integer LOCI_LOCR_ID = lociVO.getLOCI_LOCR_ID();
//				errorMsgs.add("刪除資料失敗:" + e.getMessage());
//				RequestDispatcher failureView = request.getRequestDispatcher(
//						"/locationroom/locationRoomServlet?action=locationRoomUpdate&locr_id=" + LOCI_LOCR_ID);
//				failureView.forward(request, response);
				e.printStackTrace();
			}

		}

		if ("insert".equals(action)) { // 來自addEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			request.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				Integer LOCI_LOCR_ID = new Integer(request.getParameter("LOCI_LOCR_ID"));
				Part part = request.getPart("file1");

				InputStream in = part.getInputStream();

				LociService locisvc = new LociService();
				LociVO lociVO = new LociVO();
				byte[] buf = null;

				buf = new byte[in.available()];
				in.read(buf);
				in.close();

				

				/*************************** 2.開始新增資料 ***************************************/
				System.out.println("1");
				lociVO.setLOCI_LOCR_ID(LOCI_LOCR_ID);
				lociVO.setLOCI_IMAGES(buf);
				locisvc.addLoci(LOCI_LOCR_ID, buf);
				
				
				
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/locationroom/locationRoomServlet?action=locationRoomUpdate&locr_id=" + LOCI_LOCR_ID;
				System.out.println("2");
				RequestDispatcher successView = request.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(request, response);
				
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				System.out.println("3");
				Integer LOCI_LOCR_ID = new Integer(request.getParameter("LOCI_LOCR_ID"));
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = request
						.getRequestDispatcher("/locationroom/locationRoomServlet?action=locationRoomUpdate&locr_id=" + LOCI_LOCR_ID);
				failureView.forward(request, response);
			}
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
