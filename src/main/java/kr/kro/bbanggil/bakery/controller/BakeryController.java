package kr.kro.bbanggil.bakery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bakery")
public class BakeryController {

	@GetMapping("/list")
	public String list() {
		return "user/bakery-list";
	}
	
	@GetMapping("/insert/form")
	public String bakeryInsertForm() {
		return "owner/bakery-insert";
	}
	@GetMapping("/menu/insert/form")
	public String menuInsertForm() {
		return "owner/menu-insert";
	}
}
