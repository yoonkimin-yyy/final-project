package kr.kro.bbanggil.admin.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import kr.kro.bbanggil.admin.dto.request.InquiryReplyRequestDto;
import kr.kro.bbanggil.admin.dto.request.InquiryRequestDto;
import kr.kro.bbanggil.admin.dto.response.AdminResponseDto;
import kr.kro.bbanggil.admin.dto.response.InquiryResponseDto;
import kr.kro.bbanggil.admin.mapper.AdminMapper;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

	private final AdminMapper adminMapper;

	@Override
	public List<AdminResponseDto> subList() {
		return adminMapper.subList();
	}

	@Override
	public AdminResponseDto acceptList(int listNum) {
		return adminMapper.acceptList(listNum);
	}

	@Override
	public void update(String action, int listNum, String rejectReason) {
		adminMapper.update(action, listNum, rejectReason);
	}

	@Override
	public void saveInquiry(InquiryRequestDto inquiryRequestDto) {

		String userType = adminMapper.getUserType(inquiryRequestDto.getUserNo());

		if (!userType.equals("user") && !userType.equals("owner")) {
			throw new IllegalArgumentException("일반 사용자 또는 사업자만 문의할 수 있습니다.");
		}

		adminMapper.insertInquiry(inquiryRequestDto);

	}
	@Override
	public List<InquiryResponseDto> getInquiryList(){
		return adminMapper.selectInquiryList();
	}
	
	@Override
	public void saveAnswer(InquiryReplyRequestDto inquiryReplyDto) {
	    // 현재 시간 세팅 (replyDate가 null이면)
	    if (inquiryReplyDto.getReplyDate() == null || inquiryReplyDto.getReplyDate().isEmpty()) {
	    	inquiryReplyDto.setReplyDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
	    }

	    // 답변 INSERT
	    adminMapper.insertInquiryReply(inquiryReplyDto);

	    // 문의 상태 "답변 완료"로 변경
	    adminMapper.updateInquiryStatusToAnswered(inquiryReplyDto.getInquiryNo());
	
}
}
