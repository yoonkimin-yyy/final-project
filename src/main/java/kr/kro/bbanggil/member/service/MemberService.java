package kr.kro.bbanggil.member.service;

import java.util.List;

import kr.kro.bbanggil.member.model.dto.request.MemberRequestCheckBoxDto;
import kr.kro.bbanggil.member.model.dto.request.MemberRequestSigninDto;
import kr.kro.bbanggil.member.model.dto.request.MemberRequestSignupDto;
import kr.kro.bbanggil.member.model.dto.response.OwnerMypageResponseDTO;
import kr.kro.bbanggil.member.model.dto.response.SigninResponseDto;

public interface MemberService {
	
	public String checkbox(MemberRequestCheckBoxDto checkBoxRequestDto);
	
	public String loginup(MemberRequestSignupDto signupRequestDto, MemberRequestCheckBoxDto checkBoxRequestDto);
	
	public String businessloginup(MemberRequestSignupDto signupRequestDto, MemberRequestCheckBoxDto checkBoxRequestDto);
	
	public int userIdCheck(String userId); // 아이디 중복 체크
	
	public int businessNoCheck(String businessNumber); // 사업자번호 중복 체크 

	public int userEmailCheck(String userEmail); // 이메일 중복 체크

	public MemberRequestSignupDto loginIn(MemberRequestSignupDto memberRequestSignupDto);

	
}
