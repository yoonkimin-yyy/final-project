package kr.kro.bbanggil.owner.pickup.service;

import java.util.List;

import kr.kro.bbanggil.owner.pickup.response.dto.PickupBakeryInfoResponseDTO;
import kr.kro.bbanggil.owner.pickup.response.dto.PickupMenuResponseDTO;

public interface PickupService {
	
	
	
    // 모든 주문을 조회하는 메서드
    List<PickupBakeryInfoResponseDTO> getAllOrders(int userNo, int bakeryNo);
    
    // 주문 상태를 변경하는 메서드
    public boolean updateOrderStatus(int orderNo, String status, String rejectionReason);
    
    List<PickupMenuResponseDTO> getMenusByOrderNo(int orderNo);

}
