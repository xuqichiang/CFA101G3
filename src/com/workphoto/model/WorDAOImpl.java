package com.workphoto.model;

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

import com.weddingphoto.model.WedDAOImpl;
import com.weddingphoto.model.WedVO;


public class WorDAOImpl implements WorDAO {
	
	//新增作品集
	private static final String INSERT = "insert into WORK_PHOTO (WOR_NAME, WOR_PHOG_ID, WOR_LOGO) values (?, ?, ?)";
	//修改作品集封面
	private static final String UPDATA_LOGO = "update WORK_PHOTO set WOR_LOGO = ? where WOR_ID = ?";
	//查詢全部作品集資訊
	private static final String GET_ALL = "select w.WOR_ID,w.WOR_NAME,w.WOR_PHOG_ID, p.PHOG_NAME,p.PHOG_STATUS,m.MEM_SHOP_NAME,w.WOR_LOGO from WORK_PHOTO w join PHOTOGRAPHER p on w.WOR_PHOG_ID = p.PHOG_ID join `MEMBER` m on PHOG_SMEM_ID = m.MEM_ID";
	//查詢一個作品集資訊
	private static final String GET_ONE = "select w.WOR_ID,w.WOR_NAME, w.WOR_PHOG_ID, p.PHOG_NAME,m.MEM_SHOP_NAME,w.WOR_LOGO from WORK_PHOTO w join PHOTOGRAPHER p on w.WOR_PHOG_ID = p.PHOG_ID join `MEMBER` m on PHOG_SMEM_ID = m.MEM_ID where WOR_ID = ?";
	//查詢此作品集的攝影師
	private static final String GET_FK = "select w.WOR_ID,w.WOR_NAME, w.WOR_PHOG_ID, p.PHOG_NAME,m.MEM_SHOP_NAME,w.WOR_LOGO from WORK_PHOTO w join PHOTOGRAPHER p on w.WOR_PHOG_ID = p.PHOG_ID join `MEMBER` m on PHOG_SMEM_ID = m.MEM_ID where WOR_PHOG_ID = ?";
	//查詢作品集店家LOGO & CITY
	private static final String FIND_SMEM_INFO = "select w.WOR_ID,m.MEM_ID,m.MEM_SHOP_NAME,m.MEM_CITY,m.MEM_SHOP_LOGO from WORK_PHOTO w join PHOTOGRAPHER p on w.WOR_PHOG_ID = p.PHOG_ID join `MEMBER` m on PHOG_SMEM_ID = m.MEM_ID where WOR_ID = ?";
	//連線池
		private static DataSource ds = null;
		
