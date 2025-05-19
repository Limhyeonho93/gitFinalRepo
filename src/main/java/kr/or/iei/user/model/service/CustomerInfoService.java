package kr.or.iei.user.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.tracking.model.vo.TrackingJoin;
import kr.or.iei.user.model.dao.CustomerInfoDao;
import kr.or.iei.user.model.vo.Company;
import kr.or.iei.user.model.vo.User;

public class CustomerInfoService {
	private CustomerInfoDao dao;

	public CustomerInfoService() {
		dao = new CustomerInfoDao();
	}

	public ArrayList<Company> getCustomerInfo() {
		Connection conn = JDBCTemplate.getConnection();

		ArrayList<Company> arr = dao.getCustomerInfo(conn);

		JDBCTemplate.close(conn);
		return arr;
	}

	public ArrayList<Company> getSelerCustomerInfo(User loginUser) {
		Connection conn = JDBCTemplate.getConnection();

		ArrayList<Company> arr = dao.getSelerCustomerInfo(conn, loginUser);

		JDBCTemplate.close(conn);
		return arr;
	}

	public Company checkCompcd(String compCd) {
		Connection conn = JDBCTemplate.getConnection();

		Company comp = dao.checkCompcd(conn, compCd);

		JDBCTemplate.close(conn);
		return comp;
	}

	public int insertCustomerInfo(Company c) {
		Connection conn = JDBCTemplate.getConnection();

		// impBonded에 데이터 등록
		int res = dao.insertCustomerInfo(conn, c);

		if (res > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}

		JDBCTemplate.close(conn);

		return res;
	}

	public int updateCustomerInfo(Company c) {
		Connection conn = JDBCTemplate.getConnection();

		// impBonded에 데이터 등록
		int res = dao.updateCustomerInfo(conn, c);

		if (res > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}

		JDBCTemplate.close(conn);

		return res;
	}
}
