package com.shop_order.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.product.model.ProVO;
import com.shop_order_item.model.SpoiDAOImpl;
import com.shop_order_item.model.SpoiVO;

public class SpoDAOImpl implements SpoDAO{
	
	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/CFA101G3");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	private static final String INSERT = "insert into SHOP_ORDER (spo_time,spo_payment,spo_postage,spo_bmem_id,spo_smem_id,spo_receiver_name,spo_receiver_phone,spo_receiver_address,spo_paytype,spo_status,spo_pay_status,spo_cargo_status) values(?,?,?,?,?,?,?,?,?,?,?,?)";
	private static final String UPDATE_SPO_PAY_STATUS = "update SHOP_ORDER set spo_pay_status = ? where spo_id = ?";
	private static final String GETALL = "select * from SHOP_ORDER";
	private static final String GETALL_BY_BMEM = "select * from SHOP_ORDER where SPO_BMEM_ID = ?";


	//新增商品訂單(需藉由別人的連線)(單筆)
	@Override
	public Integer insert(SpoVO spoVO, List<SpoiVO> spoiVOList,Connection con) {
		PreparedStatement pstmt = null;
		Integer spo_id = null;
		try {
			String[] cols = {"SPO_ID"};
			pstmt = con.prepareStatement(INSERT,cols);
			pstmt.setTimestamp(1, spoVO.getSpo_time());
			pstmt.setInt(2, spoVO.getSpo_payment());
			pstmt.setInt(3, spoVO.getSpo_postage());
			pstmt.setInt(4, spoVO.getSpo_bmem_id());
			pstmt.setInt(5, spoVO.getSpo_smem_id());
			pstmt.setString(6, spoVO.getSpo_receiver_name());
			pstmt.setString(7, spoVO.getSpo_receiver_phone());
			pstmt.setString(8, spoVO.getSpo_receiver_address());
			pstmt.setInt(9, spoVO.getSpo_paytype());
			pstmt.setInt(10, spoVO.getSpo_status());
			pstmt.setInt(11, spoVO.getSpo_pay_status());
			pstmt.setInt(12, spoVO.getSpo_cargo_status());
			pstmt.executeUpdate();
			
			//取得訂單ID
			String next_spo_id = null;
			ResultSet rs = pstmt.getGeneratedKeys();
			rs.next();
			next_spo_id = rs.getString(1);
			rs.close();
			spo_id = Integer.parseInt(next_spo_id);
			
			
			//新增商品明細
			SpoiDAOImpl dao = new SpoiDAOImpl();
			for (SpoiVO spoiVO : spoiVOList) {
				spoiVO.setSpo_id(Integer.parseInt(next_spo_id));
				dao.insertByShopOrder(spoiVO, con);
			}
			
			System.out.println("success");
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
		return spo_id;
	}
	//新增多筆訂單
	@Override
	public List<Integer> insertMultiple(HashMap<Integer, List<SpoiVO>> smemMap, String name, String phone, Integer paytype,
			HashMap<Integer,Integer> postageMap, String address, Integer bmem_id) {
		Connection con = null;
		SpoVO spoVO = null;
		List<Integer> spo_idList = new ArrayList<Integer>();
		try {
			con = ds.getConnection();
			con.setAutoCommit(false);//開啟交易模式
			 Set<Integer> keySet = smemMap.keySet();
			 for (Integer smem_id : keySet) {
				 	Integer payment = 0;//給定實付金額初值
				 	List<SpoiVO> list = smemMap.get(smem_id);//獲取訂單明細VO
				 	for(SpoiVO spoiVO : list) {
				 		payment += spoiVO.getSpoi_totalprice();//訂單明細金額加總
				 	}
				 	payment += postageMap.get(smem_id);//實付金額加上運費
				 	spoVO = new SpoVO();
					spoVO.setSpo_time(new Timestamp(new Date().getTime()));
					spoVO.setSpo_payment(payment);
					spoVO.setSpo_postage(postageMap.get(smem_id));
					spoVO.setSpo_bmem_id(bmem_id);
					spoVO.setSpo_smem_id(smem_id);
					spoVO.setSpo_receiver_name(name);
					spoVO.setSpo_receiver_phone(phone);
					spoVO.setSpo_receiver_address(address);
					spoVO.setSpo_paytype(paytype);
					spoVO.setSpo_status(0);
					spoVO.setSpo_pay_status(0);
					spoVO.setSpo_cargo_status(0);
					Integer spo_id = insert(spoVO, list, con);//新增單筆訂單
					spo_idList.add(spo_id);//訂單ID加到List
			}
			con.commit();//交易完成
			con.setAutoCommit(true);//關閉交易
			System.out.println("全部訂單新增完成");
		} catch (SQLException e) {
			if(con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		} finally {
			if(con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return spo_idList;
	}
	
	//更新多筆訂單付款狀態
	@Override
	public void updateAllSpo_pay_status(List<Integer> spo_idList, Integer spo_pay_status) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
			for(Integer spo_id : spo_idList) {
				pstmt = con.prepareStatement(UPDATE_SPO_PAY_STATUS);
				pstmt.setInt(1, spo_pay_status);
				pstmt.setInt(2, spo_id);
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
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
	}
	
	//查詢所有訂單
	@Override
 	public List<SpoVO> getOrdall(Integer spo_smem_id){
		
		//準備好 SQL list vo con pstmt rs 1.宣告2.取值3.拿來用
				java.lang.String GETALL = "SELECT * FROM SHOP_ORDER WHERE SPO_SMEM_ID = ?";
				List<SpoVO> list = new ArrayList<SpoVO>();
				SpoVO spoVO = null;
				Connection con = null;
				PreparedStatement pstmt =null;
				ResultSet rs = null;
				//連線開始
				try {
					con = ds.getConnection();
					pstmt = con.prepareStatement(GETALL);
					pstmt.setInt(1, spo_smem_id);
					rs = pstmt.executeQuery();
					//集合集撈出每一筆
					while(rs.next()) {
						spoVO = new SpoVO();
						spoVO.setSpo_id(rs.getInt("spo_id"));
						//時間地雷
						spoVO.setSpo_time(rs.getTimestamp("spo_time")); 
						spoVO.setSpo_payment(rs.getInt("spo_payment"));
						spoVO.setSpo_postage(rs.getInt("spo_postage"));
						spoVO.setSpo_bmem_id(rs.getInt("spo_bmem_id"));
//						spoVO.setSpo_smem_id(rs.getInt("spo_smem_id"));
						spoVO.setSpo_receiver_name(rs.getString("spo_receiver_name"));
						spoVO.setSpo_receiver_phone(rs.getString("spo_receiver_phone"));
						spoVO.setSpo_receiver_address(rs.getString("spo_receiver_address"));
						spoVO.setSpo_paytype(rs.getInt("spo_paytype"));
						spoVO.setSpo_status(rs.getInt("spo_status"));
						spoVO.setSpo_pay_status(rs.getInt("spo_pay_status"));
						spoVO.setSpo_cargo_status(rs.getInt("spo_cargo_status"));

						list.add(spoVO);
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
	
	//查詢單一訂單
	@Override
	public SpoVO findByPK(Integer spo_id) {
		//準備好 SQL list VO con pstmt rs 1.宣告2.取值3.拿來用
				java.lang.String SQL ="SELECT * FROM SHOP_ORDER WHERE SPO_ID = ?";
				SpoVO spoVO =null;
				Connection con =null;
				PreparedStatement pstmt =null;
				ResultSet rs = null;
				//連線開始
				try {
					con = ds.getConnection();
					pstmt = con.prepareStatement(SQL);
					pstmt.setInt(1, spo_id);
					rs = pstmt.executeQuery();
					
					while(rs.next()) {
						spoVO = new SpoVO();
						spoVO.setSpo_id(rs.getInt("spo_id"));
						spoVO.setSpo_time(rs.getTimestamp("spo_time")); 
						spoVO.setSpo_payment(rs.getInt("spo_payment"));
						spoVO.setSpo_postage(rs.getInt("spo_postage"));
						spoVO.setSpo_bmem_id(rs.getInt("spo_bmem_id"));
						spoVO.setSpo_smem_id(rs.getInt("spo_smem_id"));
						spoVO.setSpo_receiver_name(rs.getString("spo_receiver_name"));
						spoVO.setSpo_receiver_phone(rs.getString("spo_receiver_phone"));
						spoVO.setSpo_receiver_address(rs.getString("spo_receiver_address"));
						spoVO.setSpo_paytype(rs.getInt("spo_paytype"));
						spoVO.setSpo_status(rs.getInt("spo_status"));
						spoVO.setSpo_pay_status(rs.getInt("spo_pay_status"));
						spoVO.setSpo_cargo_status(rs.getInt("spo_cargo_status"));
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
				};
				return spoVO;
			}
	
	//查詢貨況訂單
	@Override
 	public List<SpoVO> getOrdReturn(Integer spo_smem_id,Integer spo_cargo_status){
		
		//準備好 SQL list vo con pstmt rs 1.宣告2.取值3.拿來用
				java.lang.String SQL = "SELECT * FROM SHOP_ORDER WHERE SPO_SMEM_ID = ? AND SPO_CARGO_STATUS = ?";
				List<SpoVO> list = new ArrayList<SpoVO>();
				SpoVO spoVO = null;
				Connection con = null;
				PreparedStatement pstmt =null;
				ResultSet rs = null;
				//連線開始
				try {
					con = ds.getConnection();
					pstmt = con.prepareStatement(SQL);
					pstmt.setInt(1, spo_smem_id);
					pstmt.setInt(2, spo_cargo_status);
					rs = pstmt.executeQuery();
					//集合集撈出每一筆
					while(rs.next()) {
						spoVO = new SpoVO();
						spoVO.setSpo_id(rs.getInt("spo_id"));
						spoVO.setSpo_time(rs.getTimestamp("spo_time")); 
						spoVO.setSpo_payment(rs.getInt("spo_payment"));
						spoVO.setSpo_postage(rs.getInt("spo_postage"));
						spoVO.setSpo_bmem_id(rs.getInt("spo_bmem_id"));
//						spoVO.setSpo_smem_id(rs.getInt("spo_smem_id"));
						spoVO.setSpo_receiver_name(rs.getString("spo_receiver_name"));
						spoVO.setSpo_receiver_phone(rs.getString("spo_receiver_phone"));
						spoVO.setSpo_receiver_address(rs.getString("spo_receiver_address"));
						spoVO.setSpo_paytype(rs.getInt("spo_paytype"));
						spoVO.setSpo_status(rs.getInt("spo_status"));
						spoVO.setSpo_pay_status(rs.getInt("spo_pay_status"));
						spoVO.setSpo_cargo_status(rs.getInt("spo_cargo_status"));

						list.add(spoVO);
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
	
	//查詢付款訂單
	public List<SpoVO> getOrdRefund(Integer spo_smem_id,Integer spo_pay_status){
	
	//準備好 SQL list vo con pstmt rs 1.宣告2.取值3.拿來用
	java.lang.String SQL = "SELECT * FROM SHOP_ORDER WHERE SPO_SMEM_ID = ? AND SPO_PAY_STATUS = ?";
	List<SpoVO> list = new ArrayList<SpoVO>();
	SpoVO spoVO = null;
	Connection con = null;
	PreparedStatement pstmt =null;
	ResultSet rs = null;
	//連線開始
	try {
		con = ds.getConnection();
		pstmt = con.prepareStatement(SQL);
		pstmt.setInt(1, spo_smem_id);
		pstmt.setInt(2, spo_pay_status);
		rs = pstmt.executeQuery();
		//集合集撈出每一筆
		while(rs.next()) {
			spoVO = new SpoVO();
			spoVO.setSpo_id(rs.getInt("spo_id"));
			spoVO.setSpo_time(rs.getTimestamp("spo_time")); 
			spoVO.setSpo_payment(rs.getInt("spo_payment"));
			spoVO.setSpo_postage(rs.getInt("spo_postage"));
			spoVO.setSpo_bmem_id(rs.getInt("spo_bmem_id"));
//			spoVO.setSpo_smem_id(rs.getInt("spo_smem_id"));
			spoVO.setSpo_receiver_name(rs.getString("spo_receiver_name"));
			spoVO.setSpo_receiver_phone(rs.getString("spo_receiver_phone"));
			spoVO.setSpo_receiver_address(rs.getString("spo_receiver_address"));
			spoVO.setSpo_paytype(rs.getInt("spo_paytype"));
			spoVO.setSpo_status(rs.getInt("spo_status"));
			spoVO.setSpo_pay_status(rs.getInt("spo_pay_status"));
			spoVO.setSpo_cargo_status(rs.getInt("spo_cargo_status"));

			list.add(spoVO);
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

	
	//獲取單筆訂單(捷哥)
	@Override
	public SpoVO getOne(Integer spo_id) {
		String GETONE = "select * from SHOP_ORDER where spo_id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SpoVO spoVO = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GETONE);
			pstmt.setInt(1, spo_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				spoVO = new SpoVO();
				spoVO.setSpo_id(rs.getInt("SPO_ID"));
				spoVO.setSpo_time(rs.getTimestamp("SPO_TIME"));
				spoVO.setSpo_payment(rs.getInt("SPO_PAYMENT"));
				spoVO.setSpo_postage(rs.getInt("SPO_POSTAGE"));
				spoVO.setSpo_bmem_id(rs.getInt("SPO_BMEM_ID"));
				spoVO.setSpo_smem_id(rs.getInt("SPO_SMEM_ID"));
				spoVO.setSpo_receiver_name(rs.getString("SPO_RECEIVER_NAME"));
				spoVO.setSpo_receiver_phone(rs.getString("SPO_RECEIVER_PHONE"));
				spoVO.setSpo_receiver_address(rs.getString("SPO_RECEIVER_ADDRESS"));
				spoVO.setSpo_paytype(rs.getInt("SPO_PAYTYPE"));
				spoVO.setSpo_status(rs.getInt("SPO_STATUS"));
				spoVO.setSpo_pay_status(rs.getInt("SPO_PAY_STATUS"));
				spoVO.setSpo_cargo_status(rs.getInt("SPO_CARGO_STATUS"));
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

		return spoVO;
	}
	

	
	
	JdbcTemplate template = new JdbcTemplate(ds);
	//更新訂單狀態
	@Override
	public void update_spo_status(Integer spo_id, Integer spo_status) {
		String sql = "update SHOP_ORDER set spo_status = ? where spo_id = ?";
		template.update(sql,spo_status,spo_id);
	}
	
	//更新付款狀態
	@Override
	public void update_spo_pay_status(Integer spo_id, Integer spo_pay_status) {
		String sql = "update SHOP_ORDER set spo_pay_status = ? where spo_id = ?";
		template.update(sql,spo_pay_status,spo_id);
	}
	
	//更新發貨狀態
	@Override
	public void update_spo_cargo_status(Integer spo_id, Integer spo_cargo_status) {
		String sql = "update SHOP_ORDER set spo_cargo_status = ? where spo_id = ?";
		template.update(sql,spo_cargo_status,spo_id);
	}
	
	//更新付款方式
	@Override
	public void update_spo_paytype(Integer spo_id, Integer spo_paytype) {
		String sql = "update SHOP_ORDER set spo_paytype = ? where spo_id = ?";
		template.update(sql,spo_paytype,spo_id);
	}
	
	//獲取所有訂單
	@Override
	public List<SpoVO> getAll() {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SpoVO spoVO = null;
		List<SpoVO> list = new ArrayList<SpoVO>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GETALL);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				spoVO = new SpoVO();
				spoVO.setSpo_id(rs.getInt("SPO_ID"));
				spoVO.setSpo_time(rs.getTimestamp("SPO_TIME"));
				spoVO.setSpo_payment(rs.getInt("SPO_PAYMENT"));
				spoVO.setSpo_postage(rs.getInt("SPO_POSTAGE"));
				spoVO.setSpo_bmem_id(rs.getInt("SPO_BMEM_ID"));
				spoVO.setSpo_smem_id(rs.getInt("SPO_SMEM_ID"));
				spoVO.setSpo_receiver_name(rs.getString("SPO_RECEIVER_NAME"));
				spoVO.setSpo_receiver_phone(rs.getString("SPO_RECEIVER_PHONE"));
				spoVO.setSpo_receiver_address(rs.getString("SPO_RECEIVER_ADDRESS"));
				spoVO.setSpo_paytype(rs.getInt("SPO_PAYTYPE"));
				spoVO.setSpo_status(rs.getInt("SPO_STATUS"));
				spoVO.setSpo_pay_status(rs.getInt("SPO_PAY_STATUS"));
				spoVO.setSpo_cargo_status(rs.getInt("SPO_CARGO_STATUS"));
				list.add(spoVO);
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
	public List<SpoVO> findByForeignKey(Integer spo_bmem_id) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		SpoVO spoVO = null;
		List<SpoVO> list = new ArrayList<SpoVO>();
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GETALL_BY_BMEM);
			pstmt.setInt(1, spo_bmem_id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				spoVO = new SpoVO();
				spoVO.setSpo_id(rs.getInt("SPO_ID"));
				spoVO.setSpo_time(rs.getTimestamp("SPO_TIME"));
				spoVO.setSpo_payment(rs.getInt("SPO_PAYMENT"));
				spoVO.setSpo_postage(rs.getInt("SPO_POSTAGE"));
				spoVO.setSpo_bmem_id(rs.getInt("SPO_BMEM_ID"));
				spoVO.setSpo_smem_id(rs.getInt("SPO_SMEM_ID"));
				spoVO.setSpo_receiver_name(rs.getString("SPO_RECEIVER_NAME"));
				spoVO.setSpo_receiver_phone(rs.getString("SPO_RECEIVER_PHONE"));
				spoVO.setSpo_receiver_address(rs.getString("SPO_RECEIVER_ADDRESS"));
				spoVO.setSpo_paytype(rs.getInt("SPO_PAYTYPE"));
				spoVO.setSpo_status(rs.getInt("SPO_STATUS"));
				spoVO.setSpo_pay_status(rs.getInt("SPO_PAY_STATUS"));
				spoVO.setSpo_cargo_status(rs.getInt("SPO_CARGO_STATUS"));
				list.add(spoVO);
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
	
}
