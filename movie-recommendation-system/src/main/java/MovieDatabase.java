import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MovieDatabase {
    private Map<String, Movie> movies = new HashMap<>();

    public MovieDatabase(String directoryPath) {
        loadMovies(directoryPath);
    }

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
                        movies.putIfAbsent(movie.getMovieId(), movie);
                    }
                } catch (FileNotFoundException e) {
                    System.out.println("File not found: " + file.getName());
                } catch (Exception e) {
                    System.out.println("Error reading file: " + file.getName());
                }
            }
        }
    }

    public Map<String, Movie> getMovies() {
        return movies;
    }
}
