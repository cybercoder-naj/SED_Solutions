package Q1;

import static java.time.temporal.ChronoUnit.MINUTES;

import java.time.LocalTime;

/**
 * a)
 * i) The first source of Coupling is the BillingSystem.
 * ii) Another source of coupling is LocalTime peak and off-peak logic.
 * iii) The charge() function is tightly coupled with it, so any changes or replacement
 * to BillingSystem requires changes to this class.
 *
 * We may want to provide our own implementation. It helps in testing the class too. Any changes to the clocking
 * logic will result in drastic and costly changes in the entire class.
 */
public class PhoneCall {

  private static final long PEAK_RATE = 25;
  private static final long OFF_PEAK_RATE = 10;

  private final String caller;
  private final String callee;

  private LocalTime startTime;
  private LocalTime endTime;
  private final BillingSystem billing;
  private final Clock clock;

  public PhoneCall(String caller, String callee, BillingSystem billing, Clock clock) {
    this.caller = caller;
    this.callee = callee;
    this.billing = billing;
    this.clock = clock;
  }

  public void start() {
    startTime = clock.getTime();
  }

  public void end() {
    endTime = clock.getTime();
  }

  public void charge() {
    billing.addBillItem(caller, callee, priceInPence());
  }

  private long priceInPence() {
    if (billing.shouldChargePeak(startTime, endTime)) {
      return duration() * PEAK_RATE;
    } else {
      return duration() * OFF_PEAK_RATE;
    }
  }

  private long duration() {
    return MINUTES.between(startTime, endTime) + 1;
  }
}
