package com.locationprogram.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.jasper.tagplugins.jstl.core.ForEach;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.locationroom.model.LocrVO;
import com.member.model.MemVO;



public class LocpDAOImpl implements LocpDAO{
	
//	String driver = "com.mysql.cj.jdbc.Driver";
//	String url = "jdbc:mysql://35.221.190.216:3306/CFA101G3?serverTimezone=Asia/Taipei";
//	String userid = "David";
//	String passwd = "123456";
	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/CFA101G3?serverTimezone=Asia/Taipei";
	String userid = "root";
	String passwd = "19881201";

	@Override
	public void insert(LocpVO locpVO) {
		
		String sql = "INSERT INTO LOCATION_PROGRAM (LOCP_NAME, LOCP_PRICE, LOCP_START_TIME, LOCP_END_TIME, LOCP_CONTENT, LOCP_SMEM_ID, LOCP_STATUS) VALUES (?, ?, ?, ?, ?, ?, ?)";
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, locpVO.getLocp_name());
			pstmt.setInt(2, locpVO.getLocp_price());
			pstmt.setDate(3, locpVO.getLocp_start_time());
			pstmt.setDate(4, locpVO.getLocp_end_time());
			pstmt.setString(5, locpVO.getLocp_content());
			pstmt.setInt(6, locpVO.getLocp_smem_id());
			pstmt.setInt(7, locpVO.getLocp_status());
			
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
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
		
	}

	@Override
	public void update(LocpVO locpVO) {
		
		String sql = "UPDATE LOCATION_PROGRAM SET LOCP_NAME=?, LOCP_PRICE=?, LOCP_START_TIME=?, LOCP_END_TIME=?, LOCP_CONTENT=?, LOCP_STATUS=? WHERE LOCP_ID = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(sql);
			
			pstmt.setString(1, locpVO.getLocp_name());
			pstmt.setInt(2, locpVO.getLocp_price());
			pstmt.setDate(3, locpVO.getLocp_start_time());
			pstmt.setDate(4, locpVO.getLocp_end_time());
			pstmt.setString(5, locpVO.getLocp_content());
			pstmt.setInt(6, locpVO.getLocp_status());
			pstmt.setInt(7, locpVO.getLocp_id());
			
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
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
		
	}

	@Override
	public void delete(Integer locp_id) {
		
		String sql = "DELETE FROM LOCATION_PROGRAM WHERE LOCP_ID = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, locp_id);
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		} finally {
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
	}
	
	@Override
	public LocpVO getOneByLocpid(Integer locp_id) {
		
		String sql = "SELECT * FROM LOCATION_PROGRAM WHERE LOCP_ID = ?";
		LocpVO locpVO = new LocpVO();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, locp_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				locpVO.setLocp_id(rs.getInt("LOCP_ID"));
				locpVO.setLocp_name(rs.getString("LOCP_NAME"));
				locpVO.setLocp_price(rs.getInt("LOCP_PRICE"));
				locpVO.setLocp_start_time(rs.getDate("LOCP_START_TIME"));
				locpVO.setLocp_end_time(rs.getDate("LOCP_END_TIME"));
				locpVO.setLocp_smem_id(rs.getInt("LOCP_SMEM_ID"));
				locpVO.setLocp_content(rs.getString("LOCP_CONTENT"));
				locpVO.setLocp_status(rs.getInt("LOCP_STATUS"));
			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return locpVO;
	}

	@Override
	public List<LocpVO> getOneLocpBySmemid(Integer locp_smem_id) {
		
		String sql = "SELECT * FROM LOCATION_PROGRAM WHERE LOCP_SMEM_ID = ?";
		List<LocpVO> list = new ArrayList<LocpVO>();
		LocpVO locpVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, locp_smem_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				locpVO = new LocpVO();
				locpVO.setLocp_id(rs.getInt("LOCP_ID"));
				locpVO.setLocp_name(rs.getString("LOCP_NAME"));
				locpVO.setLocp_price(rs.getInt("LOCP_PRICE"));
				locpVO.setLocp_start_time(rs.getDate("LOCP_START_TIME"));
				locpVO.setLocp_end_time(rs.getDate("LOCP_END_TIME"));
				locpVO.setLocp_content(rs.getString("LOCP_CONTENT"));
				locpVO.setLocp_status(rs.getInt("LOCP_STATUS"));
				list.add(locpVO);
			}
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
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
		
		return list;
	}
	
	public LocpVO getOneLocpByLocpname(String locp_name) {
		
		String sql = "SELECT * FROM LOCATION_PROGRAM WHERE LOCP_NAME = ?";
		LocpVO locpVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, locp_name);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				locpVO = new LocpVO();
				locpVO.setLocp_id(rs.getInt("LOCP_ID"));
				locpVO.setLocp_name(rs.getString("LOCP_NAME"));
				locpVO.setLocp_price(rs.getInt("LOCP_PRICE"));
				locpVO.setLocp_start_time(rs.getDate("LOCP_START_TIME"));
				locpVO.setLocp_end_time(rs.getDate("LOCP_END_TIME"));
				locpVO.setLocp_content(rs.getString("LOCP_CONTENT"));
				locpVO.setLocp_status(rs.getInt("LOCP_STATUS"));
				
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		
		return locpVO;
	}
	
	@Override
	public List<LocpVO> getAll() {
		
		String sql = "SELECT p.LOCP_ID as LOCP_ID, p.LOCP_NAME as LOCP_NAME, p.LOCP_PRICE as LOCP_PRICE, p.LOCP_START_TIME as LOCP_START_TIME, p.LOCP_END_TIME as LOCP_END_TIME, p.LOCP_CONTENT as LOCP_CONTENT, p.LOCP_SMEM_ID as LOCP_SMEM_ID, p.LOCP_STATUS as LOCP_STATUS, m.MEM_SHOP_NAME as MEM_SHOP_NAME, m.MEM_SHOP_LOGO as MEM_SHOP_LOGO FROM LOCATION_PROGRAM p JOIN `MEMBER` m on p.LOCP_SMEM_ID = m.MEM_ID";
		List<LocpVO> list = new ArrayList<LocpVO>();
		LocpVO locpVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				locpVO = new LocpVO();
				locpVO.setLocp_id(rs.getInt("LOCP_ID"));
				locpVO.setLocp_name(rs.getString("LOCP_NAME"));
				locpVO.setLocp_price(rs.getInt("LOCP_PRICE"));
				locpVO.setLocp_start_time(rs.getDate("LOCP_START_TIME"));
				locpVO.setLocp_end_time(rs.getDate("LOCP_END_TIME"));
				locpVO.setLocp_content(rs.getString("LOCP_CONTENT"));
				locpVO.setLocp_smem_id(rs.getInt("LOCP_SMEM_ID"));
				locpVO.setLocp_status(rs.getInt("LOCP_STATUS"));
				locpVO.setLoc_name(rs.getString("MEM_SHOP_NAME"));
				locpVO.setLoc_logo(rs.getBytes("MEM_SHOP_LOGO"));
				list.add(locpVO);
			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		
		return list;
	}
	
	@Override
	public LocpVO getLastID() {
		
		String sql = "SELECT * FROM LOCATION_PROGRAM ORDER BY LOCP_ID DESC LIMIT 0 , 1;";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LocpVO locpVO = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				locpVO = new LocpVO();
				locpVO.setLocp_id(rs.getInt("LOCP_ID"));
			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return locpVO;
	}
	
	@Override
	public List<MemVO> getAllLoc() {
		
		String sql = "SELECT * FROM `MEMBER` WHERE MEM_ROLE = 30";
		List<MemVO> list = new ArrayList<MemVO>();
		MemVO memVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				memVO = new MemVO();
				memVO.setMem_id(rs.getInt("mem_id"));
				memVO.setMem_username(rs.getString("mem_username"));
				memVO.setMem_password(rs.getString("mem_password"));
				memVO.setMem_name(rs.getString("mem_name"));
				memVO.setMem_role(rs.getInt("mem_role"));
				memVO.setMem_phone(rs.getString("mem_phone"));
				memVO.setMem_city(rs.getString("mem_city"));
				memVO.setMem_cityarea(rs.getString("mem_cityarea"));
				memVO.setMem_street(rs.getString("mem_street"));
				memVO.setMem_status(rs.getInt("mem_status"));
				memVO.setMem_shop_name(rs.getString("mem_shop_name"));
				memVO.setMem_shop_content(rs.getString("mem_shop_content"));
				memVO.setMem_shop_logo(rs.getBytes("mem_shop_logo"));
				memVO.setMem_shop_banner(rs.getBytes("mem_shop_banner"));
				memVO.setMem_shop_status(rs.getInt("mem_shop_status"));
				memVO.setMem_headshot(rs.getBytes("mem_headshot"));
				memVO.setMem_review_count(rs.getInt("mem_review_count"));
				memVO.setMem_review_score(rs.getInt("mem_review_score"));
				list.add(memVO);
				
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
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
		return list;
	}

	@Override
	public List<LocpVO> getAllHighToLow() {
		
		String sql = "SELECT p.LOCP_ID as LOCP_ID, p.LOCP_NAME as LOCP_NAME, p.LOCP_PRICE as LOCP_PRICE, p.LOCP_START_TIME as LOCP_START_TIME, p.LOCP_END_TIME as LOCP_END_TIME, p.LOCP_CONTENT as LOCP_CONTENT, p.LOCP_SMEM_ID as LOCP_SMEM_ID, p.LOCP_STATUS as LOCP_STATUS, i.LOCPI_IMAGES as LOCP_IMAGE FROM LOCATION_PROGRAM p join LOCATION_PROGRAM_IMAGES i on p.LOCP_ID = i.LOCPI_LOCP_ID order by p.LOCP_PRICE DESC";
		List<LocpVO> list = new ArrayList<LocpVO>();
		LocpVO locpVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				locpVO = new LocpVO();
				locpVO.setLocp_id(rs.getInt("LOCP_ID"));
				locpVO.setLocp_name(rs.getString("LOCP_NAME"));
				locpVO.setLocp_price(rs.getInt("LOCP_PRICE"));
				locpVO.setLocp_start_time(rs.getDate("LOCP_START_TIME"));
				locpVO.setLocp_end_time(rs.getDate("LOCP_END_TIME"));
				locpVO.setLocp_content(rs.getString("LOCP_CONTENT"));
				locpVO.setLocp_status(rs.getInt("LOCP_STATUS"));
				locpVO.setLocp_images(rs.getBytes("LOCP_IMAGE"));;
				list.add(locpVO);
			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			se.printStackTrace();
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
		
		return list;
	}

	@Override
	public List<LocpVO> getAllLowToHigh() {
		
		String sql = "SELECT p.LOCP_ID as LOCP_ID, p.LOCP_NAME as LOCP_NAME, p.LOCP_PRICE as LOCP_PRICE, p.LOCP_START_TIME as LOCP_START_TIME, p.LOCP_END_TIME as LOCP_END_TIME, p.LOCP_CONTENT as LOCP_CONTENT, p.LOCP_SMEM_ID as LOCP_SMEM_ID, p.LOCP_STATUS as LOCP_STATUS, i.LOCPI_IMAGES as LOCP_IMAGE FROM LOCATION_PROGRAM p join LOCATION_PROGRAM_IMAGES i on p.LOCP_ID = i.LOCPI_LOCP_ID order by p.LOCP_PRICE";
		List<LocpVO> list = new ArrayList<LocpVO>();
		LocpVO locpVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				locpVO = new LocpVO();
				locpVO.setLocp_id(rs.getInt("LOCP_ID"));
				locpVO.setLocp_name(rs.getString("LOCP_NAME"));
				locpVO.setLocp_price(rs.getInt("LOCP_PRICE"));
				locpVO.setLocp_start_time(rs.getDate("LOCP_START_TIME"));
				locpVO.setLocp_end_time(rs.getDate("LOCP_END_TIME"));
				locpVO.setLocp_content(rs.getString("LOCP_CONTENT"));
				locpVO.setLocp_status(rs.getInt("LOCP_STATUS"));
				locpVO.setLocp_images(rs.getBytes("LOCP_IMAGE"));;
				list.add(locpVO);
			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			se.printStackTrace();
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
		
		return list;
	}

	@Override
	public List<LocpVO> getAllLogoLowToHigh() {
		
		String sql = "SELECT p.LOCP_ID as LOCP_ID, p.LOCP_NAME as LOCP_NAME, p.LOCP_PRICE as LOCP_PRICE, p.LOCP_START_TIME as LOCP_START_TIME, p.LOCP_END_TIME as LOCP_END_TIME, p.LOCP_CONTENT as LOCP_CONTENT, p.LOCP_SMEM_ID as LOCP_SMEM_ID, p.LOCP_STATUS as LOCP_STATUS, m.MEM_SHOP_NAME as MEM_SHOP_NAME, m.MEM_SHOP_LOGO as MEM_SHOP_LOGO FROM LOCATION_PROGRAM p JOIN MEMBER m on p.LOCP_SMEM_ID = m.MEM_ID ORDER BY p.LOCP_PRICE";
		List<LocpVO> list = new ArrayList<LocpVO>();
		LocpVO locpVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				locpVO = new LocpVO();
				locpVO.setLocp_id(rs.getInt("LOCP_ID"));
				locpVO.setLocp_name(rs.getString("LOCP_NAME"));
				locpVO.setLocp_price(rs.getInt("LOCP_PRICE"));
				locpVO.setLocp_start_time(rs.getDate("LOCP_START_TIME"));
				locpVO.setLocp_end_time(rs.getDate("LOCP_END_TIME"));
				locpVO.setLocp_content(rs.getString("LOCP_CONTENT"));
				locpVO.setLocp_status(rs.getInt("LOCP_STATUS"));
				locpVO.setLoc_name(rs.getString("MEM_SHOP_NAME"));
				locpVO.setLoc_logo(rs.getBytes("MEM_SHOP_LOGO"));;
				list.add(locpVO);
			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			se.printStackTrace();
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
		
		return list;
	}

	@Override
	public List<LocpVO> getAllLogoHighToLow() {
		
		String sql = "SELECT p.LOCP_ID as LOCP_ID, p.LOCP_NAME as LOCP_NAME, p.LOCP_PRICE as LOCP_PRICE, p.LOCP_START_TIME as LOCP_START_TIME, p.LOCP_END_TIME as LOCP_END_TIME, p.LOCP_CONTENT as LOCP_CONTENT, p.LOCP_SMEM_ID as LOCP_SMEM_ID, p.LOCP_STATUS as LOCP_STATUS, m.MEM_SHOP_NAME as MEM_SHOP_NAME, m.MEM_SHOP_LOGO as MEM_SHOP_LOGO FROM LOCATION_PROGRAM p JOIN MEMBER m on p.LOCP_SMEM_ID = m.MEM_ID ORDER BY p.LOCP_PRICE DESC";
		List<LocpVO> list = new ArrayList<LocpVO>();
		LocpVO locpVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				locpVO = new LocpVO();
				locpVO.setLocp_id(rs.getInt("LOCP_ID"));
				locpVO.setLocp_name(rs.getString("LOCP_NAME"));
				locpVO.setLocp_price(rs.getInt("LOCP_PRICE"));
				locpVO.setLocp_start_time(rs.getDate("LOCP_START_TIME"));
				locpVO.setLocp_end_time(rs.getDate("LOCP_END_TIME"));
				locpVO.setLocp_content(rs.getString("LOCP_CONTENT"));
				locpVO.setLocp_status(rs.getInt("LOCP_STATUS"));
				locpVO.setLoc_name(rs.getString("MEM_SHOP_NAME"));
				locpVO.setLoc_logo(rs.getBytes("MEM_SHOP_LOGO"));;
				list.add(locpVO);
			}
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. "
					+ e.getMessage());
		} catch (SQLException se) {
			se.printStackTrace();
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
		
		return list;
		
	}

	@Override
	public List<LocrVO> getAllLocrBySmemid(Integer locr_smem_id) {
		
		String sql = "SELECT * FROM LOCATION_ROOM WHERE LOCR_SMEM_ID = ?";
		List<LocrVO> list = new ArrayList<LocrVO>();
		LocrVO locrVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, locr_smem_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				locrVO = new LocrVO();
				locrVO.setLOCR_ID(rs.getInt("LOCR_ID"));
				locrVO.setLOCR_SMEM_ID(rs.getInt("LOCR_SMEM_ID"));
				locrVO.setLOCR_NAME(rs.getString("LOCR_NAME"));
				locrVO.setLOCR_MAX_TABLE(rs.getInt("LOCR_MAX_TABLE"));
				locrVO.setLOCR_MIN_TABLE(rs.getInt("LOCR_MIN_TABLE"));
				locrVO.setLOCR_MAIN_TABLE(rs.getInt("LOCR_MAIN_TABLE"));
				locrVO.setLOCR_GUEST_TABLE(rs.getInt("LOCR_GUEST_TABLE"));
				locrVO.setLOCR_FLOOR(rs.getInt("LOCR_FLOOR"));
				locrVO.setLOCR_CONTENT(rs.getString("LOCR_CONTENT"));
				locrVO.setLOCR_STATUS(rs.getInt("LOCR_STATUS"));
				list.add(locrVO);
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
		
		return list;
	}

	
	
}
