package kr.or.iei.invoice.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Invoice {
	private String compCd;
	private String compName;
	private Date deliverDate;
	private int dayAmount;
	private int totalPayment;
	private int totalWeight;
}
