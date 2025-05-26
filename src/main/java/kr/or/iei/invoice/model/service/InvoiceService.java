package kr.or.iei.invoice.model.service;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;

import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.invoice.model.dao.InvoiceDao;
import kr.or.iei.invoice.model.vo.CargoUnitInvoice;
import kr.or.iei.invoice.model.vo.CompInfo;
import kr.or.iei.invoice.model.vo.DailyInsertInvoice;
import kr.or.iei.invoice.model.vo.Invoice;
import kr.or.iei.invoice.model.vo.ShoppingCost;
import kr.or.iei.user.model.vo.User;

public class InvoiceService {
	private InvoiceDao dao;

	public InvoiceService() {
		this.dao = new InvoiceDao();
	}

	public ArrayList<Invoice> allInvoice(Date from, Date to) {
		Connection conn = JDBCTemplate.getConnection();

		ArrayList<Invoice> arr = dao.allInvoice(conn, from, to);

		JDBCTemplate.close(conn);
		return arr;
	}
	
	public ArrayList<Invoice> allSelerInvoice(Date from, Date to, User loginUser) {
		Connection conn = JDBCTemplate.getConnection();

		ArrayList<Invoice> arr = dao.allSelerInvoice(conn, from, to,loginUser);

		JDBCTemplate.close(conn);
		return arr;
	}

	public ArrayList<CompInfo> getAllSeellerComp() {
		Connection conn = JDBCTemplate.getConnection();

		ArrayList<CompInfo> arr = dao.getAllSeellerComp(conn);

		JDBCTemplate.close(conn);
		return arr;
	}

	public ArrayList<CargoUnitInvoice> trackingNoInvoice(String from, String to, String compCd) {
		Connection conn = JDBCTemplate.getConnection();

		ArrayList<CargoUnitInvoice> arr = dao.trackingNoInvoice(conn,from,to,compCd);

		JDBCTemplate.close(conn);
		return arr;
	}

	public ArrayList<DailyInsertInvoice> getDailyInvoiceValue(String yesterdayStr) {
		Connection conn = JDBCTemplate.getConnection();

		ArrayList<DailyInsertInvoice> res = dao.getDailyInvoiceValue(conn,yesterdayStr);

		JDBCTemplate.close(conn);
		return res;
	}

	public ArrayList<ShoppingCost> getShoppingCost() {
		Connection conn = JDBCTemplate.getConnection();

		ArrayList<ShoppingCost> arr = dao.getShoppingCost(conn);

		JDBCTemplate.close(conn);
		return arr;
	}

	public int insertInvoice(String compCd, String yesterdayStr, int amount, int weight) {
		Connection conn = JDBCTemplate.getConnection();


        int res = dao.insertInvoice(conn,compCd,yesterdayStr,amount,weight); 
        
        if(res >0) {
            JDBCTemplate.commit(conn);
        }else {
            JDBCTemplate.rollback(conn);
            System.out.println("error발생");
        }
        
        JDBCTemplate.close(conn);

        return res;
	}

	public ArrayList<CompInfo> getAllComp() {
		Connection conn = JDBCTemplate.getConnection();

		ArrayList<CompInfo> arr = dao.getAllComp(conn);

		JDBCTemplate.close(conn);
		return arr;
	}


	
	
	
}
