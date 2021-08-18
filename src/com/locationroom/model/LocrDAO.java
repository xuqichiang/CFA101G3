package com.locationroom.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.member.model.*;


public class LocrDAO implements LocrDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();	
			ds=(DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G3");
			}catch(NamingException e){
				e.printStackTrace();
			}
	}
	
	private static final String INSERT_LOCR ="INSERT INTO LOCATION_ROOM (	LOCR_SMEM_ID,LOCR_NAME,LOCR_MAX_TABLE,LOCR_MIN_TABLE,\r\n" + 
			"	LOCR_MAIN_TABLE,LOCR_GUEST_TABLE,\r\n" + 
			"	LOCR_FLOOR,LOCR_CONTENT,LOCR_STATUS) VALUES (?,?,?,?,?,?,?,?,?)";
	private static final String GET_ALL_LOCR ="SELECT 	LOCR_ID,LOCR_SMEM_ID,LOCR_NAME,LOCR_MAX_TABLE,LOCR_MIN_TABLE,\r\n" + 
			"	LOCR_MAIN_TABLE,LOCR_GUEST_TABLE,\r\n" + 
			"	LOCR_FLOOR,LOCR_CONTENT,LOCR_STATUS  from LOCATION_ROOM order by LOCR_ID";
	private static final String GET_ONE_LOCR ="SELECT 	LOCR_ID,LOCR_SMEM_ID,LOCR_NAME,LOCR_MAX_TABLE,LOCR_MIN_TABLE,\r\n" + 
			"	LOCR_MAIN_TABLE,LOCR_GUEST_TABLE,\r\n" + 
			"	LOCR_FLOOR,LOCR_CONTENT,LOCR_STATUS   from LOCATION_ROOM where  LOCR_ID=?";
	private static final String DELETE ="DELETE FROM LOCATION_ROOM where LOCR_ID=?";
	private static final String UPDATE="UPDATE LOCATION_ROOM set 	LOCR_SMEM_ID=?,LOCR_NAME=?,LOCR_MAX_TABLE=?,LOCR_MIN_TABLE=?,\r\n" + 
			"	LOCR_MAIN_TABLE=?,LOCR_GUEST_TABLE=?,\r\n" + 
			"	LOCR_FLOOR=?,LOCR_CONTENT=?,LOCR_STATUS=? where LOCR_ID= ?";
	private static final String GET_SMEM_LOCR ="SELECT 	LOCR_ID,LOCR_SMEM_ID,LOCR_NAME,LOCR_MAX_TABLE,LOCR_MIN_TABLE,\r\n" + 
			"	LOCR_MAIN_TABLE,LOCR_GUEST_TABLE,\r\n" + 
			"	LOCR_FLOOR,LOCR_CONTENT,LOCR_STATUS   from LOCATION_ROOM where  LOCR_SMEM_ID=?";
	
	
	
	@Override
	public void insert(LocrVO LocrVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_LOCR);
			
			pstmt.setInt(1, LocrVO.getLOCR_SMEM_ID());
			pstmt.setString(2, LocrVO.getLOCR_NAME());
			pstmt.setInt(3, LocrVO.getLOCR_MAX_TABLE());
			pstmt.setInt(4, LocrVO.getLOCR_MIN_TABLE());
			pstmt.setInt(5, LocrVO.getLOCR_MAIN_TABLE());
			pstmt.setInt(6, LocrVO.getLOCR_GUEST_TABLE());
			pstmt.setInt(7, LocrVO.getLOCR_FLOOR());
			pstmt.setString(8, LocrVO.getLOCR_CONTENT());
			pstmt.setInt(9, LocrVO.getLOCR_STATUS());
			
		
			
			
			pstmt.executeUpdate();
			
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	public void update(LocrVO LocrVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
						
			pstmt.setInt(1, LocrVO.getLOCR_SMEM_ID());
			pstmt.setString(2, LocrVO.getLOCR_NAME());
			pstmt.setInt(3, LocrVO.getLOCR_MAX_TABLE());
			pstmt.setInt(4, LocrVO.getLOCR_MIN_TABLE());
			pstmt.setInt(5, LocrVO.getLOCR_MAIN_TABLE());
			pstmt.setInt(6, LocrVO.getLOCR_GUEST_TABLE());
			pstmt.setInt(7, LocrVO.getLOCR_FLOOR());
			pstmt.setString(8, LocrVO.getLOCR_CONTENT());
			pstmt.setInt(9, LocrVO.getLOCR_STATUS());
			pstmt.setInt(10, LocrVO.getLOCR_ID());
		
			
			
			pstmt.executeUpdate();
			
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	public void delete(Integer LOCR_ID) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, LOCR_ID);

			pstmt.executeUpdate();

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	public LocrVO findByPrimaryKey(Integer LOCR_ID) {
		// TODO Auto-generated method stub
		
		LocrVO LocrVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_LOCR);
			pstmt.setInt(1,LOCR_ID);
			rs = pstmt.executeQuery();
			

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				LocrVO =new LocrVO();
				LocrVO.setLOCR_ID(rs.getInt("LOCR_ID"));
				LocrVO.setLOCR_SMEM_ID(rs.getInt("LOCR_SMEM_ID"));
				LocrVO.setLOCR_NAME(rs.getString("LOCR_NAME"));
				LocrVO.setLOCR_MAX_TABLE(rs.getInt("LOCR_MAX_TABLE"));
				LocrVO.setLOCR_MIN_TABLE(rs.getInt("LOCR_MIN_TABLE"));
				LocrVO.setLOCR_MAIN_TABLE(rs.getInt("LOCR_MAIN_TABLE"));
				LocrVO.setLOCR_GUEST_TABLE(rs.getInt("LOCR_GUEST_TABLE"));
				LocrVO.setLOCR_FLOOR(rs.getInt("LOCR_FLOOR"));
				LocrVO.setLOCR_CONTENT(rs.getString("LOCR_CONTENT"));
				LocrVO.setLOCR_STATUS(rs.getInt("LOCR_STATUS"));
			
				
					
			}

			// Handle any driver errors
		} catch (SQLException se) {
			se.printStackTrace();
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
		return LocrVO;
	}
	//sql搜尋廳房名
	public List<LocrVO> findbysqlList(String locr_name) {
		
		java.lang.String sql = "select * from LOCATION_ROOM where 1=1 ";
		StringBuilder sb = new StringBuilder();
		sb.append(sql);
		if(locr_name != null) {
			sb.append(" and LOCR_NAME like '%"+locr_name+"%'");
		}
//		sb.append(" limit ?,?");
		sql = sb.toString();
		
		
		
		List<LocrVO> list = new ArrayList<LocrVO>();
		LocrVO LocrVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				LocrVO =new LocrVO();
				LocrVO.setLOCR_ID(rs.getInt("LOCR_ID"));
				LocrVO.setLOCR_SMEM_ID(rs.getInt("LOCR_SMEM_ID"));
				LocrVO.setLOCR_NAME(rs.getString("LOCR_NAME"));
				LocrVO.setLOCR_MAX_TABLE(rs.getInt("LOCR_MAX_TABLE"));
				LocrVO.setLOCR_MIN_TABLE(rs.getInt("LOCR_MIN_TABLE"));
				LocrVO.setLOCR_MAIN_TABLE(rs.getInt("LOCR_MAIN_TABLE"));
				LocrVO.setLOCR_GUEST_TABLE(rs.getInt("LOCR_GUEST_TABLE"));
				LocrVO.setLOCR_FLOOR(rs.getInt("LOCR_FLOOR"));
				LocrVO.setLOCR_CONTENT(rs.getString("LOCR_CONTENT"));
				LocrVO.setLOCR_STATUS(rs.getInt("LOCR_STATUS"));
			
				list.add(LocrVO);
					
			}

			// Handle any driver errors
		} catch (SQLException se) {
			se.printStackTrace();
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	public List<LocrVO> getAll() {
		// TODO Auto-generated method stub
		List<LocrVO> list = new ArrayList<LocrVO>();
		LocrVO LocrVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_LOCR);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				LocrVO =new LocrVO();
				LocrVO.setLOCR_ID(rs.getInt("LOCR_ID"));
				LocrVO.setLOCR_SMEM_ID(rs.getInt("LOCR_SMEM_ID"));
				LocrVO.setLOCR_NAME(rs.getString("LOCR_NAME"));
				LocrVO.setLOCR_MAX_TABLE(rs.getInt("LOCR_MAX_TABLE"));
				LocrVO.setLOCR_MIN_TABLE(rs.getInt("LOCR_MIN_TABLE"));
				LocrVO.setLOCR_MAIN_TABLE(rs.getInt("LOCR_MAIN_TABLE"));
				LocrVO.setLOCR_GUEST_TABLE(rs.getInt("LOCR_GUEST_TABLE"));
				LocrVO.setLOCR_FLOOR(rs.getInt("LOCR_FLOOR"));
				LocrVO.setLOCR_CONTENT(rs.getString("LOCR_CONTENT"));
				LocrVO.setLOCR_STATUS(rs.getInt("LOCR_STATUS"));
			
				
					
				list.add(LocrVO);
			}

			// Handle any driver errors
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
	public LocrVO getLastID() {
		
		String sql = "SELECT * FROM LOCATION_ROOM ORDER BY LOCR_ID DESC LIMIT 0 , 1;";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LocrVO locrVO = null;
		
		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				locrVO = new LocrVO();
				locrVO.setLOCR_ID(rs.getInt("LOCR_ID"));
			}
			
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
		return locrVO;
	}
	
	public MemVO getSmem(Integer MEM_ID) {
		
		String sql = "SELECT * FROM `MEMBER`  where MEM_ID=? ORDER BY MEM_ID";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		MemVO MemVO = null;
		
		try {
			con = ds.getConnection();

			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,MEM_ID);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
					MemVO = new MemVO();
				MemVO.setMem_name(rs.getString("Mem_NAME"));
				MemVO.setMem_name(rs.getString("mem_name"));
				 MemVO.setMem_role(rs.getInt("mem_role"));
				 MemVO.setMem_phone(rs.getString("mem_phone"));
				 MemVO.setMem_city(rs.getString("mem_city"));
				 MemVO.setMem_cityarea(rs.getString("mem_cityarea"));
				 MemVO.setMem_street(rs.getString("mem_street"));
				 MemVO.setMem_status(rs.getInt("mem_status"));
				 MemVO.setMem_shop_name(rs.getString("mem_shop_name"));
				 MemVO.setMem_shop_content(rs.getString("mem_shop_content"));
				 MemVO.setMem_shop_logo(rs.getBytes("mem_shop_logo"));
				 MemVO.setMem_shop_banner(rs.getBytes("mem_shop_banner"));
				 MemVO.setMem_shop_status(rs.getInt("mem_shop_status"));
				 MemVO.setMem_headshot(rs.getBytes("mem_headshot"));
			}
			
		}catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
			// Clean up JDBC resources
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
		return MemVO;
	}

	@Override
	public MemVO getBmem(Integer MEM_ID) {
		// TODO Auto-generated method stub
		return null;
	}
}
