package kr.kro.bbanggil.mypage.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import kr.kro.bbanggil.mypage.mapper.MypageMapper;
import kr.kro.bbanggil.mypage.model.dto.response.MypagePageInfoDto;
import kr.kro.bbanggil.mypage.util.MypagePagination;

@Service
public class MypageServiceImpl implements MypageService {
	
    private final MypageMapper mypageMapper;

    @Override
	public Map<String, Object> getMyList(MypagePagination mypagePagination, 
										   int currentPage,
										   int postCount,
										   int pageLimit) {
		// 페이징 처리
		MypagePageInfoDto pi = mypagePagination.getnoticesList(postCount, currentPage, pageLimit, boardLimit);
				
		Map<String, Object> result = new HashMap<>();
		result.put("pi", pi);
		
		return result;
	}
}
