package kr.kro.bbanggil.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import kr.kro.bbanggil.order.dto.OrderDto;
import kr.kro.bbanggil.order.dto.PaymentDto;
import kr.kro.bbanggil.order.mapper.OrderMapper;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
	
	private final OrderMapper orderMapper;
	private final IamportClient iamportClient;
	
	/**
	 * 장바구니 출력
	 */
	@Override
	public List<OrderDto> list(OrderDto orderDto) {

		List<OrderDto> result = orderMapper.list(orderDto);

		return result;
	}
	
	/**
	 * 가격검증
	 */
	@Override
	public boolean accountCheck(int totalCount, OrderDto orderDto) {
		
		int dbTotalCount = 0; // DB 총가격 저장  
		
		List<OrderDto> result = orderMapper.list(orderDto);
		
		for(OrderDto rs : result) {
			dbTotalCount += rs.getMenuPrice() * rs.getMenuCount();
		}
		
		if(totalCount == dbTotalCount) {
			return true;
		} 
		
		return false;
	}
	
	/**
	 * 사후검증 & 취소처리
	 */
	@Override
	public IamportResponse<Payment> validateIamport(String imp_uid, PaymentDto paymentDto, OrderDto orderDto) {
		int dbTotalPrice = 0; // DB 총가격 저장
		
		List<OrderDto> result = orderMapper.list(orderDto);
		
		for(int i=0; i<result.size(); i++) {
			dbTotalPrice += (result.get(i).getMenuPrice() * result.get(i).getMenuCount());
		}
		
		if(dbTotalPrice == paymentDto.getAccount()) {
			try {
				IamportResponse<Payment> payment = iamportClient.paymentByImpUid(imp_uid);
				System.out.println("결제 요청 응답. 결제 내역 - 주문 번호: {" + payment.getResponse() + "}");
				return payment;
			} catch(Exception e) {
				System.out.println(e.getMessage());
				return null;
			} 
		} else {
			try {
				CancelData cancelData = new CancelData(imp_uid, true);
				IamportResponse<Payment> payment = iamportClient.cancelPaymentByImpUid(cancelData);
	            System.out.println("결제 금액 오류. 결제 취소 처리 - 주문 번호 : {" + payment.getResponse() + "}");
	            return null;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return null;
			}
		}
	}
	
	/**
	 * DB저장
	 */
	@Override
	@Transactional
	public String saveOrder(PaymentDto paymentDto) {
		try {
			orderMapper.save(paymentDto);
			
			int cartNo = orderMapper.findcart(); 
			int payNo = orderMapper.findpay(paymentDto.getMerchantUid());
			
			orderMapper.orderInfo(cartNo, payNo);
			orderMapper.pickupCheck(payNo);
			
			return "주문 정보가 성공적으로 저장되었습니다.";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			cancelPayment(paymentDto.getImpUid());
			return "주문 정보 저장에 실패했습니다.";
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
        	System.out.println(e.getMessage());
            return null;
        }
    }
	
}
