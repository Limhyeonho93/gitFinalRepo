package kr.or.iei.invoice.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.invoice.model.vo.CargoUnitInvoice;
import kr.or.iei.invoice.model.vo.CompInfo;
import kr.or.iei.invoice.model.vo.DailyInsertInvoice;
import kr.or.iei.invoice.model.vo.Invoice;
import kr.or.iei.invoice.model.vo.ShoppingCost;
import kr.or.iei.user.model.vo.User;

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
	

	public ArrayList<Invoice> allSelerInvoice(Connection conn, Date from, Date to, User loginUser) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String query = "SELECT ti.comp_cd,max(comp_name) comp_name, sum(day_amount) as total_payment,sum(ti.total_weight) as total_weight  FROM t_invoice ti LEFT JOIN t_customerinfo tc ON ti.comp_cd = tc.comp_cd  WHERE deliveryed_date BETWEEN ? AND ? AND ti.comp_cd = ? GROUP BY ti.comp_cd";
		ArrayList<Invoice> arr = new ArrayList<Invoice>();
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setDate(1, from);
			pstmt.setDate(2, to);
			pstmt.setString(3, loginUser.getCompCd());

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

	public ArrayList<CompInfo> getAllSeellerComp(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query = "SELECT * FROM T_CUSTOMERINFO WHERE COMP_DIV = 'S' AND DEAL_FLG = 1";
		ArrayList<CompInfo> arr = new ArrayList<CompInfo>();
		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				CompInfo ci = new CompInfo();
				
				ci.setCompCd(rset.getString("comp_cd"));
				ci.setCompName(rset.getString("comp_name"));
				ci.setCompDiv(rset.getString("comp_div"));
				ci.setCompAddr(rset.getString("comp_addr"));
				ci.setCompZip(rset.getString("comp_zip"));
				ci.setCompTel(rset.getString("comp_tel"));
				ci.setDealFlg(rset.getInt("deal_flg"));

				arr.add(ci);
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

	public ArrayList<CargoUnitInvoice> trackingNoInvoice(Connection conn, String from, String to, String compCd) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query = "SELECT tm.*,TO_CHAR(tb.in_date, 'YY\"년\" MM\"월\" DD\"일\" HH24:MI') AS in_date, TO_CHAR(tb.out_date, 'YY\"년\" MM\"월\" DD\"일\" HH24:MI') AS out_date,tc.comp_name FROM t_impbonded tb "
				+ "LEFT JOIN t_cargomain tm ON tb.manage_no = tm.manage_no "
				+ "LEFT JOIN t_customerinfo tc ON tm.comp_cd = tc.comp_cd "
				+ "WHERE tb.out_date BETWEEN ? AND ? "
				+ "AND tm.comp_cd = ? ";
		ArrayList<CargoUnitInvoice> arr = new ArrayList<CargoUnitInvoice>();
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, from);
			pstmt.setString(2, to);
			pstmt.setString(3, compCd);

			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				CargoUnitInvoice cui = new CargoUnitInvoice();
				cui.setCompName(rset.getString("comp_name"));
				cui.setCompCd(rset.getString("comp_cd"));
				cui.setWarehouseMoveid(rset.getString("warehouse_moveid"));
				cui.setTrackingNo(rset.getString("tracking_no"));
				cui.setManageNo(rset.getString("manage_no"));
				cui.setInDate(rset.getString("in_date"));
				cui.setOutDate(rset.getString("out_date"));
				cui.setNo(rset.getInt("no"));
				cui.setGw(rset.getInt("gw"));
				cui.setReceiverName(rset.getString("receiver_name"));
				cui.setReceiverAdd(rset.getString("receiver_add"));
				cui.setSellerName(rset.getString("seller_name"));

				arr.add(cui);
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

	public ArrayList<DailyInsertInvoice> getDailyInvoiceValue(Connection conn, String yesterdayStr) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query = "SELECT"
				+ "    tm.comp_cd,"
				+ "    TO_CHAR(tb.out_date, 'YYYY-MM-DD') AS out_date,"
				+ "    mw.dis_grade,"
				+ "    SUM(tm.gw) AS total_weight"
				+ "  FROM t_impbonded tb"
				+ "  INNER JOIN t_cargomain tm ON tb.manage_no = tm.manage_no"
				+ "  INNER JOIN m_ziplist mz ON tm.receiver_zip = mz.zip"
				+ "  INNER JOIN m_warehouse mw ON mz.ware_cd = mw.ware_cd"
				+ "  WHERE tb.out_date = ?"
				+ "  GROUP BY tm.comp_cd, TO_CHAR(tb.out_date, 'YYYY-MM-DD'), mw.dis_grade";
		ArrayList<DailyInsertInvoice> arr = new ArrayList<DailyInsertInvoice>();
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, yesterdayStr);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				DailyInsertInvoice res = new DailyInsertInvoice();
				res.setCompCd(rset.getString("comp_cd"));
				res.setOutDate(rset.getString("out_date"));
				res.setDisGrade(rset.getString("dis_grade"));
				res.setTotalWeight(rset.getInt("total_weight"));
				arr.add(res);
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

	public ArrayList<ShoppingCost> getShoppingCost(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query = "SELECT * FROM m_shoppingcost";
		ArrayList<ShoppingCost> arr = new ArrayList<ShoppingCost>();
		try {
			pstmt = conn.prepareStatement(query);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				ShoppingCost sc = new ShoppingCost();
				
				sc.setDisGrade(rset.getString("dis_grade"));
				sc.setWeight(rset.getInt("weight"));
				sc.setWareCd(rset.getString("ware_cd"));
				sc.setPrice(rset.getInt("price"));

				arr.add(sc);
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

	public int insertInvoice(Connection conn, String compCd, String yesterdayStr, int amount, int weight) {
		PreparedStatement pstmt = null;

        int result = 0;

        String query = "INSERT INTO T_INVOICE (COMP_CD, DELIVERYED_DATE, DAY_AMOUNT, TOTAL_WEIGHT) "
        		+ "VALUES (?, TO_DATE(?, 'YYYY-MM-DD'), ?, ?)";

        try {
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, compCd);
            pstmt.setString(2, yesterdayStr);
            //pstmt.setString(2, "2025-05-11");

            pstmt.setInt(3, amount);
            
            pstmt.setInt(4, weight);

            result = pstmt.executeUpdate();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            JDBCTemplate.close(pstmt);
        }
        return result;
	}


	public ArrayList<CompInfo> getAllComp(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query = "SELECT * FROM T_CUSTOMERINFO WHERE DEAL_FLG = 1";
		ArrayList<CompInfo> arr = new ArrayList<CompInfo>();
		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				CompInfo ci = new CompInfo();
				
				ci.setCompCd(rset.getString("comp_cd"));
				ci.setCompName(rset.getString("comp_name"));
				ci.setCompDiv(rset.getString("comp_div"));
				ci.setCompAddr(rset.getString("comp_addr"));
				ci.setCompZip(rset.getString("comp_zip"));
				ci.setCompTel(rset.getString("comp_tel"));
				ci.setDealFlg(rset.getInt("deal_flg"));

				arr.add(ci);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return arr;
	}




}
