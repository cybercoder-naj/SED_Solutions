package ic.doc;

import ic.doc.movies.Movie;
import ic.doc.streaming.PlaybackEventLog;
import ic.doc.streaming.StreamTracker;
import ic.doc.streaming.VideoStream;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class VideoStreamer {

  private final Map<VideoStream, StreamTracker> currentStreams = new HashMap<>();
  private final PlaybackEventLog playbackEvents = new PlaybackEventLog();
  private final Recommendable recommendable;
  private final Clock clock;

  public VideoStreamer(Recommendable recommendable, Clock clock) {
    this.recommendable = recommendable;
    this.clock = clock;
  }

  public List<Movie> getSuggestedMovies(User user) {
    List<Movie> recommendations = recommendable.recommendedMoviesFor(user);

    // sort the list of suggestions in descending order of number of views
    List<Movie> suggestions =  new ArrayList<>(recommendations);
    suggestions.sort(Comparator.comparing(Movie::numberOfViews).reversed());
    return suggestions;
  }

  public VideoStream startStreaming(Movie movie, User user) {
    VideoStream stream = new VideoStream(movie);
    currentStreams.put(stream, new StreamTracker(user, clock));
    return stream;
  }

  public void stopStreaming(VideoStream stream) {
    StreamTracker streamTracker = currentStreams.remove(stream);
    LocalTime endTime = clock.getTime();
    long minutesWatched = ChronoUnit.MINUTES.between(streamTracker.startTime(), endTime);
    if (minutesWatched > 15) {
      playbackEvents.logWatched(streamTracker.user(), stream.movie());
    }
  }

}
