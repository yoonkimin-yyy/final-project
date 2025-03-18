package kr.kro.bbanggil.mypage.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import kr.kro.bbanggil.mypage.dto.response.OwnerMypageResponseDTO;
import kr.kro.bbanggil.mypage.mapper.MypageMapper;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MypageServiceImpl implements MypageService{
	private MypageMapper mapper;

	@Override
	public Map<String,Object> getOwnerMypage(int userNo){
		List<OwnerMypageResponseDTO> ownerResult = mapper.getOwnerInfo(userNo);
		Map<String,Object> result = new HashMap<>();
		return result;
	}
}
