package ic.doc;

import ic.doc.movies.Movie;
import ic.doc.streaming.VideoStream;
import org.junit.Test;

import java.util.List;

public class VideoStreamerTest {

    @Test
    public void allowsUserToStreamSuggestedMovies() {

        VideoStreamer streamer = new VideoStreamer();
        User user = new User("Adam", 9);

        List<Movie> movies = streamer.getSuggestedMovies(user);
        VideoStream stream = streamer.startStreaming(movies.get(0), user);

        // adam watches the movie

        streamer.stopStreaming(stream);
    }
}
