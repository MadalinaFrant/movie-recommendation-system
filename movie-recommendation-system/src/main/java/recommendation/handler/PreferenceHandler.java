package recommendation.handler;

import recommendation.data.Movie;

import java.util.List;
import java.util.Map;

// Interface for the preference handlers
public interface PreferenceHandler {
	int calculateScore(Movie movie, Map<String, List<String>> userPreferences);
}

