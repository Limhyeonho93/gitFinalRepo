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

}
