package recommendation.strategy;

import recommendation.data.Movie;
import recommendation.service.UserPreferencesUpdater;

// Strategy for updating user preferences when a movie is liked
public class LikedMovieStrategy implements MoviePreferenceStrategy {

	@Override
	public void process(Movie movie, UserPreferencesUpdater preferences) {
		preferences.addUniqueItems("genres", movie.getGenresAsList());
		preferences.addUniqueItems("actors", movie.getStarsAsList());
		preferences.addUniqueItems("directors", movie.getDirectorsAsList());
	}
}
