package kr.kro.bbanggil.mypage.mapper;

<<<<<<< HEAD
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.kro.bbanggil.mypage.dto.response.OwnerMypageResponseDTO;

@Mapper
public interface MypageMapper {

	List<OwnerMypageResponseDTO> getOwnerInfo(int userNo);

=======
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MypageMapper {
	
	
	
>>>>>>> 6a5c69b42ab239ac06b23ac9c17eba9130b6e0f0
}
