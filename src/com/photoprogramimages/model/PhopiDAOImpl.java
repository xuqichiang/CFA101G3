package com.photoprogramimages.model;

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

public class PhopiDAOImpl implements PhopiDAO {

	// 新增圖片
	private static final String INSERT = "insert into PHOTO_PROGRAM_IMAGES (PHOPI_PHOP_ID,PHOPI_IMAGES) values (?,?)";
	private static final String FIND_BY_ALL = "select * from PHOTO_PROGRAM_IMAGES";
	private static final String FIND_BY_ONE = "select * from PHOTO_PROGRAM_IMAGES where phopi_id=? ";
	private static final String DELETE ="delete  from PHOTO_PROGRAM_IMAGES where phopi_id =?";

	// 連線池
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	//查所有方案照片
	public List<PhopiVO> getAll() {
		List<PhopiVO> list = new ArrayList<PhopiVO>();
		PhopiVO phopiVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_ALL);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				phopiVO = new PhopiVO();
				phopiVO.setPhopi_id(rs.getInt("phopi_id"));
				phopiVO.setPhopi_phop_id(rs.getInt("phopi_phop_id"));
				phopiVO.setPhopi_images(rs.getBytes("phopi_images"));
				list.add(phopiVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	//查詢方案照片FK
	@Override
	public List<PhopiVO> findByForeignkey(Integer PHOPI_PHOP_ID) {
		String FIND_FK = "SELECT * FROM PHOTO_PROGRAM_IMAGES where PHOPI_PHOP_ID = ?";
		PhopiVO phopiVO = null;
		List<PhopiVO> list = new ArrayList<PhopiVO>();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_FK);
			pstmt.setInt(1, PHOPI_PHOP_ID);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				phopiVO = new PhopiVO();
				phopiVO.setPhopi_id(rs.getInt("phopi_id"));
				phopiVO.setPhopi_phop_id(rs.getInt("phopi_phop_id"));
				list.add(phopiVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	//新增圖片
	@Override
	public void insert(PhopiVO phopiVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT);
			pstmt.setInt(1, phopiVO.getPhopi_phop_id());
			pstmt.setBytes(2, phopiVO.getPhopi_images());
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
	@Override
	public void updtaephopi(PhopiVO phopiVO) {
		// TODO Auto-generated method stub
	}
	//刪除
	@Override
	public void delete(Integer phopi_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			pstmt.setInt(1,phopi_id);
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

	// 同時新增作品集及圖片(交易)
	@Override
	public void insert2(PhopiVO phopiVO, Connection con) {
		PreparedStatement pstmt = null;
		try {
			System.out.println(con);
			pstmt = con.prepareStatement(INSERT);
			pstmt.setInt(1, phopiVO.getPhopi_phop_id());// 方案ID
			pstmt.setBytes(2, phopiVO.getPhopi_images());// 圖片
			pstmt.executeUpdate();
			System.out.println("新增成功");
		} catch (SQLException e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException excep) {
					throw new RuntimeException("rollback error occured." + excep.getMessage());
				}
			}
			throw new RuntimeException("A database error occured." + e.getMessage());// SQL語法錯誤
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
	//查詢單筆
	@Override
	public PhopiVO findByPrimaryKey(Integer phopi_id) {
		PhopiVO phopiVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_ONE);
			pstmt.setInt(1, phopi_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				phopiVO = new PhopiVO();
				phopiVO.setPhopi_id(rs.getInt("phopi_id"));
				phopiVO.setPhopi_phop_id(rs.getInt("phopi_phop_id"));
				phopiVO.setPhopi_images(rs.getBytes("phopi_images"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return phopiVO;
	}
}
