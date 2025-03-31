package kr.kro.bbanggil.user.member.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberRequestCheckBoxDto {
	
	private int userNo; // 유저 번호
	private String createdDate; // 동의한 날짜
	private String termsofuse; // 이용약관 (필수)
	private String information; // 개인정보 (필수)
	private String location = "N";// 위치기반 (선택)
	private String marketing = "N"; // 마케팅 (선택)
	private String email = "N"; // 마케팅 이메일 (선택)
	private String sms = "N"; // 마케팅 문자 (선택)

	
}
