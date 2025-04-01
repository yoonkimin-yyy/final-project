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
		String time = reportDTO.getReportResult();
		reportDTO.setReportNo(reportNo);
		if (time != "경고") {
			adminMapper.insertReport(reportDTO, userId);
		}
	}
}
