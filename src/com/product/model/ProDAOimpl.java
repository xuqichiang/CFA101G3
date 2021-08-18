package com.product.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

public class ProDAOimpl implements ProDAO {

//	SQL指令：
//	private static final java.lang.String INSERT= "INSERT INTO PRODUCT(PRO_ID,PRO_NAME,PRO_PRICE,PRO_CONTENT,PRO_SMEM_ID,PRO_PROC_ID,PRO_STATUS) VALUES(?, ?, ?, ?, ?, ?, ?)";
//	private static final String UPDATE_STMT = "UPDATE USER SET NAME = ?, GENDER = ?, AGE = ?, ADDRESS = ?, LINE = ?, EMAIL =? WHERE ID = ?";
//	private static final java.lang.String FIND_BY_PK = "SELECT * FROM PRODUCT WHERE ID = ?";
//	private static final java.lang.String GET_ALL = "SELECT * FROM PRODUCT";
//	private static final String FIND_USERS = "SELECT * FROM USER WHERE NAME = ?";
//  方便複製用
//	private Integer proi_id;
//	private Integer proi_pro_id;
//	private byte[] proi_images;
	
	//獲取DS使用連線池
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	
	//查詢商品
	@Override
	public List<ProVO> getAll(){
		//準備好 SQL list vo con pstmt rs 1.宣告2.取值3.拿來用
		java.lang.String GETALL = "SELECT * FROM PRODUCT";
		List<ProVO> list = new ArrayList<ProVO>();
		ProVO proVO = null;
		Connection con = null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		//連線開始
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GETALL);
			rs = pstmt.executeQuery();
			//集合集撈出每一筆
			while(rs.next()) {
				proVO = new ProVO();
				proVO.setPro_id(rs.getInt("pro_id"));
				proVO.setPro_name(rs.getString("pro_name"));
				proVO.setPro_price(rs.getInt("pro_price"));
				proVO.setPro_content(rs.getString("pro_content"));
				proVO.setPro_smem_id(rs.getInt("pro_smem_id"));
				proVO.setPro_proc_id(rs.getInt("pro_proc_id"));
				proVO.setPro_status(rs.getInt("pro_status"));
				list.add(proVO);
			}
			
		} catch (SQLException se) {
			throw new RuntimeException("資料錯誤"+
			se.getMessage());
		}finally {
		if(rs != null) {
		try {
			rs.close();
		} catch (SQLException se) {
			se.printStackTrace();
			}
		}
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException se) {
				se.printStackTrace();
				}
			}
		if(con != null) {
			try {
				con.close();
			} catch (SQLException se) {
				se.printStackTrace();
				}
			}
		}
		return list;
	}
	//商品上架
	@Override
	public void insert(ProVO proVO) {
		
		//準備好 SQL con pstmt 1.宣告2.取值3.拿來用
		java.lang.String INSERT= "INSERT INTO PRODUCT(PRO_SMEM_ID, PRO_NAME,PRO_PRICE,PRO_CONTENT,PRO_PROC_ID,PRO_STATUS) VALUES(?, ?, ?, ?, ?, ?)";
		Connection con = null;
		PreparedStatement pstmt = null;
		//連線開始
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT);
			
