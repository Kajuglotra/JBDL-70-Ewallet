package org.gfg.UserServiceApplication;

import org.gfg.UserServiceApplication.model.UserType;
import org.gfg.UserServiceApplication.model.Users;
import org.gfg.UserServiceApplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class UserServiceApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Value("${service.Authority}")
	private String serviceAuthority;

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Users users = Users.builder().contact("txn-service").
				password(passwordEncoder.encode("txn-service")).
				enabled(true).accountNonLocked(true).credentialsNonExpired(true).accountNonExpired(true).
				email("txnService@gmail.com").authorities(serviceAuthority).
				userType(UserType.SERVICE).
				build();
		userRepository.save(users);

	}
}
