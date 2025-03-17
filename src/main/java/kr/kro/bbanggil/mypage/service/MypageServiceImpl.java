package kr.kro.bbanggil.mypage.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import kr.kro.bbanggil.mypage.mapper.MypageMapper;

@Service
public class MypageServiceImpl implements MypageService {
    private static final Logger logger = LogManager.getLogger(MypageServiceImpl.class);
    private final MypageMapper mypageMapper;

    public MypageServiceImpl(MypageMapper mypageMapper) {
        this.mypageMapper = mypageMapper;
    }
    
    
}
