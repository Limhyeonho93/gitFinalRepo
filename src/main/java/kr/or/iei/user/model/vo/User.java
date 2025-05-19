package kr.or.iei.user.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	private String userId; // 유저 ID
	private String compCd; // 회사 코드
	private String userPw; // 유저 PW
	private String userName; // 유저명
	private String deptName; // 부서
	private String telNo; // 연락처
	
	
	private Date regDate; // 등록일
	private Date updDate; // 갱신일
    private String grade; // 회사 등급 2에 고정
    
}
