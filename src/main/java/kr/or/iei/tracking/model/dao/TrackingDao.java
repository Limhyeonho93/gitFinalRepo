package kr.or.iei.tracking.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.invoice.model.vo.CompInfo;
import kr.or.iei.tracking.model.vo.DailyWarehouseSummary;

public class TrackingDao {

	public ArrayList<DailyWarehouseSummary> getDailyWareData(Connection conn, String searchDate) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String query = "select mw.ware_cd,max(mw.ware_name) ware_name, sum(tm.gw) gw, sum(tm.no) no ,count(tm.manage_no) all_count,count(tb.manage_no) tb_count, count(tb.in_date) in_count ,count(tb.out_date) out_count from t_cargomain tm "
				+ "LEFT JOIN m_warehouse mw ON SUBSTR(tm.warehouse_moveid,7) = mw.ware_cd "
				+ "LEFT JOIN t_impbonded tb ON tm.manage_no = tb.manage_no "
				+ "WHERE SUBSTR(tm.warehouse_moveid,1,6) = ? " + "GROUP BY mw.ware_cd ";

		ArrayList<DailyWarehouseSummary> arr = new ArrayList<DailyWarehouseSummary>();
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, searchDate);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				DailyWarehouseSummary dws = new DailyWarehouseSummary();
				dws.setWareCd(rset.getString("ware_cd"));
				dws.setWareName(rset.getString("ware_name"));
				dws.setTotalWeight(rset.getInt("gw"));
				dws.setTotalQty(rset.getInt("no"));
				dws.setAllCount(rset.getInt("all_count"));
				dws.setTbCount(rset.getInt("tb_count"));

				dws.setInCount(rset.getInt("in_count"));
				dws.setOutCount(rset.getInt("out_count"));

				arr.add(dws);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return arr;
	}

	public ArrayList<DailyWarehouseSummary> getManageNoWithBonded(Connection conn, String searchDate, String wareCd) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String query = "SELECT tm.manage_no, tb.in_date FROM t_cargomain tm "
				+ "LEFT JOIN m_warehouse mw ON SUBSTR(tm.warehouse_moveid,7) = mw.ware_cd "
				+ "LEFT JOIN t_impbonded tb ON tm.manage_no = tb.manage_no "
				+ "WHERE SUBSTR(tm.warehouse_moveid ,1 ,6) = ? " + "AND mw.ware_cd = ? AND tb.manage_no IS NULL";

		ArrayList<DailyWarehouseSummary> arr = new ArrayList<DailyWarehouseSummary>();
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, searchDate);
			pstmt.setString(2, wareCd);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				DailyWarehouseSummary dws = new DailyWarehouseSummary();

				dws.setManageNo(rset.getString("manage_no"));

				arr.add(dws);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return arr;
	}

	public int insertBondedData(Connection conn, String manageNo) {
		PreparedStatement pstmt = null;

		int result = 0;

		String query = "INSERT INTO t_impbonded (manage_no) " + "VALUES (?)";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, manageNo);

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public ArrayList<DailyWarehouseSummary> getManageNoJoinBonded(Connection conn, String searchDate, String wareCd,
			String updateColumn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String where = "";

		if (updateColumn.equals("imp")) {
			where = "AND tb.in_date IS NULL";
		} else {
			where = "AND tb.out_date IS NULL";
		}

		String query = "SELECT tb.manage_no FROM t_impbonded tb LEFT JOIN t_cargomain tm ON tb.manage_no = tm.manage_no "
				+ "WHERE SUBSTR(tm.WAREHOUSE_MOVEID,1,6) = ? "
				+ "AND SUBSTR(tm.WAREHOUSE_MOVEID,7) = ? " + where ;

		ArrayList<DailyWarehouseSummary> arr = new ArrayList<DailyWarehouseSummary>();
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, searchDate);
			pstmt.setString(2, wareCd);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				DailyWarehouseSummary dws = new DailyWarehouseSummary();

				dws.setManageNo(rset.getString("manage_no"));

				arr.add(dws);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return arr;
	}

	public int updateBondedTable(Connection conn, String manageNo, String updateColumn) {
		PreparedStatement pstmt = null;

		int result = 0;

		String column = "";
		if (updateColumn.equals("imp")) {
			column = "in_date";
		} else {
			column = "out_date";
		}

		String query = "UPDATE t_impbonded SET " + column + " = SYSDATE WHERE manage_no = ?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, manageNo);

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

}
