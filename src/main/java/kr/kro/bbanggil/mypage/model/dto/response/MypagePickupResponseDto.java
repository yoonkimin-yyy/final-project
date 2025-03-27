package kr.kro.bbanggil.mypage.model.dto.response;

import lombok.Getter;

@Getter
public class MypagePickupResponseDto {
	
	/**
	 * 픽업 테이블 
	 * 예약 상태 (승인, 거절, 완료)
	 */
	
	private String pickupStatus;
	
}
