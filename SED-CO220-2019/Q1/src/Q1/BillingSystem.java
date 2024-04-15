package Q1;

import java.time.LocalTime;

public interface BillingSystem {
  void addBillItem(String caller, String callee, long callCostInPence);
  boolean shouldChargePeak(LocalTime start, LocalTime end);
}
