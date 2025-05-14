package kr.or.iei.invoice.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class DailyInsertInvoice {

	private String compCd;
	private String OutDate;
	private String disGrade;
	private int totalWeight;
}
