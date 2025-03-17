package kr.kro.bbanggil.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PageInfoDTO {
	// 전체 게시글 수 
		private int listCount;
		
		// 현재 페이지
		private int currentPage = 1;
		
		// 보여질 페이지 (버튼) 수 
		private int pageLimit;
		
		// 보여질 게시글 수 
		private int boardLimit;
		
		// 전체 페이지
		private int maxPage;
		
		// 시젝 페이지
		private int startPage;
		
		// 끝 페이지
		private int endPage;
		
		// view에서 꺼낼 게시글 번호
		private int row;
		
		private int offset;
		
		private int limit;
}
