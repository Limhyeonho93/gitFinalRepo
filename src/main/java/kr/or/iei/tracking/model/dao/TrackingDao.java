package kr.or.iei.tracking.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.or.iei.cargo.model.vo.CargoMain;
import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.invoice.model.vo.CompInfo;
import kr.or.iei.tracking.model.vo.DailyWarehouseSummary;
import kr.or.iei.tracking.model.vo.TrackingJoin;
import kr.or.iei.user.model.vo.User;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
public class TrackingDao {

	// 날짜 기준 창고별 입고예정 및 입출 상태 리스트 쿼리
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

	// impBonded에 등록되어 있지 않은 manage_no추출하고 그걸 리턴시키기 위해서
	public ArrayList<DailyWarehouseSummary> getManageNoWithBonded(Connection conn, String searchDate, String wareCd) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String query = "SELECT tm.manage_no, tb.in_date, tm.warehouse_moveid FROM t_cargomain tm "
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
				dws.setWarehouseMoveid(rset.getString("warehouse_moveid"));

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

	// impbonded에 manage_no만 등록 (입고/출고 상태 없이 빈줄 등록)
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

	// impbonded에 in/out 날짜 아직 안 들어간 애들 조회 (화면에서 스캔 처리용)
	public ArrayList<DailyWarehouseSummary> getManageNoJoinBonded(Connection conn, String searchDate, String wareCd,
			String updateColumn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String where = "";

		// imp면 in_date가 NULL인 애들 / exp면 out_date가 NULL인 애들
		if (updateColumn.equals("imp")) {
			where = "AND tb.in_date IS NULL";
		} else {
			where = "AND tb.out_date IS NULL";
		}

		String query = "SELECT tb.manage_no, tm.warehouse_moveid FROM t_impbonded tb LEFT JOIN t_cargomain tm ON tb.manage_no = tm.manage_no "
				+ "WHERE SUBSTR(tm.WAREHOUSE_MOVEID,1,6) = ? " + "AND SUBSTR(tm.WAREHOUSE_MOVEID,7) = ? " + where;

		ArrayList<DailyWarehouseSummary> arr = new ArrayList<DailyWarehouseSummary>();
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, searchDate);
			pstmt.setString(2, wareCd);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				DailyWarehouseSummary dws = new DailyWarehouseSummary();

				dws.setManageNo(rset.getString("manage_no"));
				dws.setWarehouseMoveid(rset.getString("warehouse_moveid"));

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

	// impbonded에 in_date, out_date update
	public int updateBondedTable(Connection conn, String manageNo, String updateColumn) {
		PreparedStatement pstmt = null;

		int result = 0;

		String column = "";
		// ajax로 넘겨주는 부분에 따라서 update칼럼을 구분한다
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

	// t_tracking_history에 데이터 insert
	public int insertTrackingData(Connection conn, String manageNo, String wareHouseMoveId, String statusCd) {

		PreparedStatement pstmt = null;

		int result = 0;

		String query = "INSERT INTO t_tracking_history (manage_no,warehouse_moveId,state_code) " + "VALUES (?,?,?)";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, manageNo);
			pstmt.setString(2, wareHouseMoveId);
			pstmt.setString(3, statusCd);

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;

	}

	// group by로 manage_no기준 가장 최근 트래킹 데이터를 리턴
	public ArrayList<TrackingJoin> getTrackingGroup(Connection conn, String searchOption, String[] searchValue) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		ArrayList<TrackingJoin> list = new ArrayList<TrackingJoin>();

		StringBuilder query = new StringBuilder("SELECT tm.*,tc.comp_name, tth.state_code,tth.state_change,ms.state_title FROM t_tracking_history tth "
				+ "INNER JOIN (SELECT manage_no , max(tracking_idx) tracking_idx FROM t_tracking_history GROUP BY manage_no) tth2 ON tth.tracking_idx = tth2.tracking_idx "
				+ "LEFT JOIN t_cargomain tm ON tth.manage_no = tm.manage_no "
				+ "LEFT JOIN t_customerinfo tc ON tm.comp_cd = tc.comp_cd "
				+ "LEFT JOIN m_state_code ms ON tth.state_code = ms.state_code "
				+ "WHERE tm.");
		query.append(searchOption).append(" IN (");

