package movietheatres;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.*;

public class MovieTheatreServiceByKristof {

    private Map<String, List<Movie>> shows = new LinkedHashMap<>();

    public Map<String, List<Movie>> getShows() {
        return shows;
    }

    public void readFromFile(Path path) {
        try (BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while ((line = br.readLine()) != null) {
                parseLine(line);
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Cannot read file!");
        }
    }

    private void parseLine(String line) {
        String[] temp = line.split("-");
        String[] movieTemp = temp[1].split(";");

        if (!shows.containsKey(temp[0])) {
            shows.put(temp[0], new ArrayList<>());
        }
        shows.get(temp[0]).add(new Movie(movieTemp[0], LocalTime.parse(movieTemp[1])));
        Collections.sort(shows.get(temp[0]), Comparator.comparing(Movie::getStartTime));
    }

    public List<String> findMovie(String title) {
        return shows.entrySet().stream()
                .filter(entry -> entry.getValue().stream().anyMatch(movie -> movie.getTitle().equals(title)))
                .map(Map.Entry::getKey)
                .toList();
    }

    public LocalTime findLatestShow(String title) {
        return shows.entrySet().stream()
                .flatMap(e -> e.getValue().stream())
                .filter(m -> m.getTitle().equals(title))
                .sorted(Comparator.comparing(Movie::getStartTime).reversed())
                .findFirst().orElseThrow(() -> new IllegalArgumentException("Cannot find movie!"))
                .getStartTime();
    }
}
