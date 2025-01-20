package recommendation.handler;

import recommendation.data.Movie;

import java.util.List;
import java.util.Map;

// Chain of Responsibility: Handler for director preferences
public class DirectorPreferenceHandler implements PreferenceHandler {
	@Override
	public int calculateScore(Movie movie, Map<String, List<String>> userPreferences) {
		List<String> preferredDirectors = userPreferences.get("directors");
		if (preferredDirectors == null) return 0;

		return (int) movie.getDirectorsAsList().stream()
				.filter(director -> preferredDirectors.stream()
						.anyMatch(preferredDirector -> preferredDirector.equalsIgnoreCase(director)))
				.count() * 5; // Higher priority for directors
	}
}
