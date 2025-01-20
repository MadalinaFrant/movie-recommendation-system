package recommendation.handler;

import recommendation.data.Movie;

import java.util.List;
import java.util.Map;

// Chain of Responsibility: Handler for genre preferences
public class GenrePreferenceHandler implements PreferenceHandler {
	@Override
	public int calculateScore(Movie movie, Map<String, List<String>> userPreferences) {
		List<String> preferredGenres = userPreferences.get("genres");
		if (preferredGenres == null) return 0;

		return (int) movie.getGenresAsList().stream()
				.filter(genre -> preferredGenres.stream()
						.anyMatch(preferredGenre -> preferredGenre.equalsIgnoreCase(genre)))
				.count() * 2; // Lower priority for genres
	}
}
