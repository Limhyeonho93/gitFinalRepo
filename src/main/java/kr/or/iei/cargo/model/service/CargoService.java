package kr.or.iei.cargo.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import kr.or.iei.cargo.model.dao.CargoDao;
import kr.or.iei.cargo.model.vo.CargoMain;
import kr.or.iei.common.JDBCTemplate;

public class CargoService {
	private CargoDao dao;
	
	public CargoService() {
		dao=new CargoDao();
	}
	
	public ArrayList<CargoMain> searchCargo() {
		Connection conn =JDBCTemplate.getConnection();
		ArrayList<CargoMain> list=dao.searchCargo(conn);
		JDBCTemplate.close(conn);
		return list;
	}

}
