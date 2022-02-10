package movietheatres;

import com.sun.jdi.Value;
import vehiclerental.Rentable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.*;

public class MovieTheatreService {

    private Map<String, List<Movie>> shows = new LinkedHashMap<>();

    public Map<String, List<Movie>> getShows() {
        return shows;
    }

    public void readFromFile(Path path) {
        showsFromLines(linesFromFile(path));
    }

    private List<String> linesFromFile(Path path) {
        try {
            return Files.readAllLines(path);
        } catch (IOException ioe) {
            throw new IllegalArgumentException("Cannot read file", ioe);
        }
    }

    private void showsFromLines(List<String> lines) {
        for (String actual : lines) {
            String[] parts = actual.split("-");
            String[] filmParts = parts[1].split(";");

            String theatre = parts[0];
            String title = filmParts[0];
            LocalTime startTime = LocalTime.parse(filmParts[1]);

            if (isShowsContainTheatre(shows, theatre)) {
                List<Movie> movies = shows.get(theatre);
                movies.add(new Movie(title, startTime));
                movies.sort(Comparator.comparing(Movie::getStartTime));
                shows.put(theatre, movies);
            } else {
                shows.put(theatre, new ArrayList<>(Arrays.asList(new Movie(title, startTime))));
            }
        }
    }

    private boolean isShowsContainTheatre(Map<String, List<Movie>> shows, String theatre) {
        return shows.get(theatre) != null;
    }


    public List<String> findMovie(String title) {
        // "hagyományos módszer:
//        List<String> result = new ArrayList<>();
//        for (Map.Entry<String, List<Movie>> actual : shows.entrySet()) {
//            for ( Movie movie : actual.getValue() ){
//                if (movie.getTitle().equals(title) && !result.contains(actual.getKey())){
//                    result.add(actual.getKey());
//                }
//            }
//        }
//        return result;

        return shows.entrySet().stream()
                .filter(entry -> entry.getValue().stream().anyMatch(movie -> movie.getTitle().equals(title)))
                .map(Map.Entry::getKey)
                .toList();
    }

    public LocalTime findLatestShow(String title) {
//        LocalTime result = LocalTime.of(0, 1);
//        for (Map.Entry<String, List<Movie>> actual : shows.entrySet()) {
//            for (Movie movie : actual.getValue()) {
//                if (movie.getTitle().equals(title) && movie.getStartTime().isAfter(result)) {
//                    result = movie.getStartTime();
//                }
//            }
//        }
//        if (result.equals(LocalTime.of(0, 1))){
//            throw new IllegalArgumentException("The movie doesn't exist");
//        }
//        return result;

//        return shows.entrySet().stream()
//                .flatMap(entry -> entry.getValue().stream())
//                .filter(movie -> movie.getTitle().equals(title))
//                .map(Movie::getStartTime)
//                .max(Comparator.naturalOrder())
//                .orElseThrow(() -> new IllegalArgumentException("The movie doesn't exist: " + title));

        return shows.values().stream()
//                .flatMap(movies -> movies.stream()) // vagy:
                .flatMap(List::stream)
                .filter(movie -> movie.getTitle().equals(title))
                .max(Comparator.comparing(Movie::getStartTime))
                .orElseThrow(() -> new IllegalArgumentException("The movie doesn't exist: " + title))
                .getStartTime();

//        return shows.entrySet().stream()
//                .flatMap(e->e.getValue().stream())
//                .filter(m->m.getTitle().equals(title))
//                .sorted(Comparator.comparing(Movie::getStartTime).reversed())
//                .findFirst().orElseThrow(()->new IllegalArgumentException("Cannot find movie!"))
//                .getStartTime();
    }
}
