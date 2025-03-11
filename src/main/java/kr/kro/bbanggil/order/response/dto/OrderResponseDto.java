package kr.kro.bbanggil.order.response.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {  
    
    private String menuName;     
    private int menuPrice;       
    private String bakeryName;   
    private int menuCount;       
    
}
