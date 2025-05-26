package kr.or.iei.cargo.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.or.iei.cargo.model.vo.CargoGoods;
import kr.or.iei.cargo.model.vo.CargoMain;
import kr.or.iei.cargo.model.vo.ManageNo;
import kr.or.iei.common.JDBCTemplate;

public class ManageNoDao {

	public ArrayList<CargoMain> getNullManageNo(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		ArrayList<CargoMain> cargoArr = new ArrayList<CargoMain>();

		String query = "SELECT * FROM t_cargomain WHERE manage_no IS NULL";

		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();

			while (rset.next()) {
				CargoMain cm = new CargoMain();
				cm.setCompCd(rset.getString("comp_cd"));
				cm.setWarehouseMoveid(rset.getString("warehouse_moveId"));
				cm.setTrackingNo(rset.getString("tracking_no"));
				cm.setManageNo(rset.getString("manage_no"));
				cargoArr.add(cm);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}

		return cargoArr;
	}

	public ManageNo getManageNoDate(Connection conn, String toDayStr) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		ManageNo manageNo = null;

		String query = "SELECT * FROM t_manage_no WHERE cdate = ?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, toDayStr);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				manageNo = new ManageNo();
				manageNo.setCData(rset.getString("cdate"));
				manageNo.setNextNo(rset.getInt("nextno"));
				
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}

		return manageNo;
	}

	public int insertManageNoDate(Connection conn) {
		PreparedStatement pstmt = null;
		int res = 0;
		String query = "INSERT INTO t_manage_no values(TO_DATE(TO_CHAR(SYSDATE, 'YYYY-MM-DD'), 'YYYY-MM-DD'), 1)";
		
		try {
			pstmt = conn.prepareStatement(query);
			res = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return res;
	}

	public int updateCargoMainManageNo(Connection conn, CargoMain cm, String manageNoStr) {
		PreparedStatement pstmt = null;
		int res = 0;
		String query = "UPDATE t_cargomain SET manage_no = ? WHERE comp_cd = ? AND WAREHOUSE_MOVEID = ? AND tracking_no = ?";
		
		try {
			System.out.println(manageNoStr);
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, manageNoStr);
			pstmt.setString(2, cm.getCompCd());
			pstmt.setString(3, cm.getWarehouseMoveid());
			pstmt.setString(4, cm.getTrackingNo());

			res = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return res;
	}
	
	public int updateCargoGoodsManageNo(Connection conn, CargoMain cm, String manageNoStr) {
		PreparedStatement pstmt = null;
		int res = 0;
		String query = "UPDATE t_cargogoods SET manage_no = ? WHERE comp_cd = ? AND WAREHOUSE_MOVEID = ? AND tracking_no = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, manageNoStr);
			pstmt.setString(2, cm.getCompCd());
			pstmt.setString(3, cm.getWarehouseMoveid());
			pstmt.setString(4, cm.getTrackingNo());

			res = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return res;
	}

	public int updateNextNo(Connection conn, String toDayStr, int nextNo) {
		PreparedStatement pstmt = null;
	    int result = 0;

	    String query = "UPDATE t_manage_no SET nextno = ? WHERE cdate = TO_DATE(?, 'YYYY-MM-DD')";

	    try {
	        pstmt = conn.prepareStatement(query);
	        pstmt.setInt(1, nextNo);
	        pstmt.setString(2, toDayStr);
	        result = pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JDBCTemplate.close(pstmt);
	    }

	    return result;
	}

	public String findZipWareCd(Connection conn, String zip) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String wareCd = null;

		String query = "SELECT ware_cd FROM m_ziplist mz WHERE mz.zip = ?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, zip);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				wareCd = rset.getString("ware_cd");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}

		return wareCd;
	}

	public String likeZipWareCd(Connection conn, String zip) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String wareCd = "WARESEOUL";

		String query = "SELECT ware_cd FROM m_ziplist mz WHERE mz.zip LIKE ? AND ROWNUM = 1";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, zip.substring(0, 3) + "%" );
			rset = pstmt.executeQuery();

			if (rset.next()) {
				wareCd = rset.getString("ware_cd");
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}

		return wareCd;
		
	}

}
