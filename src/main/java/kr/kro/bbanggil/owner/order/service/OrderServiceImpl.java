package kr.kro.bbanggil.owner.order.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import kr.kro.bbanggil.owner.order.dto.request.OrderRequestDto;
import kr.kro.bbanggil.owner.order.dto.request.PaymentRequestDto;
import kr.kro.bbanggil.owner.order.dto.response.OrderResponseDto;
import kr.kro.bbanggil.owner.order.dto.response.PickupCheckResponseDto;
import kr.kro.bbanggil.owner.order.mapper.OrderMapper;
import kr.kro.bbanggil.user.bakery.dto.response.BakeryResponseDto;
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
	public List<OrderResponseDto> list(int userNo) {

		return orderMapper.list(userNo);
	}

	/**
	 * 가격검증
	 */
	@Override
	public boolean accountCheck(int totalCount, int userNo) {
		
		return orderMapper.calculate(userNo) == totalCount ? true : false;
	}

	/**
	 * 사후검증 & 취소처리
	 */
	@Override
	public IamportResponse<Payment> validateIamport(String imp_uid, PaymentRequestDto paymentRequestDto, int userNo) {
		
		int dbTotalPrice = orderMapper.calculate(userNo);
		
		if (dbTotalPrice == paymentRequestDto.getAccount()) {
			try {
				IamportResponse<Payment> payment = iamportClient.paymentByImpUid(imp_uid);

				return payment;
			} catch (Exception e) {
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
	@Transactional(rollbackFor = { Exception.class })
	public boolean saveOrder(PaymentRequestDto paymentRequestDto, int userNo) {

		try {
			orderMapper.save(paymentRequestDto);

			int cartNo = orderMapper.findcart(userNo);
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

	/**
	 * payNo 가져오는 메서드
	 */
	public int getPayNo(int userNo) {
		return orderMapper.pickupPay(userNo);
	}

	/**
	 * 픽업 상태, 거절 사유 가져오는 메서드
	 * @param payNo 결제 번호
	 */
	public PickupCheckResponseDto pickupCheckStatus(int payNo) {
		return orderMapper.pickupStatus(payNo);
	}
	
	/**
	 * 주문 리스트 가져오는 메서드
	 * @param result 픽업 상태, 거절 사유
	 * @param payNo 결제 번호
	 */
	@Transactional(rollbackFor = {Exception.class})
	public List<OrderResponseDto> pickupList(PickupCheckResponseDto result, int payNo, int userNo) {
		try {
			String pickupStatus = result.getPickupStatus();

			switch (pickupStatus) {
			case "승인":
				List<OrderResponseDto> list = orderMapper.list(userNo);
				return list;
			case "거절":
				String imp = orderMapper.refund(payNo);
				cancelPayment(imp);
			}
			
		} catch (Exception e) {
			logger.info(e.getMessage());
		}

		return null;
	}

	/*
	 * 사용자가 해당 주문의 주인인지 확인
	 */
	@Override
	public boolean isUserOrder(Integer userNo, Integer orderNo) {
		
		
		 if (userNo == null || orderNo == null) {
		        logger.warn("isUserOrder - null 전달됨! userNo: {}, orderNo: {}", userNo, orderNo);
		        return false;
		    }
		
		
		return orderMapper.countByUserAndOrder(userNo, orderNo) > 0;
	}
	
	@Override
	public BakeryResponseDto findOrderNo(int userNum, double bakeryNo) {
		return orderMapper.findRecentOrder(userNum, bakeryNo);
		
		
	}
	
	
}
