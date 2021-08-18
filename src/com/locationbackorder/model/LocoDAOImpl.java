package com.locationbackorder.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.locationprogramimages.model.LocpiVO;
import com.member.model.MemVO;

public class LocoDAOImpl implements LocoDAO {

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
	public void insertLocorder(LocoVO locoVO) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void updateLocorder(LocoVO locoVO) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void deleteLocorder(Integer loco_id) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public LocoVO getOneLocorderByPrimaryKey(Integer loco_id) {
		
		String sql = "SELECT * FROM LOCATION_ORDER WHERE LOCO_ID = ?";
		LocoVO locovo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, loco_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				locovo = new LocoVO();
				locovo.setLoco_id(rs.getInt("LOCO_ID"));
				locovo.setLoco_smem_id(rs.getInt("LOCO_SMEM_ID"));
				locovo.setLoco_bmem_id(rs.getInt("LOCO_BMEM_ID"));
				locovo.setLoco_locr_id(rs.getInt("LOCO_LOCR_ID"));
				locovo.setLoco_locp_id(rs.getInt("LOCO_LOCP_ID"));
				locovo.setLoco_totalprice(rs.getInt("LOCO_TOTALPRICE"));
				locovo.setLoco_deposit(rs.getInt("LOCO_DEPOSIT"));
				locovo.setLoco_paytype(rs.getInt("LOCO_PAYTYPE"));
				locovo.setLoco_order_status(rs.getInt("LOCO_ORDER_STATUS"));
				locovo.setLoco_pay_status(rs.getInt("LOCO_PAY_STATUS"));
				locovo.setLoco_order_time(rs.getTimestamp("LOCO_ORDER_TIME"));
				locovo.setLoco_reserve_time(rs.getTimestamp("LOCO_RESERVE_TIME"));
				locovo.setLoco_table_num(rs.getInt("LOCO_TABLE_NUM"));
				locovo.setLoco_note(rs.getString("LOCO_NOTE"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
		
		return locovo;
	}
	
	//撈所有場地訂單
	@Override
	public List getAllLocorder() {
		
		String sql = "SELECT * FROM LOCATION_ORDER";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List list = new ArrayList();
		HashMap map = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				map = new HashMap();
				map.put("loco_id", rs.getInt("LOCO_ID"));
				map.put("loco_smem_id", rs.getInt("LOCO_SMEM_ID"));
				map.put("loco_bmem_id", rs.getInt("LOCO_BMEM_ID"));
				map.put("loco_locr_id", rs.getInt("LOCO_LOCR_ID"));
				map.put("loco_locp_id", rs.getInt("LOCO_LOCP_ID"));
				map.put("loco_totalprice", rs.getInt("LOCO_TOTALPRICE"));
				map.put("loco_deposit", rs.getInt("LOCO_DEPOSIT"));
				map.put("loco_paytype", rs.getInt("LOCO_PAYTYPE"));
				map.put("loco_order_status", rs.getInt("LOCO_ORDER_STATUS"));
				map.put("loco_pay_status", rs.getInt("LOCO_PAY_STATUS"));
				String ordertime = sdf.format(rs.getTimestamp("LOCO_ORDER_TIME"));
				map.put("loco_order_time", ordertime);
				String reservetime = sdf.format(rs.getTimestamp("LOCO_RESERVE_TIME"));
				map.put("loco_reserve_time", reservetime);
				map.put("loco_table_num", rs.getInt("LOCO_TABLE_NUM"));
				map.put("loco_note", rs.getString("LOCO_NOTE"));
				list.add(map);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
	
	@Override
	public List getOrderByStatus(Integer loco_order_status) {
		
		String sql = "SELECT * FROM LOCATION_ORDER WHERE LOCO_ORDER_STATUS = ?";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List list = new ArrayList();
		HashMap map = null;
		LocoVO locovo = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, loco_order_status);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				map = new HashMap();
				map.put("loco_id", rs.getInt("LOCO_ID"));
				map.put("loco_smem_id", rs.getInt("LOCO_SMEM_ID"));
				map.put("loco_bmem_id", rs.getInt("LOCO_BMEM_ID"));
				map.put("loco_locr_id", rs.getInt("LOCO_LOCR_ID"));
				map.put("loco_locp_id", rs.getInt("LOCO_LOCP_ID"));
				map.put("loco_totalprice", rs.getInt("LOCO_TOTALPRICE"));
				map.put("loco_deposit", rs.getInt("LOCO_DEPOSIT"));
				map.put("loco_paytype", rs.getInt("LOCO_PAYTYPE"));
				map.put("loco_order_status", rs.getInt("LOCO_ORDER_STATUS"));
				map.put("loco_pay_status", rs.getInt("LOCO_PAY_STATUS"));
				String ordertime = sdf.format(rs.getTimestamp("LOCO_ORDER_TIME"));
				map.put("loco_order_time", ordertime);
				String reservetime = sdf.format(rs.getTimestamp("LOCO_RESERVE_TIME"));
				map.put("loco_reserve_time", reservetime);
				map.put("loco_table_num", rs.getInt("LOCO_TABLE_NUM"));
				map.put("loco_note", rs.getString("LOCO_NOTE"));
				list.add(map);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
	@Override
	public MemVO getMemVOByPrimaryKey(Integer mem_id) {
		
		String sql = "SELECT * FROM `MEMBER` WHERE MEM_ID = ?";
		MemVO memVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, mem_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				memVO = new MemVO();
				memVO.setMem_id(rs.getInt("mem_id"));
				memVO.setMem_username(rs.getString("mem_username"));
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
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
		
		return memVO;
	}
	
	//查詢所有訂單數
	@Override
	public Integer findTotalCount() {
		
		String sql = "SELECT COUNT(*) FROM LOCATION_ORDER";
		Integer orderCount = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			orderCount = rs.getInt(1);
			
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
		
		return orderCount;
	}
	
	//用頁數來查詢每頁顯示的訂單數
	@Override
	public List findByPage(Integer start, Integer rows) {
		
		String sql = "SELECT * FROM LOCATION_ORDER LIMIT ? , ?";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List list = new ArrayList();
		HashMap map = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, start);
			pstmt.setInt(2, rows);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				map = new HashMap();
				map.put("loco_id", rs.getInt("LOCO_ID"));
				map.put("loco_smem_id", rs.getInt("LOCO_SMEM_ID"));
				map.put("loco_bmem_id", rs.getInt("LOCO_BMEM_ID"));
				map.put("loco_locr_id", rs.getInt("LOCO_LOCR_ID"));
				map.put("loco_locp_id", rs.getInt("LOCO_LOCP_ID"));
				map.put("loco_totalprice", rs.getInt("LOCO_TOTALPRICE"));
				map.put("loco_deposit", rs.getInt("LOCO_DEPOSIT"));
				map.put("loco_paytype", rs.getInt("LOCO_PAYTYPE"));
				map.put("loco_order_status", rs.getInt("LOCO_ORDER_STATUS"));
				map.put("loco_pay_status", rs.getInt("LOCO_PAY_STATUS"));
				String ordertime = sdf.format(rs.getTimestamp("LOCO_ORDER_TIME"));
				map.put("loco_order_time", ordertime);
				String reservetime = sdf.format(rs.getTimestamp("LOCO_RESERVE_TIME"));
				map.put("loco_reserve_time", reservetime);
				map.put("loco_table_num", rs.getInt("LOCO_TABLE_NUM"));
				map.put("loco_note", rs.getString("LOCO_NOTE"));
				list.add(map);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
	
	//用訂單狀態來查詢所有的訂單數
	@Override
	public Integer findTotalCountWithStatus(Integer loco_status) {
		
		String sql = "SELECT COUNT(*) FROM LOCATION_ORDER WHERE LOCO_ORDER_STATUS = ?";
		Integer orderCount = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, loco_status);
			rs = pstmt.executeQuery();
			rs.next();
			orderCount = rs.getInt(1);
			
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
		
		return orderCount;
	}
	
	//用訂單狀態來查詢每頁顯示的訂單數
	@Override
	public List findByPageWithStatus(Integer start, Integer rows, Integer loco_status) {
		
		String sql = "SELECT * FROM LOCATION_ORDER WHERE LOCO_ORDER_STATUS = ? LIMIT ? , ?";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List list = new ArrayList();
		HashMap map = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, loco_status);
			pstmt.setInt(2, start);
			pstmt.setInt(3, rows);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				map = new HashMap();
				map.put("loco_id", rs.getInt("LOCO_ID"));
				map.put("loco_smem_id", rs.getInt("LOCO_SMEM_ID"));
				map.put("loco_bmem_id", rs.getInt("LOCO_BMEM_ID"));
				map.put("loco_locr_id", rs.getInt("LOCO_LOCR_ID"));
				map.put("loco_locp_id", rs.getInt("LOCO_LOCP_ID"));
				map.put("loco_totalprice", rs.getInt("LOCO_TOTALPRICE"));
				map.put("loco_deposit", rs.getInt("LOCO_DEPOSIT"));
				map.put("loco_paytype", rs.getInt("LOCO_PAYTYPE"));
				map.put("loco_order_status", rs.getInt("LOCO_ORDER_STATUS"));
				map.put("loco_pay_status", rs.getInt("LOCO_PAY_STATUS"));
				String ordertime = sdf.format(rs.getTimestamp("LOCO_ORDER_TIME"));
				map.put("loco_order_time", ordertime);
				String reservetime = sdf.format(rs.getTimestamp("LOCO_RESERVE_TIME"));
				map.put("loco_reserve_time", reservetime);
				map.put("loco_table_num", rs.getInt("LOCO_TABLE_NUM"));
				map.put("loco_note", rs.getString("LOCO_NOTE"));
				list.add(map);
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
