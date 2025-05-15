package kr.or.iei.invoice.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCost {
	private String disGrade;
	private int weight;
	private String wareCd;
	private int price;


}
