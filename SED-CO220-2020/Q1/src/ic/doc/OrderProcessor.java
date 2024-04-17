package ic.doc;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/*
 * b)
 * i) A command is an action that you tell an object to execute.
 * A Query is an action where you ask an object to return some data/information.
 *
 * ii) buyMoreOf(Book) is a command telling the buyer to get more books.
 * checkStockLevel(Book) is a query asking for the number of books available.
 *
 * iii) The code is easier to reason about when using commands over queries.
 * When invoking a command, by the principle of encapsulation, the object issuing the command
 * can consider it complete without constantly querying if the work has been done.
 *
 * iv) The style depicted here is called "Tell, don't ask".
 */

public class OrderProcessor implements Observable {
  private final WareHouse wareHouse;
  private final Buyer buyer;
  private final PaymentSystem paymentSystem;

  private final Set<Order> pendingOrders;

  public OrderProcessor(WareHouse wareHouse, Buyer buyer, PaymentSystem paymentSystem) {
    this.wareHouse = wareHouse;
    this.buyer = buyer;
    this.paymentSystem = paymentSystem;
    this.pendingOrders = new HashSet<>();
  }

  public void order(Book book, int quantity, Customer customer) {
    order(book, quantity, customer, true);
  }

  private void order(Book book, int quantity, Customer customer, boolean buyMoreIfUnavailable) {
    int currentStock = wareHouse.checkStockLevel(book);
    if (currentStock == 0) {
      if (buyMoreIfUnavailable)
        buyer.buyMoreOf(book);
      pendingOrders.add(new Order(book, quantity, customer));
      return;
    }

    paymentSystem.charge(book.price() * quantity, customer);
    wareHouse.dispatch(book, quantity, customer);
  }

  @Override
  public void newBooksArrived() {
    for (Order order : pendingOrders) {
      order(order.book, order.quantity, order.customer, false);
    }
  }

  private static class Order {
    public final Book book;
    public final int quantity;
    public final Customer customer;

    public Order(Book book, int quantity, Customer customer) {
      this.book = book;
      this.quantity = quantity;
      this.customer = customer;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (!(obj instanceof Order))
        return false;
      Order order = (Order) obj;
      return quantity == order.quantity && Objects.equals(book, order.book) && Objects.equals(customer, order.customer);
    }

    @Override
    public int hashCode() {
      return Objects.hash(book, quantity, customer);
    }
  }
}
