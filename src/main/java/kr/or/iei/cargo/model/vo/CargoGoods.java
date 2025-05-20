package kr.or.iei.cargo.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CargoGoods {
	private String compCd;			//회사코드
	private String warehouseMoveid;	//창고이동id
	private String trackingNo;		//송장번호
	private String manageNo;		//사내 관리번호
	private int seq;				//시퀀스
	private String goodsName;		//상품명
	private int unitPrice;			//상품단가
	private int qty;				//상품갯수
	private float unitWeight;		//중량
	private String deliveryStop;	//배송중지flg
	private String userId;			//갱신자
	private String regDate;			//등록일
	private String updDate;			//갱신일
	
}
