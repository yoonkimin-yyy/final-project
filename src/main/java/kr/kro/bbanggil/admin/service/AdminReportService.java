package kr.kro.bbanggil.admin.service;

import kr.kro.bbanggil.admin.dto.request.ReportRequestDTO;
import kr.kro.bbanggil.admin.dto.response.AdminResponseDto;

public interface AdminReportService {

	AdminResponseDto reportDetail(int reportNo);

	void insertReport(ReportRequestDTO reportDTO, String userId, int reportNo);

}
