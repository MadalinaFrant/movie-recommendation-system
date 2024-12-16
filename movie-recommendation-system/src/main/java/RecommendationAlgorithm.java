import java.util.*;
import java.util.stream.Collectors;

public class RecommendationAlgorithm {
    private Map<String, List<String>> userPreferences;
    private Map<String, Movie> movies;

    public RecommendationAlgorithm(Map<String, List<String>> userPreferences, Map<String, Movie> movies) {
        this.userPreferences = userPreferences;
        this.movies = movies;
    }

    public List<String> recommendMovies() {
        List<String> preferredDirectors = userPreferences.get("Directors");
        List<String> preferredActors = userPreferences.get("Actors");
        List<String> preferredGenres = userPreferences.get("Genres");
        List<String> likedMovies = userPreferences.get("Liked Movies");
        List<String> dislikedMovies = userPreferences.get("Disliked Movies");

        // Verifică dacă sunt preferințe pentru director, actori și genuri
        if (preferredDirectors == null) preferredDirectors = new ArrayList<>();
        if (preferredActors == null) preferredActors = new ArrayList<>();
        if (preferredGenres == null) preferredGenres = new ArrayList<>();

        // Filtrează filmele liked și disliked pentru a le exclude din recomandări
        Set<String> excludedMovies = new HashSet<>();
        if (likedMovies != null) excludedMovies.addAll(likedMovies);
        if (dislikedMovies != null) excludedMovies.addAll(dislikedMovies);

        // Listă pentru a stoca filmele și scorurile lor
        List<MovieScore> movieScores = new ArrayList<>();

        // Iterează prin fiecare film și calculează scorul pe baza preferințelor
        for (Movie movie : movies.values()) {
            // Dacă filmul este în lista de liked sau disliked, îl excludem
            if (excludedMovies.contains(movie.getMovieName())) {
                continue;
            }

            int score = 0;

            // Scor pentru regizori
            if (!preferredDirectors.isEmpty()) {
                score += (int) movie.getDirectorsAsList().stream()
                        .filter(preferredDirectors::contains)
                        .count() * 5;  // Prioritate mai mare pentru regizori
            }

            // Scor pentru actori
            if (!preferredActors.isEmpty()) {
                score += (int) movie.getStarsAsList().stream()
                        .filter(preferredActors::contains)
                        .count() * 3;  // Prioritate mai mare pentru actori
            }

            // Scor pentru genuri
            if (!preferredGenres.isEmpty()) {
                score += (int) movie.getGenresAsList().stream()
                        .filter(preferredGenres::contains)
                        .count() * 2;  // Prioritate mai mică pentru genuri
            }

            // Adaugă filmul și scorul său în lista de filme
            if (score > 0) {  // Adăugăm filmele care au cel puțin un scor mai mare decât 0
                movieScores.add(new MovieScore(movie.getMovieName(), score));
            }
        }

        // Sortează filmele după scor, în ordine descrescătoare
        List<String> recommendedMovies = movieScores.stream()
                .sorted((m1, m2) -> Integer.compare(m2.getScore(), m1.getScore())) // Scor mai mare = mai bun
                .limit(10) // Limitează la primele 10 filme
                .map(MovieScore::getMovieName)
                .collect(Collectors.toList());

        return recommendedMovies;
    }

    // Clasă internă pentru a stoca numele filmului și scorul său
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
