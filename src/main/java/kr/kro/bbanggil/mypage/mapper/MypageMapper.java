package kr.kro.bbanggil.mypage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.kro.bbanggil.mypage.dto.response.OwnerMypageResponseDTO;

@Mapper
public interface MypageMapper {

	List<OwnerMypageResponseDTO> getOwnerInfo(int userNo);

}
