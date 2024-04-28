package ic.doc;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class AuctionManagerTest {

  private static final String ITEM = "Fairy dust";

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private final Bidder ALICE = context.mock(Bidder.class, "alice");
  private final Seller BOB = context.mock(Seller.class, "bob");
  private final Bidder CAROLE = context.mock(Bidder.class, "carole");
  private final Bidder DAVID = context.mock(Bidder.class, "david");
  private final Dispatcher dispatcher = context.mock(Dispatcher.class);
  private final PaymentSystem paymentSystem = context.mock(PaymentSystem.class);
  private final AuctionManager auctionManager = new AuctionManager(paymentSystem, dispatcher);

  @Before
  public void setup() {
    auctionManager.startAction(ITEM, BOB);
  }

  @Test
  public void acceptInitialBid() {
    context.checking(new Expectations() {{
      oneOf(paymentSystem).charge(10.0, ALICE);
      oneOf(ALICE).respondWith(BidType.ACCEPTED);
    }});

    auctionManager.bid(10.0, ALICE);
  }

  @Test
  public void declineLowerBid() {
    context.checking(new Expectations() {{
      oneOf(paymentSystem).charge(10.0, ALICE);
      oneOf(ALICE).respondWith(BidType.ACCEPTED);
    }});
    auctionManager.bid(10.0, ALICE);

    context.checking(new Expectations() {{
      oneOf(CAROLE).respondWith(BidType.TOO_LOW);
    }});
    auctionManager.bid(5.0, CAROLE);
  }

  @Test
  public void acceptHigherBid() {
    context.checking(new Expectations() {{
      oneOf(paymentSystem).charge(10.0, ALICE);
      oneOf(ALICE).respondWith(BidType.ACCEPTED);
    }});
    auctionManager.bid(10.0, ALICE);

    context.checking(new Expectations() {{
      oneOf(paymentSystem).charge(20.0, DAVID);
      oneOf(DAVID).respondWith(BidType.ACCEPTED);
    }});
    auctionManager.bid(20.0, DAVID);
  }

  @Test
  public void endAuction() {
    context.checking(new Expectations() {{
      oneOf(paymentSystem).charge(10.0, ALICE);
      oneOf(paymentSystem).charge(20.0, DAVID);
      ignoring(ALICE);
      ignoring(CAROLE);
      ignoring(DAVID);
    }});
    auctionManager.bid(10.0, ALICE);
    auctionManager.bid(5.0, CAROLE);
    auctionManager.bid(20.0, DAVID);

    context.checking(new Expectations() {{
      oneOf(paymentSystem).pay(20.0, BOB);
      oneOf(dispatcher).dispatch(ITEM, DAVID);
      oneOf(paymentSystem).pay(10.0, ALICE);
    }});
    auctionManager.endAuction();
  }
}
