package kr.kro.bbanggil.owner.pickup.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import kr.kro.bbanggil.global.exception.BbanggilException;
import kr.kro.bbanggil.owner.pickup.mapper.PickupMapper;
import kr.kro.bbanggil.owner.pickup.response.dto.PickupBakeryInfoResponseDTO;
import kr.kro.bbanggil.owner.pickup.response.dto.PickupMenuResponseDTO;



@Service
public class PickupServiceImpl implements PickupService{

	@Autowired
    private PickupMapper pickupMapper;
	
	

    // 모든 주문을 조회하는 메서드
	@Override
	public List<PickupBakeryInfoResponseDTO> getAllOrders(int userNo, int bakeryNo) {
	    List<Integer> result = pickupMapper.getBakeryNoByUserNo(userNo);
	    if (result.isEmpty()) {
	        throw new BbanggilException("소유한 가게가 없습니다.","common/error",HttpStatus.BAD_REQUEST);
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
	    	throw new BbanggilException("본인의 가게가 아닙니다a.","common/error",HttpStatus.BAD_REQUEST);
	    
	}
	
	@Override
    public List<PickupMenuResponseDTO> getMenusByOrderNo(int orderNo) {
		List<PickupMenuResponseDTO> menuList = pickupMapper.selectMenusByOrderNo(orderNo);
        return menuList;
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
