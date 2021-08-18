package com.proJOINmem.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ProJoinMemDAOimpl {

	//獲取DS使用連線池
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	public ProJoinMemVO findByPid(Integer pro_id) {
		//準備好 SQL list vo con pstmt rs 1.宣告2.取值3.拿來用
		java.lang.String SQL ="SELECT MEM_ID , MEM_PHONE , MEM_SHOP_NAME ,  MEM_SHOP_CONTENT , MEM_SHOP_LOGO ,MEM_SHOP_BANNER ,MEM_REVIEW_COUNT , MEM_REVIEW_SCORE FROM `MEMBER` join PRODUCT ON MEM_ID = PRO_SMEM_ID WHERE PRO_ID =? ;";
		ProJoinMemVO pjmVO =null;
		Connection con =null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		//連線開始
		try {
			con = ds.getConnection();
			pstmt = con.prepareCall(SQL);
			pstmt.setInt(1, pro_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				pjmVO = new ProJoinMemVO();
				pjmVO.setMem_id(rs.getInt("MEM_ID"));
				pjmVO.setMem_phone(rs.getString("MEM_PHONE"));
				pjmVO.setMem_shop_name(rs.getString("MEM_SHOP_NAME"));
				pjmVO.setMem_shop_content(rs.getString("MEM_SHOP_CONTENT"));
				pjmVO.setMem_shop_logo(rs.getBytes("MEM_SHOP_LOGO"));
				pjmVO.setMem_shop_banner(rs.getBytes("MEM_SHOP_BANNER"));
				pjmVO.setMem_review_count(rs.getInt("MEM_REVIEW_COUNT"));
				pjmVO.setMem_review_score(rs.getInt("MEM_REVIEW_SCORE"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return pjmVO;
	};
}
