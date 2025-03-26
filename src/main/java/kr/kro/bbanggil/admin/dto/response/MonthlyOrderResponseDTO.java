package kr.kro.bbanggil.admin.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MonthlyOrderResponseDTO {
	private String orderMonth;
	private int orderCount;
}
