package web.app.engrivals.engrivals;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EngrivalsApplication {

	public static void main(String[] args) {
		SpringApplication.run(EngrivalsApplication.class, args);
	}

}
