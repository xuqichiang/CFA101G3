package com.locationorder.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.locationorder.model.LocoVO;

public class LocoDAO implements LocoDAO_interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();	
			ds=(DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G3");
			}catch(NamingException e){
				e.printStackTrace();
			}
	}
	
	private static final String INSERT_LOCO =
			"INSERT INTO LOCATION_ORDER (LOCO_SMEM_ID,LOCO_BMEM_ID,LOCO_LOCR_ID,"
			+ "LOCO_LOCP_ID,LOCO_TOTALPRICE,LOCO_DEPOSIT,LOCO_PAYTYPE,LOCO_ORDER_STATUS,"
			+ "LOCO_PAY_STATUS,LOCO_ORDER_TIME,LOCO_RESERVE_TIME,LOCO_TABLE_NUM,LOCO_NOTE) "
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String GET_ALL_LOCO =
			"SELECT LOCO_ID,LOCO_SMEM_ID,LOCO_BMEM_ID,LOCO_LOCR_ID," + 
			"LOCO_LOCP_ID,LOCO_TOTALPRICE,LOCO_DEPOSIT,LOCO_PAYTYPE,LOCO_ORDER_STATUS," + 
			"LOCO_PAY_STATUS,LOCO_ORDER_TIME,LOCO_RESERVE_TIME,LOCO_TABLE_NUM,LOCO_NOTE  from LOCATION_ORDER order by LOCO_ID";
	private static final String GET_ONE_LOCO =
			"SELECT LOCO_ID,LOCO_SMEM_ID,LOCO_BMEM_ID,LOCO_LOCR_ID," + 
			"LOCO_LOCP_ID,LOCO_TOTALPRICE,LOCO_DEPOSIT,LOCO_PAYTYPE,LOCO_ORDER_STATUS," + 
			"LOCO_PAY_STATUS,LOCO_ORDER_TIME,LOCO_RESERVE_TIME,LOCO_TABLE_NUM,LOCO_NOTE from LOCATION_ORDER where  LOCO_ID=?";
	private static final String DELETE =
			"DELETE FROM LOCATION_ORDER where LOCO_ID=?";
	private static final String UPDATE=
			"UPDATE LOCATION_ORDER set 	LOCO_SMEM_ID=?,LOCO_BMEM_ID=?,LOCO_LOCR_ID=?," + 
			"LOCO_LOCP_ID=?,LOCO_TOTALPRICE=?,LOCO_DEPOSIT=?,LOCO_PAYTYPE=?,LOCO_ORDER_STATUS=?," + 
			"LOCO_PAY_STATUS=?,LOCO_ORDER_TIME=?,LOCO_RESERVE_TIME=?,LOCO_TABLE_NUM=?,LOCO_NOTE=? where LOCO_ID= ?";
	private static final String UPDATEorderSTATUS=
			"UPDATE LOCATION_ORDER set 	LOCO_ORDER_STATUS=? where LOCO_ID= ?";
	
	private static final String UPDATEpaySTATUS=
			"UPDATE LOCATION_ORDER set 	LOCO_PAY_STATUS=? where LOCO_ID= ?";
	
	
	//找賣家清單
	private static final String GET_SMEM_LOCO =
			"SELECT LOCO_ID,LOCO_SMEM_ID,LOCO_BMEM_ID,LOCO_LOCR_ID," + 
			"LOCO_LOCP_ID,LOCO_TOTALPRICE,LOCO_DEPOSIT,LOCO_PAYTYPE,LOCO_ORDER_STATUS," + 
			"LOCO_PAY_STATUS,LOCO_ORDER_TIME,LOCO_RESERVE_TIME,LOCO_TABLE_NUM,LOCO_NOTE from LOCATION_ORDER where  LOCO_SMEM_ID=?";
	//找賣商品家時間清單
		private static final String GET_TIME_LOCO_LCOR =
				"SELECT LOCO_RESERVE_TIME FROM LOCATION_ORDER where LOCO_LOCR_ID=? and LOCO_ORDER_STATUS!=4 and LOCO_ORDER_STATUS!=2";
		
	
	
	//找買家清單
	private static final String GET_BMEM_LOCO =
			"SELECT LOCO_ID,LOCO_SMEM_ID,LOCO_BMEM_ID,LOCO_LOCR_ID," + 
			"LOCO_LOCP_ID,LOCO_TOTALPRICE,LOCO_DEPOSIT,LOCO_PAYTYPE,LOCO_ORDER_STATUS," + 
			"LOCO_PAY_STATUS,LOCO_ORDER_TIME,LOCO_RESERVE_TIME,LOCO_TABLE_NUM,LOCO_NOTE from LOCATION_ORDER where  LOCO_BMEM_ID=?";
	
	
	
	@Override
	public void insert(LocoVO LocoVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT_LOCO);
			System.out.println("40");
			pstmt.setInt(1, LocoVO.getLOCO_SMEM_ID());
			pstmt.setInt(2, LocoVO.getLOCO_BMEM_ID());
			pstmt.setInt(3, LocoVO.getLOCO_LOCR_ID());
			pstmt.setInt(4, LocoVO.getLOCO_LOCP_ID());
			pstmt.setInt(5, LocoVO.getLOCO_TOTALPRICE());
			pstmt.setInt(6, LocoVO.getLOCO_DEPOSIT());
			pstmt.setInt(7, LocoVO.getLOCO_PAYTYPE());
			pstmt.setInt(8, LocoVO.getLOCO_ORDER_STATUS());
			pstmt.setInt(9, LocoVO.getLOCO_PAY_STATUS());
			pstmt.setDate(10, LocoVO.getLOCO_ORDER_TIME());
			pstmt.setDate(11, LocoVO.getLOCO_RESERVE_TIME());
			pstmt.setInt(12, LocoVO.getLOCO_TABLE_NUM());
			pstmt.setString(13, LocoVO.getLOCO_NOTE());
		
			
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
	public void update(LocoVO LocoVO) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			

			pstmt.setInt(1, LocoVO.getLOCO_SMEM_ID());
			pstmt.setInt(2, LocoVO.getLOCO_BMEM_ID());
			pstmt.setInt(3, LocoVO.getLOCO_LOCR_ID());
			pstmt.setInt(4, LocoVO.getLOCO_LOCP_ID());
			pstmt.setInt(5, LocoVO.getLOCO_TOTALPRICE());
			pstmt.setInt(6, LocoVO.getLOCO_DEPOSIT());
			pstmt.setInt(7, LocoVO.getLOCO_PAYTYPE());
			pstmt.setInt(8, LocoVO.getLOCO_ORDER_STATUS());
			pstmt.setInt(9, LocoVO.getLOCO_PAY_STATUS());
			pstmt.setDate(10, LocoVO.getLOCO_ORDER_TIME());
			pstmt.setDate(11, LocoVO.getLOCO_RESERVE_TIME());
			pstmt.setInt(12, LocoVO.getLOCO_TABLE_NUM());
			pstmt.setString(13, LocoVO.getLOCO_NOTE());
			pstmt.setInt(14, LocoVO.getLOCO_ID());
			
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
	public void delete(Integer LOCO_ID) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, LOCO_ID);

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
	public LocoVO findByPrimaryKey(Integer LOCO_ID) {
		// TODO Auto-generated method stub
		
		LocoVO LocoVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_LOCO);
			pstmt.setInt(1, LOCO_ID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				LocoVO =new LocoVO();
				LocoVO.setLOCO_ID(rs.getInt("LOCO_ID"));
				LocoVO.setLOCO_SMEM_ID(rs.getInt("LOCO_SMEM_ID"));
				LocoVO.setLOCO_BMEM_ID(rs.getInt("LOCO_BMEM_ID"));
				LocoVO.setLOCO_LOCR_ID(rs.getInt("LOCO_LOCR_ID"));
				LocoVO.setLOCO_LOCP_ID(rs.getInt("LOCO_LOCP_ID"));
				LocoVO.setLOCO_TOTALPRICE(rs.getInt("LOCO_TOTALPRICE"));
				LocoVO.setLOCO_DEPOSIT(rs.getInt("LOCO_DEPOSIT"));
				LocoVO.setLOCO_PAYTYPE(rs.getInt("LOCO_PAYTYPE"));
				LocoVO.setLOCO_ORDER_STATUS(rs.getInt("LOCO_ORDER_STATUS"));
				LocoVO.setLOCO_PAY_STATUS(rs.getInt("LOCO_PAY_STATUS"));
				
				LocoVO.setLOCO_ORDER_TIME(rs.getDate("LOCO_ORDER_TIME"));
				LocoVO.setLOCO_RESERVE_TIME(rs.getDate("LOCO_RESERVE_TIME"));
				LocoVO.setLOCO_TABLE_NUM(rs.getInt("LOCO_TABLE_NUM"));
				LocoVO.setLOCO_NOTE(rs.getString("LOCO_NOTE"));
				
								
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
		return LocoVO;
	}

	@Override
	public List<LocoVO> getAll() {
		// TODO Auto-generated method stub
		List<LocoVO> list = new ArrayList<LocoVO>();
		LocoVO LocoVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_LOCO);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				LocoVO =new LocoVO();
				LocoVO.setLOCO_ID(rs.getInt("LOCO_ID"));
				LocoVO.setLOCO_SMEM_ID(rs.getInt("LOCO_SMEM_ID"));
				LocoVO.setLOCO_BMEM_ID(rs.getInt("LOCO_BMEM_ID"));
				LocoVO.setLOCO_LOCR_ID(rs.getInt("LOCO_LOCR_ID"));
				LocoVO.setLOCO_LOCP_ID(rs.getInt("LOCO_LOCP_ID"));
				LocoVO.setLOCO_TOTALPRICE(rs.getInt("LOCO_TOTALPRICE"));
				LocoVO.setLOCO_DEPOSIT(rs.getInt("LOCO_DEPOSIT"));
				LocoVO.setLOCO_PAYTYPE(rs.getInt("LOCO_PAYTYPE"));
				LocoVO.setLOCO_ORDER_STATUS(rs.getInt("LOCO_ORDER_STATUS"));
				LocoVO.setLOCO_PAY_STATUS(rs.getInt("LOCO_PAY_STATUS"));
				
				LocoVO.setLOCO_ORDER_TIME(rs.getDate("LOCO_ORDER_TIME"));
				LocoVO.setLOCO_RESERVE_TIME(rs.getDate("LOCO_RESERVE_TIME"));
				LocoVO.setLOCO_TABLE_NUM(rs.getInt("LOCO_TABLE_NUM"));
				LocoVO.setLOCO_NOTE(rs.getString("LOCO_NOTE"));
				
								
				list.add(LocoVO);
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
	public List<LocoVO> findSmemOrder(Integer LOCO_SMEM_ID) {
		List<LocoVO> list = new ArrayList<LocoVO>();
		LocoVO LocoVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_SMEM_LOCO);
			pstmt.setInt(1, LOCO_SMEM_ID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				LocoVO =new LocoVO();
				LocoVO.setLOCO_ID(rs.getInt("LOCO_ID"));
				LocoVO.setLOCO_SMEM_ID(rs.getInt("LOCO_SMEM_ID"));
				LocoVO.setLOCO_BMEM_ID(rs.getInt("LOCO_BMEM_ID"));
				LocoVO.setLOCO_LOCR_ID(rs.getInt("LOCO_LOCR_ID"));
				LocoVO.setLOCO_LOCP_ID(rs.getInt("LOCO_LOCP_ID"));
				LocoVO.setLOCO_TOTALPRICE(rs.getInt("LOCO_TOTALPRICE"));
				LocoVO.setLOCO_DEPOSIT(rs.getInt("LOCO_DEPOSIT"));
				LocoVO.setLOCO_PAYTYPE(rs.getInt("LOCO_PAYTYPE"));
				LocoVO.setLOCO_ORDER_STATUS(rs.getInt("LOCO_ORDER_STATUS"));
				LocoVO.setLOCO_PAY_STATUS(rs.getInt("LOCO_PAY_STATUS"));
				
				LocoVO.setLOCO_ORDER_TIME(rs.getDate("LOCO_ORDER_TIME"));
				LocoVO.setLOCO_RESERVE_TIME(rs.getDate("LOCO_RESERVE_TIME"));
				LocoVO.setLOCO_TABLE_NUM(rs.getInt("LOCO_TABLE_NUM"));
				LocoVO.setLOCO_NOTE(rs.getString("LOCO_NOTE"));
				
								
				list.add(LocoVO);
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
	public List<LocoVO> findBmemOrder(Integer LOCO_BMEM_ID) {
		List<LocoVO> list = new ArrayList<LocoVO>();
		LocoVO LocoVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BMEM_LOCO);
			pstmt.setInt(1, LOCO_BMEM_ID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				LocoVO =new LocoVO();
				LocoVO.setLOCO_ID(rs.getInt("LOCO_ID"));
				LocoVO.setLOCO_SMEM_ID(rs.getInt("LOCO_SMEM_ID"));
				LocoVO.setLOCO_BMEM_ID(rs.getInt("LOCO_BMEM_ID"));
				LocoVO.setLOCO_LOCR_ID(rs.getInt("LOCO_LOCR_ID"));
				LocoVO.setLOCO_LOCP_ID(rs.getInt("LOCO_LOCP_ID"));
				LocoVO.setLOCO_TOTALPRICE(rs.getInt("LOCO_TOTALPRICE"));
				LocoVO.setLOCO_DEPOSIT(rs.getInt("LOCO_DEPOSIT"));
				LocoVO.setLOCO_PAYTYPE(rs.getInt("LOCO_PAYTYPE"));
				LocoVO.setLOCO_ORDER_STATUS(rs.getInt("LOCO_ORDER_STATUS"));
				LocoVO.setLOCO_PAY_STATUS(rs.getInt("LOCO_PAY_STATUS"));
				
				LocoVO.setLOCO_ORDER_TIME(rs.getDate("LOCO_ORDER_TIME"));
				LocoVO.setLOCO_RESERVE_TIME(rs.getDate("LOCO_RESERVE_TIME"));
				LocoVO.setLOCO_TABLE_NUM(rs.getInt("LOCO_TABLE_NUM"));
				LocoVO.setLOCO_NOTE(rs.getString("LOCO_NOTE"));
				
								
				list.add(LocoVO);
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
	public List<LocoVO> getTime(Integer LOCO_LOCR_ID) {
		List<LocoVO> list = new ArrayList<LocoVO>();
		LocoVO LocoVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_TIME_LOCO_LCOR);
			pstmt.setInt(1, LOCO_LOCR_ID);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				// empVo 也稱為 Domain objects
				LocoVO =new LocoVO();
				LocoVO.setLOCO_RESERVE_TIME(rs.getDate("LOCO_RESERVE_TIME"));
									
								
				list.add(LocoVO);
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
	public void updateorder(Integer LOCO_ORDER_STATUS,Integer LOCO_ID) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATEorderSTATUS);
			

			
			pstmt.setInt(1, LOCO_ORDER_STATUS);
			pstmt.setInt(2, LOCO_ID);
			
	
			
			
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
	public void updatepay(Integer LOCO_PAY_STATUS,Integer LOCO_ID ) {
		// TODO Auto-generated method stub
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATEpaySTATUS);
			

			
			pstmt.setInt(1, LOCO_PAY_STATUS);
			pstmt.setInt(2, LOCO_ID);
			
	
			
			
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
		
		
	

}
