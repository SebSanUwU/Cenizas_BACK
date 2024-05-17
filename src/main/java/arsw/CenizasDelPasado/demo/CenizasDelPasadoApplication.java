package arsw.cenizasdelpasado.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import arsw.cenizasdelpasado.demo.persistence.LevelRepository;
import arsw.cenizasdelpasado.demo.persistence.RoomRepository;
import arsw.cenizasdelpasado.demo.persistence.UserRepository;

@SpringBootApplication
@EnableMongoRepositories
public class CenizasDelPasadoApplication {


    public CenizasDelPasadoApplication(RoomRepository roomRepository, UserRepository userRepository, LevelRepository levelRepository){

    }

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
}
