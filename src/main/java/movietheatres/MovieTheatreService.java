package movietheatres;

import vehiclerental.Rentable;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalTime;
import java.util.*;

public class MovieTheatreService {

    private Map<String, List<Movie>> shows = new TreeMap<>();

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
//        for (String actual : lines) {
//            String[] parts = actual.split("-");
//            String[] filmParts = parts[1].split(";");
//            String[] timeParts = filmParts[1].split(":");
//
//            String title = timeParts[0];
//            LocalTime startTime = LocalTime.parse(timeParts[1]);
//
//            if (isShowsContainMovie(shows, filmParts[0])) {
//                shows.put(parts[0], List.of(new Movie(filmParts[0], LocalTime.parse(filmParts[1]))));
//            } else {
//                  shows.put(parts[0], shows.get(parts[0]).add(new Movie(title,startTime)));
//            }
//        }
    }

    private boolean isShowsContainMovie(Map<String, List<Movie>> shows, String movie) {
        return shows.get(movie) == null;
    }


    public List<String> findMovie(String title) {
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, List<Movie>> actual : shows.entrySet()) {
            if (actual.getValue().contains(title)) {
                result.add(actual.getKey());
            }
        }
        return result;
    }

//    public LocalTime findLatestShow(String title){
//        for (Map.Entry<String, List<Movie>> actual : shows.entrySet()) {
//        if (actual.getValue().contains(title)){
//            for (Movie actual : ){
//
//            }
//        }
//
//        }
//
//    }
}
