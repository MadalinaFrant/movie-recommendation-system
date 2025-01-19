package recommendation;

import java.util.List;
import java.util.Map;

public class UserPreferencesUpdated {
    private Map<String, List<String>> userPreferences;
    private Map<String, Movie> movies;

    public UserPreferencesUpdated(Map<String, List<String>> userPreferences) {
        this.userPreferences = userPreferences;
        this.movies = MovieDatabase.getMovies();
    }

    public void updatePreferences() {

        // Procesăm filmele favorite: adăugăm genuri, actori și regizori
        List<String> likedMovies = userPreferences.get("likedMovies");
        if (likedMovies != null) {
            for (String movieName : likedMovies) {
                Movie movie = findMovieByName(movieName);
                if (movie != null) {
                    // Adaugă genurile, actori și regizori doar dacă nu sunt deja în listă
                    addUniqueItems("genres", movie.getGenresAsList());
                    addUniqueItems("actors", movie.getStarsAsList());
                    addUniqueItems("directors", movie.getDirectorsAsList());
                }
            }
        }

        // Procesăm filmele nesuferite: eliminăm genuri, actori și regizori
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
            if (movie.getMovieName().equalsIgnoreCase(movieName)) {  // Căutăm filmul după nume
                return movie;
            }
        }
        return null;  // Returnează null dacă filmul nu este găsit
    }

    private void removeItemsFromList(String key, List<String> items) {
        List<String> currentList = userPreferences.get(key);
        if (currentList != null) {
            currentList.removeAll(items);
        }
    }

    // Adaugă elemente unice în lista specificată (dacă nu sunt deja prezente)
    private void addUniqueItems(String key, List<String> items) {
        List<String> currentList = userPreferences.computeIfAbsent(key, k -> new java.util.ArrayList<>());
        for (String item : items) {
            if (!currentList.contains(item)) {
                currentList.add(item);
            }
        }
    }

    public Map<String, List<String>> getUpdatedPreferences() {
        return userPreferences;
    }
}
