package com.post.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.post_tag_ref.model.PostTagRefDAOImpl;
import com.post_tag_ref.model.PostTagRefJoinVO;
import com.post_tag_ref.model.PostTagRefVO;

import com.tag.model.TagService;
import com.tag.model.TagVO;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;

public class PostDAOImpl implements PostDAO {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	private static final String INSERT = "INSERT INTO CFA101G3.POST (POST_TITLE, POST_CONTENT, POST_TIME, POST_CAT_ID, POST_MEM_ID) VALUES(?, ?, ?, ?, ?)";
	private static final String UPDATE = "UPDATE CFA101G3.POST set POST_TITLE=?, POST_CONTENT=?, POST_TIME=?, POST_CAT_ID=?, POST_MEM_ID=?, POST_STATUS=?  where post_id = ?";
	private static final String DELETE = "DELETE FROM CFA101G3.POST where POST_ID = ?";
	private static final String GET_BY_POST_ID = "select p.POST_ID,p.POST_TITLE,p.POST_CONTENT,p.POST_TIME,c.CAT_ID, c.CAT_NAME,m.MEM_NAME,m.MEM_HEADSHOT,p.POST_STATUS from `MEMBER` m join CFA101G3.POST p on m.MEM_ID = p.POST_MEM_ID join CATEGORY c on p.POST_CAT_ID = c.CAT_ID where POST_ID = ? order by POST_ID";
	private static final String GET_BY_CAT_ID = "select p.POST_ID,p.POST_TITLE,p.POST_CONTENT,p.POST_TIME, c.CAT_ID,c.CAT_NAME,m.MEM_NAME,m.MEM_HEADSHOT,p.POST_STATUS,m.MEM_ID from `MEMBER` m join CFA101G3.POST p on m.MEM_ID = p.POST_MEM_ID join CFA101G3.CATEGORY c on p.POST_CAT_ID = c.CAT_ID where CAT_ID = ? and POST_STATUS = 1 order by POST_ID desc";
	private static final String GET_ALL = "SELECT * FROM CFA101G3.POST WHERE POST_STATUS = 1 order by POST_ID";
	private static final String UPDATE_POST_STATUS = "UPDATE CFA101G3.POST set POST_STATUS=0 where POST_ID= ?"; // 0隱藏
	 //join版的表格要設定order by POST_ID 才會抓到最新的文章(不然會抓到最新的會員)
	private static final String GET_POST = "select p.POST_ID,p.POST_TITLE,p.POST_CONTENT,p.POST_TIME, c.CAT_NAME,m.MEM_NAME,m.MEM_HEADSHOT,p.POST_STATUS from `MEMBER` m join CFA101G3.POST p on m.MEM_ID = p.POST_MEM_ID join CFA101G3.CATEGORY c on p.POST_CAT_ID = c.CAT_ID WHERE POST_STATUS = 1 order by POST_ID";
 
	//找文章總數
	private static final String Find_Total_Count = "SELECT COUNT(*) FROM CFA101G3.POST where POST_STATUS = 1";
	//當下分頁的起始 與每頁顯示的文章數
	private static final String Find_By_Page = "select p.POST_ID,p.POST_TITLE,p.POST_CONTENT,p.POST_TIME, c.CAT_NAME,m.MEM_NAME,m.MEM_HEADSHOT,p.POST_STATUS,m.MEM_ID from `MEMBER` m join CFA101G3.POST p on m.MEM_ID = p.POST_MEM_ID join CFA101G3.CATEGORY c on p.POST_CAT_ID = c.CAT_ID where POST_STATUS = 1 order by POST_ID desc LIMIT ? , ?";
	//用作者ID找文章資訊
	private static final String Find_By_Writer = "SELECT * FROM CFA101G3.POST WHERE POST_MEM_ID = ?";
	
	
	public void insert(PostVO post, List<TagVO> addTag) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			con.setAutoCommit(false);
			String[] col = { "post_id" };//自增主鍵綁定 新增時可得知PK
			pstmt = con.prepareStatement(INSERT, col);

			pstmt.setString(1, post.getPost_title());
			pstmt.setString(2, post.getPost_content());
			pstmt.setTimestamp(3, post.getPost_time());
			pstmt.setInt(4, post.getPost_cat_id());
			pstmt.setInt(5, post.getPost_mem_id());
			pstmt.executeUpdate();
			
