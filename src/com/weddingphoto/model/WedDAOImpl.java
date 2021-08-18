package com.weddingphoto.model;


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

import com.workphoto.model.WorVO;

public class WedDAOImpl implements WedDAO{
	
	//新增
	private static final String INSERT = "insert into WEDDING_PHOTO (WED_WOR_ID, WED_IMAGES) values (?, ?)";
	//查詢一張圖片
	private static final String GET_ONE = "select * from WEDDING_PHOTO where WED_ID = ?";
	//查詢攝影師的作品圖片
	private static final String GET_IMAGES = "select * from WEDDING_PHOTO where WED_WOR_ID = ?";
	//刪除作品集圖片
	private static final String DELETE_IMG = "delete from WEDDING_PHOTO where WED_ID = ?";
	
	//連線池
	private static DataSource ds = null;
	
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource)ctx.lookup("java:comp/env/jdbc/CFA101G3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	//新增
	@Override
	public void insert(WedVO wedVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT);//insert into WEDDING_PHOTO (WED_WOR_ID, WED_IMAGES) values (?, ?)
			
			pstmt.setInt(1, wedVO.getWed_wor_id());//作品集的ID
			pstmt.setBytes(2, wedVO.getWed_images());//圖片
			pstmt.executeUpdate();
//			System.out.println("新增"+executeUpdate);//確認是否有做新增
			
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured." + e.getMessage());//SQL語法錯誤
		} finally {
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
	}

	@Override
	public void update(WedVO wedVO) {
		// TODO Auto-generated method stub
	}
	//刪除圖片
	@Override
	public void delete(Integer wed_id) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE_IMG);//delete from WEDDING_PHOTO where WED_ID = ?
			pstmt.setInt(1, wed_id);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured." + e.getMessage());//SQL語法錯誤
		} finally {
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
	}
	//查詢作品圖片
	@Override
	public WedVO findByPrimaryKey(Integer wed_id) {
		
		WedVO wedVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE);//select * from WEDDING_PHOTO where WED_ID = ?
			pstmt.setInt(1, wed_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				wedVO = new WedVO();
				wedVO.setWed_id(rs.getInt("wed_id"));//圖片ID
				wedVO.setWed_wor_id(rs.getInt("wed_wor_id"));//作品集的ID
				wedVO.setWed_images(rs.getBytes("wed_images"));//圖片
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
		return wedVO;
	}
	//取得作品集名稱
	@Override
	public List<WedVO> findByForeignKey(Integer wed_wor_id) {
		
		List<WedVO> list = new ArrayList<WedVO>();
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		WedVO wedVO = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_IMAGES);//select * from WEDDING_PHOTO where WED_WOR_ID = ?
			pstmt.setInt(1, wed_wor_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				wedVO = new WedVO();
				wedVO.setWed_id(rs.getInt("wed_id"));//圖片ID
				wedVO.setWed_wor_id(rs.getInt("wed_wor_id"));//作品集的ID
				list.add(wedVO);
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
	//同時新增作品集與作品集圖片(交易)
	@Override
	public void insert2 (WedVO wedVO, Connection con) {
		PreparedStatement pstmt = null;
		try {
			System.out.println(con);
			pstmt = con.prepareStatement(INSERT);
			
			pstmt.setInt(1, wedVO.getWed_wor_id());//作品集的ID
			pstmt.setBytes(2, wedVO.getWed_images());//圖片
			
			pstmt.executeUpdate();
			System.out.println("新增成功");
			
		} catch (SQLException e) {
			if(con!=null) {
				try {
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured." + excep.getMessage());
				}
			} 
			throw new RuntimeException("A database error occured." + e.getMessage());//SQL語法錯誤
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
}
