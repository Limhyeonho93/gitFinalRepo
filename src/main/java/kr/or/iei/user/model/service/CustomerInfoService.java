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

	// grid에 표시할 물류회사용 전체 쿼리
	public ArrayList<Company> getCustomerInfo() {
		Connection conn = JDBCTemplate.getConnection();

		ArrayList<Company> arr = dao.getCustomerInfo(conn);

		JDBCTemplate.close(conn);
		return arr;
	}

	// grid에 표시할 셀러자신만 보이는 쿼리문
	public ArrayList<Company> getSelerCustomerInfo(User loginUser) {
		Connection conn = JDBCTemplate.getConnection();

		ArrayList<Company> arr = dao.getSelerCustomerInfo(conn, loginUser);

		JDBCTemplate.close(conn);
		return arr;
	}

	// 중복 체크
	public Company checkCompcd(String compCd) {
		Connection conn = JDBCTemplate.getConnection();

		Company comp = dao.checkCompcd(conn, compCd);

		JDBCTemplate.close(conn);
		return comp;
	}

	// custmoerInfo정보 insert
	public int insertCustomerInfo(Company c) {
		Connection conn = JDBCTemplate.getConnection();

		int res = dao.insertCustomerInfo(conn, c);

		if (res > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}

		JDBCTemplate.close(conn);

		return res;
	}

	//customerInfo 정보 udpate
	public int updateCustomerInfo(Company c) {
		Connection conn = JDBCTemplate.getConnection();

		int res = dao.updateCustomerInfo(conn, c);

		if (res > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}

		JDBCTemplate.close(conn);

		return res;
	}

	// customerInfo삭제
	public int deleteCustomerInfo(String[] compCdArr) {
		Connection conn = JDBCTemplate.getConnection();

		int res = 0;
		for (String compCd : compCdArr) {
			res = dao.deleteCustomerInfo(conn, compCd);
			
			// 삭제도중 에러 발생시 break이후 롤백
			if(res < 1) {
				break;
			}
		}
		
		if (res > 0) {
			JDBCTemplate.commit(conn);
		} else {
			JDBCTemplate.rollback(conn);
		}

		JDBCTemplate.close(conn);

		return res;
	}
}
