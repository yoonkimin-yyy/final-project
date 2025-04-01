package kr.kro.bbanggil.user.bakery.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor


public class InquiryEmailInfoDto {
	private String title;
    private String content;
    private String reply;
    private String email;
}
