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

  private final Seller SELLER = context.mock(Seller.class);
  private final Bidder ALICE = context.mock(Bidder.class);
  private final PaymentSystem paymentSystem = context.mock(PaymentSystem.class);
  private final AuctionManager auctionManager = new AuctionManager(paymentSystem);

  @Before
  public void setup() {
    auctionManager.startAction(ITEM, SELLER);
  }

  @Test
  public void acceptInitialBid() {
    context.checking(new Expectations() {{
      oneOf(paymentSystem).charge(10.0, ALICE);
      oneOf(ALICE).respondWith(BidType.ACCEPTED);
    }});

    auctionManager.bid(10.0, ALICE);
  }
}