			String post_id = null;
			ResultSet rs = pstmt.getGeneratedKeys(); //找到自增主鍵值(PK)
			if (rs.next()) {
				post_id = rs.getString(1);//取得第一列的值(最新的一筆)
				System.out.println("post_id="+post_id);

			} else {
				System.out.println("未取得自增主鍵值");
			}
			rs.close();
			System.out.println("Post新增成功");

			PostTagRefDAOImpl postTagRefDAO = new PostTagRefDAOImpl();
			for (TagVO tagVO : addTag) {
				PostTagRefVO postTagRefVO = new PostTagRefVO();
				postTagRefVO.setPtr_post_id(new Integer(post_id));
				postTagRefVO.setPtr_tag_id(tagVO.getTag_id());
				System.out.println(postTagRefVO);
				System.out.println(con);
				
				postTagRefDAO.insert(postTagRefVO,con);
			}
			con.commit();
			con.setAutoCommit(true);

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

//修改資料
	public void update(PostVO postVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

		// "UPDATE POST set POST_TITLE=?, POST_CONTENT=?,
		// POST_TIME=?, POST_CAT_ID=?, POST_MEM_ID=?, POST_STATUS=? where post_id = ?";
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE);

			pstmt.setString(1, postVO.getPost_title());
			pstmt.setString(2, postVO.getPost_content());
			pstmt.setTimestamp(3, postVO.getPost_time());
			pstmt.setInt(4, postVO.getPost_cat_id());
			pstmt.setInt(5, postVO.getPost_mem_id());
			pstmt.setInt(6, postVO.getPost_status());
			pstmt.setInt(7, postVO.getPost_id());

