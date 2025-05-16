package kr.or.iei.cargo.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import kr.or.iei.cargo.model.service.CargoService;
import kr.or.iei.cargo.model.service.ManageNoService;
import kr.or.iei.cargo.model.vo.CargoGoods;
import kr.or.iei.cargo.model.vo.CargoMain;

public class CargoMainImport {

	// 엑셀 파일을 읽는 메서드
	public String readExcel(InputStream file, String compCd , String userId) {
		String resultMessage="엑셀 파일이 업로드 되었습니다.";
		
		//시퀀스 값
		int seq = 1;
		
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
				String unitWeight="";

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
				        case 9: // J열 (unitWeight)  //중량 //여기도 문제. 중량
				        	unitWeight = cellValue; 
				            break;
				        case 10: // K열 (gwt)   //여기가 문제. 중량단위 
				            gwt = cellValue;
				            break;
				        case 11: // L열 (no)    //화물갯수
				        	no = cellValue; 
				            break;
				        case 12: // M열 (goods_name)
				            goodsName = cellValue;
				            break;
				        case 13: // N열 (unit_price)
				            unitPrice = cellValue; 
				            break;
				        case 14: // O열 (qty)		//상품갯수
				        	qty = cellValue; 
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
                
                ManageNoService mns = new ManageNoService();
                String wareCdId = mns.findZipWareCd(receiverZip); //창고이동ID생성
                
                
                CargoGoods goods=new CargoGoods();
                goods.setCompCd(compCd); // 회사코드
                goods.setWarehouseMoveid(wareCdId); // 창고이동ID 
                goods.setTrackingNo(trackingNo); // 송장번호
                goods.setSeq(seq); // 시퀀스
                goods.setGoodsName(goodsName); // 상품명
                goods.setUnitPrice(unitPrice != null && !unitPrice.isEmpty() ? Integer.parseInt(unitPrice) : 0); // 상품단가 
                goods.setQty(qty != null && !qty.isEmpty() ? Integer.parseInt(qty) : 0); // 상품갯수
                goods.setUnitWeight(unitWeight != null && !unitWeight.isEmpty() ? Float.parseFloat(unitWeight) : 0f); // 중량
                goods.setNo(no != null && !no.isEmpty() ? Integer.parseInt(no) : 0); // 화물갯수
                goods.setUserId(userId); // 갱신자
                
                CargoMain main=new CargoMain();
                main.setCompCd(compCd); // 회사코드
                main.setWarehouseMoveid(wareCdId); // 창고이동ID
                main.setTrackingNo(trackingNo); // 송장번호
                main.setReceiverName(receiverName); // 수취인 이름
                main.setReceiverAdd(receiverAdd); // 수취인 주소
                main.setReceiverZip(receiverZip); // 수취인 우편번호
                main.setReceiverTel(receiverTel); // 수취인 전화번호
                main.setSellerName(sellerName); // 판매자 이름
                main.setSellerAdd(sellerAdd); // 판매자 주소
                main.setSellerTel(sellerTel); // 판매자 전화번호
                main.setGw(gw != null && !gw.isEmpty() ? Integer.parseInt(gw) : 0); // 총 중량 
                main.setGwt(gwt); // 총 중량 단위
                main.setNo(no != null && !no.isEmpty() ? Integer.parseInt(no) : 0); // 화물갯수
                main.setDeliveryStop("N"); // 배송중지flg
                main.setUserId(userId); // 갱신자
                
                CargoService service=new CargoService();
                int result=service.insertBatchCargo(main,goods);
                seq++;
                
                if(result<=0) {
                	resultMessage="엑셀 파일 처리 중 오류가 발생하였습니다.";
                	break;
                }
                
			}
		} catch (IOException e) {
			// 파일이 없거나 읽을 수 없을 경우 오류 출력
			e.printStackTrace();
			resultMessage="엑셀 파일을 읽는 중 오류가 발생하였습니다.";
		}
		return resultMessage; 
	}
}
