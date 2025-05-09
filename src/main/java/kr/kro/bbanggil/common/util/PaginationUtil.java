package kr.kro.bbanggil.common.util;

import org.springframework.stereotype.Component;

import kr.kro.bbanggil.common.dto.PageInfoDTO;

@Component
public class PaginationUtil {

	public static PageInfoDTO getPageInfo(int listCount, int currentPage, int pageLimit, int boardLimit) {

		int maxPage = (int) (Math.ceil((double) listCount / boardLimit));

		int startPage = (currentPage - 1) / pageLimit * pageLimit + 1;

		int endPage = startPage + pageLimit - 1;

		int row = listCount - (currentPage -1) * boardLimit;

		int offset = (currentPage - 1) * boardLimit + 1;
		int limit = offset + boardLimit - 1;

		if (endPage > maxPage) {
			endPage = maxPage;
		}
		return new PageInfoDTO(listCount, currentPage, pageLimit, boardLimit, maxPage, startPage, endPage, row,
				offset, limit);

	}

}
