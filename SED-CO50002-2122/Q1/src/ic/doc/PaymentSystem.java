package ic.doc;

public interface PaymentSystem {
  void charge(double price, Bidder bidder);

  void pay(double price, Person person);
}
