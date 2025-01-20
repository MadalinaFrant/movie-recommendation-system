package recommendation.data;

public class MovieScore {
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
