package kr.kro.bbanggil.admin.service;

import java.util.List;

import kr.kro.bbanggil.admin.dto.request.InquiryReplyRequestDto;
import kr.kro.bbanggil.admin.dto.response.InquiryResponseDto;

public interface AdminInquiryService {

	List<InquiryResponseDto> getInquiryList();

	void saveAnswer(InquiryReplyRequestDto inquiryReplyDto);

	InquiryResponseDto getInquiryByNo(int inquiryNo);

}
