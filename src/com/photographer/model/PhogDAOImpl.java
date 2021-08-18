package com.photographer.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.member.model.MemVO;

public class PhogDAOImpl implements PhogDAO {

	// 新增攝影師
	private static final String INSERT = "insert into PHOTOGRAPHER (PHOG_NAME, PHOG_STATUS, PHOG_SMEM_ID) values (?, ?, ?)";
	// 修改攝影師狀態
	private static final String UPDATE_STATUS = "update PHOTOGRAPHER set PHOG_STATUS=? where PHOG_ID=?";
	// 單筆查詢攝影師
	private static final String GET_ONE = "select * from  PHOTOGRAPHER where PHOG_ID = ?";
	// 查所有攝影師資訊
	private static final String GET_ALL = "select * from  PHOTOGRAPHER order by PHOG_ID";
	// 查詢攝影師店家id
	private static final String GET_FK = "select * from PHOTOGRAPHER where PHOG_SMEM_ID = ?";
	// 查設店家資訊
	private static final String FIND_SHOP_INFO = "select p.PHOG_ID,p.PHOG_SMEM_ID,m.MEM_SHOP_NAME, m.MEM_CITY, m.MEM_SHOP_LOGO from PHOTOGRAPHER p join `MEMBER` m on p.PHOG_SMEM_ID = m.MEM_ID where PHOG_SMEM_ID = ?";
	// 找到店家所有作品集
	private static final String FIND_WORK = "select PHOG_ID, PHOG_NAME, PHOG_SMEM_ID, PHOG_STATUS, WOR_NAME from PHOTOGRAPHER join WORK_PHOTO on WOR_PHOG_ID = PHOG_ID where PHOG_SMEM_ID = ?";
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

	// 新增攝影師
	@Override
	public void insert(PhogVO phogVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT);// insert into PHOTOGRAPHER (PHOG_NAME, PHOG_STATUS, PHOG_SMEM_ID)
													// values (?, ?, ?)

			pstmt.setString(1, phogVO.getPhog_name());// 攝影師名字
			pstmt.setInt(2, phogVO.getPhog_status());// 攝影師狀態 1在職(預設) 0離職
			pstmt.setInt(3, phogVO.getPhog_smem_id());// 店家id
			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException("A database error occured." + e.getMessage());// SQL語法錯誤
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	// 修改攝影師狀態
	@Override
	public void update(PhogVO phogVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_STATUS);// update PHOTOGRAPHER set PHOG_STATUS=? where PHOG_ID=?

			pstmt.setInt(1, phogVO.getPhog_status());// 攝影師狀態
			pstmt.setInt(2, phogVO.getPhog_id());// 攝影師id
			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException("A database error occured." + e.getMessage());// SQL語法錯誤
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	// 攝影師查詢單筆
	@Override
	public PhogVO findByPhogId(Integer phog_id) {

		PhogVO phogVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE);// select * from PHOTOGRAPHER where PHOG_ID = ?

			pstmt.setInt(1, phog_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				phogVO = new PhogVO();
				phogVO.setPhog_id(phog_id);
				phogVO.setPhog_name(rs.getString("phog_name"));// 攝影師名
				phogVO.setPhog_status(rs.getInt("phog_status"));// 攝影師狀態 1在職(預設) 0離職
				phogVO.setPhog_smem_id(rs.getInt("phog_smem_id"));// 婚攝店家ID
			}

		} catch (SQLException e) {
			throw new RuntimeException("A database error occured." + e.getMessage());// SQL語法錯誤
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return phogVO;
	}

	// 查詢攝影師所有資訊
	@Override
	public List<PhogVO> getAll() {

		List<PhogVO> list = new ArrayList<PhogVO>();

		PhogVO phogVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);// select * from PHOTOGRAPHER order by PHOG_ID
			rs = pstmt.executeQuery();

			while (rs.next()) {
				phogVO = new PhogVO();
				phogVO.setPhog_id(rs.getInt("phog_id"));// 攝影師ID
				phogVO.setPhog_name(rs.getString("phog_name"));// 攝影師名
				phogVO.setPhog_status(rs.getInt("phog_status"));// 攝影師狀態 1在職(預設) 0離職
				phogVO.setPhog_smem_id(rs.getInt("phog_smem_id"));// 婚攝店家ID
				list.add(phogVO);
			}
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured." + e.getMessage());// SQL語法錯誤
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	// 獲取婚攝會員ID
	@Override
	public List<PhogVO> findByForeignKey(Integer phog_smem_id) {

		List<PhogVO> list = new ArrayList<PhogVO>();
		PhogVO phogVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_FK);// select * from PHOTOGRAPHER where PHOG_SMEM_ID = ?
			pstmt.setInt(1, phog_smem_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				phogVO = new PhogVO();
				phogVO.setPhog_id(rs.getInt("phog_id"));// 攝影師ID
				phogVO.setPhog_name(rs.getString("phog_name"));// 攝影師名
				phogVO.setPhog_status(rs.getInt("phog_status"));// 攝影師狀態 1在職(預設) 0離職
				phogVO.setPhog_smem_id(rs.getInt("phog_smem_id"));// 婚攝店家ID
				list.add(phogVO);
			}
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured." + e.getMessage());// SQL語法錯誤
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

	// 取得婚攝店家資訊
	@Override
	public Map getShopInfo(Integer phog_smem_id) {

		Map map = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_SHOP_INFO);// select p.PHOG_ID,p.PHOG_SMEM_ID,m.MEM_SHOP_NAME, m.MEM_CITY,
															// m.MEM_SHOP_LOGO from PHOTOGRAPHER p join MEMBER m on
															// p.PHOG_SMEM_ID = m.MEM_ID where PHOG_SMEM_ID = ?
			pstmt.setInt(1, phog_smem_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				map = new HashMap();
				map.put("PHOG_ID", rs.getInt("phog_id"));// 攝影師ID
				map.put("PHOG_SMEM_ID", rs.getInt("phog_smem_id"));// 店家ID
				map.put("MEM_SHOP_NAME", rs.getString("mem_shop_name"));// 店家名
				map.put("MEM_CITY", rs.getString("mem_city"));// 店家地區
				map.put("MEM_SHOP_LOGO", rs.getBytes("mem_shop_logo"));// 店家LOGO
			}
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured." + e.getMessage());// SQL語法錯誤
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return map;
	}

	// 店家找所有作品集
	@Override
	public List findWorkCount(Integer phog_smem_id) {

		List list = new ArrayList();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_WORK);// select PHOG_ID, PHOG_NAME, PHOG_SMEM_ID, PHOG_STATUS, WOR_NAME
													// from PHOTOGRAPHER join WORK_PHOTO on WOR_PHOG_ID = PHOG_ID where
													// PHOG_SMEM_ID = ?
			pstmt.setInt(1, phog_smem_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Map map = new HashMap();
				map.put("PHOG_ID", rs.getInt("phog_id"));// ID
				map.put("PHOG_NAME", rs.getString("phog_name"));// 攝影師名
				map.put("PHOG_SMEM_ID", rs.getInt("phog_smem_id"));// 店家ID
				map.put("PHOG_STATUS", rs.getInt("phog_status"));// 攝影師狀態
				map.put("WOR_NAME", rs.getString("wor_name"));// 作品名
				list.add(map);
			}
		} catch (SQLException e) {
			throw new RuntimeException("A database error occured." + e.getMessage());// SQL語法錯誤
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}

}
