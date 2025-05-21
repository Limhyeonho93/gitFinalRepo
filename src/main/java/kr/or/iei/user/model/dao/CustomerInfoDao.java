package kr.or.iei.user.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.tracking.model.vo.DailyWarehouseSummary;
import kr.or.iei.user.model.vo.Company;
import kr.or.iei.user.model.vo.User;

public class CustomerInfoDao {

	public ArrayList<Company> getCustomerInfo(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String query = "SELECT tc.*, tu.user_id FROM t_customerinfo tc LEFT JOIN (SELECT comp_cd, max(user_id) user_id FROM t_users where user_level IN ('1','2') GROUP BY comp_cd) tu ON tc.comp_cd = tu.comp_cd ";

		ArrayList<Company> arr = new ArrayList<Company>();
		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			while (rset.next()) {
				Company c = new Company();
				c.setComp_cd(rset.getString("comp_cd"));
				c.setComp_name(rset.getString("comp_name"));
				c.setComp_addr(rset.getString("comp_addr"));
				c.setComp_zip(rset.getString("comp_zip"));
				c.setComp_tel(rset.getString("comp_tel"));
				c.setDeal_flg(rset.getString("deal_flg").charAt(0));
				c.setReg_date(rset.getDate("reg_date"));
				c.setGrade(rset.getString("grade").charAt(0));
				c.setComp_div(rset.getString("comp_div"));

				arr.add(c);
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

	public ArrayList<Company> getSelerCustomerInfo(Connection conn, User loginUser) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String query = "SELECT tc.*, tu.user_id FROM t_customerinfo tc LEFT JOIN (SELECT comp_cd, max(user_id) user_id FROM t_users where user_level IN ('1','2') GROUP BY comp_cd) tu ON tc.comp_cd = tu.comp_cd WHERE tc.comp_cd = ?";

		ArrayList<Company> arr = new ArrayList<Company>();
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, loginUser.getCompCd());
			rset = pstmt.executeQuery();
			while (rset.next()) {
				Company c = new Company();
				c.setComp_cd(rset.getString("comp_cd"));
				c.setComp_name(rset.getString("comp_name"));
				c.setComp_addr(rset.getString("comp_addr"));
				c.setComp_zip(rset.getString("comp_zip"));
				c.setComp_tel(rset.getString("comp_tel"));
				c.setDeal_flg(rset.getString("deal_flg").charAt(0));
				c.setReg_date(rset.getDate("reg_date"));
				c.setGrade(rset.getString("grade").charAt(0));
				c.setComp_div(rset.getString("comp_div"));

				arr.add(c);
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

	public Company checkCompcd(Connection conn, String compCd) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String query = "SELECT * FROM t_customerinfo tc WHERE comp_cd = ? ";

		Company comp = null;
		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				comp = new Company();
				comp.setComp_cd(rset.getString("comp_cd"));

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return comp;
	}

	public int insertCustomerInfo(Connection conn, Company c) {
		PreparedStatement pstmt = null;

        int result = 0;

        String query = "INSERT INTO t_customerinfo (comp_cd, comp_name, comp_div, comp_addr, comp_zip, comp_tel, deal_flg, reg_date, upd_date, grade) "
        		+ "VALUES (?, ?, ?, ?, ?, ?, ?, SYSDATE, SYSDATE, ?)";

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, c.getComp_cd());
            pstmt.setString(2, c.getComp_name());
            pstmt.setString(3, c.getComp_div());
            pstmt.setString(4, c.getComp_addr());
            pstmt.setString(5, c.getComp_zip());
            pstmt.setString(6, c.getComp_tel());
            pstmt.setString(7, String.valueOf(c.getDeal_flg()));
            pstmt.setString(8, String.valueOf(c.getGrade()));

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            JDBCTemplate.close(pstmt);
        }
        return result;
	}

	public int updateCustomerInfo(Connection conn, Company c) {
		PreparedStatement pstmt = null;

        int result = 0;

        String query = "UPDATE t_customerinfo "
                + "SET comp_name = ?, "
                + "    comp_div = ?, "
                + "    comp_addr = ?, "
                + "    comp_zip = ?, "
                + "    comp_tel = ?, "
                + "    deal_flg = ?, "
                + "    upd_date = SYSDATE, "
                + "    grade = ? "
                + "WHERE comp_cd = ?";

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, c.getComp_name());
            pstmt.setString(2, c.getComp_div());
            pstmt.setString(3, c.getComp_addr());
            pstmt.setString(4, c.getComp_zip());
            pstmt.setString(5, c.getComp_tel());
            pstmt.setString(6, String.valueOf(c.getDeal_flg()));
            pstmt.setString(7, String.valueOf(c.getGrade()));
            pstmt.setString(8, c.getComp_cd());

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            JDBCTemplate.close(pstmt);
        }
        return result;
	}

	public int deleteCustomerInfo(Connection conn, String compCd) {
		PreparedStatement pstmt = null;

        int result = 0;

        String query = "DELETE FROM t_customerinfo WHERE comp_cd = ? ";

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, compCd);

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
