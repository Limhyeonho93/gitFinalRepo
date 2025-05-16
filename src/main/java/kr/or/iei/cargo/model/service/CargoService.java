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
		dao = new CargoDao();
	}
	
	
	//화물 조회
	public ArrayList<CargoMain> searchCargo(String[] searchValue, String searchOption){
	    System.out.println("searchCargo service");

		Connection conn = JDBCTemplate.getConnection();
		ArrayList<CargoMain> list = dao.searchCargo(conn, searchValue, searchOption);
		JDBCTemplate.close(conn);
		return list;
	}
	
	//화물 상세 조회
	public CargoGoods srchCargoDetail(String trackingNo) {
		Connection conn =JDBCTemplate.getConnection();
		CargoGoods goods=dao.srchCargoDetail(conn,trackingNo);
		JDBCTemplate.close(conn);

		return goods;
	}
	
	//화물 한 건 등록(T_cargoMain)
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
	
	//화물 일괄 등록
	public int insertBatchCargo(CargoMain cargoMain, CargoGoods cargoGoods) {
	    Connection conn = JDBCTemplate.getConnection();
	    int result = 0;
	    
	    System.out.println("insertBatchCargo");
	    
	 	//CargoMain에 동일한 Tracking Number가 있는지 없는지 확인
	    String[]TraNo= {cargoMain.getTrackingNo()}; //메소드 재사용 위한 타입 변환
	    ArrayList<CargoMain> list=searchCargo(TraNo,"tracking_no"); //화물 조회 메소드 재사용
	   

	    try {
	        // list가 비어있지 않다면 (업데이트 & 삭제 후 삽입)
	        if (!list.isEmpty()) {
	            // 1. CargoMain 업데이트
	            result = dao.updateCargoMain(conn, cargoMain);
	            System.out.println("CargoMain 업데이트 완");
	            if (result > 0) {
	                // 2. CargoGoods 삭제
	                result = dao.deleteCargoGoodsByTrackingNo(conn, cargoMain.getTrackingNo());
	                System.out.println("CargoGoods 삭제 완");
	                if (result > 0) {
	                    // 3. CargoGoods 새로 insert
	                    result = dao.insertCargoGoods(conn, cargoGoods );
	                    System.out.println("CargoGoods insert완");
	                }
	            }
	        } else { // list가 비어있으면 새로운 데이터를 CargoMain, CargoGoods에 insert
	            result = dao.insertCargo(conn, cargoMain);
	            System.out.println("CargoMain insert완");
	            if (result > 0) {
	            	
	                result = dao.insertCargoGoods(conn, cargoGoods);
	                System.out.println("CargoGoods insert완");
	            }
	        }

	        // 트랜잭션 커밋
	        if (result > 0) {
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
