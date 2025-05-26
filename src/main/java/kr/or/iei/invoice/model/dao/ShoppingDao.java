package kr.or.iei.invoice.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShoppingDao {

	// shoppingcost 등록 => 요금 조회
	public int getPrice(Connection conn, String disGrade, int weight) throws SQLException {
		PreparedStatement pstmt = null;

		ResultSet rset = null;
		int price = 0;// SQL 쿼리문

		String query = "SELECT PRICE FROM m_shoppingcost WHERE dis_grade = ? AND weight = ?";

		pstmt = conn.prepareStatement(query);

		pstmt.setString(1, disGrade);
		pstmt.setInt(2, weight);

		rset = pstmt.executeQuery();

		if (rset.next()) {
			price = rset.getInt("PRICE");
		}

		rset.close();
		pstmt.close();
		return price;
	}

	// 최종요금 업데이트(기본요금 + 추가요금)
	public int updateInvoice(Connection conn, int total) {
		PreparedStatement pstmt = null;

		int result = 0;

		String query = "UPDATE m_shoppingcost SET price = ?,  WHERE dis_grade = ? AND weight = ? ";

		try {
			pstmt = conn.prepareStatement(query);


			pstmt.setInt(1, total);
			
			result = pstmt.executeUpdate();
			
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
		}

		return result;
	}
}
