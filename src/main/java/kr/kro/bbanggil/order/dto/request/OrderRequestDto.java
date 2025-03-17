package kr.kro.bbanggil.order.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderRequestDto { 
    
	private String bakeryName; 
    private String menuName;  
    private int menuCount;    
    private int menuPrice;    
    
}
