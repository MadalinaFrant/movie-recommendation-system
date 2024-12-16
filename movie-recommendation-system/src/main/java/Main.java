import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
	public static void main(String[] args) {

		Map<String, Movie> movies = new HashMap<>();

		File dir = new File("../dataset");

		for (File file : dir.listFiles()) {
			if (file.isFile() && file.getName().endsWith(".csv")) {

				FileReader reader = null;
				try {
					reader = new FileReader(file);
				} catch (FileNotFoundException e) {
					System.out.println("File not found");
				}

				CsvToBean<Movie> csvToBean = new CsvToBeanBuilder<Movie>(reader)
						.withType(Movie.class)
						.withIgnoreLeadingWhiteSpace(true)
						.build();

				List<Movie> parsedMovies = csvToBean.parse();

				for (Movie movie : parsedMovies) {
					movies.putIfAbsent(movie.getMovieId(), movie);
				}
			}
		}

		System.out.println(movies);
	}
}