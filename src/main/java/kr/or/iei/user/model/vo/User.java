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
	private String userLevel;



public User(String userId, String compCd, String userPw, String userName, String deptName, String telNo) {
    this.userId = userId;
    this.compCd = compCd;
    this.userPw = userPw;
    this.userName = userName;
    this.deptName = deptName;
    this.telNo = telNo;
    this.regDate = new Date(System.currentTimeMillis()); // 기본값으로 현재 시간 설정
    this.updDate = this.regDate; // 등록일과 갱신일을 동일하게 처리
}

}