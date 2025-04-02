package kr.kro.bbanggil.user.member.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.kro.bbanggil.common.dto.PageInfoDTO;
import kr.kro.bbanggil.common.util.PaginationUtil;
import kr.kro.bbanggil.user.member.dto.request.MemberRequestSignupDto;
import kr.kro.bbanggil.user.member.dto.request.PasswordRequestDto;
import kr.kro.bbanggil.user.member.dto.response.MypageListResponseDto;
import kr.kro.bbanggil.user.member.dto.response.MypageUserResponseDto;
import kr.kro.bbanggil.user.member.dto.response.OwnerInfoResponseDTO;
import kr.kro.bbanggil.user.member.dto.response.OwnerMypageResponseDTO;
import kr.kro.bbanggil.user.member.mapper.MypageMapper;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MypageServiceImpl implements MypageService {
	private static final Logger logger = LogManager.getLogger(MypageServiceImpl.class);
    private final MypageMapper mypageMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
	public Map<String, Object> getMyList(PaginationUtil mypagePagination, 
										   int currentPage,
										   int listCount,
										   int pageLimit,
										   int boardLimit,
										   int userNo) {
		// 페이징 처리
		PageInfoDTO pi = mypagePagination.getPageInfo(listCount, currentPage, pageLimit, boardLimit);
		List<MypageListResponseDto> getBuyHistory = mypageMapper.getBuyHistory(userNo,pi);
		
		
		

		
		Map<String, Object> result = new HashMap<>();
		
		result.put("pi", pi);
		result.put("getBuyHistory", getBuyHistory);
		
		
		return result;
	}
    
    @Override
    public int getTotalCount(int userNo) {
    	int result = mypageMapper.getTotalCount(userNo);
    	return result;
    }
    
    @Override
    public MypageListResponseDto getMyInfo(int userNo) {
    	List<MypageListResponseDto> userInfo =  mypageMapper.getUserInfo(userNo);
    	
    	for(MypageListResponseDto item : userInfo) {
			if(item.getUserDto().getPhoneNum().length() == 11) {
				item.getUserDto().setPhoneNum(item.getUserDto().getPhoneNum().replaceAll("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3"));
			}
		}

    	
		return userInfo.get(0);
    	
    	
    }
    
    @Override
	public int updateUser(MypageUserResponseDto userDto,int userNo) {

		int result = mypageMapper.updateUser(userDto,userNo);
		
		return result;
	}

    @Override
	public int updatePassword(int userNo, PasswordRequestDto passwordDto) {

		String currentPassword = mypageMapper.getPassword(userNo);
		
		if(currentPassword == null) {
			throw new RuntimeException("사용자를 찾을 수 없습니다.");
		}
		
		if(!passwordEncoder.matches(passwordDto.getCurrentPassword(), currentPassword)) {
			return 0;
		}
		
		if(!passwordDto.getNewPassword().equals(passwordDto.getConfirmPassword())) {
			throw new IllegalArgumentException("새 비밀번호가 일치하지 않습니다");
		}
		
		String newPassword = passwordEncoder.encode(passwordDto.getNewPassword());
		int result = mypageMapper.updatePassword(userNo, newPassword);
		
		return result;
	}
	
    @Override
    public int updateAddress(MemberRequestSignupDto signupRequestDto,int userNo) {
    	int result = mypageMapper.updateAddress(signupRequestDto, userNo);
    	return result;
    }
    @Override
	public List<OwnerMypageResponseDTO> ownerMypage(int userNum) {
    		
		return mypageMapper.ownerMypage(userNum);
	}
    
    @Override
	public OwnerInfoResponseDTO ownerInfo(int userNum) {
		OwnerInfoResponseDTO result = mypageMapper.ownerInfo(userNum);
		result.setBusinessNo(result.getBusinessNo().replaceAll("(\\d{3})(\\d{2})(\\d{5})", "$1-$2-$3"));
			if(result.getUserPhone().length()==11) {
				result.setUserPhone(result.getUserPhone().replaceAll("(\\d{3})(\\d{4})(\\d{4})", "$1-$2-$3"));
			} else {
				result.setUserPhone(result.getUserPhone().replaceAll("(\\d{4})(\\d{4})(\\d{4})", "$1-$2-$3"));
			}
		return result;
	}

    
    @Override
    public int writeReview(MypageListResponseDto mypageListDto, int userNo) {
    	int result = mypageMapper.writeReview(mypageListDto,userNo);
    	
    	return result;
    }
    
    @Override
    public int deleteReview(MypageListResponseDto mypageListDto) {
    	
    	int result = mypageMapper.deleteReview(mypageListDto);
    	return result;
    }
   
}
