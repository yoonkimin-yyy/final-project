package kr.kro.bbanggil.member.service;

import java.util.List;
import java.util.Map;

import kr.kro.bbanggil.member.model.dto.response.OwnerMypageResponseDTO;
import kr.kro.bbanggil.member.util.MypagePagination;
import kr.kro.bbanggil.mypage.model.dto.response.MypageListResponseDto;
import kr.kro.bbanggil.mypage.model.dto.response.MypageUserResponseDto;

public interface MypageService {
	
	Map<String, Object> getMyList(MypagePagination mypagePagination, 
								  int currentPage, 
								  int postCount, 
								  int pageLimit,
								  int boardLimit,
								  int userNo);
	
	public int getTotalCount(int userNo);
	
	public MypageListResponseDto getMyInfo(int userNo);
	
	
	public int updateUser(MypageUserResponseDto userDto,int userNo);

	List<OwnerMypageResponseDTO> ownerMypage(int userNum);
	
}
	
