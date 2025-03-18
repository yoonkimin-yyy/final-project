package kr.kro.bbanggil.mypage.service;

import java.util.List;
import java.util.Map;

import kr.kro.bbanggil.mypage.dto.response.OwnerMypageResponseDTO;

public interface MypageService {

	Map<String, Object> getOwnerMypage(int userNo);

}
