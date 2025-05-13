package kr.or.iei.cargo.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CargoMain {
	private String compCd;
	private String warehouseMoveid;
	private String trackingNo;
	private String manageNo;
	private String receiverName;
	private String receiverAdd;
	private String receiverZip;
	private String receiverTel;
	private String sellerName;
	private String sellerAdd;
	private String sellerTel;
	private int gw;
	private String gwt;
	private int no;
	private String deliveryStop;
	private String UserId;
	private String regDate;
	private String updDate;

}
