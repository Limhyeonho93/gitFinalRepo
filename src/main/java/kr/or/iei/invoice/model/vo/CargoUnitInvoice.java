package kr.or.iei.invoice.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CargoUnitInvoice {
	
	private String compName;
	private String compCd;
	private String warehouseMoveid;
	private String trackingNo;
	private String manageNo;
	private String inDate;
	private String outDate;
	private int no;
	private int gw;
	private String receiverName;
	private String receiverAdd;
	private String sellerName;
}
