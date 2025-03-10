package kr.kro.bbanggil.bakery.util;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class LocationSelectUtil {
	
	public void selectLocation(String location) {
		if(location.matches("경상남도")) {
			location="경남";
		} else if(location.matches("경상북도")) {
			location="경북";
		} else if(location.matches("전라남도")) {
			location="전남";
		}else if(location.matches("전라북도")) {
			location="전북";
		}else if(location.matches("충청남도")) {
			location="충남";
		} else if(location.matches("충청북도")) {
			location="충북";
		}
	}
}
