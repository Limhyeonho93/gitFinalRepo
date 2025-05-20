package kr.or.iei.tracking.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackingJoin {

	private String compCd;
	private String warehouseMoveid;
	private String trackingNo;
	private String manageNo;
	private String compName;
	private String stateTitle;
	private String stateCd;
	private String stateChange;
}
