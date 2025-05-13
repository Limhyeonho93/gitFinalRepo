package kr.or.iei.invoice.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.invoice.model.vo.Invoice;

public class InvoiceDao {

	public ArrayList<Invoice> allInvoice(Connection conn, Date from, Date to) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query = "SELECT ti.comp_cd,max(comp_name) comp_name, sum(day_amount) as total_payment,sum(ti.total_weight) as total_weight  FROM t_invoice ti LEFT JOIN t_customerinfo tc ON ti.comp_cd = tc.comp_cd  WHERE deliveryed_date BETWEEN ? AND ? GROUP BY ti.comp_cd";
		ArrayList<Invoice> arr = new ArrayList<Invoice>();
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setDate(1, from);
			pstmt.setDate(2, to);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Invoice iv = new Invoice();
				
				iv.setCompCd(rset.getString("comp_cd"));
				iv.setCompName(rset.getString("comp_name"));
				iv.setTotalPayment(rset.getInt("total_payment"));
				iv.setTotalWeight(rset.getInt("total_weight"));

				arr.add(iv);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return arr;
	}

}
