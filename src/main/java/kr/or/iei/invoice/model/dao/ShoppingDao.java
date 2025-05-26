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

		// 삭제 프린트
		
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

	// 변경된 요금 저장
	public boolean insertShoppingCost(Connection conn, String disGrade, int weight, int price) throws SQLException {
		PreparedStatement pstmt = null;
		boolean result = false;

		String query = "INSERT INTO m_shoppingcost (dis_grade, weight, price) VALUES (?, ?, ?)";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, disGrade);
			pstmt.setInt(2, weight);
			pstmt.setInt(3, price);

			int rows = pstmt.executeUpdate();
			result = rows > 0;
		} finally {
			if (pstmt != null)
				pstmt.close();
		}

		return result;

	}
}
