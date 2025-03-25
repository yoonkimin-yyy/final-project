package kr.kro.bbanggil.member.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.kro.bbanggil.member.util.MypagePagination;
import kr.kro.bbanggil.mypage.mapper.MypageMapper;
import kr.kro.bbanggil.mypage.model.dto.response.MypagePageInfoDto;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MypageServiceImpl implements MypageService {
	private static final Logger logger = LogManager.getLogger(MypageServiceImpl.class);
    private final MypageMapper mypageMapper;

    @Override
	public Map<String, Object> getMyList(MypagePagination mypagePagination, 
										   int currentPage,
										   int postCount,
										   int pageLimit,
										   int boardLimit) {
		// 페이징 처리
		MypagePageInfoDto pi = mypagePagination.getMyList(postCount, currentPage, pageLimit, boardLimit);
				
		Map<String, Object> result = new HashMap<>();
		
		result.put("pi", pi);
		
		return result;
	}
}
