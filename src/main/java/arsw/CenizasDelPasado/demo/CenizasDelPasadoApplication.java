package arsw.CenizasDelPasado.demo;

import arsw.CenizasDelPasado.demo.persistence.LevelRepository;
import arsw.CenizasDelPasado.demo.persistence.RoomRepository;
import arsw.CenizasDelPasado.demo.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableMongoRepositories
public class CenizasDelPasadoApplication implements CommandLineRunner {
	@Autowired
	RoomRepository roomRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	LevelRepository levelRepository;


	public static void main(String[] args) {
		SpringApplication.run(CenizasDelPasadoApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:4200")
						.allowedMethods("GET", "POST","PUT", "DELETE");
			}
		};
	}


	@Override
	public void run(String... args) throws Exception {
		/*
		roomRepository.deleteAll();
		userRepository.deleteAll();
		levelRepository.deleteAll();*/

	}
}
