package kr.or.iei.cargo.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.or.iei.cargo.model.vo.CargoMain;
import kr.or.iei.common.JDBCTemplate;

public class CargoDao {
	
	
	public ArrayList<CargoMain> searchCargo(Connection conn, String keyword) {
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		
		ArrayList<CargoMain> list=new ArrayList<CargoMain>();
		String query="select * from T_cargoMain where warehouse_moveId LIKE ? OR comp_cd LIKE ?";
		
		try {
			pstmt=conn.prepareStatement(query);
			
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setString(2, "%" + keyword + "%");
			
			rset=pstmt.executeQuery();
			
			while(rset.next()) {
				CargoMain c=new CargoMain();
				c.setCompCd(rset.getString("comp_cd")); //회사명으로 바꾸기 
				c.setWarehouseMoveid(rset.getString("warehouse_moveId"));
				c.setTrackingNo(rset.getString("tracking_no"));
				c.setWarehouseMoveid(rset.getString("manage_no"));
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
}
