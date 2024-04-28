package ic.doc;

public interface PaymentSystem {
  boolean charge(double price, Bidder bidder);
}
