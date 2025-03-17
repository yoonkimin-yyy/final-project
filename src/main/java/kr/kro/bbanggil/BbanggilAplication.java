package kr.kro.bbanggil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BbanggilAplication {

	
	public static void main(String[] args) {
		SpringApplication.run(BbanggilAplication.class, args);
	}
	
}
