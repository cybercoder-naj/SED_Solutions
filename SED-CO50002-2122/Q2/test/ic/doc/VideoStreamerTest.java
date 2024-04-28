package ic.doc;

import ic.doc.awards.Oscar;
import ic.doc.movies.Actor;
import ic.doc.movies.Genre;
import ic.doc.movies.Movie;
import org.junit.Test;

import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static ic.doc.movies.Certification.*;
import static java.util.Collections.EMPTY_LIST;
import static org.junit.Assert.*;


public class VideoStreamerTest {

    private static final Movie jurassicPark = new Movie("Jurassic Park",
        "A pragmatic paleontologist touring an almost complete theme park on an " +
            "island in Central America is tasked with protecting a couple of kids after " +
            "a power failure causes the park's cloned dinosaurs to run loose.",
        2342384,
        List.of(new Actor("Richard Attenborough"),
            new Actor("Laura Dern"),
            new Actor("Sam Neill"),
            new Actor("Jeff Goldblum")),
        Set.of(Genre.ADVENTURE),
        List.of(Oscar.forBest("Visual Effects")),
        EIGHTEEN
    );
    private static final Movie noTimeToDie = new Movie("No Time To Die",
        "Another installment of the James Bond franchise",
        1342365,
        List.of(new Actor("Daniel Craig")),
        Set.of(Genre.ACTION, Genre.ADVENTURE),
        EMPTY_LIST,
        TWELVE_A
    );

    @Test
    public void allowsUserToStreamSuggestedMovies() {

        VideoStreamer streamer = new VideoStreamer(new FakeRecommender(), new FakeClock());
        User user = new User("Adam", 19);

        List<Movie> movies = streamer.getSuggestedMovies(user);
        assertTrue(movies.contains(jurassicPark));
        assertTrue(movies.contains(noTimeToDie));
    }

    @Test
    public void preventsChildToSeeNaughtyFilm() {

        VideoStreamer streamer = new VideoStreamer(new FakeRecommender(), new FakeClock());
        User user = new User("Adam", 9);

        List<Movie> movies = streamer.getSuggestedMovies(user);
        assertFalse(movies.contains(jurassicPark));
        assertTrue(movies.contains(noTimeToDie));
    }

    private static class FakeRecommender implements Recommendable {
        @Override
        public List<Movie> recommendedMoviesFor(User user) {
            if (user.isChild())
                return List.of(noTimeToDie);
            else
                return List.of(noTimeToDie, jurassicPark);
        }
    }

    private static class FakeClock implements Clock {
        LocalTime time = LocalTime.now();

        @Override
        public LocalTime getTime() {
            return time;
        }

        public void setTime(LocalTime time) {
            this.time = time;
        }

        public void advanceMinutes(long minutes) {
            time = time.plusMinutes(minutes);
        }
    }
}
