package ic.doc;

import ic.doc.awards.Oscar;
import ic.doc.movies.Actor;
import ic.doc.movies.Genre;
import ic.doc.movies.Movie;
import ic.doc.streaming.VideoStream;
import org.junit.Test;

import java.util.List;
import java.util.Set;

import static ic.doc.movies.Certification.PARENTAL_GUIDANCE;
import static ic.doc.movies.Certification.TWELVE_A;
import static java.util.Collections.EMPTY_LIST;

public class VideoStreamerTest {

    @Test
    public void allowsUserToStreamSuggestedMovies() {

        VideoStreamer streamer = new VideoStreamer(new FakeRecommender());
        User user = new User("Adam", 9);

        List<Movie> movies = streamer.getSuggestedMovies(user);
        VideoStream stream = streamer.startStreaming(movies.get(0), user);

        // adam watches the movie

        streamer.stopStreaming(stream);
    }

    private static class FakeRecommender implements Recommendable {
        @Override
        public List<Movie> recommendedMoviesFor(User user) {
            return List.of(
                new Movie("Jurassic Park",
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
                    PARENTAL_GUIDANCE
                ),
                new Movie("No Time To Die",
                    "Another installment of the James Bond franchise",
                    1342365,
                    List.of(new Actor("Daniel Craig")),
                    Set.of(Genre.ACTION, Genre.ADVENTURE),
                    EMPTY_LIST,
                    TWELVE_A
                ));
        }
    }
}
