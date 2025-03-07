package kr.kro.bbanggil.pickup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pickup")
public class PickupListController {
	
	@GetMapping("/list")
	public String list() {
		
		return "/owner/pickup-list.html";
	}
}
