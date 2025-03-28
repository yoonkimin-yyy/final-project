package kr.kro.bbanggil.admin.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ReportRequestDTO {
	
	private int reportNo;
	private String reportResult;
	private String unfreezeDetail;
}
