package kr.or.iei.cargo.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import kr.or.iei.cargo.model.vo.CargoMain;
import kr.or.iei.common.JDBCTemplate;

public class CargoDao {
	
	
	public ArrayList<CargoMain> searchCargo(Connection conn) {
		PreparedStatement pstmt=null;
		ResultSet rset=null;
		
		ArrayList<CargoMain> list=new ArrayList<CargoMain>();
		String query="select * from T_cargoMain";
		
		try {
			pstmt=conn.prepareStatement(query);
			rset=pstmt.executeQuery();
			
			while(rset.next()) {
				CargoMain c=new CargoMain();
				c.setCompCd(rset.getString("comp_cd"));
				c.setWarehouseMoveid(rset.getString("warehouse_moveId"));
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
