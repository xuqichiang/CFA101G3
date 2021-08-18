package com.photoorder.model;

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


public class PhooDAOImpl implements PhooDAO {
	// 新增訂單
	private static final String INSERT = "insert into PHOTO_ORDER (PHOO_BMEM_ID, PHOO_SMEM_ID, PHOO_PHOG_ID, PHOO_PHOP_ID, PHOO_PLACE, PHOO_DEPOSIT, PHOO_TOTALPRICE, PHOO_PAYTYPE, PHOO_ORDER_STATUS, PHOO_PAY_STATUS, PHOO_ORDER_TIME, PHOO_RESERVE_TIME, PHOO_NOTE) values (?,?,?,?,?,?,?,?,?,?,?,?,?)";
	// 買家查看預約單
	private static final String BY_BMEM_FORM = "select p.PHOO_ID, p.PHOO_BMEM_ID, p.PHOO_DEPOSIT, p.PHOO_TOTALPRICE, p.PHOO_PAY_STATUS, p.PHOO_RESERVE_TIME, p.PHOO_ORDER_STATUS, m.MEM_SHOP_NAME from PHOTO_ORDER p join `MEMBER` m on p.PHOO_SMEM_ID = m.MEM_ID where p.PHOO_BMEM_ID = ?";
	// 買家查看一筆預約單
	private static final String BY_BMEM_ONE = "select PHOO_ID, MEM_SHOP_NAME, PHOG_NAME, PHOP_NAME, PHOP_CONTENT, PHOO_DEPOSIT, PHOO_TOTALPRICE, PHOO_PLACE, PHOO_RESERVE_TIME, PHOO_PAYTYPE, PHOO_PAY_STATUS, PHOO_ORDER_STATUS, PHOO_NOTE from PHOTO_ORDER join `MEMBER` on PHOO_SMEM_ID = MEM_ID join PHOTOGRAPHER on PHOO_PHOG_ID = PHOG_ID join PHOTO_PROGRAM on PHOO_PHOP_ID = PHOP_ID where PHOO_ID = ?";
	// 婚攝賣家查看預約訂單
	private static final String BY_SMEM_FORM = "select PHOO_ID, MEM_NAME, PHOO_SMEM_ID, PHOO_PAY_STATUS, PHOO_ORDER_STATUS, PHOO_RESERVE_TIME, PHOP_NAME, PHOG_NAME from PHOTO_ORDER join `MEMBER` on PHOO_BMEM_ID = MEM_ID join PHOTOGRAPHER on PHOO_PHOG_ID = PHOG_ID join PHOTO_PROGRAM on PHOO_PHOP_ID = PHOP_ID where PHOO_SMEM_ID = ?";
	// 婚攝賣家查看一筆訂單
	private static final String BY_SMEM_ONE = "select PHOO_ID, MEM_NAME, PHOO_DEPOSIT, PHOO_TOTALPRICE,PHOO_PAYTYPE, PHOO_PAY_STATUS, PHOO_ORDER_STATUS, PHOO_RESERVE_TIME, PHOO_PLACE, PHOP_NAME, PHOG_NAME, PHOP_CONTENT, PHOO_NOTE from PHOTO_ORDER join `MEMBER` on PHOO_BMEM_ID = MEM_ID join PHOTOGRAPHER on PHOO_PHOG_ID = PHOG_ID join PHOTO_PROGRAM on PHOO_PHOP_ID = PHOP_ID where PHOO_ID = ?";
	// 取得婚攝店家攝影師預約時間
	private static final String GET_TIME_PHOG = "select PHOO_RESERVE_TIME from PHOTO_ORDER where PHOO_PHOG_ID = ?";
	// 更新訂單狀態
	private static final String UPDATE_ORDER = "update PHOTO_ORDER set PHOO_ORDER_STATUS = ? where PHOO_ID = ?";
	// 付款狀態
	private static final String UPDATE_PAY = "update PHOTO_ORDER set PHOO_PAY_STATUS = ? where PHOO_ID = ?";
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

