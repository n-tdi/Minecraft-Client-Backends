package rip.athena.AthenaSleeper;

import com.google.crypto.tink.aead.AeadConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.GeneralSecurityException;

@SpringBootApplication
public class AthenaSleeperApplication {

	public static void main(String[] args) throws GeneralSecurityException {
		SpringApplication.run(AthenaSleeperApplication.class, args);
		AeadConfig.register();
	}

}
