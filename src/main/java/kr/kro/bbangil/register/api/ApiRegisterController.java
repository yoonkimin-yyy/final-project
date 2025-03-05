package kr.kro.bbangil.register.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.kro.bbanggil.register.service.RegisterServiceImpl;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/register")
@AllArgsConstructor
public class ApiRegisterController {
	private final RegisterServiceImpl registerService;
	
	
}
