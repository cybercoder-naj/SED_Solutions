package ic.doc;

public class AuctionManager {

  private String currentItem = null;
  private Seller currentSeller = null;

  private final PaymentSystem paymentSystem;

  public AuctionManager(PaymentSystem paymentSystem) {
    this.paymentSystem = paymentSystem;
  }

  public void startAction(String item, Seller seller) {
    currentItem = item;
    currentSeller = seller;
  }

  public void bid(double price, Bidder bidder) {
    paymentSystem.charge(price, bidder);
    bidder.respondWith(BidType.ACCEPTED);
  }
}
