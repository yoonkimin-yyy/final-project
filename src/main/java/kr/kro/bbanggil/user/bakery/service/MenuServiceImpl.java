package kr.kro.bbanggil.user.bakery.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.kro.bbanggil.common.util.AwsS3Util;
import kr.kro.bbanggil.global.exception.BbanggilException;
import kr.kro.bbanggil.user.bakery.dto.request.MenuRequestDTO;
import kr.kro.bbanggil.user.bakery.dto.response.CategoryResponseDTO;
import kr.kro.bbanggil.user.bakery.dto.response.FileResponseDTO;
import kr.kro.bbanggil.user.bakery.dto.response.MenuResponseDto;
import kr.kro.bbanggil.user.bakery.dto.response.MenuUpdateResponseDTO;
import kr.kro.bbanggil.user.bakery.mapper.MenuMapper;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService{

	private final MenuMapper menuMapper;
	private final AwsS3Util s3Upload;
	private static final Logger logger = LogManager.getLogger(MenuServiceImpl.class);
	
	@Override
	public List<CategoryResponseDTO> getCategory() {
		return menuMapper.getCategory();
	}
	
	@Override
	@Transactional
	public void menuInsert(MenuRequestDTO menuDTO, int bakeryNo, MultipartFile file) {
		
		try {
			s3Upload.saveFile(file, menuDTO.getFileDTO());
			menuMapper.menuInsert(menuDTO, bakeryNo);
			menuMapper.menuFileUpload(menuDTO);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void menuDelete(int menuNo) {
		String changeName = menuMapper.getMenuImgInfo(menuNo);
		if(changeName!=null) {
			menuMapper.deleteMenuImg(changeName);
		}
		menuMapper.menuDelete(menuNo);
		logger.warn("{}번 메뉴 삭제됨", menuNo);
	}
	@Override
	public MenuUpdateResponseDTO getMenuDetail(int menuNo) {
		MenuUpdateResponseDTO menuDTO = menuMapper.getMenuUpdate(menuNo);
		menuDTO.getMenuCategory().addAll(menuMapper.getCategory());
		return menuDTO;
	}
	@Override
	public void updateMenu(MenuRequestDTO menuDTO, MultipartFile file) {
		try {
			FileResponseDTO fileDTO = menuMapper.getMenuImg(menuDTO.getMenuNo());
			if(file!=null&&!file.isEmpty()) {
				if(fileDTO!=null) {
					s3Upload.deleteImage(fileDTO.getChangeName());
					menuMapper.deleteMenuImg(fileDTO.getChangeName());
				}
					s3Upload.saveFile(file,menuDTO.getFileDTO());
					menuMapper.menuFileUpload(menuDTO);
			}
			menuMapper.menuUpdate(menuDTO);
		
		} catch (IOException e) {
			throw new BbanggilException("메뉴 수정 실패","common/error",HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	@Override
	public Map<String,Object> getMenuList(int bakeryNo) {
		List<MenuResponseDto> menuList = menuMapper.getMenuList(bakeryNo);
		String bakery = menuMapper.getBakeryName(bakeryNo);
		Map<String,Object> result = new HashMap<>();
		result.put("bakery", bakery);
		result.put("list", menuList);
			return result;
	}
	
}
