package kr.kro.bbanggil.mypage.service;

import java.util.Map;

import kr.kro.bbanggil.mypage.util.MypagePagination;

public interface MypageService {
	
	Map<String, Object> getMyList(MypagePagination mypagePagination, 
								  int currentPage, 
								  int postCount, 
								  int pageLimit,
								  int boardLimit);
	
	
}
