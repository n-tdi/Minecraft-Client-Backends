package rip.athena.athenasleeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SuppressWarnings("all")
@SpringBootApplication
public class AthenaSleeperApplication extends SpringBootServletInitializer {

	private static final Class applicationClass = AthenaSleeperApplication.class;

	public static void main(String[] args) {
		SpringApplication.run(AthenaSleeperApplication.class, args);

	}

}