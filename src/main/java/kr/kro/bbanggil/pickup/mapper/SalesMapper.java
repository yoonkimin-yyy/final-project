package kr.kro.bbanggil.pickup.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.kro.bbanggil.pickup.response.dto.PickupBakeryInfoResponseDTO;

@Mapper
public interface SalesMapper {
	
	
	
	List<PickupBakeryInfoResponseDTO> getMonthlySales(@Param("year") int year,@Param("bakeryNo") int bakeryNo);
    int getAnnualTotalSales(@Param("year") int year,@Param("bakeryNo") int bakeryNo);
    List<Integer> getAvailableYears(@Param("bakeryNo") int bakeryNo);
}
