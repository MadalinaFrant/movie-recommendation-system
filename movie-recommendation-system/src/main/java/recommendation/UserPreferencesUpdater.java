package recommendation;

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

        // Process liked movies: add genres, actors, and directors if they are not already in the list
        List<String> likedMovies = userPreferences.get("likedMovies");
        if (likedMovies != null) {
            for (String movieName : likedMovies) {
                Movie movie = findMovieByName(movieName);
                if (movie != null) {
                    addUniqueItems("genres", movie.getGenresAsList());
                    addUniqueItems("actors", movie.getStarsAsList());
                    addUniqueItems("directors", movie.getDirectorsAsList());
                }
            }
        }

        // Process disliked movies: remove genres, actors, and directors if they are in the list
        List<String> dislikedMovies = userPreferences.get("dislikedMovies");
        if (dislikedMovies != null) {
            for (String movieName : dislikedMovies) {
                Movie movie = findMovieByName(movieName);
                if (movie != null) {
                    removeItemsFromList("genres", movie.getGenresAsList());
                    removeItemsFromList("actors", movie.getStarsAsList());
                    removeItemsFromList("directors", movie.getDirectorsAsList());
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

    private void addUniqueItems(String key, List<String> items) {
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

    private void removeItemsFromList(String key, List<String> items) {
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
