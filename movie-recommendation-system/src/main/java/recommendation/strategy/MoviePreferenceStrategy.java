package recommendation.strategy;

import recommendation.data.Movie;
import recommendation.service.UserPreferencesUpdater;

// Interface for the strategy pattern
public interface MoviePreferenceStrategy {
	void process(Movie movie, UserPreferencesUpdater preferences);
}
