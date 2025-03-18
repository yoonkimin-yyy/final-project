package kr.kro.bbanggil.pickup.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.kro.bbanggil.pickup.response.dto.PickupBakeryInfoResponseDTO;
import kr.kro.bbanggil.pickup.response.dto.PickupMenuResponseDTO;

@Mapper
public interface PickupMapper {

	// 세션에 있는 userNo로 bakeryNo 찾기
	public List<Integer> getBakeryNoByUserNo(int userNo);
	
    // 모든 주문 조회
    List<PickupBakeryInfoResponseDTO> findAllOrders(int bakeryNo);
    
    // 주문 상태를 변경하는 코드
    // 상태와 거절 사유를 업데이트
    boolean updateStatusAndRejectionReason(@Param("orderNo") int orderNo, 
                                            @Param("status") String status,
                                            @Param("rejectionReason") String rejectionReason);

    // 상태만 업데이트
    boolean updateStatus(@Param("orderNo") int orderNo, @Param("status") String status);

    
	
	
	

}