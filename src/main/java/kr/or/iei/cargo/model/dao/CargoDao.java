package kr.or.iei.cargo.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.or.iei.cargo.model.vo.CargoGoods;
import kr.or.iei.cargo.model.vo.CargoMain;
import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.user.model.vo.User;

public class CargoDao {
	
	//화물 조회
	public ArrayList<CargoMain> searchCargo(Connection conn, String[] searchValue, String searchOption, User loginUser) {
		
		PreparedStatement pstmt = null;
		
		ResultSet rset = null;

		ArrayList<CargoMain> list = new ArrayList<CargoMain>();
		
		StringBuilder query = new StringBuilder("SELECT * FROM T_cargoMain WHERE ");
		
		query.append(searchOption).append(" IN (");

		for (int i = 0; i < searchValue.length; i++) {
			query.append("?");
			if (i < searchValue.length - 1) {
				query.append(",");
			}
		}
		query.append(")");
		
		// 유저 권한에 따라서 쿼리문 변경하기 위해서
		if (!loginUser.getUserLevel().equals("1")) {
		    query.append(" AND comp_cd = '" + loginUser.getCompCd() + "'");
		}

		try {
			// pstmt=conn.prepareStatement(query);

			pstmt = conn.prepareStatement(query.toString());

			for (int i = 0; i < searchValue.length; i++) {
				pstmt.setString(i + 1, searchValue[i]);
			}

			// System.out.println(searchValue);
			// System.out.println(query);

			rset = pstmt.executeQuery();

			while (rset.next()) {
				CargoMain c = new CargoMain();
				c.setCompCd(rset.getString("comp_cd")); // 회사명으로 바꾸기
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
				c.setGw(rset.getFloat("gw"));
				c.setGwt(rset.getString("gwt"));
				c.setNo(rset.getInt("no"));
				c.setDeliveryStop(rset.getString("delivery_stop"));
				c.setUserId(rset.getString("user_id"));
				c.setRegDate(rset.getString("reg_date"));
				c.setUpdDate(rset.getString("upd_date"));

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
	
	
	   //화물 한 건 등록(T_cargoMain)
	   public int insertCargo(Connection conn, CargoMain cargo) {
	      PreparedStatement pstmt = null;
	      int result = 0;

		String query = "INSERT INTO T_cargoMain (" + "comp_cd, warehouse_moveId, tracking_no, manage_no, "
				+ "receiver_name, receiver_add, receiver_zip, receiver_tel, "
				+ "seller_name, seller_add, seller_tel, gw, gwt, no, delivery_Stop, User_Id, reg_Date, upd_Date) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, sysdate, sysdate)";

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
			pstmt.setFloat(12, cargo.getGw());
			pstmt.setString(13, cargo.getGwt());
			pstmt.setInt(14, cargo.getNo());
			pstmt.setString(15, cargo.getDeliveryStop());
			pstmt.setString(16, cargo.getUserId());

	        result = pstmt.executeUpdate();

	      } catch (SQLException e) {
	         // TODO Auto-generated catch block
	         e.printStackTrace();

	      } finally {
	         JDBCTemplate.close(pstmt);
	      }
	      return result;
	   }

	
	
	//화물 상세 조회
	public ArrayList<CargoGoods> srchCargoDetail(Connection conn, String manageNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		ArrayList<CargoGoods> goods= new ArrayList<CargoGoods>();

		String query = "select * from T_cargoGoods where manage_no = ? ";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, manageNo);
			rset = pstmt.executeQuery();

			while (rset.next()) {
				CargoGoods g=new CargoGoods();
				g.setCompCd(rset.getString("comp_cd"));
				g.setWarehouseMoveid(rset.getString("warehouse_moveId"));
				g.setTrackingNo(rset.getString("tracking_no"));
				g.setManageNo(rset.getString("manage_no"));
				g.setSeq(rset.getInt("seq"));
				g.setGoodsName(rset.getString("goods_name"));
				g.setUnitPrice(rset.getInt("unit_price"));
				g.setQty(rset.getInt("qty"));
				g.setUnitWeight(rset.getFloat("unit_weight"));
				g.setDeliveryStop(rset.getString("delivery_stop"));
				g.setUserId(rset.getString("user_id"));
				g.setRegDate(rset.getString("reg_date"));
				g.setUpdDate(rset.getString("upd_date"));
				
				goods.add(g);

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}

		return goods;
	}
	
	
	//T_cargoMain 업데이트
	public int updateCargoMain(Connection conn, CargoMain cargoMain) {
		
	    int result = 0;
	    PreparedStatement pstmt = null;
	    
	    String query = "UPDATE T_CARGOMAIN SET "
	            + "RECEIVER_NAME = ?, "
	            + "RECEIVER_ADD = ?, "
	            + "RECEIVER_ZIP = ?, "
	            + "RECEIVER_TEL = ?, "
	            + "SELLER_NAME = ?, "
	            + "SELLER_ADD = ?, "
	            + "SELLER_TEL = ?, "
	            + "GW = ?, "
	            + "GWT = ?, "
	            + "NO = ?, "
	            + "DELIVERY_STOP = ?, "
	            + "USER_ID = ? "
	            + "WHERE TRACKING_NO = ?";

	    try {
	    	pstmt=conn.prepareStatement(query);
	    	
	        pstmt.setString(1, cargoMain.getReceiverName());      
	        pstmt.setString(2, cargoMain.getReceiverAdd());         
	        pstmt.setString(3, cargoMain.getReceiverZip());      
	        pstmt.setString(4, cargoMain.getReceiverTel());
	        pstmt.setString(5, cargoMain.getSellerName()); 
	        pstmt.setString(6, cargoMain.getSellerAdd());
	        pstmt.setString(7, cargoMain.getSellerTel());
	        pstmt.setFloat(8, cargoMain.getGw());          
	        pstmt.setString(9, cargoMain.getGwt());          
	        pstmt.setInt(10, cargoMain.getNo()); 
	        pstmt.setString(11, cargoMain.getDeliveryStop());
	        pstmt.setString(12, cargoMain.getUserId()); 
	        pstmt.setString(13, cargoMain.getTrackingNo());

	        result = pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JDBCTemplate.close(pstmt);
	    }

	    return result;
	}
	
	
	//T_CargoGoods 삭제(송장번호 일치 전부 삭제)
	public int deleteCargoGoodsByTrackingNo(Connection conn, String trackingNo) {
	    int result = 0;
	    PreparedStatement pstmt = null;
	    String query = "DELETE FROM T_cargoGoods WHERE tracking_No = ?";

	    try {
	        pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, trackingNo);

	        result = pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JDBCTemplate.close(pstmt);
	    }

	    return result;
	}
	        
