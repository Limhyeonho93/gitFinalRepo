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

	//
	
	public User loginUser(String email, String password) {
	    Connection conn = JDBCTemplate.getConnection();
	    User loginUser = dao.loginUser(conn, email, password);
	    JDBCTemplate.close(conn);
	    return loginUser;
	}

	
	public int registerCompany(Company company, String password) {
	    Connection conn = JDBCTemplate.getConnection();
	    int result = 0;

	    try {
	        int companyResult = dao.insertCompanyInfo(conn, company);
	        if (companyResult > 0) {
	            int userResult = dao.insertUser(conn, company.getEmail(), password, company.getComp_cd());
	            if (userResult > 0) {
	                JDBCTemplate.commit(conn);
	                result = 1;
	            } else {
	                JDBCTemplate.rollback(conn);
	            }
	        } else {
	            JDBCTemplate.rollback(conn);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        JDBCTemplate.rollback(conn);
	    } finally {
	        JDBCTemplate.close(conn);
	    }

	    return result;
	}
	
	//
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
}

