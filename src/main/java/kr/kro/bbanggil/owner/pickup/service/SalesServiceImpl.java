package kr.kro.bbanggil.owner.pickup.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.kro.bbanggil.owner.pickup.mapper.SalesMapper;
import kr.kro.bbanggil.owner.pickup.response.dto.PickupBakeryInfoResponseDTO;

@Service
public class SalesServiceImpl implements SalesService {

    @Autowired
    private SalesMapper salesMapper;

    @Override
    public List<PickupBakeryInfoResponseDTO> getMonthlySales(int year, int bakeryNo) {
        return salesMapper.getMonthlySales(year, bakeryNo);  // 해당 연도의 월별 매출
    }

    @Override
    public int getAnnualTotalSales(int year, int bakeryNo) {
        return salesMapper.getAnnualTotalSales(year, bakeryNo);  // 연간 총 매출
    }

    @Override
    public List<Integer> getAvailableYears(int bakeryNo) {
    	System.out.println("==");
    	System.out.println(bakeryNo);
    	System.out.println("==");
        return salesMapper.getAvailableYears(bakeryNo);  // 사용 가능한 년도 목록
    }
}