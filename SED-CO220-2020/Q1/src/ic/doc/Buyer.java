package ic.doc;

import java.util.List;

public interface Buyer {
  void buyMoreOf(Book book);
  void receivesBooks(List<Book> book);

  void registerObserver(Observable observable);
}
