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

	// 변경된 요금 저장
	public boolean saveShoppingCost(String disGrade, int weight, int price) {
		 Connection conn = JDBCTemplate.getConnection();
		    boolean result = false;

		    try {
		        result = dao.insertShoppingCost(conn, disGrade, weight, price);
		        if (result) {
		            JDBCTemplate.commit(conn);
		        } else {
		            JDBCTemplate.rollback(conn);
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		        JDBCTemplate.rollback(conn);
		    } finally {
		        JDBCTemplate.close(conn);
		    }

		    return result;
	}
}
