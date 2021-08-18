package com.tag.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.category.model.CategoryVO;
import com.post_tag_ref.model.PostTagRefDAOImpl;
import com.post_tag_ref.model.PostTagRefVO;

public class TagDAOImpl implements TagDAO {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INSERT_TAG = "INSERT INTO CFA101G3.TAG (tag_name) VALUES (?);";
	private static final String UPDATE = "UPDATE CFA101G3.TAG set tag_name=? where tag_id = ?";
	private static final String DELETE = "DELETE FROM CFA101G3.TAG where tag_id = ?";
	private static final String GET_ONE_TAG_BY_TAGID = "SELECT * FROM CFA101G3.TAG where tag_id = ?";
	private static final String GET_ONE_TAG_BY_TAGNAME = "SELECT * FROM CFA101G3.TAG where tag_name = ?";
	private static final String GET_ALL_TAG_BY_TAGID = "SELECT * FROM CFA101G3.TAG order by tag_id";

	public TagVO insertTag(TagVO tagVO) {
		Connection con = null;
		PreparedStatement pstmt = null;
		TagVO addTagVO = null;
		try {
			con = ds.getConnection();
			String cols[] = { "tag_id" }; // 自增主鍵綁定 新增時可得知PK
			pstmt = con.prepareStatement(INSERT_TAG, cols);

			pstmt.setString(1, tagVO.getTag_name());
			pstmt.executeUpdate();
			String tag_id = null;
			ResultSet rs = pstmt.getGeneratedKeys(); // 找到PK
			if (rs.next()) {
				tag_id = rs.getString(1);
				System.out.println(tag_id);
				addTagVO = new TagVO();
				addTagVO.setTag_id(new Integer(tag_id));// 字串轉型Int
				addTagVO.setTag_name(tagVO.getTag_name());
			} else {
				System.out.println("未取得自增主鍵值");
			}
			rs.close();

			System.out.println("新增成功");

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
		return addTagVO;
	}

	@Override
	public void updateTag(TagVO tag) {

	}

	@Override
	public void deleteTag(Integer tag_id) {

	}

	@Override
	public TagVO findOneTagByTagId(Integer tag_id) {
		TagVO tagVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_TAG_BY_TAGID);
//			GET_ONE_TAG_BY_TAGID = "SELECT * FROM tag where tag_id = ?";
			pstmt.setInt(1, tag_id);
			rs = pstmt.executeQuery();

			while (rs.next()) {

				tagVO = new TagVO();
				tagVO.setTag_id(rs.getInt("tag_id"));
				tagVO.setTag_name(rs.getString("tag_name"));

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
		return tagVO;
	}

	@Override
	public List<TagVO> getAllTag() {
		List<TagVO> list = new ArrayList<>();
		TagVO tagVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL_TAG_BY_TAGID);
			rs = pstmt.executeQuery();
			// GET_ALL_TAG_BY_TAGID = "SELECT * FROM tag order by tag_id"; 實際只會查到標籤名稱
			while (rs.next()) {

				tagVO = new TagVO();
				tagVO.setTag_id(rs.getInt("tag_id"));
				tagVO.setTag_name(rs.getString("tag_name"));

				list.add(tagVO);
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

//	GET_ONE_TAG_BY_TAGNAME = "SELECT * FROM tag where tag_name = ?";
	@Override
	public TagVO findOneTagByTagName(String tag_name) {
		TagVO tagVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ONE_TAG_BY_TAGNAME);
			pstmt.setString(1, tag_name);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				tagVO = new TagVO();
				tagVO.setTag_id(rs.getInt("tag_id"));
				tagVO.setTag_name(rs.getString("tag_name"));
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
		return tagVO;

	}

}
