package kr.kro.bbanggil.admin.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kr.kro.bbanggil.admin.service.AdminOrderService;
import kr.kro.bbanggil.common.util.PaginationUtil;
import kr.kro.bbanggil.owner.order.dto.response.OrderResponseDto;
import kr.kro.bbanggil.user.bakery.dto.response.PageResponseDto;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin/order")
@RequiredArgsConstructor
public class AdminOrderController {
	private final AdminOrderService adminOrderService;

	@GetMapping("/list")
	public String getOrderList(@RequestParam(value="currentPage",defaultValue="1")int currentPage,
			@RequestParam(value = "keyword", required = false) String keyword,Model model) {
		
		
		int listCount = adminOrderService.getOrderCount(keyword); // 전체 주문수
		int pageLimit = 5;
		int boardLimit = 10;
		
		PageResponseDto pi = PaginationUtil.getPageInfo(listCount, currentPage, pageLimit, boardLimit);
		
		List<OrderResponseDto> orderList = adminOrderService.getPagedOrders(pi,keyword);
		model.addAttribute("orderList", orderList);
		model.addAttribute("pi", pi);
		model.addAttribute("keyword", keyword);
		
		return "admin/admin-order-list";
	}
}
