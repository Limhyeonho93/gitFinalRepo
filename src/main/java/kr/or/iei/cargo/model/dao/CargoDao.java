package kr.or.iei.cargo.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.or.iei.cargo.model.vo.CargoGoods;
import kr.or.iei.cargo.model.vo.CargoMain;
import kr.or.iei.common.JDBCTemplate;

public class CargoDao {

	public ArrayList<CargoMain> searchCargo(Connection conn, String[] searchValue, String searchOption) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		ArrayList<CargoMain> list = new ArrayList<CargoMain>();

		StringBuilder query = new StringBuilder("SELECT * FROM T_cargoMain WHERE ");
		query.append(searchOption).append(" IN (");

		for (int i = 0; i < searchValue.length; i++) {
			query.append("?");
			if (i < searchValue.length - 1) {
				query.append(",");
			}
		}
		query.append(")");

		try {
			// pstmt=conn.prepareStatement(query);

			pstmt = conn.prepareStatement(query.toString());

			for (int i = 0; i < searchValue.length; i++) {
				pstmt.setString(i + 1, searchValue[i]);
			}

			// System.out.println(searchValue);
			// System.out.println(query);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				CargoMain c = new CargoMain();
				c.setCompCd(rset.getString("comp_cd")); // 회사명으로 바꾸기
				c.setWarehouseMoveid(rset.getString("warehouse_moveId"));
				c.setTrackingNo(rset.getString("tracking_no"));
				c.setManageNo(rset.getString("manage_no"));
				c.setReceiverName(rset.getString("receiver_name"));
				c.setReceiverAdd(rset.getString("receiver_add"));
				c.setReceiverZip(rset.getString("receiver_zip"));
				c.setReceiverTel(rset.getString("receiver_tel"));
				c.setSellerName(rset.getString("seller_name"));
				c.setSellerAdd(rset.getString("seller_add"));
				c.setSellerTel(rset.getString("seller_tel"));
				c.setGw(rset.getInt("gw"));
				c.setGwt(rset.getString("gwt"));
				c.setNo(rset.getInt("no"));
				// c.set(rset.getString("")); //가격 합계 추가
				// c.set(rset.getString("")); 상품명1개(MAX or MIN 1개만) 추가

				list.add(c);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return list;
	}

	public int insertCargo(Connection conn, CargoMain cargo) {
		PreparedStatement pstmt = null;
		int result = 0;

		String query = "INSERT INTO T_cargoMain (" + "comp_cd, warehouse_moveId, tracking_no, manage_no, "
				+ "receiver_name, receiver_add, receiver_zip, receiver_tel, "
				+ "seller_name, seller_add, seller_tel, gw, gwt, no) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, cargo.getCompCd());
			pstmt.setString(2, cargo.getWarehouseMoveid());
			pstmt.setString(3, cargo.getTrackingNo());
			pstmt.setString(4, cargo.getManageNo());
			pstmt.setString(5, cargo.getReceiverName());
			pstmt.setString(6, cargo.getReceiverAdd());
			pstmt.setString(7, cargo.getReceiverZip());
			pstmt.setString(8, cargo.getReceiverTel());
			pstmt.setString(9, cargo.getSellerName());
			pstmt.setString(10, cargo.getSellerAdd());
			pstmt.setString(11, cargo.getSellerTel());
			pstmt.setInt(12, cargo.getGw());
			pstmt.setString(13, cargo.getGwt());
			pstmt.setInt(14, cargo.getNo());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public CargoGoods srchCargoDetail(Connection conn, String trackingNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		CargoGoods goods = new CargoGoods();

		String query = "select * from T_cargoGoods where tracking_no = ? ";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, trackingNo);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				goods.setCompCd(rset.getString("comp_cd"));
				goods.setWarehouseMoveid(rset.getString("warehouse_moveId"));
				goods.setTrackingNo(rset.getString("tracking_no"));
				goods.setManageNo(rset.getString("manage_no"));
				goods.setSeq(rset.getInt("seq"));
				goods.setGoodsName(rset.getString("goods_name"));
				goods.setUnitPrice(rset.getInt("unit_price"));
				goods.setQty(rset.getInt("qty"));
				goods.setUnitWeight(rset.getFloat("unit_weight"));
				goods.setNo(rset.getInt("no"));
				goods.setDeliveryStop(rset.getString("delivery_stop"));
				goods.setUserId(rset.getString("user_id"));
				goods.setRegDate(rset.getString("reg_date"));
				goods.setUpdDate(rset.getString("upd_date"));
			}

			System.out.println("dao:" + goods.getTrackingNo());

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}

		return goods;
	}

	public int updateCargoDetails(Connection conn, CargoMain cargo) {
	    PreparedStatement pstmt = null;
	    int result = 0;

	    // Ensure COMP_CD is not null and is within the valid length (10 characters)
	    String compCd = cargo.getCompCd();
	    if (compCd == null) {
	        compCd = "DEFAULT_COMP_CD";  // Set a default value for COMP_CD if it is null
	    }
	    if (compCd.length() > 10) {
	        compCd = compCd.substring(0, 10);  // Truncate COMP_CD to 10 characters
	    }

	    // Ensure warehouseMoveid is not null or empty
	    String warehouseMoveid = cargo.getWarehouseMoveid();
	    if (warehouseMoveid == null || warehouseMoveid.trim().isEmpty()) {
	        warehouseMoveid = "DEFAULT_WAREHOUSE";  // Set a default value if it is null
	    }

	    // UPDATE 쿼리 작성
	    String query = "UPDATE T_cargoMain SET "
	            + "comp_cd = ?, warehouse_moveId = ?, manage_no = ?, receiver_name = ?, "
	            + "receiver_add = ?, receiver_zip = ?, receiver_tel = ?, "
	            + "seller_name = ?, seller_add = ?, seller_tel = ?, gw = ?, gwt = ?, no = ? "
	            + "WHERE tracking_no = ?";

	    try {
	        pstmt = conn.prepareStatement(query);

	        // Set parameters for the SQL query
	        pstmt.setString(1, compCd);  // Truncated COMP_CD
	        pstmt.setString(2, warehouseMoveid);  // Ensure warehouseMoveid is set
	        pstmt.setString(3, cargo.getManageNo());
	        pstmt.setString(4, cargo.getReceiverName());
	        pstmt.setString(5, cargo.getReceiverAdd());
	        pstmt.setString(6, cargo.getReceiverZip());
	        pstmt.setString(7, cargo.getReceiverTel());
	        pstmt.setString(8, cargo.getSellerName());
	        pstmt.setString(9, cargo.getSellerAdd());
	        pstmt.setString(10, cargo.getSellerTel());
	        pstmt.setInt(11, cargo.getGw());
	        pstmt.setString(12, cargo.getGwt());
	        pstmt.setInt(13, cargo.getNo());
	        pstmt.setString(14, cargo.getTrackingNo());

	        // Execute the update query
	        result = pstmt.executeUpdate();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JDBCTemplate.close(pstmt);
	    }

	    return result;
	}
}