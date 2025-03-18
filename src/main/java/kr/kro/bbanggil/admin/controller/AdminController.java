package kr.kro.bbanggil.admin.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.kro.bbanggil.admin.dto.response.AdminResponseDto;
import kr.kro.bbanggil.admin.service.AdminService;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/admin")
@AllArgsConstructor
public class AdminController {

	private final AdminService adminService;

	@GetMapping("/form")
	public String adminForm(Model model) {
		
		List<AdminResponseDto> sublist = adminService.subList();
		
		model.addAttribute("sublists", sublist);

		return "admin/admin-page";
	}

	@GetMapping("/bakery/detail")
	public String bakeryDetailForm() {
		return "admin/bakery-detail";
	}

	@GetMapping("/user/detail")
	public String userDetailForm() {
		return "admin/user-detail";
	}
	
	@GetMapping("/bakery/accept")
	public String bakeryAcceptForm(@RequestParam("listNum") int listNum,
								   Model model) {
		
		AdminResponseDto result = adminService.acceptList(listNum);
		
		for(int i=0; i<result.getBakeryImgPath().size(); i++) {
			System.out.println(result.getBakeryImgPath().get(i).getResourcesPath());
			System.out.println(result.getBakeryImgPath().get(i).getChangeName());
		}
		
		model.addAttribute("result", result);
		model.addAttribute("listNum", listNum);
		
		return "admin/bakery-accept";
	}
	
	@PostMapping("/bakery/update")
	@ResponseBody
	public String bakeryUpdateForm(@RequestParam("action") String action,
								   @RequestParam("listNum") int listNum,
								   @RequestParam("rejectReason") String rejectReason) {
		
		adminService.update(action, listNum, rejectReason);
		
		String message = ("승인".equals(action) ? "승인" : "거절") + " 완료되었습니다.";
		
	    return "<script>alert('" + message + "'); window.opener.location.reload(); window.close();</script>";
	}
	
}
