package kr.kro.bbanggil.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import kr.kro.bbanggil.admin.dto.request.AdminEmailRequestDto;
import kr.kro.bbanggil.admin.dto.request.InquiryRequestDto;
import kr.kro.bbanggil.admin.dto.response.AdminResponseDto;
import kr.kro.bbanggil.admin.dto.response.InquiryResponseDto;
import kr.kro.bbanggil.admin.dto.response.MenuResponseDto;
import kr.kro.bbanggil.admin.dto.response.MonthlyOrderResponseDTO;
import kr.kro.bbanggil.admin.dto.response.NewlyResponseDTO;
import kr.kro.bbanggil.admin.dto.response.ReportResponseDTO;
import kr.kro.bbanggil.admin.mapper.AdminMapper;
import kr.kro.bbanggil.common.service.EmailServiceImpl;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdminMainServiceImpl implements AdminMainService {

	private final EmailServiceImpl emailServiceImpl;
	private final AdminMapper adminMapper;
	private final Logger logger = LogManager.getLogger(AdminMainServiceImpl.class);

	@Override
	public List<AdminResponseDto> subList() {
		return adminMapper.subList();
	}

	@Override
	public List<AdminResponseDto> reportList() {
		List<AdminResponseDto> result = adminMapper.reportList();
		for (AdminResponseDto item : result) {
			int answer = adminMapper.getReportReplyCount(item.getReportNo());
			item.setAnswer(answer > 0 ? "Y" : "N");
		}
		return result;
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
	public void saveInquiry(InquiryRequestDto inquiryRequestDto) {

		Integer userNo = inquiryRequestDto.getUserNo();

		logger.info("문의 등록 요청 - userNo: {}", userNo);

		if (userNo == null || userNo == 0) {
			logger.info("비회원 문의 등록 시도");
			adminMapper.insertInquiry(inquiryRequestDto);
			logger.info("비회원 문의 등록 완료");
			return;
		}

		String userType = adminMapper.getUserType(userNo);
		logger.info("조회된 userType: {}", userType);

		if (userType == null) {
			logger.warn("해당 userNo는 존재하지 않음 → 비회원 처리");
			inquiryRequestDto.setUserNo(null); // 또는 0으로 설정 가능
			adminMapper.insertInquiry(inquiryRequestDto);
			logger.info("비회원 문의 등록 완료 (userNo 없어서)");
			return;
		}

		if (!userType.equals("user") && !userType.equals("owner")) {
			logger.error("잘못된 사용자 타입으로 문의 시도 - userType: {}", userType);
			throw new IllegalArgumentException("일반 사용자 또는 사업자만 문의할 수 있습니다.");
		}

		logger.info("정상 사용자 문의 등록 진행");
		adminMapper.insertInquiry(inquiryRequestDto);
		logger.info("문의 등록 완료 - userNo: {}", userNo);

	}

	

	@Override
	public void sendEmail(AdminEmailRequestDto adminRequestDto) {

		String[] addresses = adminRequestDto.getAddress().split("\\s*,\\s*"); // 정규식
		String title = adminRequestDto.getTitle();
		String content = adminRequestDto.getContent();

		for (int i = 0; i < addresses.length; i++) {
			emailServiceImpl.sendEmail(addresses[i], title, content);
		}
	}

	@Override
	public Map<String, Object> trafficMonitoring() {
		int todayUser = adminMapper.getTodayUser();
		int totalOrder = adminMapper.getTotalOrder();
		int newUser = adminMapper.getNewUser();

		Map<String, Object> result = new HashMap<>();
		result.put("today", todayUser);
		result.put("order", totalOrder);
		result.put("user", newUser);

		return result;

	}

	@Override
	public List<MonthlyOrderResponseDTO> getMonthlyOrderCount() {
		return adminMapper.getMonthlyOrderCount();
	}

	public Map<String,Object> bottomContent(){
		//최근 주문

		List<NewlyResponseDTO> newlyOrder = adminMapper.getNewlyOrder();

		// 신고 내역

		// 1:1 문의
		List<InquiryResponseDto> inquiry = adminMapper.getInquiries();
		
		List<ReportResponseDTO> report = adminMapper.getReport();
		for(ReportResponseDTO item : report) {
			
			List<String> result = adminMapper.answer(item.getReportNo());
				item.setResult(result==null || result.isEmpty()?"답변대기":"답변완료");
		}

		Map<String, Object> result = new HashMap<>();
		result.put("new", newlyOrder);
		result.put("inquiry", inquiry);
		result.put("report",report);

		return result;

	}

	

	

	@Override
	public List<MenuResponseDto> categoryList() {
		return adminMapper.categoryList();
	}

	@Override
	public void addCategory(String newCategory) {
		adminMapper.addCategory(newCategory);
	}

	@Override
	public void deleteCategory(Map<String, List<String>> requestBody) {

		List<String> categories = requestBody.get("selectedCategories");

		for (int i = 0; i < categories.size(); i++) {
			String category = categories.get(i); // 각 카테고리 이름을 꺼냄
			adminMapper.deleteCategory(category);
		}
	}


}
