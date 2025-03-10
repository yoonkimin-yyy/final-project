package kr.kro.bbanggil.bakery.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import kr.kro.bbanggil.bakery.dto.request.BakeryInsertImgRequestDTO;
import kr.kro.bbanggil.bakery.dto.request.BakeryInsertRequestDTO;
import kr.kro.bbanggil.bakery.dto.request.MenuRequestDTO;
import kr.kro.bbanggil.bakery.dto.response.CategoryResponseDTO;
import kr.kro.bbanggil.bakery.dto.response.MenuResponseDTO;

public interface BakeryService {

	MenuResponseDTO menuInsert(MenuRequestDTO menuRequestDTO, MultipartFile file);

	List<CategoryResponseDTO> getCategory();

	void bakeryInsert(BakeryInsertRequestDTO bakeryRequestDTO, BakeryInsertImgRequestDTO bakeryImgRequestDTO) throws Exception;

}
