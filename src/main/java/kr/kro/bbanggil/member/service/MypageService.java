package kr.kro.bbanggil.member.service;

import java.util.Map;

import kr.kro.bbanggil.member.util.MypagePagination;

public interface MypageService {
	
	Map<String, Object> getMyList(MypagePagination mypagePagination, 
								  int currentPage, 
								  int postCount, 
								  int pageLimit,
								  int boardLimit);
	
	
}