	// 新增預約訂單
	@Override
	public void insert(PhooVO phooVO) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT);// insert into PHOTO_ORDER (PHOO_BMEM_ID, PHOO_SMEM_ID, PHOO_PHOG_ID, PHOO_PHOP_ID, PHOO_PLACE, PHOO_DEPOSIT, PHOO_TOTALPRICE, PHOO_PAYTYPE, PHOO_ORDER_STATUS, PHOO_PAY_STATUS, PHOO_ORDER_TIME, PHOO_RESERVE_TIME, PHOO_NOTE) values (?,?,?,?,?,?,?,?,?,?,?,?,?)
			pstmt.setInt(1, phooVO.getPhoo_bmem_id());// 買家ID
			pstmt.setInt(2, phooVO.getPhoo_smem_id());// 店家ID
			pstmt.setInt(3, phooVO.getPhoo_phog_id());// 攝影師ID
			pstmt.setInt(4, phooVO.getPhoo_phop_id());// 方案ID
			pstmt.setString(5, phooVO.getPhoo_place());// 拍攝地點
			pstmt.setInt(6, phooVO.getPhoo_deposit());// 訂金
			pstmt.setInt(7, phooVO.getPhoo_totalprice());// 總金額
			pstmt.setInt(8, phooVO.getPhoo_paytype());// 付款方式
			pstmt.setInt(9, phooVO.getPhoo_order_status());// 訂單狀態
			pstmt.setInt(10, phooVO.getPhoo_pay_status());// 付款狀態
			pstmt.setTimestamp(11, phooVO.getPhoo_order_time());// 訂單產生日
			pstmt.setDate(12, phooVO.getPhoo_reserve_time());// 預約時間
			pstmt.setString(13, phooVO.getPhoo_note());// 備註
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
	// 買家預約查詢all
	@Override
	public List getBmemReserveForm(Integer phoo_bmem_id) {

		List list = new ArrayList();
		Map map = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(BY_BMEM_FORM);// select p.PHOO_ID, p.PHOO_BMEM_ID, p.PHOO_DEPOSIT, p.PHOO_TOTALPRICE, p.PHOO_PAY_STATUS, p.PHOO_RESERVE_TIME, p.PHOO_ORDER_STATUS, m.MEM_SHOP_NAME from PHOTO_ORDER p join `MEMBER` m on p.PHOO_SMEM_ID = m.MEM_ID where p.PHOO_BMEM_ID = ?
			pstmt.setInt(1, phoo_bmem_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				map = new HashMap();
				map.put("PHOO_ID", rs.getInt("phoo_id"));// 訂單ID
				map.put("PHOO_BMEM_ID", rs.getInt("phoo_bmem_id"));// 買家ID
				map.put("PHOO_DEPOSIT", rs.getInt("phoo_deposit"));// 訂金
				map.put("PHOO_TOTALPRICE", rs.getInt("phoo_totalprice"));// 總金額
				map.put("PHOO_PAY_STATUS", rs.getInt("phoo_pay_status"));// 付款狀態
				map.put("PHOO_RESERVE_TIME", rs.getDate("phoo_reserve_time"));// 拍攝時間
				map.put("PHOO_ORDER_STATUS", rs.getInt("phoo_order_status"));// 訂單狀態
				map.put("MEM_SHOP_NAME", rs.getString("mem_shop_name"));// 婚攝店家名
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

	// 買家查詢單筆
	@Override
	public Map getBmemOneForm(Integer phoo_id) {
		Map map = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(BY_BMEM_ONE);// select PHOO_ID, MEM_SHOP_NAME, PHOG_NAME, PHOP_NAME, PHOP_CONTENT, PHOO_DEPOSIT, PHOO_TOTALPRICE, PHOO_PLACE, PHOO_PAYTYPE, PHOO_RESERVE_TIME, PHOO_NOTE from PHOTO_ORDER join `MEMBER` on PHOO_SMEM_ID = MEM_ID join PHOTOGRAPHER on PHOO_PHOG_ID = PHOG_ID join PHOTO_PROGRAM on PHOO_PHOP_ID = PHOP_ID where PHOO_ID = ?
			pstmt.setInt(1, phoo_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				map = new HashMap();
				map.put("PHOO_ID", rs.getInt("phoo_id"));// 預約單id
				map.put("MEM_SHOP_NAME", rs.getString("mem_shop_name"));// 店家名
				map.put("PHOO_DEPOSIT", rs.getInt("phoo_deposit"));// 訂金
				map.put("PHOO_TOTALPRICE", rs.getInt("phoo_totalprice"));// 總金額
				map.put("PHOO_PLACE", rs.getString("phoo_place"));// 拍攝地點
				map.put("PHOO_RESERVE_TIME", rs.getDate("phoo_reserve_time"));// 拍攝時間
				map.put("PHOG_NAME", rs.getString("phog_name"));// 攝影師
				map.put("PHOP_NAME", rs.getString("phop_name"));// 方案
				map.put("PHOP_CONTENT", rs.getString("phop_content"));// 方案內容
				map.put("PHOO_PAYTYPE", rs.getInt("phoo_paytype"));//付款方式
				map.put("PHOO_ORDER_STATUS", rs.getInt("phoo_order_status"));// 訂單狀態
				map.put("PHOO_PAY_STATUS", rs.getInt("phoo_pay_status"));// 付款狀態
				map.put("PHOO_NOTE", rs.getString("phoo_note"));// 備註
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

	// 婚攝店家查詢all
	@Override
	public List getSmemReserveForm(Integer phoo_smem_id) {
		List list = new ArrayList();
		Map map = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(BY_SMEM_FORM);// select PHOO_ID, PHOO_SMEM_ID, MEM_NAME, PHOO_PAY_STATUS, PHOO_ORDER_STATUS, PHOO_RESERVE_TIME, PHOP_NAME, PHOG_NAME from PHOTO_ORDER join `MEMBER` on PHOO_BMEM_ID = MEM_ID join PHOTOGRAPHER on PHOO_PHOG_ID = PHOG_ID join PHOTO_PROGRAM on PHOO_PHOP_ID = PHOP_ID where PHOO_SMEM_ID = ?
			pstmt.setInt(1, phoo_smem_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				map = new HashMap();
				map.put("PHOO_ID", rs.getInt("phoo_id"));// 訂單ID
				map.put("PHOO_SMEM_ID", rs.getInt("phoo_smem_id"));// 賣家ID
				map.put("MEM_NAME", rs.getString("mem_name"));// 買家名
				map.put("PHOP_NAME", rs.getString("phop_name"));// 婚攝方案
				map.put("PHOG_NAME", rs.getString("phog_name"));// 攝影師
				map.put("PHOO_PAY_STATUS", rs.getInt("phoo_pay_status"));// 付款狀態
				map.put("PHOO_RESERVE_TIME", rs.getDate("phoo_reserve_time"));// 拍攝時間
				map.put("PHOO_ORDER_STATUS", rs.getInt("phoo_order_status"));// 訂單狀態
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

	// 婚攝店家查詢單筆
	@Override
	public Map getSmemOneForm(Integer phoo_id) {// select PHOO_ID, MEM_NAME, PHOO_DEPOSIT, PHOO_TOTALPRICE, PHOO_PAYTYPE, PHOO_PAY_STATUS, PHOO_ORDER_STATUS, PHOO_RESERVE_TIME, PHOO_PLACE, PHOP_NAME, PHOG_NAME, PHOP_CONTENT, PHOO_NOTE from PHOTO_ORDER join `MEMBER` on PHOO_BMEM_ID = MEM_ID join PHOTOGRAPHER on PHOO_PHOG_ID = PHOG_ID join PHOTO_PROGRAM on PHOO_PHOP_ID = PHOP_ID where PHOO_ID = ?
		Map map = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(BY_SMEM_ONE);
			pstmt.setInt(1, phoo_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				map = new HashMap();
				map.put("PHOO_ID", rs.getInt("phoo_id"));// 訂單ID
				map.put("MEM_NAME", rs.getString("mem_name"));// 買家名
				map.put("PHOP_NAME", rs.getString("phop_name"));// 婚攝方案
				map.put("PHOP_CONTENT", rs.getString("phop_content"));// 方案內容
				map.put("PHOG_NAME", rs.getString("phog_name"));// 攝影師
				map.put("PHOO_DEPOSIT", rs.getInt("phoo_deposit"));// 訂金
				map.put("PHOO_TOTALPRICE", rs.getInt("phoo_totalprice"));// 總金額
				map.put("PHOO_PAYTYPE", rs.getInt("phoo_paytype"));// 付款方式
				map.put("PHOO_PLACE", rs.getString("phoo_place"));// 拍攝地點
				map.put("PHOO_RESERVE_TIME", rs.getDate("phoo_reserve_time"));// 拍攝時間
				map.put("PHOO_PAY_STATUS", rs.getInt("phoo_pay_status"));// 付款狀態
				map.put("PHOO_ORDER_STATUS", rs.getInt("phoo_order_status"));// 訂單狀態
				map.put("PHOO_NOTE", rs.getString("phoo_note"));// 備註
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

	// 找到婚攝店家已預約時間
	@Override
	public List<PhooVO> getTime(Integer phoo_phog_id) {
		List<PhooVO> list = new ArrayList<PhooVO>();
		PhooVO phooVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_TIME_PHOG);// select PHOO_RESERVE_TIME from PHOTO_ORDER where PHOO_PHOG_ID = ?
			pstmt.setInt(1, phoo_phog_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				phooVO = new PhooVO();
				phooVO.setPhoo_reserve_time(rs.getDate("phoo_reserve_time"));
				list.add(phooVO);
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

	// 賣家更新訂單狀態
	@Override
	public void updateOrder(Integer phoo_order_status, Integer phoo_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_ORDER);// update PHOTO_ORDER set PHOO_ORDER_STATUS = ? where PHOO_ID = ?
			pstmt.setInt(1, phoo_order_status);// 訂單狀態
			pstmt.setInt(2, phoo_id);// 訂單ID
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

	// 更新付款狀態
	@Override
	public void updatePay(Integer phoo_pay_status, Integer phoo_id) {

		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_PAY);// update PHOTO_ORDER set PHOO_PAY_STATUS = ? where PHOO_ID = ?
			pstmt.setInt(1, phoo_pay_status);// 付款狀態
			pstmt.setInt(2, phoo_id);// 訂單ID
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
	//以PK查詢訂單
	@Override
	public PhooVO findByPhooId(Integer phoo_id) {
		String sql ="SELECT * FROM PHOTO_ORDER where PHOO_ID = ? ";
		
		PhooVO phooVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1,phoo_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				phooVO = new PhooVO();
				phooVO.setPhoo_id(rs.getInt("Phoo_id"));
				phooVO.setPhoo_bmem_id(rs.getInt("Phoo_bmem_id"));
				phooVO.setPhoo_smem_id(rs.getInt("Phoo_smem_id"));
				phooVO.setPhoo_phog_id(rs.getInt("Phoo_phog_id"));
				phooVO.setPhoo_phop_id(rs.getInt("Phoo_phop_id"));
				phooVO.setPhoo_place(rs.getString("Phoo_place"));
				phooVO.setPhoo_deposit(rs.getInt("Phoo_deposit"));
				phooVO.setPhoo_totalprice(rs.getInt("Phoo_totalprice"));
				phooVO.setPhoo_paytype(rs.getInt("Phoo_paytype"));
				phooVO.setPhoo_order_status(rs.getInt("Phoo_order_status"));
				phooVO.setPhoo_pay_status(rs.getInt("Phoo_pay_status"));
				phooVO.setPhoo_order_time(rs.getTimestamp("Phoo_order_time"));
				phooVO.setPhoo_reserve_time(rs.getDate("Phoo_reserve_time"));
				phooVO.setPhoo_note(rs.getString("Phoo_note"));
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
		return phooVO;
	}
}
