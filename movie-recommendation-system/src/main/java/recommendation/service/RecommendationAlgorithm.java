package recommendation.service;

import recommendation.data.Movie;
import recommendation.data.MovieDatabase;
import recommendation.data.MovieScore;
import recommendation.handler.ActorPreferenceHandler;
import recommendation.handler.DirectorPreferenceHandler;
import recommendation.handler.GenrePreferenceHandler;
import recommendation.handler.PreferenceHandler;

import java.util.*;
import java.util.stream.Collectors;

public class RecommendationAlgorithm {
    private Map<String, List<String>> userPreferences; // Stores user preferences
    private Map<String, Movie> movies; // Stores movie data from the database

    private List<PreferenceHandler> preferenceHandlers;

    public RecommendationAlgorithm(Map<String, List<String>> userPreferences) {
        this.userPreferences = userPreferences;
        this.movies = MovieDatabase.getMovies();
        this.preferenceHandlers = new ArrayList<>();
        initPreferenceHandlers(); // Initialize preference handlers
    }

    // Initialize handlers for each preference type
    private void initPreferenceHandlers() {
        preferenceHandlers.add(new GenrePreferenceHandler());
        preferenceHandlers.add(new ActorPreferenceHandler());
        preferenceHandlers.add(new DirectorPreferenceHandler());
    }

    // Recommend movies based on user preferences
    public List<String> recommendMovies() {
        List<String> likedMovies = userPreferences.get("likedMovies");
        List<String> dislikedMovies = userPreferences.get("dislikedMovies");

        // Exclude liked and disliked movies from recommendations
        Set<String> excludedMovies = new HashSet<>();
        if (likedMovies != null) excludedMovies.addAll(likedMovies);
        if (dislikedMovies != null) excludedMovies.addAll(dislikedMovies);

        // List to store movies and their scores
        List<MovieScore> movieScores = new ArrayList<>();

        // Calculate score for each movie based on user preferences
        for (Movie movie : movies.values()) {
            // Skip movies that are already liked or disliked
            boolean isExcluded = excludedMovies.stream().anyMatch(excluded -> excluded.equalsIgnoreCase(movie.getMovieName()));
            if (isExcluded) {
                continue;
            }

            int score = 0;

            // Add scores from each preference handler
            for (PreferenceHandler handler : preferenceHandlers) {
                score += handler.calculateScore(movie, userPreferences);
            }

            // Add movie to the list if it has a positive score
            if (score > 0) {
                movieScores.add(new MovieScore(movie.getMovieName(), score));
            }
        }

        // Sort movies by score in descending order and limit to the top 10
        List<String> recommendedMovies = movieScores.stream()
                .sorted((m1, m2) -> Integer.compare(m2.getScore(), m1.getScore()))
                .limit(10)
                .map(MovieScore::getMovieName)
                .collect(Collectors.toList());

        return recommendedMovies;
    }

}
