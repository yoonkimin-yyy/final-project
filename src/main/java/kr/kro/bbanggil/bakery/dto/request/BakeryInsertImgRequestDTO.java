package kr.kro.bbanggil.bakery.dto.request;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BakeryInsertImgRequestDTO {
	List<MultipartFile> main;
	List<MultipartFile> inside;
	List<MultipartFile> outside;
	List<MultipartFile> parking;
	
	public boolean checkFile(List<MultipartFile> obj) {
		return obj==null&&!obj.isEmpty();
	}
}
