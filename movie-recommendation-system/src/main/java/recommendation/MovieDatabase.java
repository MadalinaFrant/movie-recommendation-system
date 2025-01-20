package recommendation;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import static org.springframework.util.StringUtils.capitalize;

public class MovieDatabase {
    private static Map<String, Movie> movies = new HashMap<>();

    private static Set<String> movieNames = new HashSet<>();
    private static Set<String> genres = new HashSet<>();
    private static Set<String> actors = new HashSet<>();
    private static Set<String> directors = new HashSet<>();

    // Load movies from dataset if not already loaded
    public MovieDatabase(String directoryPath) {
        if (!movies.isEmpty()) {
            return;
        }

        loadMovies(directoryPath);
    }

    // Load movies from CSV files
    private void loadMovies(String directoryPath) {
        File dir = new File(directoryPath);

        for (File file : dir.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".csv")) {
                try (FileReader reader = new FileReader(file)) {
                    CsvToBean<Movie> csvToBean = new CsvToBeanBuilder<Movie>(reader)
                            .withType(Movie.class)
                            .withIgnoreLeadingWhiteSpace(true)
                            .build();

                    List<Movie> parsedMovies = csvToBean.parse();
                    for (Movie movie : parsedMovies) {
                        movies.putIfAbsent(movie.getMovieId(), movie); // Add movie to the database

                        movieNames.add(movie.getMovieName());
                        actors.addAll(movie.getStarsAsList());
                        directors.addAll(movie.getDirectorsAsList());
                    }

                    genres.add(capitalize(file.getName().replace(".csv", "")));
                } catch (FileNotFoundException e) {
                    System.out.println("File not found: " + file.getName());
                } catch (Exception e) {
                    System.out.println("Error reading file: " + file.getName());
                }
            }
        }
    }

    public static Map<String, Movie> getMovies() {
        return movies;
    }

    public static Set<String> getMovieNames() {
        return movieNames;
    }

    public static Set<String> getGenres() {
        return genres;
    }

    public static Set<String> getActors() {
        return actors;
    }

    public static Set<String> getDirectors() {
        return directors;
    }
}
