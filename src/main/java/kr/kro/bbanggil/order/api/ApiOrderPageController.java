package kr.kro.bbanggil.order.api;

import java.io.IOException;
import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
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

import kr.kro.bbanggil.order.dto.request.OrderRequestDto;
import kr.kro.bbanggil.order.dto.request.PaymentRequestDto;
import kr.kro.bbanggil.order.exception.OrderException;
import kr.kro.bbanggil.order.service.OrderService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
public class ApiOrderPageController {
	
	private static final Logger logger = LogManager.getLogger(ApiOrderPageController.class);
	private final OrderService orderService;
	private final IamportClient iamportClient;
	
	/**
	 * 가격검증
	 */
	@PostMapping("/accountCheck")
	@ResponseBody
	public Boolean accountCheck(@RequestParam("totalCount") int totalCount, 
								 OrderRequestDto orderRequestDto) {
		
		return orderService.accountCheck(totalCount, orderRequestDto);
	}
	
	/**
	 * 사전검증 
	 */
	@PostMapping("/prepare")
	@ResponseBody 
	public String paymentCheck(@RequestParam("merchantUid") String merchantUid,
							   @RequestParam("amount") BigDecimal amount) {
		
		try {
			iamportClient.postPrepare(new PrepareData(merchantUid, amount));
			return merchantUid;
		} catch(Exception e) {
			logger.info(e.getMessage());
			throw new OrderException("결제금액이 일치하지 않습니다.", "/order/page", HttpStatus.BAD_REQUEST);
		}
	}
	
	/**
	 * 사후검증
	 */
	@PostMapping("/validation/{imp_uid}")
	@ResponseBody
	public IamportResponse<Payment> validateIamport(@PathVariable("imp_uid") String imp_uid,
													@RequestBody PaymentRequestDto paymentRequestDto,
													OrderRequestDto orderRequestDto) 
	throws IamportResponseException, IOException {

		return orderService.validateIamport(imp_uid, paymentRequestDto, orderRequestDto);
	}
	
	/**
	 * DB저장
	 */
	@PostMapping("/success")
	public ResponseEntity<String> saveOrder(@RequestBody PaymentRequestDto paymentRequestDtoDto) {
		
		if(orderService.saveOrder(paymentRequestDtoDto)) {
			return ResponseEntity.ok("주문정보가 성공적으로 저장되었습니다.");
		}
		
		return ResponseEntity.badRequest().body("주문 저장에 실패했습니다");
	}
	
	/**
	 * 결제취소
	 */
	@PostMapping("/cancel/{imp_uid}")
	public IamportResponse<Payment> cancelPayment(@PathVariable String imp_uid) 
			throws IamportResponseException, IOException {
		return orderService.cancelPayment(imp_uid);
	}
	
}
