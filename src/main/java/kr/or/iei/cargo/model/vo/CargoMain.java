package kr.or.iei.cargo.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CargoMain {
	private String compCd; // 회사코드
	private String warehouseMoveid;// 창고이동ID
	private String trackingNo; // 송장번호
	private String manageNo;// 사내 관리번호
	private String receiverName;// 수취인 이름
	private String receiverAdd;// 수취인 주소
	private String receiverZip;// 수취인 우편번호
	private String receiverTel; // 수취인 전화번호
	private String sellerName; // 판매자 이름
	private String sellerAdd; // 판매자 주소
	private String sellerTel;// 판매자 전화번호
	private float gw;// 총 중량
	private String gwt;// 총 중량 단위
	private int no;// 화물갯수
	private String deliveryStop;// 배송중지flg
	private String UserId;// 갱신자 
	private String regDate;// 등록일
	private String updDate; // 갱신일

}