		static {
			try {
				Context ctx = new InitialContext();
				ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G3");
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
	//新增作品集
	@Override
	public void insert(WorVO worVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT);//insert into WORK_PHOTO (WOR_NAME, WOR_PHOG_ID, WOR_LOGO) values (?, ?, ?)
			
			pstmt.setString(1, worVO.getWor_name());//作品名
			pstmt.setInt(2, worVO.getWor_phog_id());//攝影師
			pstmt.setBytes(3, worVO.getWor_logo());//作品banner
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
	//修改作品集封面
	@Override
	public void updateLogo(WorVO worVO) {
		
		Connection con =null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATA_LOGO);//update WORK_PHOTO set WOR_LOGO = ? where WOR_ID = ?
			
			pstmt.setBytes(1, worVO.getWor_logo());//作品集banner	
			pstmt.setInt(2, worVO.getWor_id());//作品集ID
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
	//查詢單筆作品集資訊
	@Override
	public WorVO findPrimaryKey(Integer wor_id) {
		
		WorVO worVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE);//select w.WOR_ID,w.WOR_NAME, w.WOR_PHOG_ID, p.PHOG_NAME,m.MEM_SHOP_NAME,w.WOR_LOGO from WORK_PHOTO w join PHOTOGRAPHER p on w.WOR_PHOG_ID = p.PHOG_ID join MEMBER m on PHOG_SMEM_ID = m.MEM_ID where WOR_ID = ?
			pstmt.setInt(1,wor_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				worVO = new WorVO();
				worVO.setWor_id(rs.getInt("wor_id"));//ID
				worVO.setWor_name(rs.getString("wor_name"));//作品名
				worVO.setPhog_name(rs.getString("phog_name"));//攝影師名
				worVO.setMem_shop_name(rs.getString("mem_shop_name"));//店家名
				worVO.setWor_logo(rs.getBytes("wor_logo"));//頁面banner
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
		return worVO;
	}
	//瀏覽婚攝作品
	@Override
	public List<WorVO> getAll() {
		List<WorVO> list = new ArrayList<WorVO>();
		WorVO worVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);//select w.WOR_ID,w.WOR_NAME,w.WOR_PHOG_ID, p.PHOG_NAME,p.PHOG_STATUS,m.MEM_SHOP_NAME,w.WOR_LOGO from WORK_PHOTO w join PHOTOGRAPHER p on w.WOR_PHOG_ID = p.PHOG_ID join MEMBER m on PHOG_SMEM_ID = m.MEM_ID
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				worVO = new WorVO();
				worVO.setWor_id(rs.getInt("wor_id"));//ID
				worVO.setWor_name(rs.getString("wor_name"));//作品名
				worVO.setWor_phog_id(rs.getInt("wor_phog_id"));//攝影師ID
				worVO.setPhog_name(rs.getString("phog_name"));//攝影師名
				worVO.setPhog_status(rs.getInt("phog_status"));//攝影師狀態
				worVO.setMem_shop_name(rs.getString("mem_shop_name"));//店家名
				worVO.setWor_logo(rs.getBytes("wor_logo"));//頁面banner
				list.add(worVO);
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
	//取得攝影師名字
	@Override
	public List<WorVO> findByForeignKey(Integer wor_phog_id) {
		
		ArrayList<WorVO> list = new ArrayList<WorVO>();
		WorVO worVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_FK);//select w.WOR_ID,w.WOR_NAME, w.WOR_PHOG_ID, p.PHOG_NAME,m.MEM_SHOP_NAME,w.WOR_LOGO from WORK_PHOTO w join PHOTOGRAPHER p on w.WOR_PHOG_ID  = p.PHOG_ID join MEMBER m on PHOG_SMEM_ID = m.MEM_ID where WOR_PHOG_ID = ?
			pstmt.setInt(1, wor_phog_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				worVO = new WorVO();
				worVO.setWor_id(rs.getInt("wor_id"));//ID
				worVO.setWor_name(rs.getString("wor_name"));//作品名
				worVO.setWor_phog_id(rs.getInt("wor_phog_id"));//攝影師ID
				worVO.setPhog_name(rs.getString("phog_name"));//攝影師名
				worVO.setMem_shop_name(rs.getString("mem_shop_name"));//店家名
				worVO.setWor_logo(rs.getBytes("wor_logo"));//頁面banner
				list.add(worVO);
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
	public void insertList(WorVO worVO ,List<WedVO> list) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			con = ds.getConnection();
			//啟用手動交易控制
			con.setAutoCommit(false);
			//新增作品集
			String cols[] = {"wor_id"};//自增主鍵綁定 新增時可得知PK
			pstmt = con.prepareStatement(INSERT, cols);//insert into WORK_PHOTO (WOR_NAME, WOR_PHOG_ID, WOR_LOGO) values (?, ?, ?)
			
			pstmt.setString(1, worVO.getWor_name());//作品名
			pstmt.setInt(2, worVO.getWor_phog_id());//攝影師
			pstmt.setBytes(3, worVO.getWor_logo());//作品banner
			pstmt.executeUpdate();
			
			//取對應的自增主鍵值
			String wor_id = null;
			ResultSet rs = pstmt.getGeneratedKeys(); //找到PK
			if(rs.next()) {
				wor_id = rs.getString(1);//唯一主鍵 取得第一列的值(最新的一筆)
				System.out.println("wor_id="+wor_id);
			}else {
				System.out.println("未取得自增主鍵值");
			}
			rs.close();
			//新增作品圖片
			WedDAOImpl dao = new WedDAOImpl();
			for(WedVO wed : list) {
				wed.setWed_wor_id(new Integer (wor_id));//新增作品集放入多張圖片物件
				dao.insert2(wed, con);
			}
			con.commit();//送出變更
			con.setAutoCommit(true);//停用
			
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
			if(con!=null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
	//取得此作品的店家LOGO及CITY
	@Override
	public Map getShopInfo(Integer wor_id) {
		
		Map map = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_SMEM_INFO);//select w.WOR_ID,m.MEM_ID,m.MEM_SHOP_NAME,m.MEM_CITY,m.MEM_SHOP_LOGO from WORK_PHOTO w join PHOTOGRAPHER p on w.WOR_PHOG_ID = p.PHOG_ID join MEMBER m on PHOG_SMEM_ID = m.MEM_ID where WOR_ID = ?
			pstmt.setInt(1, wor_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				map = new HashMap();
				map.put("WOR_ID", rs.getInt("wor_id"));//作品集ID
				map.put("MEM_ID", rs.getInt("mem_id"));//店家ID
				map.put("MEM_SHOP_NAME", rs.getString("mem_shop_name"));//店家名
				map.put("MEM_CITY",rs.getString("mem_city"));//店家地區
				map.put("MEM_SHOP_LOGO", rs.getBytes("mem_shop_logo"));//店家LOGO
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
		return map;
	}
	//搜尋作品店家
	@Override
	public List<WorVO> findByWork(String mem_shop_name) {
		
		String SEARCH = "select w.WOR_ID, w.WOR_NAME, w.WOR_PHOG_ID, p.PHOG_NAME,p.PHOG_STATUS,m.MEM_SHOP_NAME,w.WOR_LOGO from WORK_PHOTO w join PHOTOGRAPHER p on w.WOR_PHOG_ID = p.PHOG_ID join `MEMBER` m on p.PHOG_SMEM_ID = m.MEM_ID where 1=1 ";
		StringBuilder sb = new StringBuilder();//拼接
		sb.append(SEARCH);
		if(mem_shop_name!=null) {
			sb.append(" and m.MEM_SHOP_NAME like '%"+mem_shop_name+"%'");
		}
		SEARCH = sb.toString();
		
		List<WorVO> list = new ArrayList<WorVO>();
		WorVO worVO = new WorVO();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SEARCH);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				worVO = new WorVO();
				worVO.setWor_id(rs.getInt("wor_id"));//ID
				worVO.setWor_name(rs.getString("wor_name"));//作品名
				worVO.setWor_phog_id(rs.getInt("wor_phog_id"));//攝影師ID
				worVO.setPhog_name(rs.getString("phog_name"));//攝影師名
				worVO.setPhog_status(rs.getInt("phog_status"));//攝影師狀態
				worVO.setMem_shop_name(rs.getString("mem_shop_name"));//店家名
				worVO.setWor_logo(rs.getBytes("wor_logo"));//頁面banner
				list.add(worVO);
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
}
