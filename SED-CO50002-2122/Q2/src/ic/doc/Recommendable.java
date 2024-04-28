package ic.doc;

import ic.doc.movies.Movie;

import java.util.List;

public interface Recommendable {
  List<Movie> recommendedMoviesFor(User user);
}
