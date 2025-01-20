package recommendation.service;

import recommendation.data.Movie;
import recommendation.data.MovieDatabase;
import recommendation.strategy.DislikedMovieStrategy;
import recommendation.strategy.LikedMovieStrategy;
import recommendation.strategy.MoviePreferenceStrategy;

import java.util.List;
import java.util.Map;

public class UserPreferencesUpdater {
    private Map<String, List<String>> userPreferences;
    private Map<String, Movie> movies;

    public UserPreferencesUpdater(Map<String, List<String>> userPreferences) {
        this.userPreferences = userPreferences;
        this.movies = MovieDatabase.getMovies();
    }

    public void updatePreferences() {
        // Process liked and disliked movies using the corresponding strategies
        processMovies("likedMovies", new LikedMovieStrategy());
        processMovies("dislikedMovies", new DislikedMovieStrategy());
    }

    private void processMovies(String key, MoviePreferenceStrategy strategy) {
        List<String> moviesList = userPreferences.get(key);
        if (moviesList != null) {
            for (String movieName : moviesList) {
                Movie movie = findMovieByName(movieName);
                if (movie != null) {
                    strategy.process(movie, this); // Process the movie using the corresponding strategy
                }
            }
        }
    }

    private Movie findMovieByName(String movieName) {
        for (Movie movie : movies.values()) {
            if (movie.getMovieName().equalsIgnoreCase(movieName)) {
                return movie;
            }
        }
        return null;
    }

    public void addUniqueItems(String key, List<String> items) {
        List<String> currentList = userPreferences.computeIfAbsent(key, k -> new java.util.ArrayList<>());
        for (String item : items) {
            boolean found = false;
            for (String existingItem : currentList) {
                if (existingItem.equalsIgnoreCase(item)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                currentList.add(item);
            }
        }
    }

    public void removeItemsFromList(String key, List<String> items) {
        List<String> currentList = userPreferences.get(key);
        if (currentList != null) {
            for (String item : items) {
                currentList.removeIf(existingItem -> existingItem.equalsIgnoreCase(item));
            }
        }
    }

    public Map<String, List<String>> getUpdatedPreferences() {
        return userPreferences;
    }
}
