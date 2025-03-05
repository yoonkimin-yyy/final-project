package kr.kro.bbanggil.bakery.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kr.kro.bbanggil.bakery.dto.request.MenuRequestDTO;
import kr.kro.bbanggil.bakery.dto.response.CategoryResponseDTO;
import kr.kro.bbanggil.bakery.dto.response.MenuResponseDTO;
import kr.kro.bbanggil.bakery.mapper.BakeryMapper;
import kr.kro.bbanggil.bakery.util.FileUploadUtil;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BakeryServiceImpl implements BakeryService{
	private final BakeryMapper mapper;
	private final FileUploadUtil fileUpload;
	
	@Override
	public MenuResponseDTO menuInsert(MenuRequestDTO menuRequestDTO,MultipartFile file) {
		
		try {
			if(file==null &&!file.isEmpty()) {
					fileUpload.uploadFile(file, menuRequestDTO.getFileRequestDTO(), "menu");
				mapper.menuFileUpload(menuRequestDTO.getFileRequestDTO());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
//		mapper.menuInsert();
		MenuResponseDTO result = null;
		return result;
	}
	
	@Override
	public List<CategoryResponseDTO> getCategory() {
		return mapper.getCategory();
	}
	
}
