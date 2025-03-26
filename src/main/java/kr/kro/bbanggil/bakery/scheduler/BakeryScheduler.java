package kr.kro.bbanggil.bakery.scheduler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kr.kro.bbanggil.bakery.mapper.BakeryMapper;
import kr.kro.bbanggil.bakery.service.BakeryServiceImpl;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BakeryScheduler {
	private final BakeryMapper bakeryMapper;
	private static final Logger logger = LogManager.getLogger(BakeryServiceImpl.class);
	
	@Scheduled(cron = "0 0 0 * * *")
	public void resetDailyVisitCount() {
		bakeryMapper.resetDailyVisitCount();
		logger.info("방문자수 초기화 완료");
	}
}
