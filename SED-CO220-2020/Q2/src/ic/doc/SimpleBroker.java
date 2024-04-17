package ic.doc;

public class SimpleBroker implements Broker {

  // imagine that this class is fully implemented so that it really places trades.
  // For the purposes of the exam we have left it as a skeleton.

  @Override
  public void buy(String stock) {
    // not implemented for this exam
    System.out.println("Buy, Buy, Buy! " + stock);
  }

  @Override
  public void sell(String stock) {
    // not implemented for this exam
    System.out.println("Sell, Sell, Sell! " + stock);
  }
}
