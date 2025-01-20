package recommendation;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class RecommendationController {

	@GetMapping("/")
	public String homePage(Model model) {
		model.addAttribute("moviesList", MovieDatabase.getMovieNames());
		model.addAttribute("genresList", MovieDatabase.getGenres());
		model.addAttribute("actorsList", MovieDatabase.getActors());
		model.addAttribute("directorsList", MovieDatabase.getDirectors());
		return "home";
	}

	@GetMapping("/classic_recommendation")
	public String classicRecommendation() {
		return "classic_recommendation";
	}

	@GetMapping("/movie_night")
	public String movieNight() {
		return "movie_night";
	}

	@PostMapping("/recommendation")
	public String recommendMovies(@RequestParam Map<String, String> userPreferencesInput, Model model) {

		Map<String, List<String>> userPreferences = new HashMap<>();

		for (Map.Entry<String, String> entry : userPreferencesInput.entrySet()) {
			String key = entry.getKey();
			String baseKey = key.replaceAll("[12]$", "");
			String value = entry.getValue();
			List<String> valuesList = Arrays.stream(value.split("\n"))
					.map(String::trim) // Remove extra spaces
					.filter(s -> !s.isEmpty()) // Exclude empty values
					.toList();
			if (key.equals(baseKey)) { // Classic Recommendation
				userPreferences.put(key, new ArrayList<>(valuesList));
			} else { // Movie Night
				List<String> baseValuesList = userPreferences.getOrDefault(baseKey, new ArrayList<>());
				baseValuesList.addAll(valuesList);
				userPreferences.put(baseKey, baseValuesList);
			}
		}

		System.out.println("User preferences:");
		System.out.println(userPreferences);

		UserPreferencesUpdater updatedPreferences = new UserPreferencesUpdater(userPreferences);
		updatedPreferences.updatePreferences();
		Map<String, List<String>> updatedUserPreferences = updatedPreferences.getUpdatedPreferences();

		recommendation.RecommendationAlgorithm recommendationAlgorithm = new recommendation.RecommendationAlgorithm(updatedUserPreferences);
		List<String> recommendedMovies = recommendationAlgorithm.recommendMovies();

		System.out.println("Updated user preferences:");
		System.out.println(updatedUserPreferences);

		model.addAttribute("recommendedMovies", recommendedMovies);

		return "recommendation";
	}

}
