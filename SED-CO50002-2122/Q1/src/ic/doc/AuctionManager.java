package ic.doc;

public class AuctionManager {

  private String currentItem = null;
  private Seller currentSeller = null;
  private double currentPrice = 0.0d;

  private final PaymentSystem paymentSystem;

  public AuctionManager(PaymentSystem paymentSystem) {
    this.paymentSystem = paymentSystem;
  }

  public void startAction(String item, Seller seller) {
    currentItem = item;
    currentSeller = seller;
  }

  public void bid(double price, Bidder bidder) {
    if (price <= currentPrice) {
      bidder.respondWith(BidType.TOO_LOW);
      return;
    }

    paymentSystem.charge(price, bidder);
    bidder.respondWith(BidType.ACCEPTED);
    currentPrice = price;
  }
}
