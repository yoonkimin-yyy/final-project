package kr.kro.bbanggil.admin.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import kr.kro.bbanggil.admin.dto.request.InquiryReplyRequestDto;
import kr.kro.bbanggil.admin.dto.request.InquiryRequestDto;
import kr.kro.bbanggil.admin.dto.request.ReportRequestDTO;
import kr.kro.bbanggil.admin.dto.response.AdminResponseDto;
import kr.kro.bbanggil.admin.dto.response.InquiryResponseDto;
import kr.kro.bbanggil.admin.dto.response.MenuResponseDto;
import kr.kro.bbanggil.admin.dto.response.MonthlyOrderResponseDTO;
import kr.kro.bbanggil.admin.dto.response.NewlyResponseDTO;
import kr.kro.bbanggil.admin.dto.response.ReportResponseDTO;
import kr.kro.bbanggil.admin.dto.response.myBakeryResponseDTO;
import kr.kro.bbanggil.common.dto.PageInfoDTO;
import kr.kro.bbanggil.owner.order.dto.response.OrderResponseDto;
import kr.kro.bbanggil.user.bakery.dto.InquiryEmailInfoDto;

@Mapper
public interface AdminMapper {

	List<AdminResponseDto> subList();

	List<AdminResponseDto> bakeryList();

	List<AdminResponseDto> userList();
	
	AdminResponseDto userDetailList(int userNo);
	
	void insertInquiry(InquiryRequestDto inquiryRequestDto);
	
	public String getUserType(int userNo);

	List<InquiryResponseDto> selectInquiryList();

	void insertInquiryReply(InquiryReplyRequestDto inquiryReplyDto);

	void updateInquiryStatusToAnswered(@Param("inquiryNo")int inquiryNo);


	@Select("SELECT SUM(user_count) FROM user_count")
	int getTodayUser();

	@Select("SELECT count(*) FROM order_info")
	int getTotalOrder();

	@Select("SELECT COUNT(*) FROM user_info WHERE created_date >= ADD_MONTHS(SYSDATE,-1)")
	int getNewUser();

	List<NewlyResponseDTO> getNewlyOrder();

	List<MonthlyOrderResponseDTO> getMonthlyOrderCount();

	List<InquiryResponseDto> getInquiries();

	List<MenuResponseDto> categoryList();

	void addCategory(@Param("newCategory") String newCategory);

	void deleteCategory(@Param("category") String category);

	InquiryEmailInfoDto getInquiryEmailInfo(@Param("inquiryNo")int inquiryNo);

	InquiryResponseDto selectInquiryByNo(@Param("inquiryNo")int inquiryNo);

	List<AdminResponseDto> reportList();

	int getReportReplyCount(int reportNo);

	void insertReport(@Param("reportDTO")ReportRequestDTO reportDTO, 
					  @Param("userId")String userId);

	AdminResponseDto reportDetail(int reportNo);
	
	AdminResponseDto bakeryDetailList(@Param("bakeryNo") int bakeryNo, 
			  						  @Param("userNo") int userNo);

	AdminResponseDto acceptList(@Param("bakeryNo") int bakeryNo, 
		    					@Param("userNo") int userNo);
	
	List<MenuResponseDto> menuList(int bakeryNo);
	
	void update(@Param("action") String action,
				@Param("bakeryNo") int listNum,
				@Param("rejectReason") String rejectReason);
	
	int selectOrderCount(@Param("keyword")String keyword);

	List<OrderResponseDto> selectPagedOrders(@Param("pi")PageInfoDTO pi,@Param("keyword") String keyword);

	myBakeryResponseDTO bakeryInfo(int bakeryNo);

	List<ReportResponseDTO> getReport();

	List<String> answer(int reportNo);
  
	void deleteBakery(int bakeryNo);

	int warningCount(int criminalNo);

	int searchCriminal(ReportRequestDTO reportDTO);
	
}