		for (int i = 0; i < searchValue.length; i++) {
			query.append("?");
			if (i < searchValue.length - 1) {
				query.append(",");
			}
		}
		query.append(")");
		

		try {

			pstmt = conn.prepareStatement(query.toString());

			for (int i = 0; i < searchValue.length; i++) {
				pstmt.setString(i + 1, searchValue[i]);
			}

			rset = pstmt.executeQuery();

			while (rset.next()) {
				TrackingJoin tj = new TrackingJoin();
				tj.setCompCd(rset.getString("comp_cd")); // 회사명으로 바꾸기
				tj.setWarehouseMoveid(rset.getString("warehouse_moveid"));
				tj.setTrackingNo(rset.getString("tracking_no"));
				tj.setManageNo(rset.getString("manage_no"));
				tj.setCompName(rset.getString("comp_name"));
				tj.setStateTitle(rset.getString("state_title"));
				tj.setStateCd(rset.getString("state_code"));
				tj.setStateChange(rset.getTimestamp("state_change").toLocalDateTime().format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm")));

				list.add(tj);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return list;
	}

	// 하나의 manage_no기준 가장 상세 정보
	public ArrayList<TrackingJoin> getTrackingDetail(Connection conn, String manageNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String query = "SELECT tth.*, ms.state_title FROM t_tracking_history tth "
				+ "LEFT JOIN m_state_code ms ON tth.state_code = ms.state_code  "
				+ "WHERE tth.manage_no = ? ORDER BY tth.tracking_idx DESC";

		ArrayList<TrackingJoin> arr = new ArrayList<TrackingJoin>();
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, manageNo);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				TrackingJoin tj = new TrackingJoin();
				tj.setWarehouseMoveid(rset.getString("warehouse_moveid"));
				tj.setManageNo(rset.getString("manage_no"));
				tj.setStateTitle(rset.getString("state_title"));
				tj.setStateCd(rset.getString("state_code"));
				tj.setStateChange(rset.getTimestamp("state_change").toLocalDateTime().format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm")));

				arr.add(tj);
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

	public ArrayList<TrackingJoin> getSelerTrackingGroup(Connection conn, String searchOption, String[] searchValue,
			User loginUser) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		ArrayList<TrackingJoin> list = new ArrayList<TrackingJoin>();

		StringBuilder query = new StringBuilder("SELECT tm.*,tc.comp_name, tth.state_code,tth.state_change,ms.state_title FROM t_tracking_history tth "
				+ "INNER JOIN (SELECT manage_no , max(tracking_idx) tracking_idx FROM t_tracking_history GROUP BY manage_no) tth2 ON tth.tracking_idx = tth2.tracking_idx "
				+ "LEFT JOIN t_cargomain tm ON tth.manage_no = tm.manage_no "
				+ "LEFT JOIN t_customerinfo tc ON tm.comp_cd = tc.comp_cd "
				+ "LEFT JOIN m_state_code ms ON tth.state_code = ms.state_code "
				+ "WHERE tm.comp_cd = ? AND ");
		query.append(searchOption).append(" IN (");

		for (int i = 0; i < searchValue.length; i++) {
			query.append("?");
			if (i < searchValue.length - 1) {
				query.append(",");
			}
		}
		query.append(")");
		

		try {

			pstmt = conn.prepareStatement(query.toString());
			pstmt.setString(1, loginUser.getCompCd());
			for (int i = 0; i < searchValue.length; i++) {
				pstmt.setString(i + 2, searchValue[i]);
			}

			rset = pstmt.executeQuery();

			while (rset.next()) {
				TrackingJoin tj = new TrackingJoin();
				tj.setCompCd(rset.getString("comp_cd")); // 회사명으로 바꾸기
				tj.setWarehouseMoveid(rset.getString("warehouse_moveid"));
				tj.setTrackingNo(rset.getString("tracking_no"));
				tj.setManageNo(rset.getString("manage_no"));
				tj.setCompName(rset.getString("comp_name"));
				tj.setStateTitle(rset.getString("state_title"));
				tj.setStateCd(rset.getString("state_code"));
				tj.setStateChange(rset.getTimestamp("state_change").toLocalDateTime().format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm")));

				list.add(tj);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return list;
	}

}
