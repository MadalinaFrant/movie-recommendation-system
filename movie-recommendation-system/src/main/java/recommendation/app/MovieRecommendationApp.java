package recommendation.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import recommendation.data.MovieDatabase;

@SpringBootApplication
@ComponentScan("recommendation.controller")
public class MovieRecommendationApp {
	public static void main(String[] args) {
		// Initialize the movie database with movies from the dataset
		new MovieDatabase("../dataset");

		// Start the Spring Boot application
		SpringApplication.run(MovieRecommendationApp.class, args);
	}
}
