package kr.or.iei.invoice.model.service;

import java.sql.Connection;

import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.invoice.model.dao.ShoppingDao;

public class ShoppingService {

	private ShoppingDao dao = new ShoppingDao();

	// shoppingcost 등록
	public int calculateCost(String disGrade, int weight) {
		Connection conn = JDBCTemplate.getConnection();
		int price = 0;

		try {
			price = dao.getPrice(conn, disGrade, weight);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}

		return price;
	}

//	// 최종요금 업데이트(기본요금 + 추가요금)
//	public int updateInvoice(int total) {
//		Connection conn = JDBCTemplate.getConnection();
//
//		int result = new ShoppingDao().updateInvoice(conn, total);
//
//		if (result > 0) {
//			JDBCTemplate.commit(conn);
//		} else {
//			JDBCTemplate.rollback(conn);
//		}
//		JDBCTemplate.close(conn);
//
//		return result;
//	}

	public String getRegionName(String disGrade) {

		switch (disGrade) {
		case "A":
			return "수도권";
		case "B":
			return "충청/강원";
		case "C":
			return "경상/전라";
		case "D":
			return "제주/도서산간";
		default:
			return "알 수 없음";
		}
	}

	public boolean updateShoppingData(int total) {
		Connection conn = JDBCTemplate.getConnection();
		boolean isSuccess = false;

		try {
			int result = dao.updateInvoice(conn, total);
			if (result > 0) {
				JDBCTemplate.commit(conn);
				isSuccess = true;
			} else {
				JDBCTemplate.rollback(conn);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(conn);
		}

		return isSuccess;

	}
}
