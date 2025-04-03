package kr.kro.bbanggil.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import kr.kro.bbanggil.admin.dto.request.ReportRequestDTO;
import kr.kro.bbanggil.admin.dto.response.AdminResponseDto;
import kr.kro.bbanggil.admin.service.AdminReportService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/report")
@RequiredArgsConstructor
public class AdminReportController {
	private final AdminReportService adminReportService;
	
	@GetMapping("/form")
	public String reportForm(@RequestParam("reportNo")int reportNo,
							 Model model) {
		AdminResponseDto result = adminReportService.reportDetail(reportNo);
		model.addAttribute("result",result);
		model.addAttribute("reportNo",reportNo);
		return "admin/report-reply";
	}
	@PostMapping("")
	public String report(ReportRequestDTO reportDTO,
						 @SessionAttribute("userId")String userId,
						 @RequestParam("reportNo")int reportNo,
						 Model model) {
		adminReportService.insertReport(reportDTO,userId,reportNo);
			
		return "redirect:/admin/form";
	}
	
}
