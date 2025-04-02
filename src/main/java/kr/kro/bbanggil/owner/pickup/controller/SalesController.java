package kr.kro.bbanggil.owner.pickup.controller;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import kr.kro.bbanggil.global.exception.BbanggilException;
import kr.kro.bbanggil.owner.pickup.response.dto.PickupBakeryInfoResponseDTO;
import kr.kro.bbanggil.owner.pickup.service.SalesServiceImpl;

@Controller
@RequestMapping("/sales")
public class SalesController {

    @Autowired
    private SalesServiceImpl salesService;

    @GetMapping("/annual")
    public String getSales(@RequestParam(name = "year", required = false) Integer year,
                           Model model, 
                           HttpSession session) {
        
    	int bakeryNo = (int) session.getAttribute("bakeryNo");
        if (bakeryNo == 0) {
        	throw new BbanggilException("잘못된 접근입니다.","common/error",HttpStatus.BAD_REQUEST);
        }
        
        if (year == null) {
             year = LocalDate.now().getYear(); 
        }
        // 매출 조회
        List<PickupBakeryInfoResponseDTO> monthlySales = salesService.getMonthlySales(year, bakeryNo);
        int totalSales = salesService.getAnnualTotalSales(year, bakeryNo);

        Map<String, Integer> monthlySalesMap = new HashMap<>();
        for (PickupBakeryInfoResponseDTO sales : monthlySales) {
            String month = sales.getSalesDTO().getMonth();  
            int totalSale = sales.getSalesDTO().getTotalSales();  
            monthlySalesMap.put(month, totalSale);  
        }
        // 숫자 형식화
        DecimalFormat formatter = new DecimalFormat("#,###");
        String formattedTotalSales = formatter.format(totalSales);

        // 사용 가능한 연도 목록 조회 (DB에서 가져오기)
        List<Integer> availableYears = salesService.getAvailableYears(bakeryNo);

        model.addAttribute("availableYears", availableYears);
        model.addAttribute("monthlySalesMap", monthlySalesMap);
        model.addAttribute("monthlySales", monthlySales);
        model.addAttribute("totalSales", formattedTotalSales);
        model.addAttribute("year", year); 

        return "/owner/sales-by-year";
    }


}
