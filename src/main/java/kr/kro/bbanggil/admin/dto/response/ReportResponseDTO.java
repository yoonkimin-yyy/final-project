package kr.kro.bbanggil.admin.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportResponseDTO {
	private String reportDetail;
	private String userId;
	private int reportNo;
	private String result;
}
