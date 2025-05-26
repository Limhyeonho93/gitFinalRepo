package kr.or.iei.invoice.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShoppingDao {

	// shoppingcost 등록 => 요금 조회
	public int getPrice(Connection conn, String disGrade, int weight) throws SQLException {
		String query = "SELECT PRICE FROM m_shoppingcost WHERE dis_grade = ? AND weight = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(query)) {
			
			// 삭제
			System.out.println("Dao disGrade: " + disGrade + ", weight: " + weight);
			
			pstmt.setString(1, disGrade);
			pstmt.setInt(2, weight);
			try (ResultSet rset = pstmt.executeQuery()) {
				if (rset.next()) {
					return rset.getInt("PRICE");
				}
			}
		}
		return 0;
	}

	// 최종요금 업데이트(기본요금 + 추가요금)
	public int updateInvoice(Connection conn, int total, String disGrade, String weightParam) {

		String query = "UPDATE m_shoppingcost SET price = ? WHERE dis_grade = ? and weight = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(query)) {
			pstmt.setInt(1, total); // 최종 요금 설정
			pstmt.setString(2, disGrade); // 최종 요금 설정
			pstmt.setString(3, weightParam); // 최종 요금 설정
			pstmt.executeUpdate(); // 업데이트 실행
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;

	}
}