	// T_cargoMain 수정
	public int updateCargoDetails(Connection conn, CargoMain cargo) {
	    PreparedStatement pstmt = null;
	    int result = 0;

	    // UPDATE 쿼리 작성
	    String query = "UPDATE T_cargoMain SET "
	            + "receiver_name = ?, "
	            + "receiver_add = ?, receiver_zip = ?, receiver_tel = ?, "
	            + "seller_name = ?, seller_add = ?, seller_tel = ?, gw = ?, gwt = ?, "
	            + "no = ?, delivery_stop = ?, user_id  = ?, upd_date= sysdate "
	            + "WHERE tracking_no = ? and comp_cd = ?";

	    try {
	        pstmt = conn.prepareStatement(query);

	        pstmt.setString(1, cargo.getReceiverName());
	        pstmt.setString(2, cargo.getReceiverAdd());
	        pstmt.setString(3, cargo.getReceiverZip());
	        pstmt.setString(4, cargo.getReceiverTel());
	        pstmt.setString(5, cargo.getSellerName());
	        pstmt.setString(6, cargo.getSellerAdd());
	        pstmt.setString(7, cargo.getSellerTel());
	        pstmt.setFloat(8, cargo.getGw());
	        pstmt.setString(9, cargo.getGwt());
	        pstmt.setInt(10, cargo.getNo());
	        pstmt.setString(11, cargo.getDeliveryStop());
	        pstmt.setString(12, cargo.getUserId());
	        
	        pstmt.setString(13, cargo.getTrackingNo());
	        pstmt.setString(14, cargo.getCompCd());

	        result = pstmt.executeUpdate();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JDBCTemplate.close(pstmt);
	    }
	    return result;
	}

	
	//T_CargoGoods 등록
	public int insertCargoGoods(Connection conn, CargoGoods cargoGoods) {
		PreparedStatement pstmt = null;
		int result = 0;
	  
	    String query = "INSERT INTO T_cargoGoods (comp_Cd, warehouse_Moveid, tracking_No, seq, goods_Name, " +
	            "unit_Price, qty, unit_Weight, delivery_Stop, user_Id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    try {
            pstmt = conn.prepareStatement(query);
            
            pstmt.setString(1, cargoGoods.getCompCd());
            pstmt.setString(2, cargoGoods.getWarehouseMoveid());
            pstmt.setString(3, cargoGoods.getTrackingNo());
            pstmt.setInt(4, cargoGoods.getSeq());
            pstmt.setString(5, cargoGoods.getGoodsName());
            pstmt.setInt(6, cargoGoods.getUnitPrice());
            pstmt.setInt(7, cargoGoods.getQty());
            pstmt.setFloat(8, cargoGoods.getUnitWeight());
            pstmt.setString(9, cargoGoods.getDeliveryStop());
            pstmt.setString(10, cargoGoods.getUserId());

            result = pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JDBCTemplate.close(pstmt);
	    }

