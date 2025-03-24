package kr.kro.bbanggil.admin.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


import org.springframework.stereotype.Service;

<<<<<<< HEAD
import kr.kro.bbanggil.admin.dto.request.InquiryReplyRequestDto;
import kr.kro.bbanggil.admin.dto.request.InquiryRequestDto;
=======
import jakarta.validation.constraints.Email;
import kr.kro.bbanggil.admin.dto.request.AdminEmailRequestDto;
>>>>>>> 44d6a0e0c72f2b4a2a2fa346f94a5bc2ebe65166
import kr.kro.bbanggil.admin.dto.response.AdminResponseDto;
import kr.kro.bbanggil.admin.dto.response.InquiryResponseDto;
import kr.kro.bbanggil.admin.mapper.AdminMapper;
import kr.kro.bbanggil.email.service.EmailServiceImpl;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {

	private final EmailServiceImpl emailServiceImpl;
	private final AdminMapper adminMapper;

	@Override
	public List<AdminResponseDto> subList() {
		return adminMapper.subList();
	}

<<<<<<< HEAD
=======
	@Override
	public List<AdminResponseDto> bakeryList() {
		return adminMapper.bakeryList();
	}

	@Override
	public List<AdminResponseDto> userList() {
		return adminMapper.userId();
	}

>>>>>>> 44d6a0e0c72f2b4a2a2fa346f94a5bc2ebe65166
	@Override
	public AdminResponseDto acceptList(int listNum) {
		return adminMapper.acceptList(listNum);
	}

	@Override
	public void update(String action, int listNum, String rejectReason) {
		adminMapper.update(action, listNum, rejectReason);
	}

	@Override
<<<<<<< HEAD
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
	
=======
	public void sendEmail(AdminEmailRequestDto adminRequestDto) {

		String[] addresses = adminRequestDto.getAddress().split("\\s*,\\s*"); // 정규식
		String title = adminRequestDto.getTitle();
		String content = adminRequestDto.getContent();
		
		for(int i=0; i<addresses.length; i++) {
			emailServiceImpl.sendEmail(addresses[i], title, content);
		}
	}

>>>>>>> 44d6a0e0c72f2b4a2a2fa346f94a5bc2ebe65166
}
}
