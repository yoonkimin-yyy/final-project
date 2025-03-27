package kr.kro.bbanggil.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.kro.bbanggil.member.model.dto.response.OwnerInfoResponseDTO;
import kr.kro.bbanggil.member.model.dto.response.OwnerMypageResponseDTO;
import kr.kro.bbanggil.mypage.model.dto.response.MypageListResponseDto;
import kr.kro.bbanggil.mypage.model.dto.response.MypageUserResponseDto;


@Mapper
public interface MypageMapper {
	
	public int getTotalCount(@Param("userNo")int userNo);
	
	//회원정보
	public List<MypageListResponseDto> getUserInfo(@Param("userNo")int userNo);
	
	//구매내역 정보
	public List<MypageListResponseDto> getBuyHistory(@Param("userNo")int userNo);
	
	// 회원정보수정
	public int updateUser(@Param("userDto")MypageUserResponseDto userDto,
						 @Param("userNo")int userNo);
	
	//현재 비밀번호 가져오기
	public String getPassword(@Param("userNo") int userNo);
	
	//비밀번호수정
	public int updatePassword(@Param("userNo")int userNo,
							  @Param("userPassword")String userPassword);

	public List<OwnerMypageResponseDTO> ownerMypage(int userNum);

	public OwnerInfoResponseDTO ownerInfo(int userNum);
}
