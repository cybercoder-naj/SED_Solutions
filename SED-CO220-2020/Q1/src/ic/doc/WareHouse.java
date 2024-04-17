package ic.doc;

public interface WareHouse {
  int checkStockLevel(Book book);
  void dispatch(Book book, int quantity, Customer customer);
}
