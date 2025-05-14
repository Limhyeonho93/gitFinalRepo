package kr.or.iei.user.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Company {

	private String comp_cd; // 회사 코드
	private String comp_name; // 회사명
	private String comp_addr; // 회사 주소
	private String comp_zip; // 우편번호
	private String comp_tel; // 회사 전화번호
	private char deal_flg; // 거래처 플래그
	private Date reg_date; // 등록일
	private Date upd_date; // 갱신일
	private char grade; // 등급
}
