package recommendation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class RecommendationController {

	@Value("${spring.application.name}")
	String appName;

	@GetMapping("/")
	public String homePage(Model model) {
		model.addAttribute("appName", appName);
		model.addAttribute("moviesList", MovieDatabase.getMovieNames());
		model.addAttribute("genresList", MovieDatabase.getGenres());
		model.addAttribute("actorsList", MovieDatabase.getActors());
		model.addAttribute("directorsList", MovieDatabase.getDirectors());
		return "home";
	}

	@PostMapping("/recommendation")
	public String recommendMovies(@RequestParam Map<String, String> userPreferencesInput, Model model) {

		System.out.println("User preferences input:");
		System.out.println(userPreferencesInput);

		Map<String, List<String>> userPreferences = new HashMap<>();

		for (Map.Entry<String, String> entry : userPreferencesInput.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			List<String> valuesList = Arrays.stream(value.split(","))
					.map(String::trim) // Remove extra spaces
					.filter(s -> !s.isEmpty()) // Exclude empty values
					.toList();
			userPreferences.put(key, new ArrayList<>(valuesList));
		}

		recommendation.UserPreferencesUpdated updatedPreferences = new recommendation.UserPreferencesUpdated(userPreferences);
		updatedPreferences.updatePreferences();
		Map<String, List<String>> updatedUserPreferences = updatedPreferences.getUpdatedPreferences();

		recommendation.RecommendationAlgorithm recommendationAlgorithm = new recommendation.RecommendationAlgorithm(updatedUserPreferences);
		List<String> recommendedMovies = recommendationAlgorithm.recommendMovies();

		System.out.println("User preferences:");
		System.out.println(userPreferences);

		System.out.println("Updated user preferences:");
		System.out.println(updatedUserPreferences);

		System.out.println("Recommended movies:");
		recommendedMovies.forEach(System.out::println);

		model.addAttribute("recommendedMovies", recommendedMovies);

		return "recommendation";
	}

}
