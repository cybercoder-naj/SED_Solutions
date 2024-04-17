package ic.doc;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

public class OrderProcessorTest {

  static final Book DESIGN_PATTERNS_BOOK =
      new Book("Design Patterns", "Gamma, Helm, Johnson and Vlissides", 25.99);
  static final Book LEGACY_CODE_BOOK =
      new Book("Working Effectively with Legacy Code", "Feathers", 29.99);

  static final Customer ALICE = new Customer("Alice Jones");
  static final Customer BOB = new Customer("Bob Smith");

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();
  private final WareHouse wareHouse = context.mock(WareHouse.class);
  private final Buyer buyer = context.mock(Buyer.class);
  private final PaymentSystem paymentSystem = context.mock(PaymentSystem.class);

  private final OrderProcessor orderProcessor = new OrderProcessor(wareHouse, buyer, paymentSystem);

  @Test
  public void buyBooksIfUnavailable() {
    int currentStock = 0;
    Book book = LEGACY_CODE_BOOK;
    context.checking(new Expectations() {{
      exactly(1).of(wareHouse).checkStockLevel(book);
      will(returnValue(currentStock));
      exactly(1).of(buyer).buyMoreOf(book);
    }});

    orderProcessor.order(book, 1, BOB);
  }

  @Test
  public void dispatchIfBookAvailable() {
    int currentStock = 3;
    int quantity = 2;
    Book book = DESIGN_PATTERNS_BOOK;
    Customer customer = ALICE;
    context.checking(new Expectations() {{
      exactly(1).of(wareHouse).checkStockLevel(book);
      will(returnValue(currentStock));
      exactly(1)
          .of(paymentSystem)
          .charge(book.price() * quantity, customer);
      exactly(1).of(wareHouse).dispatch(book, quantity, customer);
    }});

    orderProcessor.order(book, quantity, customer);
  }

  @Test
  public void checkStockOnBooksArrival() {
    Book book = LEGACY_CODE_BOOK;
    context.checking(new Expectations() {{
      exactly(1).of(wareHouse).checkStockLevel(book);
      will(returnValue(0));
      exactly(1).of(buyer).buyMoreOf(book);
    }});
    orderProcessor.order(book, 1, BOB);

    context.checking(new Expectations() {{
      exactly(1).of(wareHouse).checkStockLevel(book);
      will(returnValue(0));
    }});
    orderProcessor.newBooksArrived();

    context.checking(new Expectations() {{
      exactly(1).of(wareHouse).checkStockLevel(book);
      will(returnValue(5));
      exactly(1)
          .of(paymentSystem)
          .charge(book.price(), BOB);
      exactly(1).of(wareHouse).dispatch(book, 1, BOB);
    }});
    orderProcessor.newBooksArrived();
  }
}
