package kr.or.iei.invoice.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CompInfo {

	private String compCd;
	private String compName;
	private String compDiv;
	private String compAddr;
	private String compZip;
	private String compTel;
	private int dealFlg;
}
