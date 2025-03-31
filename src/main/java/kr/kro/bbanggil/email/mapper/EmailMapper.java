package kr.kro.bbanggil.email.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.kro.bbanggil.email.dto.request.SubscriptionRequsetDto;
import kr.kro.bbanggil.email.dto.response.SubscriptionResponseDto;

@Mapper
public interface EmailMapper {

	List<String> getAllSubscriberEmails();

	Map<String, String> getRandomBakery();

	int checkEmailExists(@Param("email") String toEmail);

	void reactivateSubscription(@Param("email") String toEmail);

	void insertSubscriber(@Param("email") String toEmail);

	List<SubscriptionResponseDto> selectAllSubscribers();
	
	 void insertSendLog(@Param("email") String email, @Param("status") String status);
	 
	 int countTotalSends();
	 
	 int countSuccessfulSends();
	 
	 /*
	  * 발송 이력 insert
	  */
	 void insertNewsletterHistory(
			    @Param("bakeryName") String bakeryName,
			    @Param("resourcesPath") String resourcesPath,
			    @Param("changeName") String changeName,
			    @Param("openTime") String openTime,
			    @Param("closeTime") String closeTime
			);
	 
	 /*
	  * 이미지 정보 조회
	  */
	 Map<String, String> getImageInfoByImgNo(@Param("imgNo") int imgNo);
	 
	 
	void unsubscribeEmail(@Param("email") String email); // 구독해지
	
	void insertNewsletterHistory(Map<String, String> bakeryInfo);

}
