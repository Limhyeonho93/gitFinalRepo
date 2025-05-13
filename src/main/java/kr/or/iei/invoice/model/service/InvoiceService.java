package kr.or.iei.invoice.model.service;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;

import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.invoice.model.dao.InvoiceDao;
import kr.or.iei.invoice.model.vo.Invoice;

public class InvoiceService {
	private InvoiceDao dao;
	
	public InvoiceService(){
		this.dao = new InvoiceDao();
	}

	public ArrayList<Invoice> allInvoice(Date from,Date to) {
		Connection conn = JDBCTemplate.getConnection();
		
		ArrayList<Invoice> arr = dao.allInvoice(conn, from, to);
		
		JDBCTemplate.close(conn);
		return arr;
	}
}
