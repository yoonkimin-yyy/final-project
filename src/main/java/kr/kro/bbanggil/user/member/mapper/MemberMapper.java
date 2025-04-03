package kr.kro.bbanggil.user.member.mapper;

import org.apache.ibatis.annotations.Mapper;

import kr.kro.bbanggil.user.member.dto.request.MemberRequestCheckBoxDto;
import kr.kro.bbanggil.user.member.dto.request.MemberRequestSignupDto;

@Mapper
public interface MemberMapper {
	
	int loginup(MemberRequestSignupDto signupRequestDto);

	int businessloginup(MemberRequestSignupDto signupRequestDto);
	
	int insertAddress(MemberRequestSignupDto signupRequestDto);
	
	int userIdCheck(String userId);

	int insertCheckbox(MemberRequestCheckBoxDto checkBoxRequestDto);

	int insertbusiness(MemberRequestSignupDto signupRequestDto);

	int businessNoCheck(String businessNo);

	int userEmailCheck(String userEmail);

	MemberRequestSignupDto loginIn(MemberRequestSignupDto memberRequestSignupDto);

	String findUserIdByEmail(String userEmail);
	    
	MemberRequestSignupDto findUserByEmail(MemberRequestSignupDto memberRequestSignupDto);
	
	MemberRequestSignupDto findUserPassword(MemberRequestSignupDto memberRequestSignupDto);

	int updatePassword(MemberRequestSignupDto userNo);

	String isBanned(MemberRequestSignupDto memberRequestSignupDto);

	String alert(int userNo);
	

}
