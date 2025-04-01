package kr.kro.bbanggil.global.config;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import kr.kro.bbanggil.admin.dto.response.MenuResponseDto;
import kr.kro.bbanggil.admin.service.AdminMainService;
import lombok.RequiredArgsConstructor;

@ControllerAdvice
@RequiredArgsConstructor
public class CategoryListConfig {

	private final AdminMainService adminMainService;
	
	@ModelAttribute
	public void categoryList(Model model) {
		
		List<MenuResponseDto> categoryList = adminMainService.categoryList();
		
		model.addAttribute("categoryList", categoryList);
		
	}
	
}
