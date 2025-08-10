package space.algoritmos.habit_tracker_back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@SpringBootApplication
public class HabitTrackerBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(HabitTrackerBackApplication.class, args);

		gerarSenhas();
	}

	private static void gerarSenhas() {
		// Usa o padrão de configuração que o Spring Boot 3+ espera
		Pbkdf2PasswordEncoder encoder = Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();

		System.out.println("admin@example.com -> " + encoder.encode("admin123"));
		System.out.println("user@example.com -> " + encoder.encode("user123"));
		System.out.println("manager@example.com -> " + encoder.encode("manager123"));
		System.out.println("guest@example.com -> " + encoder.encode("guest123"));
	}
}
