package it.aulab.progetto_blog;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProgettoBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProgettoBlogApplication.class, args);
	}

	@Bean
	// handler per istanziare l'oggetto modelMapper che mi fa il mapping
	public ModelMapper instanceModelMapper() {
		ModelMapper mapper = new ModelMapper();
		// ... adrò ad inserisci delle configurazioni di model mapper, per gestire il
		// mapping
		return mapper;
	}

}
