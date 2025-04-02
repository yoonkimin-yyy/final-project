package kr.kro.bbanggil.user.bakery.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import kr.kro.bbanggil.user.bakery.dto.request.MenuRequestDTO;
import kr.kro.bbanggil.user.bakery.dto.response.CategoryResponseDTO;
import kr.kro.bbanggil.user.bakery.dto.response.MenuUpdateResponseDTO;
import kr.kro.bbanggil.user.bakery.service.MenuService;
import kr.kro.bbanggil.user.member.dto.response.OwnerInfoResponseDTO;
import kr.kro.bbanggil.user.member.service.MypageService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/bakery/menu")
public class MenuController {
	private final MenuService menuService;
	private final MypageService mypageService;
	
	@GetMapping("/list/form")
	public String menuListForm(@RequestParam("no") int bakeryNo, @SessionAttribute("userNum") int userNum,
			Model model) {
		Map<String, Object> result = menuService.getMenuList(bakeryNo);
		OwnerInfoResponseDTO info = mypageService.ownerInfo(userNum);
		model.addAttribute("info", info);
		model.addAttribute("menu", result.get("list"));
		model.addAttribute("bakery", result.get("bakery"));
		model.addAttribute("no", bakeryNo);
		model.addAttribute("goMyPage",true);
		return "/owner/menu-list";
	}

	@GetMapping("/insert/form")
	public String menuInsertForm(@RequestParam("bakeryNo") int bakeryNo, Model model) {
		List<CategoryResponseDTO> category = menuService.getCategory();
		model.addAttribute("category", category);
		model.addAttribute("bakeryNo", bakeryNo);
		return "owner/menu-insert";
	}

	@PostMapping("/insert")
	public ResponseEntity<?> menuInsert(MenuRequestDTO menuDTO,
							 @RequestParam("bakeryNo") int bakeryNo,
							 @RequestParam("menuImage") MultipartFile file) {
		menuService.menuInsert(menuDTO,bakeryNo,file);
		return ResponseEntity.ok().body("{\"message\": \"메뉴 추가 성공\"}");
	}

	@PostMapping("/delete")
	public String menuDelete(@RequestParam("menuNo") int menuNo, @RequestParam("no") int no,
			RedirectAttributes redirectAttributes) {
		menuService.menuDelete(menuNo);
		redirectAttributes.addAttribute("no", no);
		return "redirect:/bakery/menu/list/form";
	}

	@GetMapping("/update/form")
	public String menuUpdateForm(@RequestParam("menuNo") int menuNo,
								 @RequestParam("bakeryNo") int bakeryNo,
								 Model model) {
		MenuUpdateResponseDTO menuDTO = menuService.getMenuDetail(menuNo);
		model.addAttribute("menu", menuDTO);
		model.addAttribute("menuNo", menuNo);
		model.addAttribute("bakeryNo",bakeryNo);
		return "/owner/menu-update";
	}

	@PostMapping("/update")
	public ResponseEntity<?> menuUpdate(MenuRequestDTO menuDTO,
							 @RequestParam("menuImage") MultipartFile file,
							 @RequestParam("bakeryNo")int bakeryNo) {
		menuService.updateMenu(menuDTO,file);
		return ResponseEntity.ok().body("{\"message\": \"메뉴 수정 성공\"}");
	}
}
