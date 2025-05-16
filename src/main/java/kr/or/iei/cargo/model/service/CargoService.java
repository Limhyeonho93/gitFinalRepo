package kr.or.iei.cargo.model.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.or.iei.cargo.model.dao.CargoDao;
import kr.or.iei.cargo.model.vo.CargoGoods;
import kr.or.iei.cargo.model.vo.CargoMain;
import kr.or.iei.common.JDBCTemplate;

public class CargoService {
	private CargoDao dao;

	public CargoService() {
		dao = new CargoDao();
	}

	public ArrayList<CargoMain> searchCargo(String[] searchValue, String searchOption) {
		Connection conn = JDBCTemplate.getConnection();
		ArrayList<CargoMain> list = dao.searchCargo(conn, searchValue, searchOption);
		JDBCTemplate.close(conn);
		return list;
	}

	public int insertCargo(CargoMain cargo) {
		Connection conn = JDBCTemplate.getConnection();
		int result = 0;

		
		result = dao.insertCargo(conn, cargo);

		if (result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}

		JDBCTemplate.close(conn);

		return result;
	}
	public CargoGoods srchCargoDetail(String trackingNo) {
		Connection conn =JDBCTemplate.getConnection();
		CargoGoods goods=dao.srchCargoDetail(conn,trackingNo);
		JDBCTemplate.close(conn);

		return goods;
	}

	public boolean updateCargoDetails(CargoMain cargo) {
		Connection conn = JDBCTemplate.getConnection();
	    try {
	        // DAO 메서드를 호출하여 예외는 DAO에서 처리하게 합니다.
	        int result = dao.updateCargoDetails(conn, cargo);

	        if (result > 0) {
	            JDBCTemplate.commit(conn);
	            return true;
	        } else {
	            JDBCTemplate.rollback(conn);
	            return false;
	        }
	    } catch (Exception e) { 
	        e.printStackTrace();
	        JDBCTemplate.rollback(conn);
	        return false;
	    } finally {
	        JDBCTemplate.close(conn);
	    }
	}
}
	       
