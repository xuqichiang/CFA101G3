package com.photoprogram.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.photoprogramimages.model.PhopiDAOImpl;
import com.photoprogramimages.model.PhopiVO;

public class PhopDAOImpl implements PhopDAO {

	private static DataSource ds = null;

	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	// 查所有方案
	@Override
	public List<PhopVO> getAll() {
		String FIND_BY_ALL = "SELECT * FROM PHOTO_PROGRAM";
		List<PhopVO> list = new ArrayList<PhopVO>();
		PhopVO photoprogramVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_ALL);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				photoprogramVO = new PhopVO();
				photoprogramVO.setPhop_id(rs.getInt("phop_id"));
				photoprogramVO.setPhop_phoc_id(rs.getInt("phop_phoc_id"));
				photoprogramVO.setPhop_smem_id(rs.getInt("phop_smem_id"));
				photoprogramVO.setPhop_name(rs.getString("phop_name"));
				photoprogramVO.setPhop_content(rs.getString("phop_content"));
				photoprogramVO.setPhop_start_time(rs.getDate("phop_start_time"));
				photoprogramVO.setPhop_end_time(rs.getDate("phop_end_time"));
				photoprogramVO.setPhop_status(rs.getInt("phop_status"));
				photoprogramVO.setPhop_price(rs.getInt("phop_price"));
				list.add(photoprogramVO);
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
	// 新增方案
	@Override
	public void insert(PhopVO phopVO) {
		String INSERT = "INSERT INTO PHOTO_PROGRAM(PHOP_PHOC_ID,PHOP_SMEM_ID,PHOP_NAME,PHOP_CONTENT,PHOP_START_TIME,PHOP_END_TIME,PHOP_STATUS,PHOP_PRICE) VALUES (?,?,?,?,?,?,?,?)";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT);
			pstmt.setInt(1, phopVO.getPhop_phoc_id());
			pstmt.setInt(2, phopVO.getPhop_smem_id());
			pstmt.setString(3, phopVO.getPhop_name());
			pstmt.setString(4, phopVO.getPhop_content());
			pstmt.setDate(5, phopVO.getPhop_start_time());
			pstmt.setDate(6, phopVO.getPhop_end_time());
			pstmt.setInt(7, phopVO.getPhop_status());
			pstmt.setInt(8, phopVO.getPhop_price());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
	}
	// 修改方案
	@Override
	public void update(PhopVO phopVO) {
		String UPDATE = "UPDATE PHOTO_PROGRAM SET PHOP_NAME=?,PHOP_CONTENT=?,PHOP_START_TIME=?,PHOP_END_TIME=?,PHOP_STATUS=?,PHOP_PRICE=? WHERE PHOP_ID= ? ";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setString(1, phopVO.getPhop_name());
			pstmt.setString(2, phopVO.getPhop_content());
			pstmt.setDate(3, phopVO.getPhop_start_time());
			pstmt.setDate(4, phopVO.getPhop_end_time());
			pstmt.setInt(5, phopVO.getPhop_status());
			pstmt.setInt(6, phopVO.getPhop_price());
			pstmt.setInt(7, phopVO.getPhop_id());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
	}
	//以ID尋找方案
	@Override
	public PhopVO findByPhopId(Integer phop_id) {
		String FIND_BY_ONE = "SELECT * FROM PHOTO_PROGRAM  WHERE PHOP_ID = ?";
		PhopVO phopVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_BY_ONE);
			pstmt.setInt(1, phop_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				phopVO = new PhopVO();
				phopVO.setPhop_id(rs.getInt("phop_id"));
				phopVO.setPhop_phoc_id(rs.getInt("phop_phoc_id"));
				phopVO.setPhop_smem_id(rs.getInt("phop_smem_id"));
				phopVO.setPhop_name(rs.getString("phop_name"));
				phopVO.setPhop_content(rs.getString("phop_content"));
				phopVO.setPhop_start_time(rs.getDate("phop_start_time"));
				phopVO.setPhop_end_time(rs.getDate("phop_end_time"));
				phopVO.setPhop_status(rs.getInt("phop_status"));
				phopVO.setPhop_price(rs.getInt("phop_price"));
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

		return phopVO;
	}
	// 下架方案
	@Override
	public void delete(Integer phop_id) {
		String DELETE = "DELETE FROM PHOTO_PROGRAM  WHERE PHOP_ID=?";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			pstmt.setInt(1, phop_id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
	}
	@Override
	public List getContent() {
		String GET_CONTET = "SELECT p.PHOP_ID,p.PHOP_NAME,p.PHOP_CONTENT,p.PHOP_PRICE,m.MEM_SHOP_LOGO,m.MEM_SHOP_NAME FROM PHOTO_PROGRAM p join `MEMBER` m on p.PHOP_ID = m.MEM_ID";

		ArrayList list = new ArrayList();
		PhopVO phop = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_CONTET);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Map map = new HashMap();
				map.put("PHOP_ID", rs.getBytes("PHOP_ID"));
				map.put("PHOP_NAME", rs.getBytes("PHOP_NAME"));
				map.put("PHOP_CONTENT", rs.getString("PHOP_CONTENT"));
				map.put("PHOP_PRICE", rs.getString("PHOP_PRICE"));
				map.put("MEM_SHOP_NAME", rs.getString("MEM_SHOP_NAME"));
				map.put("MEM_SHOP_LOGO", rs.getBytes("MEM_SHOP_LOGO"));
				list.add(map);
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
	
//---------------//
	//瀏覽頁面所有方案 含圖片
	@Override
	public List getimages() {
		String GET_IMAGES = "SELECT p.PHOP_ID,p.PHOP_NAME,p.PHOP_CONTENT,p.PHOP_PRICE,p.PHOP_STATUS,m.MEM_SHOP_NAME,m.MEM_SHOP_LOGO, m.MEM_CITY, p.PHOP_PHOC_ID, pc.PHOC_NAME FROM PHOTO_PROGRAM p join `MEMBER` m on p.PHOP_SMEM_ID = m.MEM_ID join PHOTO_CATEGORY pc on p.PHOP_PHOC_ID = PHOC_ID";
		ArrayList list = new ArrayList();
		PhopVO phop = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_IMAGES);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Map map = new HashMap();
				map.put("PHOP_ID", rs.getInt("PHOP_ID"));
				map.put("PHOP_NAME", rs.getString("PHOP_NAME"));
				map.put("PHOP_CONTENT", rs.getString("PHOP_CONTENT"));
				map.put("PHOP_PRICE", rs.getInt("PHOP_PRICE"));
				map.put("PHOP_PHOC_ID", rs.getInt("PHOP_PHOC_ID"));
				map.put("PHOC_NAME", rs.getString("PHOC_NAME"));
				map.put("MEM_SHOP_NAME", rs.getString("MEM_SHOP_NAME"));
				map.put("MEM_SHOP_LOGO", rs.getBytes("MEM_SHOP_LOGO"));
				map.put("MEM_CITY", rs.getString("MEM_CITY"));
				map.put("PHOP_STATUS",rs.getInt("PHOP_STATUS"));
				list.add(map);
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
	//瀏覽方案頁面
	public Map getOneContent(Integer phop_id) {
		String GET_CONTENT = "SELECT p.PHOP_ID,p.PHOP_NAME,p.PHOP_CONTENT,p.PHOP_PRICE, m.MEM_ID ,m.MEM_SHOP_NAME,m.MEM_SHOP_LOGO, m.MEM_CITY from PHOTO_PROGRAM p join `MEMBER` m on p.PHOP_SMEM_ID = m.MEM_ID where p.phop_id = ?";
		Map map = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_CONTENT);
			pstmt.setInt(1, phop_id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				map = new HashMap();
				map.put("PHOP_NAME", rs.getString("PHOP_NAME"));// 名稱
				map.put("PHOP_CONTENT", rs.getString("PHOP_CONTENT"));// 內容
				map.put("PHOP_PRICE", rs.getInt("PHOP_PRICE"));// 價錢
				map.put("MEM_ID", rs.getInt("MEM_ID"));//會員ID
				map.put("MEM_SHOP_NAME", rs.getString("MEM_SHOP_NAME"));// 店家名
				map.put("MEM_SHOP_LOGO", rs.getBytes("MEM_SHOP_LOGO"));// 封面
				map.put("MEM_CITY", rs.getString("MEM_CITY"));// 城市
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
		return map;
	}
	//同時新增方案及圖片(交易)
	@Override
	public void insertList(PhopVO phopVO, List<PhopiVO> list) {
		String INSERT = "insert into PHOTO_PROGRAM(PHOP_PHOC_ID,PHOP_SMEM_ID,PHOP_NAME,PHOP_CONTENT,PHOP_START_TIME,PHOP_END_TIME,PHOP_STATUS,PHOP_PRICE) values (?,?,?,?,?,?,?,?)";
		Connection con = null;
		PreparedStatement pstmt =null;
		
		try {
			con = ds.getConnection();
			//啟用手動交易控制
			con.setAutoCommit(false);
			//新增方案
			String cols[] = {"phop_id"};//自增主鍵綁定 新增可得知PK
			pstmt = con.prepareStatement(INSERT, cols);
			
			pstmt.setInt(1, phopVO.getPhop_phoc_id());//類別ID
			pstmt.setInt(2, phopVO.getPhop_smem_id());//會員ID
			pstmt.setString(3, phopVO.getPhop_name());//方案名
			pstmt.setString(4, phopVO.getPhop_content());//方案內容
			pstmt.setDate(5, phopVO.getPhop_start_time());//開始時間
			pstmt.setDate(6, phopVO.getPhop_end_time());//結束時間
			pstmt.setInt(7, phopVO.getPhop_status());//狀態
			pstmt.setInt(8, phopVO.getPhop_price());//價錢
			pstmt.executeUpdate();
			
			//取得對應自增主鍵值
			String phop_id = null;
			ResultSet rs = pstmt.getGeneratedKeys();//找到PK
			if(rs.next()) {
				phop_id = rs.getString(1);//唯一主鍵 取得第一列的值(最新一筆)
				System.out.println("phop_id="+phop_id);
			}else {
				System.out.println("未取得自增主鍵值");
			}
			rs.close();
			//新增方案圖片
			PhopiDAOImpl dao = new PhopiDAOImpl();
			for(PhopiVO phopi : list) {
				phopi.setPhopi_phop_id(new Integer (phop_id));//新增作品集放入多張圖片物件
				dao.insert2(phopi, con);
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
	//方案店家ID
	@Override
	public List<PhopVO> findByForeignKey(Integer phop_smem_id) {
		
		String GET_FK = "select PHOP_ID,PHOP_NAME,PHOP_PRICE, PHOP_STATUS, PHOP_SMEM_ID from PHOTO_PROGRAM where PHOP_SMEM_ID = ?";
		List<PhopVO> list = new ArrayList<PhopVO>();
		PhopVO phopVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_FK);
			pstmt.setInt(1, phop_smem_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				phopVO = new PhopVO();
				phopVO.setPhop_id(rs.getInt("phop_id"));//方案id
				phopVO.setPhop_name(rs.getString("phop_name"));//方案名稱
				phopVO.setPhop_status(rs.getInt("phop_status"));//放案狀態
				phopVO.setPhop_price(rs.getInt("phop_price"));//方案價錢
				phopVO.setPhop_smem_id(rs.getInt("phop_smem_id"));//店家id
				list.add(phopVO);
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
	//搜尋方案名
	@Override
	public List findByPhotoProgram(String phop_name) {
		String SEARCH = "select PHOP_ID,PHOP_NAME,PHOP_CONTENT,PHOP_PRICE,PHOP_STATUS,MEM_SHOP_NAME,MEM_SHOP_LOGO,MEM_CITY,PHOP_PHOC_ID,PHOC_NAME from PHOTO_PROGRAM join `MEMBER` on PHOP_SMEM_ID = MEM_ID join PHOTO_CATEGORY on PHOP_PHOC_ID = PHOC_ID where 1=1 ";
		StringBuilder sb = new StringBuilder();
		sb.append(SEARCH);
		if(phop_name!=null) {
			sb.append(" and PHOP_NAME like '%"+phop_name+"%' ");
		}
		SEARCH = sb.toString();
		List list = new ArrayList();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SEARCH);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Map map = new HashMap();
				map.put("PHOP_ID", rs.getInt("PHOP_ID"));
				map.put("PHOP_NAME", rs.getString("PHOP_NAME"));
				map.put("PHOP_CONTENT", rs.getString("PHOP_CONTENT"));
				map.put("PHOP_PRICE", rs.getInt("PHOP_PRICE"));
				map.put("PHOP_PHOC_ID", rs.getInt("PHOP_PHOC_ID"));
				map.put("PHOC_NAME", rs.getString("PHOC_NAME"));
				map.put("MEM_SHOP_NAME", rs.getString("MEM_SHOP_NAME"));
				map.put("MEM_SHOP_LOGO", rs.getBytes("MEM_SHOP_LOGO"));
				map.put("MEM_CITY", rs.getString("MEM_CITY"));
				map.put("PHOP_STATUS",rs.getInt("PHOP_STATUS"));
				list.add(map);
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
	//多項條件搜尋
	@Override
	public List findBySearchList(Integer phop_phoc_id, Integer phop_price_1, Integer phop_price_2, String city) {
		String SEARCH = "select p.PHOP_ID,p.PHOP_NAME,p.PHOP_CONTENT,p.PHOP_PRICE,p.PHOP_STATUS,m.MEM_SHOP_NAME,m.MEM_SHOP_LOGO, m.MEM_CITY, p.PHOP_PHOC_ID, pc.PHOC_NAME from PHOTO_PROGRAM p join `MEMBER` m on p.PHOP_SMEM_ID = m.MEM_ID join PHOTO_CATEGORY pc on p.PHOP_PHOC_ID = PHOC_ID where 1=1 ";
		StringBuilder sb = new StringBuilder();//拼接
		
		sb.append(SEARCH);
		if(phop_phoc_id!=null) {
			sb.append(" and p.PHOP_PHOC_ID like "+phop_phoc_id);//方案類別
		}
		if(city!=null){
			sb.append(" and m.MEM_CITY like '"+city+"' ");//地區
		}
		if(phop_price_1!=null && phop_price_2!=null){
			sb.append(" and p.PHOP_PRICE between "+phop_price_1+" and "+phop_price_2);//價錢
		}
		SEARCH = sb.toString();
		List list = new ArrayList();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SEARCH);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Map map = new HashMap();
				map.put("PHOP_ID", rs.getInt("PHOP_ID"));
				map.put("PHOP_NAME", rs.getString("PHOP_NAME"));
				map.put("PHOP_CONTENT", rs.getString("PHOP_CONTENT"));
				map.put("PHOP_PRICE", rs.getInt("PHOP_PRICE"));
				map.put("PHOP_PHOC_ID", rs.getInt("PHOP_PHOC_ID"));
				map.put("PHOC_NAME", rs.getString("PHOC_NAME"));
				map.put("MEM_SHOP_NAME", rs.getString("MEM_SHOP_NAME"));
				map.put("MEM_SHOP_LOGO", rs.getBytes("MEM_SHOP_LOGO"));
				map.put("MEM_CITY", rs.getString("MEM_CITY"));
				map.put("PHOP_STATUS",rs.getInt("PHOP_STATUS"));
				list.add(map);
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
	
	@Override
	public List findBySearchCategory(Integer phop_phoc_id) {
		String SEARCH = "select p.PHOP_ID,p.PHOP_NAME,p.PHOP_CONTENT,p.PHOP_PRICE,p.PHOP_STATUS,m.MEM_SHOP_NAME,m.MEM_SHOP_LOGO, m.MEM_CITY, p.PHOP_PHOC_ID, pc.PHOC_NAME from PHOTO_PROGRAM p join `MEMBER` m on p.PHOP_SMEM_ID = m.MEM_ID join PHOTO_CATEGORY pc on p.PHOP_PHOC_ID = PHOC_ID where 1=1 ";
		StringBuilder sb = new StringBuilder();//拼接
		
		sb.append(SEARCH);
		if(phop_phoc_id!=null) {
			sb.append(" and p.PHOP_PHOC_ID like "+phop_phoc_id);//方案類別
		}
		SEARCH = sb.toString();
		List list = new ArrayList();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(SEARCH);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Map map = new HashMap();
				map.put("PHOP_ID", rs.getInt("PHOP_ID"));
				map.put("PHOP_NAME", rs.getString("PHOP_NAME"));
				map.put("PHOP_CONTENT", rs.getString("PHOP_CONTENT"));
				map.put("PHOP_PRICE", rs.getInt("PHOP_PRICE"));
				map.put("PHOP_PHOC_ID", rs.getInt("PHOP_PHOC_ID"));
				map.put("PHOC_NAME", rs.getString("PHOC_NAME"));
				map.put("MEM_SHOP_NAME", rs.getString("MEM_SHOP_NAME"));
				map.put("MEM_SHOP_LOGO", rs.getBytes("MEM_SHOP_LOGO"));
				map.put("MEM_CITY", rs.getString("MEM_CITY"));
				map.put("PHOP_STATUS",rs.getInt("PHOP_STATUS"));
				list.add(map);
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
