package kr.kro.bbanggil.owner.pickup.service;
import java.util.List;

import kr.kro.bbanggil.owner.pickup.response.dto.PickupBakeryInfoResponseDTO;

public interface SalesService {
    List<PickupBakeryInfoResponseDTO> getMonthlySales(int year, int bakeryNo); // 월별 매출
    int getAnnualTotalSales(int year, int bakeryNo); // 연간 총 매출
    List<Integer> getAvailableYears(int bakeryNo); // 사용 가능한 년도 리스트
}