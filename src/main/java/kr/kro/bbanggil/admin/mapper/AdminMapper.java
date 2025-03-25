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

	List<AdminResponseDto> userList();
	
	AdminResponseDto bakeryDetailList(int listNum);
	
	AdminResponseDto userDetailList(int listNum);
	
	AdminResponseDto acceptList(int listNum);

	void update(@Param("action") String action,
				@Param("listNum") int listNum,
				@Param("rejectReason") String rejectReason);

	void insertInquiry(InquiryRequestDto inquiryRequestDto);
	
	public String getUserType(int userNo);

	List<InquiryResponseDto> selectInquiryList();

	void insertInquiryReply(InquiryReplyRequestDto inquiryReplyDto);

	void updateInquiryStatusToAnswered(@Param("inquiryNo")int inquiryNo);

	
}
