package kr.kro.bbanggil.user.member.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MypageUserResponseDto {
	
	/**
	 * 유저 테이블
	 * 유저 번호
	 * 이름
	 * 아이디
	 * 비밀번호
	 * 이메일
	 * 생년월일
	 * 폰 번호
	 * 타입 (user,owner,admin)
	 * 계정 활성화 디폴트 값 N
	 * 계정 생성일 sysdate;
	 */
	private int userNo;
	private String userName;
	private String userId;
	private String userPassword;
	private String userEmail;
	private String birthDate;
	private String phoneNum;
	private String userType;
	private String userBan;
	private String createdDate;
	
}
