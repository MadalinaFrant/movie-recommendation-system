// MovieSystem.java
package recommendation;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@SpringBootApplication
@Controller
public class MovieSystem {
    // Global static state - everything accessible from everywhere
    public static List<MovieData> ALL_MOVIES = new ArrayList<>();
    public static Set<String> MOVIE_NAMES = new HashSet<>();
    public static Set<String> GENRES = new HashSet<>();
    public static Set<String> ACTORS = new HashSet<>();
    public static Set<String> DIRECTORS = new HashSet<>();
    public static boolean IS_INITIALIZED = false;

    // Data class with no encapsulation
    public static class MovieData {
        @CsvBindByName(column = "movie_id")
        public String movieId;
        @CsvBindByName(column = "movie_name")
        public String movieName;
        @CsvBindByName(column = "year")
        public String year;
        @CsvBindByName(column = "certificate")
        public String certificate;
        @CsvBindByName(column = "runtime")
        public String runtime;
        @CsvBindByName(column = "genre")
        public String genres;
        @CsvBindByName(column = "rating")
        public double rating;
        @CsvBindByName(column = "description")
        public String description;
        @CsvBindByName(column = "director")
        public String directors;
        @CsvBindByName(column = "director_id")
        public String directorIds;
        @CsvBindByName(column = "star")
        public String stars;
        @CsvBindByName(column = "star_id")
        public String starIds;
        @CsvBindByName(column = "votes")
        public String votes;
        @CsvBindByName(column = "gross(in $)")
        public String gross;

        // Duplicate code everywhere for list processing
        public List<String> getGenresList() {
            if(genres == null || genres.isEmpty()) return new ArrayList<>();
            return Arrays.stream(genres.split(",")).map(String::trim).collect(Collectors.toList());
        }

        public List<String> getDirectorsList() {
            if(directors == null || directors.isEmpty()) return new ArrayList<>();
            return Arrays.stream(directors.split(",")).map(String::trim).collect(Collectors.toList());
        }

        public List<String> getStarsList() {
            if(stars == null || stars.isEmpty()) return new ArrayList<>();
            return Arrays.stream(stars.split(",")).map(String::trim).collect(Collectors.toList());
        }
    }

    // God controller handling everything
    @GetMapping("/")
    public String homePage(Model model) {
        if(!IS_INITIALIZED) initializeMovies();
        model.addAttribute("moviesList", MOVIE_NAMES);
        model.addAttribute("genresList", GENRES);
        model.addAttribute("actorsList", ACTORS);
        model.addAttribute("directorsList", DIRECTORS);
        return "home";
    }

    @GetMapping("/classic_recommendation")
    public String classicRecommendation() {
        return "classic_recommendation";
    }

    @GetMapping("/movie_night")
    public String movieNight() {
        return "movie_night";
    }

