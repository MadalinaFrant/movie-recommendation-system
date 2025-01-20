package recommendation;

import com.opencsv.bean.CsvBindByName;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Movie {

	public Movie() {
	}

	@CsvBindByName(column = "movie_id")
	private String movieId = "";

	@CsvBindByName(column = "movie_name")
	private String movieName = "";

	@CsvBindByName(column = "year")
	private String year = "";

	@CsvBindByName(column = "certificate")
	private String certificate = "";

	@CsvBindByName(column = "runtime")
	private String runtime = "";

	@CsvBindByName(column = "genre")
	private String genres = "";

	@CsvBindByName(column = "rating")
	private double rating = 0.0;

	@CsvBindByName(column = "description")
	private String description = "";

	@CsvBindByName(column = "director")
	private String directors = "";

	@CsvBindByName(column = "director_id")
	private String directorIds = "";

	@CsvBindByName(column = "star")
	private String stars = "";

	@CsvBindByName(column = "star_id")
	private String starIds = "";

	@CsvBindByName(column = "votes")
	private String votes = "";

	@CsvBindByName(column = "gross(in $)")
	private String gross = "";

	public String getMovieId() {
		return movieId;
	}

	public String getMovieName() {
		return movieName;
	}

	public String getYear() {
		return year;
	}

	public String getCertificate() {
		return certificate;
	}

	public String getRuntime() {
		return runtime;
	}

	public String getGenres() {
		return genres;
	}

	public List<String> getGenresAsList() {
		return Arrays.stream(genres.split(",")).map(String::trim).collect(Collectors.toList());
	}

	public double getRating() {
		return rating;
	}

	public String getDescription() {
		return description;
	}

	public String getDirectors() {
		return directors;
	}

	public List<String> getDirectorsAsList() {
		return Arrays.stream(directors.split(",")).map(String::trim).collect(Collectors.toList());
	}

	public String getDirectorIds() {
		return directorIds;
	}

	public String getStars() {
		return stars;
	}

	public List<String> getStarsAsList() {
		return Arrays.stream(stars.split(",")).map(String::trim).collect(Collectors.toList());
	}

	public String getStarIds() {
		return starIds;
	}

	public String getVotes() {
		return votes;
	}

	public String getGross() {
		return gross;
	}

	@Override
	public String toString() {
		return "Movie {" +
				"movieId='" + movieId + '\'' +
				", movieName='" + movieName + '\'' +
				", year='" + year + '\'' +
				", certificate='" + certificate + '\'' +
				", runtime='" + runtime + '\'' +
				", genres='" + getGenresAsList() + '\'' +
				", rating=" + rating +
				", description='" + description + '\'' +
				", directors='" + getDirectorsAsList() + '\'' +
				", directorIds='" + directorIds + '\'' +
				", stars='" + getStarsAsList() + '\'' +
				", starIds='" + starIds + '\'' +
				", votes='" + votes + '\'' +
				", gross='" + gross + '\'' +
				'}';
	}
}
