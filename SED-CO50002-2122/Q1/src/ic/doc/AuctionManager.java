package ic.doc;

import java.util.Stack;

public class AuctionManager {

  private String item = null;
  private Seller seller = null;

  private final Stack<Bid> bids = new Stack<>();
  private final PaymentSystem paymentSystem;
  private final Dispatcher dispatcher;

  public AuctionManager(PaymentSystem paymentSystem, Dispatcher dispatcher) {
    this.paymentSystem = paymentSystem;
    this.dispatcher = dispatcher;
  }

  public void startAction(String item, Seller seller) {
    this.item = item;
    this.seller = seller;
  }

  public void bid(double price, Bidder bidder) {
    if (bids.isEmpty() || price > bids.peek().price()) {
      paymentSystem.charge(price, bidder);
      bidder.respondWith(BidType.ACCEPTED);
      bids.push(new Bid(bidder, price));
      return;
    }

    bidder.respondWith(BidType.TOO_LOW);
  }

  public void endAuction() {
    if (bids.isEmpty())
      return;

    Bid winner = bids.pop();
    paymentSystem.pay(winner.price(), seller);
    dispatcher.dispatch(item, winner.bidder());

    while (!bids.isEmpty()) {
      Bid loser = bids.pop();
      paymentSystem.pay(loser.price(), loser.bidder());
    }

    item = null;
    seller = null;
  }
}
