package recommendation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MovieRecommendationApp {
	public static void main(String[] args) {
		// Citirea filmelor din CSV
		new recommendation.MovieDatabase("../dataset");

		SpringApplication.run(MovieRecommendationApp.class, args);
	}
}