			pstmt.executeUpdate();

		} catch (SQLException se) {
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

	public void delete(Integer post_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		// "DELETE FROM POST where POST_ID = ?"

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DELETE);

			pstmt.setInt(1, post_id);
			pstmt.executeUpdate();

		} catch (SQLException se) {
			throw new RuntimeException("A database error occured. " + se.getMessage());
			// Clean up JDBC resources
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

	//用post_id抓一則文章資訊(join版本)
	public Map findByPostId(Integer post_id) {
		Map map = null;
		PostVO postVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
//	GET_BY_POST_ID = "select p.POST_ID,p.POST_TITLE,p.POST_CONTENT,p.POST_TIME, "
//	+ "c.CAT_NAME,m.MEM_NAME,m.MEM_HEADSHOT,p.POST_STATUS from MEMBER m "
//	+ "join POST p on m.MEM_ID = p.POST_MEM_ID join CATEGORY c on p.POST_CAT_ID = c.CAT_ID "
//	+ "where POST_ID = ? order by POST_ID";
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_POST_ID);
			pstmt.setInt(1, post_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				map = new HashMap();
				map.put("POST_ID", rs.getInt("post_id"));
				map.put("POST_TITLE", rs.getString("post_title"));
				map.put("POST_CONTENT", rs.getString("post_content"));
				map.put("POST_TIME", rs.getTimestamp("post_time"));
				map.put("CAT_ID", rs.getString("cat_id"));
				map.put("CAT_NAME", rs.getString("cat_name"));
				map.put("MEM_NAME", rs.getString("mem_name"));
				map.put("MEM_HEADSHOT", rs.getBytes("mem_headshot"));
				map.put("POST_STATUS", rs.getInt("post_status"));
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
		return map;
	}
	
	
	

	public List<PostVO> getAll() {
		List<PostVO> list = new ArrayList<PostVO>();
		PostVO post = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();

			while (rs.next()) {
//	GET_ALL = "SELECT * FROM POST order by POST_ID";		
				post = new PostVO();
				post.setPost_id(rs.getInt("post_id"));
				post.setPost_title(rs.getString("post_title"));
				post.setPost_content(rs.getString("post_content"));
				post.setPost_time(rs.getTimestamp("post_time"));
				post.setPost_cat_id(rs.getInt("post_cat_id"));
				post.setPost_mem_id(rs.getInt("post_mem_id"));
				post.setPost_status(rs.getInt("post_status"));
				list.add(post);
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

	public void updatePostStatus(PostVO postVO) {

		Connection con = null;
		PreparedStatement pstmt = null;

//UPDATE_POST_STATUS = "UPDATE POST set POST_STATUS=0 where POST_ID"; //0隱藏
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(UPDATE_POST_STATUS);
			pstmt.setInt(1, postVO.getPost_id());

			pstmt.executeUpdate();

		} catch (SQLException se) {
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

	public List getPost() {
		List list = new ArrayList();
		PostVO postAll = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_POST);
			rs = pstmt.executeQuery();
//GET_POST = "select p.POST_ID,p.POST_TITLE,p.POST_CONTENT,p.POST_TIME, c.CAT_NAME,m.MEM_NAME,m.MEM_HEADSHOT,p.POST_STATUS from MEMBER m join POST p on m.MEM_ID = p.POST_MEM_ID join CATEGORY c on p.POST_CAT_ID = c.CAT_ID order by POST_ID";
			while (rs.next()) {
				Map map = new HashMap();
				map.put("POST_ID", rs.getInt("post_id"));
				map.put("POST_TITLE", rs.getString("post_title"));
				map.put("POST_CONTENT", rs.getString("post_content"));
				map.put("POST_TIME", rs.getTimestamp("post_time"));
				map.put("CAT_NAME", rs.getString("cat_name"));
				map.put("MEM_NAME", rs.getString("mem_name"));
				map.put("MEM_HEADSHOT", rs.getBytes("mem_headshot"));
				map.put("POST_STATUS", rs.getInt("post_status"));
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

	@Override
	public List findByCatId(Integer cat_id) {
		List list = new ArrayList();
		Map map = null;
		PostVO postVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;	
//GET_BY_CAT_ID = "select p.POST_ID,p.POST_TITLE,p.POST_CONTENT,p.POST_TIME, c.CAT_ID,c.CAT_NAME,
//m.MEM_NAME,m.MEM_HEADSHOT,p.POST_STATUS,m.MEM_ID from MEMBER m join POST p on m.MEM_ID = p.POST_MEM_ID 
//join CATEGORY c on p.POST_CAT_ID = c.CAT_ID where CAT_ID = ? order by POST_ID desc";		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_CAT_ID);
			pstmt.setInt(1, cat_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				
				map = new HashMap();
				map.put("POST_ID", rs.getInt("post_id"));
				map.put("POST_TITLE", rs.getString("post_title"));
				map.put("POST_CONTENT", rs.getString("post_content"));
				map.put("POST_TIME", rs.getTimestamp("post_time"));
				map.put("CAT_ID", rs.getInt("cat_id"));
				map.put("CAT_NAME", rs.getString("cat_name"));
				map.put("MEM_NAME", rs.getString("mem_name"));
				map.put("POST_STATUS", rs.getInt("post_status"));
				map.put("MEM_ID", rs.getInt("mem_id"));
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

	@Override
	public Integer findTotalCount() {;
//		PostPageVO postPageVO  = new PostPageVO();
	    Integer postCount =null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			//Find_Total_Count = "SELECT COUNT(*) FROM POST";	
			con = ds.getConnection();
			pstmt = con.prepareStatement(Find_Total_Count);
			rs = pstmt.executeQuery();
			rs.next();
			postCount =  rs.getInt(1);

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
		
		return postCount;
	}

	@Override
	public List findByPage(Integer start, Integer rows) {
		List list = new ArrayList();
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;		
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(Find_By_Page);
			pstmt.setInt(1,start); 
			pstmt.setInt(2,rows ); 
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Map map = new HashMap();
				map.put("POST_ID", rs.getInt("post_id"));
				map.put("POST_TITLE", rs.getString("post_title"));
				map.put("POST_CONTENT", rs.getString("post_content"));
				map.put("POST_TIME", rs.getTimestamp("post_time"));
				map.put("CAT_NAME", rs.getString("cat_name"));
				map.put("MEM_NAME", rs.getString("mem_name"));
				map.put("POST_STATUS", rs.getInt("post_status"));
				map.put("MEM_ID", rs.getInt("mem_id"));
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

	
	
	// 根據作者ID尋找文章們
	@Override
	public List findByWriter(Integer post_mem_id) {
//		Find_By_Writer = "SELECT * FROM POST WHERE POST_MEM_ID = ?"
		List list = new ArrayList();
		PostVO postVO =null;
		Connection con =null;
		PreparedStatement pstmt =null;
		ResultSet rs = null;
		//連線開始
		try {
			con = ds.getConnection();
			pstmt = con.prepareCall(Find_By_Writer);
			pstmt.setInt(1, post_mem_id);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				postVO = new PostVO();
				postVO.setPost_id(rs.getInt("post_id"));
				postVO.setPost_title(rs.getString("post_title"));
				postVO.setPost_content(rs.getString("post_content"));
				postVO.setPost_time(rs.getTimestamp("post_time"));
				postVO.setPost_cat_id(rs.getInt("post_cat_id"));
				postVO.setPost_mem_id(rs.getInt("post_mem_id"));
				postVO.setPost_status(rs.getInt("post_status"));
				list.add(postVO);
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
}