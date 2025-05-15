package kr.or.iei.cargo.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CargoMainImport {

	// 엑셀 파일을 읽는 메서드
	public void readExcel(InputStream file) {

		// try-with-resources 문법: 자동으로 파일 및 리소스를 닫아줌
		try (Workbook wb = new XSSFWorkbook(file)) { // .xlsx 전용 처리 클래스
			// 첫 번째 시트 가져오기
			Sheet sheet = wb.getSheetAt(0);

			// DataFormatter 사용하여 셀 값 읽기 ==> 모두 문자열로 자동 변환됨
	        DataFormatter dataFormatter = new DataFormatter();
	        
			// 모든 행(Row)을 반복
			for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) { // i=1부터 시작 (0은 헤더)
	            Row row = sheet.getRow(i); // 데이터 행

				String trackingNo = "";
				String receiverName = "";
				String receiverAdd = "";
				String receiverZip = "";
				String receiverTel = "";
				String sellerName = "";
				String sellerAdd = "";
				String sellerTel = "";
				String gw = "";
				String gwt = "";
				String qty = "";
				String goodsName = "";
				String unitPrice = "";
				String no = "";

				// 각 행의 셀(Cell)을 반복
				for (Cell cell : row) {
					String cellValue = dataFormatter.formatCellValue(cell);
					// 열마다 따로 리스트로 추가
				    switch (cell.getColumnIndex()) {
				        case 1: // B열 (tracking_no)
				            trackingNo = cellValue;
				            break;
				        case 2: // C열 (receiver_name)
				            receiverName = cellValue;
				            break;
				        case 3: // D열 (receiver_address)
				            receiverAdd = cellValue;
				            break;
				        case 4: // E열 (receiver_zip)
				            receiverZip = cellValue;
				            break;
				        case 5: // F열 (receiver_tel)
				            receiverTel = cellValue;
				            break;
				        case 6: // G열 (seller_name)
				            sellerName = cellValue;
				            break;
				        case 7: // H열 (seller_address)
				            sellerAdd = cellValue;
				            break;
				        case 8: // I열 (seller_tel)
				            sellerTel = cellValue;
				            break;
				        case 9: // J열 (gw)
				            gw = cellValue; // 숫자도 문자열로 처리
				            break;
				        case 10: // K열 (gwt)
				            gwt = cellValue;
				            break;
				        case 11: // L열 (qty)
				            qty = cellValue; // 숫자도 문자열로 처리
				            break;
				        case 12: // M열 (goods_name)
				            goodsName = cellValue;
				            break;
				        case 13: // N열 (unit_price)
				            unitPrice = cellValue; // 숫자도 문자열로 처리
				            break;
				        case 14: // O열 (no)
				            no = cellValue; // 숫자도 문자열로 처리
				            break;
				    }
				}
				 // 출력 (한 행을 출력)
                System.out.println("Tracking Number: " + trackingNo + " | Receiver Name: " + receiverName);
                System.out.println("Receiver Address: " + receiverAdd + " | Receiver Zip: " + receiverZip);
                System.out.println("Receiver Tel: " + receiverTel);
                System.out.println("Seller Name: " + sellerName + " | Seller Address: " + sellerAdd);
                System.out.println("Seller Tel: " + sellerTel);
                System.out.println("GW: " + gw + " | GWT: " + gwt + " | Quantity: " + qty);
                System.out.println("Goods Name: " + goodsName + " | Unit Price: " + unitPrice);
                System.out.println("No: " + no);
                System.out.println("----------------------------------------------------");
			}

		} catch (IOException e) {
			// 파일이 없거나 읽을 수 없을 경우 오류 출력
			e.printStackTrace();
		}
	}
}
