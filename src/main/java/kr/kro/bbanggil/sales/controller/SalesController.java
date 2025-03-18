package kr.kro.bbanggil.sales.controller;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import kr.kro.bbanggil.pickup.response.dto.PickupBakeryInfoResponseDTO;
import kr.kro.bbanggil.sales.service.SalesServiceImpl;

@Controller
@RequestMapping("/sales")
public class SalesController {

    @Autowired
    private SalesServiceImpl salesService;

    @GetMapping("/annual")
    public String getSales(@RequestParam(name = "year", required = false) Integer year, Model model, HttpSession session) {
        
        if (year == null) {
            year = LocalDate.now().getYear();  // 현재 연도
        }
        
        // 세션에서 userNo 가져오기 (로그인 구현 후 세션에서 가져오게 변경)
        //session.setAttribute("bakeryNo", bakeryNo);
        int bakeryNo = 14; // 하드코딩 대신 bakeryNo 가져오기

        // 매출 조회
        List<PickupBakeryInfoResponseDTO> monthlySales = salesService.getMonthlySales(year, bakeryNo);
        int totalSales = salesService.getAnnualTotalSales(year, bakeryNo);
        
        
        
        Map<String, Integer> monthlySalesMap = new HashMap<>();
        for (PickupBakeryInfoResponseDTO sales : monthlySales) {
            String month = sales.getSalesDTO().getMonth();  // String 타입의 키 사용
            int totalSale = sales.getSalesDTO().getTotalSales();  // int 값

            monthlySalesMap.put(month, totalSale);  // String을 키로 사용
            
        }
        
        
        
        // 숫자 형식화
        DecimalFormat formatter = new DecimalFormat("#,###");
        String formattedTotalSales = formatter.format(totalSales);
        
        // 사용 가능한 연도 목록 조회 (DB에서 가져오기)
        List<Integer> availableYears = salesService.getAvailableYears(bakeryNo);

        // 모델에 데이터 추가
        model.addAttribute("availableYears", availableYears);
        model.addAttribute("monthlySalesMap", monthlySalesMap);
        model.addAttribute("monthlySales", monthlySales);
        model.addAttribute("totalSales", formattedTotalSales);
        model.addAttribute("year", year);  // 선택한 년도

        return "owner/sales-by-year";
    }
}
