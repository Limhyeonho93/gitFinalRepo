package kr.or.iei.tracking.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.invoice.model.vo.ShoppingCost;
import kr.or.iei.tracking.model.dao.TrackingDao;
import kr.or.iei.tracking.model.vo.DailyWarehouseSummary;
import kr.or.iei.tracking.model.vo.TrackingJoin;
import kr.or.iei.user.model.vo.User;

public class TrackingService {

	private TrackingDao dao;

	public TrackingService() {
		dao = new TrackingDao();
	}

	// 날짜 기준 창고별 입고예정 및 입출 상태 리스트
	public ArrayList<DailyWarehouseSummary> getDailyWareData(String searchDate) {
		Connection conn = JDBCTemplate.getConnection();

		ArrayList<DailyWarehouseSummary> arr = dao.getDailyWareData(conn, searchDate);

		JDBCTemplate.close(conn);
		return arr;
	}

	// impBonded에 등록되어 있지 않은 manage_no추출하고 그걸 리턴시키기 위해서
	public ArrayList<DailyWarehouseSummary> getManageNoWithBonded(String searchDate, String wareCd) {
		Connection conn = JDBCTemplate.getConnection();

		ArrayList<DailyWarehouseSummary> arr = dao.getManageNoWithBonded(conn, searchDate, wareCd);

		JDBCTemplate.close(conn);
		return arr;
	}

	// impBonded에 데이터 등록
	public int insertBondedData(String manageNo, String wareHouseMoveId) {
		Connection conn = JDBCTemplate.getConnection();

		// impBonded에 데이터 등록
		int res = dao.insertBondedData(conn, manageNo);

		if (res > 0) {
			// trackingHistory테이블에 스테이터스 등록
			int trRes = dao.insertTrackingData(conn, manageNo, wareHouseMoveId, "S2");
			JDBCTemplate.commit(conn);

		} else {
			JDBCTemplate.rollback(conn);
		}

		JDBCTemplate.close(conn);

		return res;
	}

	// impBonded에 반입 반출 update
	public int updateBondedData(String searchDate, String wareCd, String updateColumn) {
		Connection conn = JDBCTemplate.getConnection();

		ArrayList<DailyWarehouseSummary> dws = dao.getManageNoJoinBonded(conn, searchDate, wareCd, updateColumn);

		int res = 0;

		for (DailyWarehouseSummary d : dws) {
			String manageNo = d.getManageNo();
			String wareHouseMoveId = d.getWarehouseMoveid();

			String StatusCd = "";
			// 반입 반출이냐에 따라서 statusCd를 나누어서 trackingHistroy에 insert
			if (updateColumn.equals("imp")) {
				StatusCd = "S3";
			} else {
				StatusCd = "S4";
			}
			res = dao.updateBondedTable(conn, manageNo, updateColumn);
			int trRes = dao.insertTrackingData(conn, manageNo, wareHouseMoveId, StatusCd);

		}

		if (res > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);

		return res;
	}

	// group by로 manage_no기준 가장 최근 트래킹 데이터를 리턴
	public ArrayList<TrackingJoin> getTrackingGroup(String searchOption, String[] searchValue) {
		Connection conn = JDBCTemplate.getConnection();

		ArrayList<TrackingJoin> arr = dao.getTrackingGroup(conn, searchOption,searchValue);

		JDBCTemplate.close(conn);
		return arr;
	}

	public ArrayList<TrackingJoin> getTrackingDetail(String manageNo) {
		Connection conn = JDBCTemplate.getConnection();

		ArrayList<TrackingJoin> arr = dao.getTrackingDetail(conn, manageNo);

		JDBCTemplate.close(conn);
		return arr;
	}

	public ArrayList<TrackingJoin> getSelerTrackingGroup(String searchOption, String[] searchValue, User loginUser) {
		Connection conn = JDBCTemplate.getConnection();

		ArrayList<TrackingJoin> arr = dao.getSelerTrackingGroup(conn, searchOption,searchValue,loginUser);

		JDBCTemplate.close(conn);
		return arr;
	}
}
