package kr.or.iei.cargo.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.or.iei.cargo.model.vo.CargoMain;
import kr.or.iei.common.JDBCTemplate;

public class CargoDao {
	
	
	public ArrayList<CargoMain> searchCargo(Connection conn, String[] searchValue,String searchOption) {
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		
		ArrayList<CargoMain> list=new ArrayList<CargoMain>();
	    
		StringBuilder query = new StringBuilder("SELECT * FROM T_cargoMain WHERE ");
	    query.append(searchOption).append(" IN (");

	    for (int i = 0; i < searchValue.length; i++) {
	        query.append("?");
	        if (i < searchValue.length - 1) {
	            query.append(",");
	        }
	    }
	    query.append(")");
		
	      try {
	         //pstmt=conn.prepareStatement(query);
	         
	    	  pstmt = conn.prepareStatement(query.toString());

	          for (int i = 0; i < searchValue.length; i++) {
	              pstmt.setString(i + 1, searchValue[i]);
	          }
	    	  
	         System.out.println(searchValue);
	         System.out.println(query);

	         //pstmt.setString(1, searchOption);
			
			rset=pstmt.executeQuery();
			
			while(rset.next()) {
				CargoMain c= new CargoMain();
				c.setCompCd(rset.getString("comp_cd")); //회사명으로 바꾸기 
				c.setWarehouseMoveid(rset.getString("warehouse_moveId"));
				c.setTrackingNo(rset.getString("tracking_no"));
				c.setManageNo(rset.getString("manage_no"));
				c.setReceiverName(rset.getString("receiver_name"));
				c.setReceiverAdd(rset.getString("receiver_add"));
				c.setReceiverZip(rset.getString("receiver_zip"));
				c.setReceiverTel(rset.getString("receiver_tel"));
				c.setSellerName(rset.getString("seller_name"));
				c.setSellerAdd(rset.getString("seller_add"));
				c.setSellerTel(rset.getString("seller_tel"));
				c.setGw(rset.getInt("gw"));
				c.setGwt(rset.getString("gwt"));
				c.setNo(rset.getInt("no"));
				//c.set(rset.getString("")); //가격 합계 추가
				//c.set(rset.getString("")); 상품명1개(MAX or MIN 1개만) 추가 
				
				list.add(c);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}

		return list;
	}

	public int insertCargo(Connection conn, CargoMain cargo) {
        PreparedStatement pstmt = null;
        int result = 0;
        
        String query = "INSERT INTO T_cargoMain ("
                + "comp_cd, warehouse_moveId, tracking_no, manage_no, "
                + "receiver_name, receiver_add, receiver_zip, receiver_tel, "
                + "seller_name, seller_add, seller_tel, gw, gwt, no) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
		try {
			pstmt = conn.prepareStatement(query);
			
			 pstmt.setString(1, cargo.getCompCd());
		        pstmt.setString(2, cargo.getWarehouseMoveid());
		        pstmt.setString(3, cargo.getTrackingNo());
		        pstmt.setString(4, cargo.getManageNo());
		        pstmt.setString(5, cargo.getReceiverName());
		        pstmt.setString(6, cargo.getReceiverAdd());
		        pstmt.setString(7, cargo.getReceiverZip());
		        pstmt.setString(8, cargo.getReceiverTel());
		        pstmt.setString(9, cargo.getSellerName());
		        pstmt.setString(10, cargo.getSellerAdd());
		        pstmt.setString(11, cargo.getSellerTel());
		        pstmt.setInt(12, cargo.getGw());
		        pstmt.setString(13, cargo.getGwt());
		        pstmt.setInt(14, cargo.getNo());
		        
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
