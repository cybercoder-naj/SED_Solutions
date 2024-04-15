package Q1;

import java.time.LocalTime;

public class SimpleBilling implements BillingSystem {

  private static final LocalTime peakStart = LocalTime.of(9, 0);
  private static final LocalTime peakEnd = LocalTime.of(18,0);
  private static final SimpleBilling INSTANCE = new SimpleBilling();

  public static SimpleBilling getInstance() {
    return INSTANCE;
  }

  @Override
  public void addBillItem(String caller, String callee, long callCostInPence) {

    // Imagine lots more code here that really does payment processing - we
    // did not implement it all for the purposes of the exam.

    System.out.printf("Bill item added: %s => %s [ cost: %d ]%n", caller, callee, callCostInPence);
  }

  @Override
  public boolean shouldChargePeak(LocalTime start, LocalTime end) {
    return start.isAfter(peakStart) && end.isBefore(peakEnd);
  }
}
