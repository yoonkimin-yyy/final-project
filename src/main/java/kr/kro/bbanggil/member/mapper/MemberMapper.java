package kr.kro.bbanggil.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.kro.bbanggil.member.model.dto.request.MemberRequestCheckBoxDto;
import kr.kro.bbanggil.member.model.dto.request.MemberRequestSigninDto;
import kr.kro.bbanggil.member.model.dto.request.MemberRequestSignupDto;
import kr.kro.bbanggil.member.model.dto.response.OwnerMypageResponseDTO;
import kr.kro.bbanggil.member.model.dto.response.SigninResponseDto;

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

	List<OwnerMypageResponseDTO> ownerMypage(int userNum);
}