	    return result;
	}


	// cargoMain 삭제
	public int deleteCargoMainsByTrackingNo(Connection conn, String trackingNo) {
	    PreparedStatement pstmt = null;
	    int result = 0;
	    try {
	     
	        pstmt = conn.prepareStatement("DELETE FROM T_cargoMain WHERE tracking_no = ?");
	        pstmt.setString(1, trackingNo);
	        result = pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JDBCTemplate.close(pstmt);
	    }
	    return result;
	}

	// 체크항목중 goods 삭제
	public int deleteCargoGoodsByMultiTrackingNos(Connection conn, List<String> trackingNos) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		// 유효성 검사: 리스트가 비었으면 삭제할 것이 없음
	    if (trackingNos == null || trackingNos.isEmpty()) {
	        return 0;
	    }

	    // SQL 쿼리 생성: DELETE FROM ... WHERE tracking_no IN (?, ?, ...)
	    StringBuilder query = new StringBuilder("DELETE FROM T_cargoGoods WHERE tracking_no IN (");
	    for (int i = 0; i < trackingNos.size(); i++) {
	        query.append("?");
	        if (i < trackingNos.size() - 1) {
	            query.append(", ");
	        }
	    }
	    query.append(")");
		
		try {
			pstmt = conn.prepareStatement(query.toString());
			// 바인딩 변수 설정
	        for (int i = 0; i < trackingNos.size(); i++) {
	            pstmt.setString(i + 1, trackingNos.get(i));
	        }
	        
	        result = pstmt.executeUpdate();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JDBCTemplate.close(pstmt); 
	    }
			
			
		return result;
	}


	public int deleteCargoMainsByMultiTrackingNos(Connection conn, List<String> trackingNos) {
		PreparedStatement pstmt = null;
		int result = 0;
		
		if (trackingNos == null || trackingNos.isEmpty()) {
	        return 0;
	    }

	    // SQL 쿼리 생성
	    StringBuilder query = new StringBuilder("DELETE FROM T_cargoMain WHERE tracking_no IN (");
	    for (int i = 0; i < trackingNos.size(); i++) {
	        query.append("?");
	        if (i < trackingNos.size() - 1) {
	            query.append(", ");
	        }
	    }
	    query.append(")");

	    try {
	        pstmt = conn.prepareStatement(query.toString());

	        // 바인딩 변수 설정
	        for (int i = 0; i < trackingNos.size(); i++) {
	            pstmt.setString(i + 1, trackingNos.get(i));
	        }

	        // 삭제 실행
	        result = pstmt.executeUpdate();

	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JDBCTemplate.close(pstmt);
	    }

	    return result;
	}

	
	//T_CargoGoods 수정 
	   public int updCargoGoodsDetail(Connection conn, CargoGoods goods) {
	      PreparedStatement pstmt = null;
	       int result = 0;

	       // UPDATE 쿼리
	       String query = "UPDATE T_cargoGoods SET "
	               + "goods_Name = ?, unit_Price = ?, qty = ?, unit_Weight = ?, "
	             + "user_Id = ?, upd_date = sysdate "
	               + "WHERE tracking_no = ? and seq = ? and comp_Cd = ?";

	       try {
	           pstmt = conn.prepareStatement(query);

	           pstmt.setString(1, goods.getGoodsName());
	           pstmt.setInt(2, goods.getUnitPrice());
	           pstmt.setInt(3, goods.getQty());
	           pstmt.setFloat(4, goods.getUnitWeight());
	           pstmt.setString(5, goods.getUserId());
	           
	           pstmt.setString(6, goods.getTrackingNo());
	           pstmt.setInt(7, goods.getSeq());
	           pstmt.setString(8, goods.getCompCd());

	           result = pstmt.executeUpdate();

	       } catch (SQLException e) {
	           e.printStackTrace();
	       } finally {
	           JDBCTemplate.close(pstmt);
	       }
	       return result;
	   }

	//T_CargoGoods 삭제(송장번호+시퀀스+회사코드 값 일치하는 한 개만)
	public int deleteCargoGoods(Connection conn, CargoGoods goods) {
	    int result = 0;
	    PreparedStatement pstmt = null;
	    String query = "DELETE FROM T_cargoGoods WHERE tracking_No = ? AND seq = ? AND comp_Cd = ? ";

	    try {
	        pstmt = conn.prepareStatement(query);
	        pstmt.setString(1, goods.getTrackingNo());
	        pstmt.setInt(2, goods.getSeq());
	        pstmt.setString(3, goods.getCompCd());

	        result = pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        JDBCTemplate.close(pstmt);
	    }

	    return result;
	}

	
	//화물 조회(엑셀 데이터 입력 위해 compCd와 등록 일자도 비교 필요)
	public CargoMain searchCargoForExcelImp(Connection conn, CargoMain cargoMain) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		CargoMain c=null;
		
		//tracking_no와 comp_cd가 일치하는 최근 2주내의 CargoMain 데이터
		String query = "SELECT * FROM T_cargoMain WHERE tracking_no = ? AND comp_cd = ? AND reg_date BETWEEN SYSDATE - 14 AND SYSDATE";
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, cargoMain.getTrackingNo());
			pstmt.setString(2, cargoMain.getCompCd());
			
			rset=pstmt.executeQuery();
			
			if(rset.next()) {
				c=new CargoMain();
				c.setCompCd(rset.getString("comp_cd"));
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
				c.setGw(rset.getFloat("gw"));
				c.setGwt(rset.getString("gwt"));
				c.setNo(rset.getInt("no"));
				c.setDeliveryStop(rset.getString("delivery_stop"));
				c.setUserId(rset.getString("user_id"));
				c.setRegDate(rset.getString("reg_date"));
				c.setUpdDate(rset.getString("upd_date"));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
	        JDBCTemplate.close(pstmt);
	    }
		
		return c;
	}

		
}

