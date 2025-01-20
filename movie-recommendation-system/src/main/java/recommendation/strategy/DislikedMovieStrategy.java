package recommendation.strategy;

import recommendation.data.Movie;
import recommendation.service.UserPreferencesUpdater;

// Strategy for updating user preferences when a movie is disliked
public class DislikedMovieStrategy implements MoviePreferenceStrategy {

	@Override
	public void process(Movie movie, UserPreferencesUpdater preferences) {
		preferences.removeItemsFromList("genres", movie.getGenresAsList());
		preferences.removeItemsFromList("actors", movie.getStarsAsList());
		preferences.removeItemsFromList("directors", movie.getDirectorsAsList());
	}
}
