package kr.kro.bbanggil.user.bakery.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import kr.kro.bbanggil.user.bakery.dto.request.MenuRequestDTO;
import kr.kro.bbanggil.user.bakery.dto.response.CategoryResponseDTO;
import kr.kro.bbanggil.user.bakery.dto.response.MenuUpdateResponseDTO;

public interface MenuService {

	Map<String, Object> getMenuList(int bakeryNo);

	void updateMenu(MenuRequestDTO menuDTO, MultipartFile file);

	MenuUpdateResponseDTO getMenuDetail(int menuNo);

	void menuDelete(int menuNo);

	void menuInsert(MenuRequestDTO menuDTO, int bakeryNo, MultipartFile file);

	List<CategoryResponseDTO> getCategory();

}
