import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;
import java.util.stream.Collectors;

public class UserInput {
    private Map<String, List<String>> userPreferences = new HashMap<>();

    public UserInput() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Liked Movies (comma separated):");
        String likedMovies = scanner.nextLine();
        userPreferences.put("Liked Movies", parseInput(likedMovies));

        System.out.println("Disliked Movies (comma separated):");
        String dislikedMovies = scanner.nextLine();
        userPreferences.put("Disliked Movies", parseInput(dislikedMovies));

        System.out.println("Genres (comma separated):");
        String genres = scanner.nextLine();
        userPreferences.put("Genres", parseInput(genres));

        System.out.println("Actors (comma separated):");
        String actors = scanner.nextLine();
        userPreferences.put("Actors", parseInput(actors));

        System.out.println("Directors (comma separated):");
        String directors = scanner.nextLine();
        userPreferences.put("Directors", parseInput(directors));
    }

    private List<String> parseInput(String input) {
        return Arrays.stream(input.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    public Map<String, List<String>> getUserPreferences() {
        return userPreferences;
    }
}
