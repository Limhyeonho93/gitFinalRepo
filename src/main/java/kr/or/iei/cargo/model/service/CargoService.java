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
	 //화물 단 건 등록 
	   public int insertCargo(CargoMain cargo, CargoGoods goods) {
	      Connection conn = JDBCTemplate.getConnection();
	      int result = 0;

	      result = dao.insertCargo(conn, cargo);
	      
	        if (result > 0) {
	           System.out.println("CargoMain insert완");
	            result = dao.insertCargoGoods(conn, goods);
	            if(result>0) {
	               JDBCTemplate.commit(conn);
	            }else {
	               JDBCTemplate.rollback(conn);
	            }
	        }else {
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


	public boolean deleteCargo(String trackingNo) {
		Connection conn = JDBCTemplate.getConnection();
	    boolean resultFlag = false;
	   
	    try {
	    
	    // CargoGoods 삭제
	    int goodsResult = dao.deleteCargoGoodsByTrackingNo(conn, trackingNo);
	    
	    // CargoMain 삭제
	    int mainResult = dao.deleteCargoMainsByTrackingNo(conn, trackingNo);
	    
	    if (goodsResult > 0 && mainResult > 0) {
	    	JDBCTemplate.commit(conn);
	    	resultFlag = true;
	    }else {
	    	JDBCTemplate.rollback(conn);
	    }
	} catch (Exception e) {
		e.printStackTrace();
		JDBCTemplate.rollback(conn);
	} finally {
		JDBCTemplate.close(conn);
	}
	    
		return resultFlag;
	}


	
}
	       
