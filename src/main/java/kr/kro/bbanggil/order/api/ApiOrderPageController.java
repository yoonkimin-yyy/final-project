package kr.kro.bbanggil.order.api;

import java.io.IOException;
import java.math.BigDecimal;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.PrepareData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import kr.kro.bbanggil.order.dto.OrderDto;
import kr.kro.bbanggil.order.dto.PaymentDto;
import kr.kro.bbanggil.order.service.OrderService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
public class ApiOrderPageController {
	
	private final OrderService orderService;
	private IamportClient iamportClient;
	
	/**
	 * 가격검증
	 */
	@PostMapping("/accountCheck")
	@ResponseBody
	public Boolean accountCheck(@RequestParam("totalCount") int totalCount, 
								 OrderDto orderDto) {
		
		boolean result = orderService.accountCheck(totalCount, orderDto);

		return result;
	}
	
	/**
	 * 사전검증 
	 */
	@PostMapping("/prepare")
	@ResponseBody 
	public String accountCheckJ(@RequestParam("merchantUid") String merchantUid,
								@RequestParam("amount") BigDecimal amount) {
		
		try {
			iamportClient.postPrepare(new PrepareData(merchantUid, amount));
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		return merchantUid;
	}
	
	/**
	 * 사후검증
	 */
	@PostMapping("/validation/{imp_uid}")
	@ResponseBody
	public IamportResponse<Payment> validateIamport(@PathVariable("imp_uid") String imp_uid,
													@RequestBody PaymentDto paymentDto,
													OrderDto orderDto) 
	throws IamportResponseException, IOException {

		return orderService.validateIamport(imp_uid, paymentDto, orderDto);
	}
	
	/**
	 * DB저장
	 */
	@PostMapping("/success")
	public ResponseEntity<String> processOrder(@RequestBody PaymentDto paymentDto) {
		
		return ResponseEntity.ok(orderService.saveOrder(paymentDto));
	}
	
	/**
	 * 결제취소
	 */
	@PostMapping("/cancel/{imp_uid}")
	public IamportResponse<Payment> cancelPayment(@PathVariable String imp_uid) throws IamportResponseException, IOException {
		return orderService.cancelPayment(imp_uid);
	}
	
}
