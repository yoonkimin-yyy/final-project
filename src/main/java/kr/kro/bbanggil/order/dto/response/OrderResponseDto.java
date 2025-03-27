package kr.kro.bbanggil.order.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {  
    
	private int payNo;
	private String bakeryName;
	private String paymentCode; // = merchant_uid
	private String userName;
	private String status;
	private String paymentDate; 
	private int amount;
    private String menuName;     
    private int menuPrice;       
    private int menuCount; 
    private String resourcesPath;
    private String changeName;
    
}
