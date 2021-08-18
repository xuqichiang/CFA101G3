package com.shop_order_item.model;

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

import com.product.model.ProDAOimpl;
import com.product.model.ProVO;

public class SpoiDAOImpl implements SpoiDAO{
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	
	private static final String INSERT = "insert into SHOP_ORDER_ITEM (spo_id,pro_id,spoi_quantity,spoi_totalprice) values (?,?,?,?)";
	private static final String GETALL = "select * from SHOP_ORDER_ITEM";
	private static final String GET_BY_SPO_ID = "select * from SHOP_ORDER_ITEM where spo_id = ?";

	//新增商品訂單明細(產生商品訂單時用)
	@Override
	public void insertByShopOrder(SpoiVO spoiVO, Connection con) {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(INSERT);
			pstmt.setInt(1, spoiVO.getSpo_id());
			pstmt.setInt(2, spoiVO.getProvo().getPro_id());
			pstmt.setInt(3, spoiVO.getSpoi_quantity());
			pstmt.setInt(4, spoiVO.getSpoi_totalprice());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			if(con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//獲取全部商品訂單明細
	@Override
	public List<SpoiVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SpoiVO spoiVO = null;
		List<SpoiVO> list = new ArrayList<SpoiVO>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GETALL);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				spoiVO = new SpoiVO();
				ProDAOimpl dao = new ProDAOimpl();
				ProVO proVO = dao.findByPK(rs.getInt("PRO_ID"));
				spoiVO.setSpo_id(rs.getInt("SPO_ID"));
				spoiVO.setProvo(proVO);
				spoiVO.setSpoi_quantity(rs.getInt("SPOI_QUANTITY"));
				spoiVO.setSpoi_totalprice(rs.getInt("SPOI_TOTALPRICE"));
				list.add(spoiVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(rs != null){
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
	
	//利用spo_id獲取商品訂單明細
	@Override
	public List<SpoiVO> getBySpo_id(Integer spo_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SpoiVO spoiVO = null;
		List<SpoiVO> list = new ArrayList<SpoiVO>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_BY_SPO_ID);
			pstmt.setInt(1, spo_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				spoiVO = new SpoiVO();
				ProDAOimpl dao = new ProDAOimpl();
				ProVO proVO = dao.findByPK(rs.getInt("PRO_ID"));
				spoiVO.setSpo_id(rs.getInt("SPO_ID"));
				spoiVO.setProvo(proVO);
				spoiVO.setSpoi_quantity(rs.getInt("SPOI_QUANTITY"));
				spoiVO.setSpoi_totalprice(rs.getInt("SPOI_TOTALPRICE"));
				list.add(spoiVO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			if(rs != null){
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
