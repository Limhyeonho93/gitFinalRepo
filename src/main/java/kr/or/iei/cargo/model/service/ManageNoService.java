package kr.or.iei.cargo.model.service;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import kr.or.iei.cargo.model.dao.CargoDao;
import kr.or.iei.cargo.model.dao.ManageNoDao;
import kr.or.iei.cargo.model.vo.CargoMain;
import kr.or.iei.cargo.model.vo.ManageNo;
import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.tracking.model.dao.TrackingDao;

public class ManageNoService {

	private ManageNoDao dao;
	
	public ManageNoService() {
		dao = new ManageNoDao();
	}
	// manage_no가 비어 있는 cargo_main 조회
	public ArrayList<CargoMain> getNullManageNo() {
		Connection conn = JDBCTemplate.getConnection();
		ArrayList<CargoMain> list = dao.getNullManageNo(conn);
		JDBCTemplate.close(conn);
		return list;
	}
	// 오늘 날짜 기준의 manage_no 정보 조회 (없으면 insert 후 재조회)
	public ManageNo getManageNoDate(String toDayStr) {
		Connection conn = JDBCTemplate.getConnection();
		ManageNo manageNo = dao.getManageNoDate(conn,toDayStr);
		if(manageNo == null) {
			int res = dao.insertManageNoDate(conn);
			if(res > 0) {
				JDBCTemplate.commit(conn);
			}else{
				JDBCTemplate.rollback(conn);
			}
			manageNo = dao.getManageNoDate(conn,toDayStr);

		}
		JDBCTemplate.close(conn);
		return manageNo;
	}
	// cargo_main 및 cargo_goods의 manage_no 업데이트
	public int updateCargoMainManageNo(CargoMain cm, String manageNoStr) {
		Connection conn = JDBCTemplate.getConnection();
		// main과 goods의 manageNo업데이트
		int res = dao.updateCargoMainManageNo(conn,cm,manageNoStr);
		dao.updateCargoGoodsManageNo(conn,cm,manageNoStr);
		// 데이터 등록과 동시에 tracking_history테이블에 데이터 등록
		TrackingDao tarckingDao = new TrackingDao();
		int trRes = tarckingDao.insertTrackingData(conn, manageNoStr, cm.getWarehouseMoveid(), "S1");
		
		if(res > 0) {
			JDBCTemplate.commit(conn);
		}else{
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		return res;
	}
	
	// t_manage_no 테이블의 nextNo 값 업데이트
	public int updateNextNo(String toDayStr, int nextNo) {
		Connection conn = JDBCTemplate.getConnection();
		int res = dao.updateNextNo(conn,toDayStr,nextNo);
		if(res > 0) {
			JDBCTemplate.commit(conn);
		}else{
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		return res;
	}
	
	// 우편번호 기준으로 창고코드 조회 (정확히 없을 경우 앞 3자리 like 검색)
	// 이후 yymmdd와 창고코드로 창고ID생성
	public String findZipWareCd(String zip) {
		Connection conn = JDBCTemplate.getConnection();
		// 우편번호에 해당되는 창고코드 추출
		String wareCd = dao.findZipWareCd(conn,zip);
		// 일치하지 않으면 앞에 3자리로 like추출
		if(wareCd == null) {
			wareCd = dao.likeZipWareCd(conn,zip);
		}
		JDBCTemplate.close(conn);
		// 오늘 날짜 글자로 추출
		LocalDate toDay = LocalDate.now();

		String yymmdd = toDay.format(DateTimeFormatter.ofPattern("yyMMdd"));

		return yymmdd + wareCd;
	}
}
