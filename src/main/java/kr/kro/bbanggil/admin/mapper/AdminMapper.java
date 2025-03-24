package kr.kro.bbanggil.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.kro.bbanggil.admin.dto.request.InquiryReplyRequestDto;
import kr.kro.bbanggil.admin.dto.request.InquiryRequestDto;
import kr.kro.bbanggil.admin.dto.response.AdminResponseDto;
import kr.kro.bbanggil.admin.dto.response.InquiryResponseDto;

@Mapper
public interface AdminMapper {

	List<AdminResponseDto> subList();

	List<AdminResponseDto> bakeryList();

	List<AdminResponseDto> userId();
	
	AdminResponseDto acceptList(int listNum);

	void update(@Param("action") String action,
				@Param("listNum") int listNum,
				@Param("rejectReason") String rejectReason);
<<<<<<< HEAD

	void insertInquiry(InquiryRequestDto inquiryRequestDto);
	
	public String getUserType(int userNo);

	List<InquiryResponseDto> selectInquiryList();

	void insertInquiryReply(InquiryReplyRequestDto inquiryReplyDto);

	void updateInquiryStatusToAnswered(@Param("inquiryNo")int inquiryNo);

=======
	
>>>>>>> 44d6a0e0c72f2b4a2a2fa346f94a5bc2ebe65166
}