    // Massive method handling all recommendation logic
    @PostMapping("/recommendation")
    public String recommendMovies(@RequestParam Map<String, String> userInput, Model model) {
        Map<String, List<String>> preferences = new HashMap<>();

        // Process all inputs into one big map
        for(Map.Entry<String, String> entry : userInput.entrySet()) {
            String key = entry.getKey();
            String baseKey = key.replaceAll("[12]$", "");
            String[] values = entry.getValue().split("\n");
            List<String> valueList = new ArrayList<>();
            for(String v : values) {
                if(v != null && !v.trim().isEmpty()) valueList.add(v.trim());
            }

            if(key.equals(baseKey)) {
                preferences.put(key, valueList);
            } else {
                List<String> existing = preferences.get(baseKey);
                if(existing == null) {
                    existing = new ArrayList<>();
                    preferences.put(baseKey, existing);
                }
                existing.addAll(valueList);
            }
        }

        // Update preferences based on liked/disliked movies - all in one big method
        List<String> likedMovies = preferences.get("likedMovies");
        List<String> dislikedMovies = preferences.get("dislikedMovies");
        if(likedMovies != null) {
            for(String movieName : likedMovies) {
                for(MovieData movie : ALL_MOVIES) {
                    if(movie.movieName.equalsIgnoreCase(movieName)) {
                        // Add genres
                        List<String> genres = preferences.computeIfAbsent("genres", k -> new ArrayList<>());
                        for(String genre : movie.getGenresList()) {
                            boolean exists = false;
                            for(String g : genres) {
                                if(g.equalsIgnoreCase(genre)) {
                                    exists = true;
                                    break;
                                }
                            }
                            if(!exists) genres.add(genre);
                        }

                        // Add actors
                        List<String> actors = preferences.computeIfAbsent("actors", k -> new ArrayList<>());
                        for(String actor : movie.getStarsList()) {
                            boolean exists = false;
                            for(String a : actors) {
                                if(a.equalsIgnoreCase(actor)) {
                                    exists = true;
                                    break;
                                }
                            }
                            if(!exists) actors.add(actor);
                        }

                        // Add directors
                        List<String> directors = preferences.computeIfAbsent("directors", k -> new ArrayList<>());
                        for(String director : movie.getDirectorsList()) {
                            boolean exists = false;
                            for(String d : directors) {
                                if(d.equalsIgnoreCase(director)) {
                                    exists = true;
                                    break;
                                }
                            }
                            if(!exists) directors.add(director);
                        }
                        break;
                    }
                }
            }
        }

        // Remove preferences from disliked movies
        if(dislikedMovies != null) {
            for(String movieName : dislikedMovies) {
                for(MovieData movie : ALL_MOVIES) {
                    if(movie.movieName.equalsIgnoreCase(movieName)) {
                        List<String> genres = preferences.get("genres");
                        if(genres != null) {
                            genres.removeIf(g -> movie.getGenresList().stream()
                                    .anyMatch(mg -> mg.equalsIgnoreCase(g)));
                        }

                        List<String> actors = preferences.get("actors");
                        if(actors != null) {
                            actors.removeIf(a -> movie.getStarsList().stream()
                                    .anyMatch(ma -> ma.equalsIgnoreCase(a)));
                        }

                        List<String> directors = preferences.get("directors");
                        if(directors != null) {
                            directors.removeIf(d -> movie.getDirectorsList().stream()
                                    .anyMatch(md -> md.equalsIgnoreCase(d)));
                        }
                        break;
                    }
                }
            }
        }

        // Calculate recommendations - everything in one huge block
        Map<String, Integer> movieScores = new HashMap<>();
        Set<String> excludedMovies = new HashSet<>();
        if(likedMovies != null) excludedMovies.addAll(likedMovies);
        if(dislikedMovies != null) excludedMovies.addAll(dislikedMovies);

        for(MovieData movie : ALL_MOVIES) {
            if(excludedMovies.stream().anyMatch(e -> e.equalsIgnoreCase(movie.movieName))) {
                continue;
            }

            int score = 0;
            List<String> preferredDirectors = preferences.get("directors");
            List<String> preferredActors = preferences.get("actors");
            List<String> preferredGenres = preferences.get("genres");

            if(preferredDirectors != null && !preferredDirectors.isEmpty()) {
                for(String director : movie.getDirectorsList()) {
                    if(preferredDirectors.stream().anyMatch(d -> d.equalsIgnoreCase(director))) {
                        score += 5;
                    }
                }
            }

            if(preferredActors != null && !preferredActors.isEmpty()) {
                for(String actor : movie.getStarsList()) {
                    if(preferredActors.stream().anyMatch(a -> a.equalsIgnoreCase(actor))) {
                        score += 3;
                    }
                }
            }

            if(preferredGenres != null && !preferredGenres.isEmpty()) {
                for(String genre : movie.getGenresList()) {
                    if(preferredGenres.stream().anyMatch(g -> g.equalsIgnoreCase(genre))) {
                        score += 2;
                    }
                }
            }

            if(score > 0) {
                movieScores.put(movie.movieName, score);
            }
        }

        // Manual sorting instead of using proper collections
        List<String> recommendations = new ArrayList<>();
        while(!movieScores.isEmpty() && recommendations.size() < 10) {
            String bestMovie = null;
            int highestScore = -1;
            for(Map.Entry<String, Integer> entry : movieScores.entrySet()) {
                if(entry.getValue() > highestScore) {
                    highestScore = entry.getValue();
                    bestMovie = entry.getKey();
                }
            }
            recommendations.add(bestMovie);
            movieScores.remove(bestMovie);
        }

        model.addAttribute("recommendedMovies", recommendations);
        return "recommendation";
    }

    // Initialization mixed with everything else
    private static void initializeMovies() {
        try {
            File dir = new File("dataset");
            if(!dir.exists()) {
                System.out.println("Dataset directory not found!");
                return;
            }

            for(File file : dir.listFiles()) {
                if(!file.getName().endsWith(".csv")) continue;

                try(FileReader reader = new FileReader(file)) {
                    CsvToBean<MovieData> csvToBean = new CsvToBeanBuilder<MovieData>(reader)
                            .withType(MovieData.class)
                            .withIgnoreLeadingWhiteSpace(true)
                            .build();

                    for(MovieData movie : csvToBean.parse()) {
                        ALL_MOVIES.add(movie);
                        MOVIE_NAMES.add(movie.movieName);
                        DIRECTORS.addAll(movie.getDirectorsList());
                        ACTORS.addAll(movie.getStarsList());
                        GENRES.addAll(movie.getGenresList());
                    }
                } catch(Exception e) {
                    System.out.println("Error reading file " + file.getName() + ": " + e.getMessage());
                }
            }

            IS_INITIALIZED = true;
        } catch(Exception e) {
            System.out.println("Initialization failed: " + e.getMessage());
        }
    }

    // Main method in the same file
    public static void main(String[] args) {
        SpringApplication.run(MovieSystem.class, args);
    }
}