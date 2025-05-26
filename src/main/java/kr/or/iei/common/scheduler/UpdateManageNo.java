package kr.or.iei.common.scheduler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import kr.or.iei.cargo.model.dao.ManageNoDao;
import kr.or.iei.cargo.model.service.ManageNoService;
import kr.or.iei.cargo.model.vo.CargoMain;
import kr.or.iei.cargo.model.vo.ManageNo;
import kr.or.iei.common.JDBCTemplate;
import kr.or.iei.tracking.model.dao.TrackingDao;

public class UpdateManageNo implements Runnable {

	public void run() {
		String pidFileName = System.getProperty("java.io.tmpdir") + "updateManageNo.pid";
		File pidFile = new File(pidFileName);

		if (pidFile.exists()) {
			System.out.println("[UpdateManageNo] 작업 이미 실행 중이라 종료합니다.");
			return;
		}
		
		boolean connFlg = true;
		Connection conn = JDBCTemplate.getConnection();

		try {
			FileWriter fileWr = new FileWriter(pidFile);
			fileWr.write("run");
			fileWr.close();

			System.out.println("manage_no작업 시작");

			ManageNoService service = new ManageNoService();

			ArrayList<CargoMain> mainArr = service.getNullManageNo();

			if (mainArr.isEmpty()) {
				System.out.println("업데이트 대상이 없습니다.");
				if (pidFile.exists()) {
					pidFile.delete();
				}
				return;
			}
			LocalDate toDay = LocalDate.now();
			String toDayStr = toDay.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			String yymmdd = toDay.format(DateTimeFormatter.ofPattern("yyMMdd"));

			ManageNo manageNo = service.getManageNoDate(toDayStr);

			int nextNo = manageNo.getNextNo();
			ManageNoDao dao = new ManageNoDao();
			TrackingDao tarckingDao = new TrackingDao();

			
			for (CargoMain cm : mainArr) {
				String manageNoStr = String.format("KH%sA%06d", yymmdd, nextNo);
				int res = dao.updateCargoMainManageNo(conn,cm,manageNoStr);
				dao.updateCargoGoodsManageNo(conn,cm,manageNoStr);
				// int res = service.updateCargoMainManageNo(cm, manageNoStr);
				int trRes = tarckingDao.insertTrackingData(conn, manageNoStr, cm.getWarehouseMoveid(), "S1");

				if (res > 0) {
					nextNo++; // 성공했을 때만 증가
				}else {
					connFlg = false;
					break;
				}
			}

			if(connFlg) {
				// 마지막 T_manageNo 테이블 nextNo 업데이트
				int manageNoUpdate = service.updateNextNo(toDayStr, nextNo);
				System.out.println("manage_no작업 끝");
				
				JDBCTemplate.commit(conn);
				
			}else {
				JDBCTemplate.rollback(conn);

			}
			
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(conn);
		    if (pidFile.exists()) {
		        pidFile.delete();
		    }
		}

	}

}
