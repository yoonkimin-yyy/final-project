package kr.kro.bbanggil.admin.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.kro.bbanggil.admin.dto.request.InquiryReplyRequestDto;
import kr.kro.bbanggil.admin.dto.response.InquiryResponseDto;
import kr.kro.bbanggil.admin.mapper.AdminMapper;
import kr.kro.bbanggil.common.service.EmailService;
import kr.kro.bbanggil.user.bakery.dto.InquiryEmailInfoDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminInquiryServiceImpl implements AdminInquiryService{
	private final AdminMapper adminMapper;
	private final EmailService emailService;
	private final Logger logger = LogManager.getLogger(AdminInquiryServiceImpl.class);
	
	
	@Override
	public List<InquiryResponseDto> getInquiryList() {
		return adminMapper.selectInquiryList();
	}

	@Override
	public void saveAnswer(InquiryReplyRequestDto inquiryReplyDto) {
		// 현재 시간 세팅 (replyDate가 null이면)
		if (inquiryReplyDto.getReplyDate() == null || inquiryReplyDto.getReplyDate().isEmpty()) {
			inquiryReplyDto
					.setReplyDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
		}

		// 답변 INSERT
		adminMapper.insertInquiryReply(inquiryReplyDto);

		// 문의 상태 "답변 완료"로 변경
		adminMapper.updateInquiryStatusToAnswered(inquiryReplyDto.getInquiryNo());

		sendAnswerEmail(inquiryReplyDto.getInquiryNo());
	}
	
	@Override
	public InquiryResponseDto getInquiryByNo(int inquiryNo) {
		return adminMapper.selectInquiryByNo(inquiryNo);
	}
	
	private void sendAnswerEmail(int inquiryNo) {
		InquiryEmailInfoDto info = adminMapper.getInquiryEmailInfo(inquiryNo);

		if (info == null || info.getEmail() == null) {
			logger.warn("이메일 전송 실패: 이메일 정보 없음 - inquiryNo: {}", inquiryNo);
			return;
		}

		String subject = "[빵모아] 문의에 대한 답변이 등록되었습니다";
		String body = """
				    안녕하세요, 빵모아입니다.<br><br>
				    문의 제목: %s<br>
				    문의 내용: %s<br><br>
				    <strong>[답변]</strong><br>
				    %s<br><br>
				    감사합니다.
				""".formatted(info.getTitle(), info.getContent(), info.getReply());

		emailService.sendEmail(info.getEmail(), subject, body);
	}
}
