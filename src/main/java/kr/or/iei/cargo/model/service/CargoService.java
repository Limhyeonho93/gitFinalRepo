package kr.or.iei.cargo.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import kr.or.iei.cargo.model.dao.CargoDao;
import kr.or.iei.cargo.model.vo.CargoGoods;
import kr.or.iei.cargo.model.vo.CargoMain;
import kr.or.iei.common.JDBCTemplate;

public class CargoService {
	private CargoDao dao;
	
	public CargoService() {
		dao=new CargoDao();
	}
	
	public ArrayList<CargoMain> searchCargo(String[] searchValue, String searchOption) {
		Connection conn =JDBCTemplate.getConnection();
		ArrayList<CargoMain> list=dao.searchCargo(conn, searchValue,searchOption);
		JDBCTemplate.close(conn);
		return list;
	}

	public CargoGoods srchCargoDetail(String trackingNo) {
		Connection conn =JDBCTemplate.getConnection();
		CargoGoods goods=dao.srchCargoDetail(conn,trackingNo);
		JDBCTemplate.close(conn);

		return goods;
	}

}
