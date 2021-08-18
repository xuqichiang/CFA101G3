package com.locationprogramimages.model;

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

public class LocpiDAOImpl implements LocpiDAO {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void insertLocpiImages(LocpiVO locpiVO) {
		
		String sql = "INSERT INTO LOCATION_PROGRAM_IMAGES (LOCPI_LOCP_ID, LOCPI_IMAGES) VALUES(?, ?)";
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			
			pstmt.setInt(1, locpiVO.getLocpi_locp_id());
			pstmt.setBytes(2, locpiVO.getLocpi_images());
			pstmt.executeUpdate();
			
		}  catch (SQLException se) {
			throw new RuntimeException("A database error occured. "
					+ se.getMessage());
		}finally {
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
	public void updateLocpiImages(LocpiVO locpiVO) {
		
		String sql = "UPDATE LOCATION_PROGRAM_IMAGES SET LOCPI_IMAGES = ? WHERE LOCPI_LOCP_ID = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			
			pstmt.setBytes(1, locpiVO.getLocpi_images());
			pstmt.setInt(2, locpiVO.getLocpi_locp_id());
			pstmt.executeUpdate();
			
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
	public void deleteLocpiImages(Integer locpi_locp_id) {
		
		String sql = "DELETE FROM LOCATION_PROGRAM_IMAGES WHERE LOCPI_LOCP_ID = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, locpi_locp_id);
			pstmt.executeUpdate();
			
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
	public LocpiVO findByPrimaryKey(Integer locpi_id) {
		
		String sql = "SELECT * FROM LOCATION_PROGRAM_IMAGES WHERE LOCPI_ID = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LocpiVO locpiVO = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, locpi_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				locpiVO = new LocpiVO();
				locpiVO.setLocpi_id(rs.getInt("locpi_id"));
				locpiVO.setLocpi_locp_id(rs.getInt("locpi_locp_id"));
				locpiVO.setLocpi_images(rs.getBytes("locpi_images"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
		return locpiVO;
	}
	
	public LocpiVO findByForeignKey(Integer locpi_locp_id) {
		
		String sql = "SELECT * FROM LOCATION_PROGRAM_IMAGES WHERE LOCPI_LOCP_ID = ?";
		LocpiVO locpiVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, locpi_locp_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				locpiVO = new LocpiVO();
				locpiVO.setLocpi_id(rs.getInt("LOCPI_ID"));
				locpiVO.setLocpi_locp_id(rs.getInt("LOCPI_LOCP_ID"));
				locpiVO.setLocpi_images(rs.getBytes("LOCPI_IMAGES"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
		return locpiVO;
	}
	
	@Override
	public List<LocpiVO> findForeignKey(Integer locpi_locp_id) {
		
		String sql = "SELECT * FROM LOCATION_PROGRAM_IMAGES WHERE LOCPI_LOCP_ID = ?";
		List<LocpiVO> list = new ArrayList<LocpiVO>();
		LocpiVO locpiVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, locpi_locp_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				locpiVO = new LocpiVO();
				locpiVO.setLocpi_id(rs.getInt("LOCPI_ID"));
				locpiVO.setLocpi_locp_id(rs.getInt("LOCPI_LOCP_ID"));
				list.add(locpiVO);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
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
		return list;
	}

	public List<LocpiVO> getAllimages(){
		
		String sql = "SELECT * FROM LOCATION_PROGRAM_IMAGES";
		List<LocpiVO> list = new ArrayList<LocpiVO>();
		LocpiVO locpiVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				locpiVO = new LocpiVO();
				locpiVO.setLocpi_id(rs.getInt("LOCPI_ID"));
				locpiVO.setLocpi_locp_id(rs.getInt("LOCPI_LOCP_ID"));
				locpiVO.setLocpi_images(rs.getBytes("LOCPI_IMAGES"));
				list.add(locpiVO);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
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
		return list;
	}

}
