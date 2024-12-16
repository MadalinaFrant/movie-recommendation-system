import java.util.List;
import java.util.Map;

public class Main {
	public static void main(String[] args) {
		// Citirea filmelor din CSV
		MovieDatabase movieDatabase = new MovieDatabase("movie-recommendation-system-main/dataset");
		Map<String, Movie> movies = movieDatabase.getMovies();

		// Citirea preferințelor utilizatorului
		UserInput userInput = new UserInput();
		Map<String, List<String>> userPreferences = userInput.getUserPreferences();

		// Afișează filmele și preferințele utilizatorului
		System.out.println("User preferences before update:");
		System.out.println(userPreferences);

		// Crează un obiect UserPreferencesUpdated
		UserPreferencesUpdated updatedPreferences = new UserPreferencesUpdated(userPreferences, movies);

		// Actualizează preferințele utilizatorului pe baza filmelor adăugate/dislike
		updatedPreferences.updatePreferences();

		// Afișează preferințele actualizate
		System.out.println("User preferences after update:");
		System.out.println(updatedPreferences.getUpdatedPreferences());

		// Obține preferințele actualizate
		Map<String, List<String>> updatedUserPreferences = updatedPreferences.getUpdatedPreferences();

		// Crează un obiect RecommendationAlgorithm cu preferințele actualizate
		RecommendationAlgorithm recommendationAlgorithm = new RecommendationAlgorithm(updatedUserPreferences, movies);

		// Obține recomandările
		List<String> recommendedMovies = recommendationAlgorithm.recommendMovies();

		// Afișează recomandările
		System.out.println("Recommended movies:");
		recommendedMovies.forEach(System.out::println);


	}
}
