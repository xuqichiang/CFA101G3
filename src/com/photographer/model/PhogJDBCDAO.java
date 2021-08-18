package com.photographer.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.member.model.MemVO;

public class PhogJDBCDAO implements PhogDAO{

	String driver = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/CFA101G3?serverTimezone=Asia/Taipei";
	String userid = "David";
	String passwd = "123456";
	
	private static final String INSERT = "insert into PHOTOGRAPHER (PHOG_NAME, PHOG_STATUS, PHOG_SMEM_ID) values (?, ?, ?)";//新增
	private static final String UPDATE = "update PHOTOGRAPHER set PHOG_NAME=?, PHOG_STATUS=?, PHOG_SMEM_ID=? where PHOG_ID=?";//修改
	private static final String FIND_BY_ONE= "select * from  PHOTOGRAPHER where PHOG_ID = ?";//查單筆
	private static final String FIND_BY_ALL = "select * from  PHOTOGRAPHER order by PHOG_ID";//查多筆
	
	//新增
	@Override
	public void insert(PhogVO phogVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(INSERT);
			
			pstmt.setString(1, phogVO.getPhog_name());
			pstmt.setInt(2, phogVO.getPhog_status());
			pstmt.setInt(3, phogVO.getPhog_smem_id());
			
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver." + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
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
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}
	
	//修改
	@Override
	public void update(PhogVO phogVO) {
		
		Connection con = null;
		PreparedStatement pstmt = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(UPDATE);
			
			pstmt.setString(1, phogVO.getPhog_name());
			pstmt.setInt(2, phogVO.getPhog_status());
			pstmt.setInt(3, phogVO.getPhog_smem_id());
			pstmt.setInt(4, phogVO.getPhog_id());
			
			pstmt.executeUpdate();
			
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver." + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured." + se.getMessage());
		} finally {
			if (pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con!=null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
	}
	//查詢(單筆)
	@Override
	public PhogVO findByPhogId(Integer phog_id) {
		
		PhogVO phogVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(FIND_BY_ONE);
			
			pstmt.setInt(1, phog_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				phogVO = new PhogVO();
				phogVO.setPhog_id(rs.getInt("phog_id"));
				phogVO.setPhog_name(rs.getString("phog_name"));
				phogVO.setPhog_status(rs.getInt("phog_status"));
				phogVO.setPhog_smem_id(rs.getInt("phog_smem_id"));
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs!=null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con!=null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
		return phogVO;
	}
	
	//查詢(多筆)
	@Override
	public List<PhogVO> getAll() {
		List<PhogVO> list = new ArrayList<PhogVO>();
		PhogVO phogVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, userid, passwd);
			pstmt = con.prepareStatement(FIND_BY_ALL);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				phogVO = new PhogVO();
				phogVO.setPhog_id(rs.getInt("phog_id"));
				phogVO.setPhog_name(rs.getString("phog_name"));
				phogVO.setPhog_status(rs.getInt("phog_status"));
				phogVO.setPhog_smem_id(rs.getInt("phog_smem_id"));
				list.add(phogVO);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Couldn't load database driver. " + e.getMessage());
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
		} finally {
			if (rs!=null) {
				try {
					rs.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
			if (con!=null) {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	
	@Override
	public List<PhogVO> findByForeignKey(Integer phog_smem_id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public static void main(String[] args) {
		PhogJDBCDAO dao = new PhogJDBCDAO();
		//新增
		PhogVO phogVO1 = new PhogVO();
		phogVO1.setPhog_name("肥鄒");
		phogVO1.setPhog_status(new Integer(1));
		phogVO1.setPhog_smem_id(new Integer(4));
		dao.insert(phogVO1);
		
		//修改
		PhogVO phogVO2 = new PhogVO();
		phogVO2.setPhog_id(10);
		phogVO2.setPhog_name("水肥鄒");
		phogVO2.setPhog_status(new Integer(0));
		phogVO2.setPhog_smem_id(new Integer(3));
		dao.update(phogVO2);
		
		//查詢(單筆)
		PhogVO phogVO3 = dao.findByPhogId(3);
		System.out.print(phogVO3.getPhog_id()+"\t");
		System.out.print(phogVO3.getPhog_name()+"\t");
		System.out.print(phogVO3.getPhog_status()+"\t");
		System.out.print(phogVO3.getPhog_smem_id()+"\t");
		System.out.println();
		System.out.println("-----------------------------");
		
		
		//查詢(多筆)
		List<PhogVO> list = dao.getAll();
		for(PhogVO phog : list) {
			System.out.print(phog.getPhog_id()+"\t");
			System.out.print(phog.getPhog_name()+"\t");
			System.out.print(phog.getPhog_status()+"\t");
			System.out.print(phog.getPhog_smem_id()+"\t");
			System.out.println();
		}
	}

	@Override
	public Map getShopInfo(Integer phog_smem_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List findWorkCount(Integer phop_smem_id) {
		// TODO Auto-generated method stub
		return null;
	}

}