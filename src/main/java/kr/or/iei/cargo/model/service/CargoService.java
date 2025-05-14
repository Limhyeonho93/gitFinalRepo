package kr.or.iei.cargo.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import kr.or.iei.cargo.model.dao.CargoDao;
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
}
