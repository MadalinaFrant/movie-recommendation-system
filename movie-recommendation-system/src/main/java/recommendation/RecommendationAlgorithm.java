package recommendation;

import java.util.*;
import java.util.stream.Collectors;

public class RecommendationAlgorithm {
    private Map<String, List<String>> userPreferences; // Stores user preferences
    private Map<String, Movie> movies; // Stores movie data from the database

    public RecommendationAlgorithm(Map<String, List<String>> userPreferences) {
        this.userPreferences = userPreferences;
        this.movies = MovieDatabase.getMovies();
    }

    // Recommend movies based on user preferences
    public List<String> recommendMovies() {
        List<String> likedMovies = userPreferences.get("likedMovies");
        List<String> dislikedMovies = userPreferences.get("dislikedMovies");
        List<String> preferredGenres = userPreferences.get("genres");
        List<String> preferredActors = userPreferences.get("actors");
        List<String> preferredDirectors = userPreferences.get("directors");

        if (preferredDirectors == null) preferredDirectors = new ArrayList<>();
        if (preferredActors == null) preferredActors = new ArrayList<>();
        if (preferredGenres == null) preferredGenres = new ArrayList<>();

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

            // Add score for preferred directors
            if (!preferredDirectors.isEmpty()) {
                List<String> finalPreferredDirectors = preferredDirectors;
                score += (int) movie.getDirectorsAsList().stream()
                        .filter(director -> finalPreferredDirectors.stream()
                                .anyMatch(preferredDirector -> preferredDirector.equalsIgnoreCase(director)))
                        .count() * 5; // Higher priority for directors
            }

            // Add score for preferred actors
            if (!preferredActors.isEmpty()) {
                List<String> finalPreferredActors = preferredActors;
                score += (int) movie.getStarsAsList().stream()
                        .filter(actor -> finalPreferredActors.stream()
                                .anyMatch(preferredActor -> preferredActor.equalsIgnoreCase(actor)))
                        .count() * 3; // Priority for actors
            }

            // Add score for preferred genres
            if (!preferredGenres.isEmpty()) {
                List<String> finalPreferredGenres = preferredGenres;
                score += (int) movie.getGenresAsList().stream()
                        .filter(genre -> finalPreferredGenres.stream()
                                .anyMatch(preferredGenre -> preferredGenre.equalsIgnoreCase(genre)))
                        .count() * 2; // Lower priority for genres
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

    // Internal class to store movie and score
    private static class MovieScore {
        private String movieName;
        private int score;

        public MovieScore(String movieName, int score) {
            this.movieName = movieName;
            this.score = score;
        }

        public String getMovieName() {
            return movieName;
        }

        public int getScore() {
            return score;
        }
    }
}
