package kr.or.iei.tracking.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.invoice.model.vo.ShoppingCost;
import kr.or.iei.tracking.model.dao.TrackingDao;
import kr.or.iei.tracking.model.vo.DailyWarehouseSummary;

public class TrackingService {

	private TrackingDao dao;

	public TrackingService() {
		dao = new TrackingDao();
	}

	public ArrayList<DailyWarehouseSummary> getDailyWareData(String searchDate) {
		Connection conn = JDBCTemplate.getConnection();

		ArrayList<DailyWarehouseSummary> arr = dao.getDailyWareData(conn, searchDate);

		JDBCTemplate.close(conn);
		return arr;
	}

	public ArrayList<DailyWarehouseSummary> getManageNoWithBonded(String searchDate, String wareCd) {
		Connection conn = JDBCTemplate.getConnection();

		ArrayList<DailyWarehouseSummary> arr = dao.getManageNoWithBonded(conn, searchDate, wareCd);

		JDBCTemplate.close(conn);
		return arr;
	}

	public int insertBondedData(String manageNo) {
		Connection conn = JDBCTemplate.getConnection();

		int res = dao.insertBondedData(conn, manageNo);

		if (res > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}

		JDBCTemplate.close(conn);

		return res;
	}

	public int updateBondedData(String searchDate, String wareCd, String updateColumn) {
		Connection conn = JDBCTemplate.getConnection();
        System.out.println("updateBondedData"+updateColumn);

		ArrayList<DailyWarehouseSummary> dws = dao.getManageNoJoinBonded(conn, searchDate, wareCd, updateColumn);

		int res = 0;

		for (DailyWarehouseSummary d : dws) {
			String manageNo = d.getManageNo();
			res = dao.updateBondedTable(conn, manageNo,updateColumn);
		}

		if (res > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);

		return res;
	}
}
