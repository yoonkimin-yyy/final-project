package kr.kro.bbanggil.admin.service;

import org.springframework.stereotype.Service;

import kr.kro.bbanggil.admin.dto.request.ReportRequestDTO;
import kr.kro.bbanggil.admin.dto.response.AdminResponseDto;
import kr.kro.bbanggil.admin.mapper.AdminMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminReportServiceImpl implements AdminReportService{

	private final AdminMapper adminMapper;
	
	@Override
	public AdminResponseDto reportDetail(int reportNo) {
		return adminMapper.reportDetail(reportNo);
	}

	@Override
	public void insertReport(ReportRequestDTO reportDTO, String userId, int reportNo) {
		reportDTO.setReportNo(reportNo);
			adminMapper.insertReport(reportDTO, userId);
			int criminalNo = adminMapper.searchCriminal(reportDTO);
			int warningCount = adminMapper.warningCount(criminalNo);
			if(warningCount==3) {
				adminMapper.insertReport(reportDTO, userId);
				reportDTO.setReportResult("3일정지");
				adminMapper.insertReport(reportDTO, userId);
			} else if(warningCount==8) {
				adminMapper.insertReport(reportDTO, userId);
				reportDTO.setReportResult("7일정지");
				adminMapper.insertReport(reportDTO, userId);
			} else if(warningCount> 12) {
				reportDTO.setReportResult("영구정지");
				adminMapper.insertReport(reportDTO, userId);
			}
	}
}
