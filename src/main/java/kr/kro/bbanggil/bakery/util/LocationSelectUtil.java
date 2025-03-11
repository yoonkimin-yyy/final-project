package kr.kro.bbanggil.bakery.util;

import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class LocationSelectUtil {
	private static final Map<String,String> LOCATION_MAP = Map.of(
			"경상남도","경남",
			"경상북도","경북",
			"전라남도","전남",
			"전라북도","전북",
			"충청남도","충남",
			"충청북도","충북"
			);
			
	
	public String selectLocation(String location) {
		return LOCATION_MAP.getOrDefault(location, location);
	}
}
