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
}
