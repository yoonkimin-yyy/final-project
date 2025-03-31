package kr.kro.bbanggil.member.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import kr.kro.bbanggil.member.mapper.MypageMapper;
import kr.kro.bbanggil.member.util.MypagePagination;
import kr.kro.bbanggil.mypage.model.dto.request.PasswordRequestDto;
import kr.kro.bbanggil.mypage.model.dto.response.MypageListResponseDto;
import kr.kro.bbanggil.mypage.model.dto.response.MypagePageInfoDto;
import kr.kro.bbanggil.mypage.model.dto.response.MypageUserResponseDto;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MypageServiceImpl implements MypageService {
	private static final Logger logger = LogManager.getLogger(MypageServiceImpl.class);
    private final MypageMapper mypageMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
	public Map<String, Object> getMyList(MypagePagination mypagePagination, 
										   int currentPage,
										   int postCount,
										   int pageLimit,
										   int boardLimit,
										   int userNo) {
		// 페이징 처리
		MypagePageInfoDto pi = mypagePagination.getMyList(postCount, currentPage, pageLimit, boardLimit);
		List<MypageListResponseDto> getBuyHistory = mypageMapper.getBuyHistory(userNo);
		
		
		
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
		System.out.println("현재 비밀번호 : " +currentPassword);
		
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
		System.out.println("인코딩된 비밀번" + newPassword);
		int result = mypageMapper.updatePassword(userNo, newPassword);
		
		return result;
	}
    
    @Override
    public int writeReview(MypageListResponseDto mypageListDto, int userNo) {
    	System.out.println("서비스 리뷰내용" + mypageListDto.getReviewDto().getReviewNo());
    	System.out.println("서비스 주문번호" + mypageListDto.getReviewDto().getOrderNo());
    	System.out.println("서비스 평점" + mypageListDto.getReviewDto().getReviewRating());
    	int result = mypageMapper.writeReview(mypageListDto,userNo);
    	
    	return result;
    }
    
    @Override
    public int deleteReview(MypageListResponseDto mypageListDto) {
    	
    	System.out.println(mypageListDto.getReviewDto().getOrderNo());
    	int result = mypageMapper.deleteReview(mypageListDto);
    	return result;
    }
   
}
