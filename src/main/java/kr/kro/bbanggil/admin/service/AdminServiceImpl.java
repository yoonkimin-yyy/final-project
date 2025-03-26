package kr.kro.bbanggil.admin.service;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import kr.kro.bbanggil.admin.dto.request.AdminEmailRequestDto;
import kr.kro.bbanggil.admin.dto.request.InquiryReplyRequestDto;
import kr.kro.bbanggil.admin.dto.request.InquiryRequestDto;
import kr.kro.bbanggil.admin.dto.response.AdminResponseDto;
import kr.kro.bbanggil.admin.dto.response.InquiryResponseDto;
import kr.kro.bbanggil.admin.dto.response.MenuResponseDto;
import kr.kro.bbanggil.admin.dto.response.MonthlyOrderResponseDTO;
import kr.kro.bbanggil.admin.dto.response.NewlyResponseDTO;
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

	@Override
	public List<AdminResponseDto> bakeryList() {
		return adminMapper.bakeryList();
	}

	@Override
	public List<AdminResponseDto> userList() {
		return adminMapper.userList();
	}

	@Override
	public AdminResponseDto bakeryDetailList(int bakeryNo, int userNo) {
		return adminMapper.bakeryDetailList(bakeryNo, userNo);
	}
	
	@Override
	public AdminResponseDto userDetailList(int userNo) {
		return adminMapper.userDetailList(userNo);
	}
	
	@Override
	public AdminResponseDto acceptList(int bakeryNo, int userNo) {
		return adminMapper.acceptList(bakeryNo, userNo);
	}
	
	@Override
	public List<MenuResponseDto> menuList(int bakeryNo) {
		return adminMapper.menuList(bakeryNo);
	}
	
	@Override
	public void update(String action, int bakeryNo, String rejectReason) {
		adminMapper.update(action, bakeryNo, rejectReason);
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
	@Override
	public void sendEmail(AdminEmailRequestDto adminRequestDto) {

		String[] addresses = adminRequestDto.getAddress().split("\\s*,\\s*"); // 정규식
		String title = adminRequestDto.getTitle();
		String content = adminRequestDto.getContent();
		
		for(int i=0; i<addresses.length; i++) {
			emailServiceImpl.sendEmail(addresses[i], title, content);
		}
	}
	
	@Override
	public Map<String,Object> trafficMonitoring() {
		int todayUser = adminMapper.getTodayUser();
		int totalOrder = adminMapper.getTotalOrder();
		int newUser = adminMapper.getNewUser();
		
		
		Map<String,Object> result = new HashMap<>();
		result.put("today",todayUser);
		result.put("order",totalOrder);
		result.put("user",newUser);
		
		return result;
		
	}
	
	@Override
	public List<MonthlyOrderResponseDTO> getMonthlyOrderCount(){
		return adminMapper.getMonthlyOrderCount();
	}

	@Override
	public Map<String,Object> bottomContent(){
		//최근 주문
		List<NewlyResponseDTO> newlyOrder = adminMapper.getNewlyOrder();
		
		//신고 내역
		
		
		//1:1 문의
		List<InquiryResponseDto> inquiry = adminMapper.getInquiries();
		
		
		Map<String,Object> result = new HashMap<>();
		result.put("new", newlyOrder);
		result.put("inquiry", inquiry);
		
		return result;
	}

}
