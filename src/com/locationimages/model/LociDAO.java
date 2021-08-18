package com.locationimages.model;

import java.sql.*;
import java.util.*;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;





public class LociDAO  implements LociDAO_interface{

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();	
			ds=(DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G3");
			}catch(NamingException e){
				e.printStackTrace();
			}
	}
	
	private static final String INSERT_LOCI =
			"INSERT INTO LOCATION_IMAGES (LOCI_LOCR_ID,LOCI_IMAGES) VALUES (?,?)";
	private static final String GET_ALL_LOCI =
			"SELECT LOCI_ID,LOCI_LOCR_ID,LOCI_IMAGES FROM LOCATION_IMAGES order by LOCI_ID";
	private static final String GET_ONE_LOCI =
			"SELECT LOCI_ID,LOCI_LOCR_ID,LOCI_IMAGES FROM LOCATION_IMAGES where  LOCI_ID=?";
	private static final String DELETE =
			"DELETE FROM LOCATION_IMAGES where LOCI_LOCR_ID=?";
	private static final String DELETEBYID =
			"DELETE FROM LOCATION_IMAGES where LOCI_ID=?";
	private static final String UPDATE=
			"UPDATE LOCATION_IMAGES set LOCI_LOCR_ID=?, LOCI_IMAGES=? where LOCI_ID= ?";
	private static final String GET_REFERENCE_LOCI = "SELECT * from LOCATION_IMAGES where LOCI_LOCR_ID=?";
	
	@Override
	public void insert(LociVO LociVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_LOCI);
			
			System.out.println(11);
			pstmt.setInt(1, LociVO.getLOCI_LOCR_ID());
			pstmt.setBytes(2, LociVO.getLOCI_IMAGES());
			
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
	public void update(LociVO LociVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			

			pstmt.setInt(1, LociVO.getLOCI_LOCR_ID());
			pstmt.setBytes(2, LociVO.getLOCI_IMAGES());
			pstmt.setInt(3, LociVO.getLOCI_ID());
			
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
	public void delete(Integer LOCI_LOCR_ID) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, LOCI_LOCR_ID);

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
	public void deleteById(Integer LOCI_ID) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETEBYID);

			pstmt.setInt(1, LOCI_ID);

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
	public LociVO findByPrimaryKey(Integer LOCI_ID) {
		// TODO Auto-generated method stub
		LociVO LociVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_LOCI);

			pstmt.setInt(1, LOCI_ID);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				LociVO =new LociVO();
				LociVO.setLOCI_ID(rs.getInt("LOCI_ID"));
				LociVO.setLOCI_LOCR_ID(rs.getInt("LOCI_LOCR_ID"));
				LociVO.setLOCI_IMAGES(rs.getBytes("lOCI_IMAGES"));
				
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
		return LociVO;
	}

	@Override
	public List<LociVO> getAll() {
		// TODO Auto-generated method stub
		List<LociVO> list = new ArrayList<LociVO>();
		LociVO LociVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_LOCI);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				LociVO =new LociVO();
				LociVO.setLOCI_ID(rs.getInt("LOCI_ID"));
				LociVO.setLOCI_LOCR_ID(rs.getInt("LOCI_LOCR_ID"));
//				LociVO.setLOCI_IMAGES(rs.getBytes("lOCI_IMAGES"));
				list.add(LociVO);
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
	public List<LociVO> findByForeignKey(Integer LOCI_LOCR_ID) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		LociVO LociVO = null;
		List<LociVO> list = new ArrayList<LociVO>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_REFERENCE_LOCI);
			pstmt.setInt(1, LOCI_LOCR_ID);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				LociVO = new LociVO();
				LociVO.setLOCI_ID(rs.getInt("LOCI_ID"));
				LociVO.setLOCI_LOCR_ID(rs.getInt("LOCI_LOCR_ID"));
				list.add(LociVO);
//				System.out.println(list);
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
