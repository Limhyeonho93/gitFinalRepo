package kr.or.iei.user.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.user.model.vo.Company;
import kr.or.iei.user.model.vo.User;

public class UserDao {

	public int insertUser(Connection conn, User user) {
		PreparedStatement pstmt = null;

		int result = 0;

		String query = "INSERT INTO T_USERS (user_id, comp_cd, user_pw, user_name, dept_name, tel_no, reg_date, upd_date ) VALUES (?, ?, ?, ?, ?, ?, sysdate, sysdate)";

		try {
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, user.getUserId());
			pstmt.setString(2, user.getCompCd());
			pstmt.setString(3, user.getUserPw());
			pstmt.setString(4, user.getUserName());
			pstmt.setString(5, user.getDeptName());
			pstmt.setString(6, user.getTelNo());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}

		return result;
	}

	public User loginUser(Connection conn, String userId, String userPw) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		User loginUser = null;

		String query = "SELECT * FROM T_USERS WHERE user_id = ? AND user_pw = ?";

		try {
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, userId);
			pstmt.setString(2, userPw);

			rset = pstmt.executeQuery();

			if (rset.next()) {

				loginUser = new User();

				loginUser.setUserId(rset.getString("user_id"));
				loginUser.setCompCd(rset.getString("comp_cd"));
				loginUser.setUserPw(rset.getString("user_pw"));
				loginUser.setUserName(rset.getString("user_name"));
				loginUser.setDeptName(rset.getString("dept_name"));
				loginUser.setTelNo(rset.getString("tel_no"));
				loginUser.setRegDate(rset.getDate("reg_date"));
				loginUser.setUpdDate(rset.getDate("upd_date"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}

		return loginUser;
	}

	public int insertCompany(Connection conn, Company company) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		String query = "INSERT INTO T_CustomerInfo (comp_cd, comp_name, comp_addr, comp_zip, comp_tel, deal_flg, reg_date, upd_date, grade) VALUES (?, ?, ?, ?, ?, ?, sysdate, sysdate, ?)";
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, company.getComp_cd());
			pstmt.setString(2, company.getComp_name());
			pstmt.setString(3, company.getComp_addr());
			pstmt.setString(4, company.getComp_zip());
			pstmt.setString(5, company.getComp_tel());
			pstmt.setString(6, String.valueOf(company.getDeal_flg()));
			pstmt.setString(7, String.valueOf(company.getGrade()));
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}

	public boolean isValidCompanyCode(Connection conn, String compCd) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
        boolean isValid = false;

		String query = "SELECT COUNT(*) FROM T_CustomerInfo WHERE comp_cd = ?";
		
		
        try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, compCd);
			
			rset = pstmt.executeQuery();	
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}

        return isValid;
	}
}