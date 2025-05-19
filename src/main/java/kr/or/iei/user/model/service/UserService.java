package kr.or.iei.user.model.service;

import java.sql.Connection;

import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.user.model.dao.UserDao;
import kr.or.iei.user.model.vo.Company;
import kr.or.iei.user.model.vo.User;

public class UserService {
	private UserDao dao;

	public UserService() {
		dao = new UserDao();
	}

	public int insertUser(User user) {
		Connection conn = JDBCTemplate.getConnection();
		int result = dao.insertUser(conn, user);

		if (result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);

		return result;
	}

	public User loginUser(String userId, String userPw) {
		Connection conn = JDBCTemplate.getConnection();
		User loginUser = dao.loginUser(conn, userId, userPw);

		JDBCTemplate.close(conn);
		return loginUser;
	}

	public int insertCompany(Company company) {
		Connection conn = JDBCTemplate.getConnection();
		int result = dao.insertCompany(conn, company);

		if (result > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		
		return result;
	}

	public boolean isValidCompanyCode(String compCd) {
		Connection conn = JDBCTemplate.getConnection();
		boolean isValid = dao.isValidCompanyCode(conn, compCd);
		JDBCTemplate.close(conn);
		return isValid;
	}

	public int updateUser(User updUser) {
		Connection conn = JDBCTemplate.getConnection();
		int result = dao.updateUser(conn, updUser);
		
		if(result > 0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		return result;
		
	}

	public int updateUserPw(String userId, String newUserPw) {
		Connection conn = JDBCTemplate.getConnection();
		int result = dao.updateUserPw(conn, userId, newUserPw);
		if(result > 0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		return result;
	}

	public User userChk(String userId, String userName) {	//
		Connection conn = JDBCTemplate.getConnection();
		User chkUser = dao.forgotPw(conn, userId, userName);
		JDBCTemplate.close(conn);
		System.out.println("return chkUser 값 : " + chkUser);
		return chkUser;
	}

	public int updateTempPw(String userId, String tempPw) {	//임시 비밀번호 확인
		 Connection conn = JDBCTemplate.getConnection();
		    UserDao dao = new UserDao();
		    int result = dao.updateTempPw(conn, userId, tempPw);
		    
		    if (result > 0) {
		    	JDBCTemplate.commit(conn);
		    }else {
		    	JDBCTemplate.rollback(conn);
		    }	
		    JDBCTemplate.close(conn);
		    
		    return result;
	}
}

