package kr.kro.bbanggil.user.member.dto.response;

import lombok.Getter;

@Getter
public class MypageListResponseDto {
	
	/**
	 * 결제 테이블
	 * 총가격 'amount'
	 */
	MypagePaymentResponseDto paymentDto = new MypagePaymentResponseDto();
	
	/**
	 * 픽업 테이블 
	 * 예약 상태 (승인, 거절, 완료) 'pickupStatus'
	 */
	MypagePickupResponseDto pickupDto = new MypagePickupResponseDto();
	
	/**
	 * 빵집 정보 테이블
	 * 빵집 이름(상호명)
	 */
	MypageBakeryInfoResponseDto bakeryInfoDto = new MypageBakeryInfoResponseDto();
	
	MypageReviewResponseDto reviewDto = new MypageReviewResponseDto();
	
	MypageReviewTagResponseDto reviewTagDto = new MypageReviewTagResponseDto();
	
	MypageUserResponseDto userDto = new MypageUserResponseDto();
	
}
