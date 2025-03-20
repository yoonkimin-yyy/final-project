package kr.kro.bbanggil.mypage.model.dto.response;

public class MypageListResponseDto {
	
	/**
	 * 결제 테이블
	 * 총가격 'amount'
	 */
	MypagePaymentResponseDto paymentResponseDto = new MypagePaymentResponseDto();
	
	/**
	 * 픽업 테이블 
	 * 예약 상태 (승인, 거절, 완료) 'pickupStatus'
	 */
	MypagePickupResponseDto pickupResponseDto = new MypagePickupResponseDto();
	
	/**
	 * 빵집 정보 테이블
	 * 빵집 이름(상호명)
	 */
	MypageBakeryInfoResponseDto bakeryInfo = new MypageBakeryInfoResponseDto();
	
}