//			pstmt.setInt(1,proVO.getPro_id());
			pstmt.setInt(1,proVO.getPro_smem_id());
			pstmt.setString(2,proVO.getPro_name());
			pstmt.setInt(3,proVO.getPro_price());
			pstmt.setString(4,proVO.getPro_content());
			pstmt.setInt(5,proVO.getPro_proc_id());
			pstmt.setInt(6,proVO.getPro_status());
			pstmt.executeUpdate();
		} catch (SQLException se) {
			throw new RuntimeException("資料錯誤"+
			se.getMessage());
		}finally {
			
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException se) {
				se.printStackTrace();
				}
			}
		if(con != null) {
			try {
				con.close();
			} catch (SQLException se) {
				se.printStackTrace();
				}
			}
		}
	}

	
	//商品下架
	@Override
	public void delete(Integer pro_id) {
		//準備好 SQL con pstmt 1.宣告2.取值3.拿來用
		java.lang.String DELETE ="DELETE  FROM PRODUCT WHERE PRO_ID = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		//連線開始
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);
			pstmt.setInt(1,pro_id);
			pstmt.executeUpdate();
			
		} catch (SQLException se) {
			throw new RuntimeException("資料錯誤"+
			se.getMessage());
		}finally {
			
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException se) {
				se.printStackTrace();
				}
			}
		if(con != null) {
			try {
				con.close();
			} catch (SQLException se) {
				se.printStackTrace();
				}
			}
		}
	}
	//更改商品
	public void update(ProVO proVO) {
		//準備好 SQL con pstmt 1.宣告2.取值3.拿來用
		java.lang.String UPDATE= "UPDATE PRODUCT SET PRO_NAME = ? ,PRO_PRICE = ? ,PRO_CONTENT = ? ,PRO_SMEM_ID = ? ,PRO_PROC_ID = ? ,PRO_STATUS = ? WHERE PRO_ID = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		//開始連線
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);
		//set資料
			pstmt.setString(1,proVO.getPro_name());
			pstmt.setInt(2,proVO.getPro_price());
			pstmt.setString(3,proVO.getPro_content());
			pstmt.setInt(4,proVO.getPro_smem_id());
			pstmt.setInt(5,proVO.getPro_proc_id());
			pstmt.setInt(6,proVO.getPro_status());
			pstmt.setInt(7,proVO.getPro_id());
			pstmt.executeUpdate();	
			
			
		} catch (SQLException se) {
			throw new RuntimeException("資料錯誤"+
			se.getMessage());
		}finally {
			
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException se) {
				se.printStackTrace();
				}
			}
		if(con != null) {
			try {
				con.close();
			} catch (SQLException se) {
				se.printStackTrace();
				}
			}
		}
	}
	//搜尋單筆商品PK
	@Override
	public ProVO findByPK(Integer pro_id) {
		//準備好 SQL list vo con pstmt rs 1.宣告2.取值3.拿來用
		java.lang.String FIND_BY_PK ="SELECT * FROM PRODUCT WHERE PRO_ID = ?";
		ProVO proVO =null;
		Connection con =null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		//連線開始
		try {
			con = ds.getConnection();
			pstmt = con.prepareCall(FIND_BY_PK);
			pstmt.setInt(1, pro_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				proVO = new ProVO();
				proVO.setPro_id(rs.getInt("pro_id"));
				proVO.setPro_name(rs.getString("pro_name"));
				proVO.setPro_price(rs.getInt("pro_price"));
				proVO.setPro_content(rs.getString("pro_content"));
				proVO.setPro_smem_id(rs.getInt("pro_smem_id"));
				proVO.setPro_proc_id(rs.getInt("pro_proc_id"));
				proVO.setPro_status(rs.getInt("pro_status"));
			}
		} catch (SQLException e) {
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
		return proVO;
	};
	//根據會員店家ID尋找商品
	@Override
	public List<ProVO> findBySeller(Integer pro_smem_id) {
	//準備好 SQL list vo con pstmt rs 1.宣告2.取值3.拿來用
	java.lang.String FIND_BY_PK_SELLER ="SELECT * FROM PRODUCT WHERE PRO_SMEM_ID = ?";
	List<ProVO> list = new ArrayList<ProVO>();
	ProVO proVO =null;
	Connection con =null;
	PreparedStatement pstmt =null;
	ResultSet rs = null;
	//連線開始
	try {
		con = ds.getConnection();
		pstmt = con.prepareCall(FIND_BY_PK_SELLER);
		pstmt.setInt(1, pro_smem_id);
		rs = pstmt.executeQuery();
		
		while(rs.next()) {
			proVO = new ProVO();
			proVO.setPro_id(rs.getInt("pro_id"));
			proVO.setPro_name(rs.getString("pro_name"));
			proVO.setPro_price(rs.getInt("pro_price"));
			proVO.setPro_content(rs.getString("pro_content"));
			proVO.setPro_smem_id(rs.getInt("pro_smem_id"));
			proVO.setPro_proc_id(rs.getInt("pro_proc_id"));
			proVO.setPro_status(rs.getInt("pro_status"));
			list.add(proVO);
		}
	} catch (SQLException e) {
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
	return list;
}

	@Override
	public List<ProVO> findByCateList(Integer pro_proc_id){
		
		java.lang.String sql = "SELECT MIN(PROI_ID), PROI_PRO_ID, PRO_NAME, PRO_PRICE, PRO_CONTENT FROM PRODUCT_IMAGES join PRODUCT ON PROI_PRO_ID = PRO_ID WHERE PRO_PROC_ID = ? GROUP BY PROI_PRO_ID";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProVO proVO = null;
		List<ProVO> list = new ArrayList<ProVO>();
		
		//開始連線
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, pro_proc_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				proVO = new ProVO();
				proVO.setPro_id(rs.getInt("PROI_PRO_ID"));
				proVO.setPro_name(rs.getString("PRO_NAME"));
				proVO.setPro_price(rs.getInt("PRO_PRICE"));
				proVO.setPro_content(rs.getString("PRO_CONTENT"));
				list.add(proVO);
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
	
	@Override
	public List<ProVO> findByCateCheapList(Integer pro_proc_id){
		
		java.lang.String sql = "SELECT MIN(PROI_ID), PROI_PRO_ID, PRO_NAME, PRO_PRICE, PRO_CONTENT FROM PRODUCT_IMAGES join PRODUCT ON PROI_PRO_ID = PRO_ID WHERE PRO_PROC_ID = ? GROUP BY PROI_PRO_ID ORDER BY PRO_PRICE";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProVO proVO = null;
		List<ProVO> list = new ArrayList<ProVO>();
		
		//開始連線
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, pro_proc_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				proVO = new ProVO();
				proVO.setPro_id(rs.getInt("PROI_PRO_ID"));
				proVO.setPro_name(rs.getString("PRO_NAME"));
				proVO.setPro_price(rs.getInt("PRO_PRICE"));
				proVO.setPro_content(rs.getString("PRO_CONTENT"));
				list.add(proVO);
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
	
	@Override
	public List<ProVO> findByCateExpList(Integer pro_proc_id){
		
		java.lang.String sql = "SELECT MIN(PROI_ID), PROI_PRO_ID, PRO_NAME, PRO_PRICE, PRO_CONTENT FROM PRODUCT_IMAGES join PRODUCT ON PROI_PRO_ID = PRO_ID WHERE PRO_PROC_ID = ? GROUP BY PROI_PRO_ID ORDER BY PRO_PRICE DESC";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ProVO proVO = null;
		List<ProVO> list = new ArrayList<ProVO>();
		
		//開始連線
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, pro_proc_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				proVO = new ProVO();
				proVO.setPro_id(rs.getInt("PROI_PRO_ID"));
				proVO.setPro_name(rs.getString("PRO_NAME"));
				proVO.setPro_price(rs.getInt("PRO_PRICE"));
				proVO.setPro_content(rs.getString("PRO_CONTENT"));
				list.add(proVO);
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
	
	@Override
	public List<ProVO> findBySQLList(String pro_name){
		java.lang.String sql = "select * from PRODUCT where 1=1 ";
		StringBuilder sb = new StringBuilder();
		sb.append(sql);
		//查名字
		if(pro_name != null) {
			sb.append(" and pro_name like '%"+pro_name+"%'");
		}
		sql = sb.toString();
		List<ProVO> list = new ArrayList<ProVO>();
		ProVO proVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
	
	//開始連線
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

		while(rs.next()) {
			proVO = new ProVO();
			proVO.setPro_id(rs.getInt("PRO_ID"));
			proVO.setPro_name(rs.getString("PRO_NAME"));
			proVO.setPro_price(rs.getInt("PRO_PRICE"));
			proVO.setPro_content(rs.getString("PRO_CONTENT"));
			list.add(proVO);
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
	
	JdbcTemplate template = new JdbcTemplate(ds); 
	
	@Override
	public List<ProVO> findBySQLList2(Integer pro_price , Integer pro_price2 , Integer pro_proc_id , String pro_name ){
		
	String sb="select * from PRODUCT where pro_price between ? and ? and pro_proc_id = ? and pro_name like '%"+pro_name+"%'";
	String sql= sb.toString();
	List list = template.queryForList(sql , pro_price , pro_price2 , pro_proc_id);
	return list;
	}
	
	@Override
	public List<ProVO> findBySQLList3(Integer pro_price , Integer pro_price2 , Integer pro_proc_id , Integer pro_smem_id , String pro_name ){
		
	String sb="select * from PRODUCT where pro_price between ? and ? and pro_proc_id = ? and pro_smem_id = ? and pro_name like '%"+pro_name+"%'";
	String sql= sb.toString();
	List list = template.queryForList(sql , pro_price , pro_price2 , pro_proc_id , pro_smem_id);
	return list;
	}
	
	@Override
	public List<ProVO> ProMain(Integer pro_proc_id){
	String sql = "select * from PRODUCT where pro_proc_id =? ORDER BY RAND() limit 4";
	List list =  template.queryForList(sql , pro_proc_id);
	return list;
	}
	
	@Override
	public ProVO findBySmemID(Integer pro_smem_id) {
	String sql = "SELECT * FROM PRODUCT WHERE PRO_SMEM_ID = ?";
	ProVO proVO = template.queryForObject(sql, ProVO.class , pro_smem_id );
	return proVO;
		
	}
}