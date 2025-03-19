package kr.kro.bbanggil.pickup.controller;

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
import kr.kro.bbanggil.pickup.service.SalesServiceImpl;

@Controller
@RequestMapping("/sales")
public class SalesController {

    @Autowired
    private SalesServiceImpl salesService;

    @GetMapping("/annual")
    public String getSales(@RequestParam(name = "year", required = false) Integer year,
                           Model model, 
                           HttpSession session) {
        
        // bakeryNo가 0인 경우 처리
    	int bakeryNo = (int) session.getAttribute("bakeryNo");
        if (bakeryNo == 0) {
            // bakeryNo 값이 잘못 전달된 경우에 대한 처리 로직
            System.out.println("bakeryNo가 전달되지 않았거나 값이 잘못되었습니다.");
            // 예외 처리 또는 기본값을 설정할 수 있습니다.
        }
        
        if (year == null) {
            year = LocalDate.now().getYear();  // 현재 연도
        }
        
        System.out.println("----");
        System.out.println(year);
        System.out.println(bakeryNo);  // bakeryNo 값 출력
        System.out.println("----");

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

        return "/owner/sales-by-year";
    }


}
