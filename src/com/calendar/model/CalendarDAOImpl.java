package com.calendar.model;

import java.sql.Connection;
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


public class CalendarDAOImpl implements CalendarDAO {
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	//抓取所有場地訂單的預約日期
	@Override
	public List<CalendarVO> getAllLocStroke() {
		
			String sql = "SELECT * FROM LOCATION_ORDER;";
			List list = new ArrayList();
			HashMap map = null;
			CalendarVO calVO = null;
			Connection con = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				
				con = ds.getConnection();
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					
					calVO = new CalendarVO();
					calVO.setLoco_id(rs.getInt("LOCO_ID"));
					calVO.setLoco_reserve_time(rs.getTimestamp("LOCO_RESERVE_TIME"));
					
					map = new HashMap();
					map.put("title", "編號:" + calVO.getLoco_id());
					map.put("start", calVO.getLoco_reserve_time());
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

	//抓取一個賣家的場地訂單預約日期
	@Override
	public List<CalendarVO> getLocmemStroke(Integer mem_id) {
		
		String sql = "SELECT * FROM LOCATION_ORDER LO JOIN LOCATION_ROOM LR ON LO.LOCO_LOCR_ID = LR.LOCR_ID WHERE LO.LOCO_SMEM_ID = ? and LO.LOCO_ORDER_STATUS!=4 and LO.LOCO_ORDER_STATUS!=2";
		List list = new ArrayList();
		HashMap map = null;
		CalendarVO calVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, mem_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				calVO = new CalendarVO();
				calVO.setLoco_id(rs.getInt("LOCO_ID"));
				calVO.setLocr_name(rs.getString("LOCR_NAME"));
				calVO.setLoco_reserve_time(rs.getTimestamp("LOCO_RESERVE_TIME"));
				
				map = new HashMap();
				map.put("title", "訂單編號:" + calVO.getLoco_id() + " , 預約廳房:" + calVO.getLocr_name());
				map.put("start", calVO.getLoco_reserve_time());
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

	//抓取所有攝影訂單的預約日期
	@Override
	public List<CalendarVO> getAllPhoStroke() {
		
		String sql = "SELECT * FROM PHOTO_ORDER;";
		List list = new ArrayList();
		HashMap map = null;
		CalendarVO calVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				calVO = new CalendarVO();
				calVO.setPhoo_id(rs.getInt("PHOO_ID"));
				calVO.setPhoo_reserve_time(rs.getDate("PHOO_RESERVE_TIME"));
				
				map = new HashMap();
				map.put("title", "編號:" + calVO.getPhoo_id());
				map.put("start", calVO.getPhoo_reserve_time());
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

	//抓取一個賣家的攝影訂單預約日期
	@Override
	public List<CalendarVO> getPhomemStroke(Integer mem_id) {
		
		String sql = "SELECT * FROM PHOTO_ORDER PO JOIN PHOTOGRAPHER P ON PO.PHOO_PHOG_ID = P.PHOG_ID JOIN `MEMBER` ON MEM_ID = PHOO_BMEM_ID WHERE PO.PHOO_SMEM_ID = ?";
		List list = new ArrayList();
		HashMap map = null;
		CalendarVO calVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, mem_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				calVO = new CalendarVO();
				calVO.setPhoo_id(rs.getInt("PHOO_ID"));
				calVO.setPhog_name(rs.getString("PHOG_NAME"));
				calVO.setPhoo_reserve_time(rs.getDate("PHOO_RESERVE_TIME"));
				calVO.setMem_name(rs.getString("MEM_NAME"));
				
				map = new HashMap();
				map.put("title", "攝影師：" + calVO.getPhog_name() + "｜拍攝對象："+ calVO.getMem_name());
				map.put("start", calVO.getPhoo_reserve_time());
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
