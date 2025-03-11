package kr.kro.bbanggil.order.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import kr.kro.bbanggil.order.mapper.OrderMapper;
import kr.kro.bbanggil.order.request.dto.OrderRequestDto;
import kr.kro.bbanggil.order.request.dto.PaymentRequestDto;
import kr.kro.bbanggil.order.response.dto.OrderResponseDto;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
	
	private static final Logger logger = LogManager.getLogger(OrderServiceImpl.class);
	private final OrderMapper orderMapper;
	private final IamportClient iamportClient;
	
	/**
	 * 장바구니 출력
	 */
	@Override
	public List<OrderResponseDto> list(OrderRequestDto orderRequestDto) {

		List<OrderResponseDto> result = orderMapper.list(orderRequestDto);

		return result;
	}
	
	/**
	 * 가격검증
	 */
	@Override
	public boolean accountCheck(int totalCount, OrderRequestDto orderRequestDto) {
		
		int dbTotalCount = orderMapper.calculate(orderRequestDto);
		
		if(totalCount == dbTotalCount) {
			return true;
		} 
		
		return false;
	}
	
	/**
	 * 사후검증 & 취소처리
	 */
	@Override
	public IamportResponse<Payment> validateIamport(String imp_uid, 
													PaymentRequestDto paymentRequestDto, 
													OrderRequestDto orderRequestDto) {
		
		int dbTotalPrice = orderMapper.calculate(orderRequestDto);
		
		if(dbTotalPrice == paymentRequestDto.getAccount()) {
			try {
				IamportResponse<Payment> payment = iamportClient.paymentByImpUid(imp_uid);
				
				return payment;
			} catch(Exception e) {
				logger.info(e.getMessage());
				cancelPayment(paymentRequestDto.getImpUid());
			} 
		} 

		return null;
	}
	
	/**
	 * DB저장
	 */
	@Override
	@Transactional(rollbackFor = {Exception.class})
	public boolean saveOrder(PaymentRequestDto paymentRequestDto) {
		try {
			orderMapper.save(paymentRequestDto);
			
			int cartNo = orderMapper.findcart(); 
			int payNo = orderMapper.findpay(paymentRequestDto.getMerchantUid());
			
			orderMapper.orderInfo(cartNo, payNo);
			orderMapper.pickupCheck(payNo);
			
			return true;
		} catch (Exception e) {
			logger.info(e.getMessage());
			cancelPayment(paymentRequestDto.getImpUid());
			return false;
		}
	}
	
	/**
	 * 결제취소
	 */
	public IamportResponse<Payment> cancelPayment(String imp_uid) {
        try {
            CancelData cancelData = new CancelData(imp_uid, true);
            IamportResponse<Payment> payment = iamportClient.cancelPaymentByImpUid(cancelData);
            return payment;
        } catch (Exception e) {
        	logger.info(e.getMessage());
            return null;
        }
    }
	
}
