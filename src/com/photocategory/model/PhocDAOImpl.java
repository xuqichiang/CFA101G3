package com.photocategory.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.mysql.cj.xdevapi.Result;
import com.photoprogram.model.PhopVO;

public class PhocDAOImpl implements PhocDAO{

	
//	private static String FIND_BY_ONE = "SELECT * FROM PHOTO_CATEGORY WHERE PHOC_ID = ?";
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	//所有方案類別
	public List<PhocVO> getAll()  {
		String GET_ALL = "SELECT * FROM PHOTO_CATEGORY";
		//取得連線
		List<PhocVO> list = new ArrayList<PhocVO>(); 
		PhocVO phocVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				phocVO = new PhocVO();
				phocVO.setPhoc_id(rs.getInt("phoc_id"));
				phocVO.setPhoc_name(rs.getString("phoc_name"));
				list.add(phocVO);
			}
			
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured." + e.getMessage());//SQL語法錯誤
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

//	@Override
//	public PhocVO getName(String phoc_name) {
//		
//		String Category ="SELECT PHOC_NAME FROM PHOTO_CATEGORY where PHOC_NAME like '%?'";
//
//		Connection con = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//
//		
//		try {
//			con = ds.getConnection();
//			pstmt = con.prepareStatement(Category);
//
//			pstmt.setString(1,phoc_name);
//
//			rs = pstmt.executeQuery();
//
//			while (rs.next()) {
//				
//				PhocVO phocVO = new PhocVO();
//				
//				phocVO.setPhoc_name(rs.getString("phoc_name"));
//			
//			}
//
//		} catch (SQLException e) {
//
//			e.printStackTrace();
//
//		} finally {
//			if (rs != null) {
//				try {
//					rs.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//			if (pstmt != null) {
//				try {
//					pstmt.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//			if (con != null) {
//				try {
//					con.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		return null;
//	}
}
