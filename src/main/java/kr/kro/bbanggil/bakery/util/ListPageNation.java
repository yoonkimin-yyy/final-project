package kr.kro.bbanggil.bakery.util;

import org.springframework.stereotype.Component;

import kr.kro.bbanggil.common.dto.PageInfoDTO;

@Component
public class ListPageNation {
public PageInfoDTO getPageInfo(int listCount, int currentPage, int pageLimit, int boardLimit) {
		
		// <전체 페이지 수 >
		
		int maxPage = (int) (Math.ceil((double) listCount / boardLimit));
		
		// <현재 페이지가 속한 범위의 시작 페이지>
		
		int startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
		
		// <현재 페이지가 속한 범위의 끝 페이지>
		
		int endPage = startPage + pageLimit - 1;
		
		
		
		int row= listCount - (currentPage - 1)*boardLimit;
		
		int offset = (currentPage - 1) * boardLimit;
		int limit = offset + boardLimit;
		
		if(endPage>maxPage) {
			endPage = maxPage;
		}
		
		return new PageInfoDTO(listCount,currentPage,pageLimit,boardLimit,
									maxPage,startPage,endPage,row,offset,limit);
	}
}
