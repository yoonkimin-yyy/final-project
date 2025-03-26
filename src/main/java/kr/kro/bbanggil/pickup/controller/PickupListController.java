package kr.kro.bbanggil.pickup.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;

import jakarta.servlet.http.HttpSession;
import kr.kro.bbanggil.pickup.response.dto.PickupBakeryInfoResponseDTO;
import kr.kro.bbanggil.pickup.service.PickupServiceImpl;

@Controller
@RequestMapping("/pickup")
public class PickupListController {
	
    private final PickupServiceImpl pickupServiceImpl;

    public PickupListController(PickupServiceImpl pickupServiceImpl) {
        this.pickupServiceImpl = pickupServiceImpl;
    }
	
	
	
	
    // 주문 목록을 테이블로 반환하는 메서드
    @GetMapping("/list") 
    public String list(Model model,@ModelAttribute PickupBakeryInfoResponseDTO pickupDTO,
    					@SessionAttribute("userNum") int userNo,
    						HttpSession session,
    						@RequestParam("bakeryNo") int bakeryNo
    						) {	
    	
    	// 로그인 된 사용자의 빵집 번호 세션에 저장
    	session.setAttribute("bakeryNo", bakeryNo);
    	
        List<PickupBakeryInfoResponseDTO> orderList = pickupServiceImpl.getAllOrders(userNo, bakeryNo);
        
        
        System.out.println("===dfdsfas");
        System.out.println(orderList.size());
        for(PickupBakeryInfoResponseDTO i : orderList) {
        	System.out.println("-=-=-=-=");
        	System.out.println(i.getPayDTO().getOrderNo());
        	System.out.println(i.getStatusDTO().getPickupStatus());
        	System.out.println(i.getPayDTO().getPaymentDate());
        	System.out.println(i.getPayDTO().getAmount());
        	System.out.println(i.getUserDTO().getRecipientsPhonenum());
        	System.out.println("-=-=-=-=");
        }
        
       
        model.addAttribute("orders",orderList);
        model.addAttribute("bakeryNo",bakeryNo);
        
        
        return "owner/pickup-list";  
    }
    


    // 주문 상태를 변경하는 코드
    @PostMapping("/update-status")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateOrderStatus(@RequestBody PickupBakeryInfoResponseDTO statusUpdateDTO) {

        int orderNo = statusUpdateDTO.getPayDTO().getOrderNo();
        String status = statusUpdateDTO.getStatusDTO().getPickupStatus();
        String rejectionReason = statusUpdateDTO.getStatusDTO().getRejectionReason();

        Map<String, Object> response = new HashMap<>();
        try {
            // 상태 업데이트 서비스 호출
            boolean isUpdated = pickupServiceImpl.updateOrderStatus(orderNo, status, rejectionReason);
            

            return ResponseEntity.ok(response);
        } catch (Exception e) {
        	e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    
}











