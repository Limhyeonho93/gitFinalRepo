package kr.or.iei.user.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.user.model.vo.Company;
import kr.or.iei.user.model.vo.User;

public class UserDao {


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
				loginUser.setGrade(rset.getString("grade"));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}

		return loginUser;
	}

	public int updateUser(Connection conn, User updUser) {
		PreparedStatement pstmt = null;

		int result = 0; // 실패는 0, 성공은 1

		String query = "UPDATE T_USERS " + "SET USER_NAME = ?, TEL_NO = ? " + "WHERE USER_ID = ?";

		try {
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, updUser.getUserName());
			pstmt.setString(2, updUser.getTelNo());
			pstmt.setString(3, updUser.getUserId());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}

		return result;
	}

	public int updateUserPw(Connection conn, String userId, String newUserPw) {

		PreparedStatement pstmt = null;

		int result = 0;
		String query = "UPDATE T_Users " + "SET user_pw  = ?, " + "    upd_date = SYSDATE " + // 갱신일도 함께 업데이트 해줌
				"WHERE user_id = ? ";

		try {
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, newUserPw);
			pstmt.setString(2, userId);

			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;

	}
	

	public int insertUser(Connection conn, User user) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "INSERT INTO T_USERS VALUES(?, ?, ?, ?, ?, ?, SYSDATE, SYSDATE, '2')";
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
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}	
		return result;
	}

	
}