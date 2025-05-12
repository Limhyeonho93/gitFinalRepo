package kr.or.iei.user.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import kr.or.iei.common.JDBCTemplate;
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
		}finally {
			JDBCTemplate.close(pstmt);
		}
				
		return result;
	}
	
	
}
