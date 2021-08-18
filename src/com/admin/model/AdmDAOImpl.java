package com.admin.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class AdmDAOImpl implements AdmDAO{
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String GET_ONE_USERNAME_PASSWORD = "select * from ADMIN where ADM_USERNAME = ? and ADM_PASSWORD = ?";

	@Override
	public AdmVO findByUsernameAndPassword(String username, String password) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		AdmVO admVO = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_USERNAME_PASSWORD);
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				admVO = new AdmVO();
				admVO.setAdm_id(rs.getInt("adm_id"));
				admVO.setAdm_username(rs.getString("adm_username"));
				admVO.setAdm_password(rs.getString("adm_password"));
				admVO.setAdm_status(rs.getInt("adm_status"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("A database error occured. " + e.getMessage());
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return admVO;
	}

}
