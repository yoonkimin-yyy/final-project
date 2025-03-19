package kr.kro.bbanggil.pickup.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.kro.bbanggil.pickup.mapper.PickupMapper;
import kr.kro.bbanggil.pickup.response.dto.PickupBakeryInfoResponseDTO;
import kr.kro.bbanggil.pickup.response.dto.PickupMenuResponseDTO;
import kr.kro.bbanggil.pickup.response.dto.PickupPaymentResponseDTO;
import kr.kro.bbanggil.pickup.response.dto.PickupPaymentResponseDTO;



@Service
public class PickupServiceImpl implements PickupService{

	@Autowired
    private PickupMapper pickupMapper;
	
	

    // 모든 주문을 조회하는 메서드
	@Override
	public List<PickupBakeryInfoResponseDTO> getAllOrders(int userNo, int bakeryNo) {
	    List<Integer> result = pickupMapper.getBakeryNoByUserNo(userNo);
	    if (result.isEmpty()) {
	        return null;
	    }
	    
	    int[] bakeryNosArray = result.stream().mapToInt(Integer::intValue).toArray();
	    
	    boolean containsBakeryNo = false;
	    for (int n : bakeryNosArray) {
	        if (n == bakeryNo) {
	            containsBakeryNo = true;
	            break;
	        }
	    }
	    
	    if (containsBakeryNo) {
	        // bakeryNosArray에 bakeryNo가 포함되어 있을 경우 실행
	        List<PickupBakeryInfoResponseDTO> orderList = pickupMapper.findAllOrders(bakeryNo);
	        
	        if (orderList == null || orderList.isEmpty()) {
	            return null;
	        }
	        return orderList;
	    }
	    return null;
	}
    
    // 상태 업데이트
    @Override
    public boolean updateOrderStatus(int orderNo, String status, String rejectionReason) {
    	
        // 주문 상태 업데이트
        if ("거절".equals(status) && rejectionReason != null && !rejectionReason.isEmpty()) {
            return pickupMapper.updateStatusAndRejectionReason(orderNo, status, rejectionReason);
        } else if (status.equals("완료") || status.equals("승인")) {
        	boolean result = pickupMapper.updateStatus(orderNo, status);
            return result;
        }
        return false;
    }
    	
}
