package recommendation.handler;

import recommendation.data.Movie;

import java.util.List;
import java.util.Map;

// Chain of Responsibility: Handler for actor preferences
public class ActorPreferenceHandler implements PreferenceHandler {
	@Override
	public int calculateScore(Movie movie, Map<String, List<String>> userPreferences) {
		List<String> preferredActors = userPreferences.get("actors");
		if (preferredActors == null) return 0;

		return (int) movie.getStarsAsList().stream()
				.filter(actor -> preferredActors.stream()
						.anyMatch(preferredActor -> preferredActor.equalsIgnoreCase(actor)))
				.count() * 3; // Priority for actors
	}
}
