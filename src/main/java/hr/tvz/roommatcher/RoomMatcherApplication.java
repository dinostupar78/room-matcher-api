package hr.tvz.roommatcher;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class RoomMatcherApplication {

	public static void main(String[] args) {
		SpringApplication.run(RoomMatcherApplication.class, args);
	}

}
