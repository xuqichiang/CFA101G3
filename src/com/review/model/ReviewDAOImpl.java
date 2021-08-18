package com.review.model;

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
import com.message.model.MessageVO;
import com.tag.model.TagVO;

public class ReviewDAOImpl implements ReviewDAO {
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT = "INSERT INTO REVIEW (SMEM_ID, BMEM_ID, REV_CONTENT, REV_TIME, REV_SCORE) VALUES(?, ?, ?, ?, ?)";
	private static final String GET_ALL_BY_SMEM_ID = "select * from REVIEW  where SMEM_ID = ? order by REV_TIME desc";
    
	//以評價數量排序
	private static final String REVIEW_COUNT_SORT = "select SMEM_ID, AVG(REV_SCORE),COUNT(SMEM_ID) from REVIEW group by SMEM_ID order by count(*) desc;";
	
	//以平均星數排序(只取整數)
//	private static final String REVIEW_SCORE_SORT = "select SMEM_ID, AVG(REV_SCORE) from REVIEW group by SMEM_ID order by AVG(REV_SCORE) desc;";
	
	
	
	@Override
	public void insert(ReviewVO reviewVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(INSERT);
			
			pstmt.setInt(1, reviewVO.getSmem_id());
			pstmt.setInt(2, reviewVO.getBmem_id());
			pstmt.setString(3, reviewVO.getRev_content());
			pstmt.setTimestamp(4, reviewVO.getRev_time());
			pstmt.setInt(5, reviewVO.getRev_score());
			pstmt.executeUpdate();
			System.out.println("新增評價成功");
			
		} catch (Exception se) {
			se.printStackTrace();
			if(con!=null) {
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			throw new RuntimeException("A database error occured. " + se.getMessage());
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
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
		



	@Override
	public List get_all_by_smem_id(Integer smem_id) {
		List list = new ArrayList();
		ReviewVO reviewVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
//GET_ALL_BY_SMEM_ID = "select * from REVIEW  where SMEM_ID = ? order by REV_TIME desc";
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_BY_SMEM_ID);
			pstmt.setInt(1, smem_id);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Map map = new HashMap();
				map.put("SMEM_ID", rs.getInt("smem_id"));
				map.put("BMEM_ID", rs.getInt("bmem_id"));
				map.put("REV_CONTENT", rs.getString("rev_content"));
				map.put("REV_TIME", rs.getDate("rev_time"));
				map.put("REV_SCORE", rs.getInt("rev_score"));
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



	//評價數最多排到最少
	@Override
	public List review_count_sort() {
		List list = new ArrayList();
		ReviewVO reviewVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
//REVIEW_COUNT_SORT = "select SMEM_ID, AVG(REV_SCORE),COUNT(SMEM_ID) from REVIEW group by SMEM_ID order by count(*) desc;";
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(REVIEW_COUNT_SORT);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				Map map = new HashMap();
				map.put("SMEM_ID", rs.getInt("smem_id"));
				map.put("AVG_SCORE", rs.getInt("avg(rev_score)")); 
				map.put("REV_COUNT", rs.getInt("count(smem_id)"));
				list.add(map);
			}
		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
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



}
