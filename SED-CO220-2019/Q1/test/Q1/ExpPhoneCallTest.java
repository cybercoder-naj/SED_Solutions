package Q1;

import org.junit.Test;

import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * c) To change part b), I changed the code to allow injection of objects by
 * dependency inversion. In the tests, I injected an extension of billing and clock classes.
 * This way, the test environment has control over the time and decisions the class
 * in test mode (i.e. PhoneCall) behaves as expected.
 *
 * I introduced methods such as advanceSeconds, and fields like cost and hasBilled
 * to query and to set parameters on which the "Fakes" should respond.
 */
public class ExpPhoneCallTest {
  FakeBilling billing = new FakeBilling();
  FakeClock fakeClock = new FakeClock();
  PhoneCall call = new PhoneCall("+447770123456",
      "+4479341554433",
      billing,
      fakeClock);

  @Test
  public void chargingNonPeakFareWithinNonPeak()  {
    int duration = 2 * 60 + 30; // 1.5 minutes

    fakeClock.time = LocalTime.of(1,0); // 01:00 am
    call.start();
    fakeClock.advanceSeconds(duration);
    call.end();

    call.charge();
    assertTrue(billing.hasBilled);
    assertEquals(30L, billing.cost);
  }

  @Test
  public void chargingPeakFareWithinPeak() {
    int duration = 2 * 60 + 30; // 1.5 minutes

    fakeClock.time = LocalTime.of(13,0); // 01:00 pm
    call.start();
    fakeClock.advanceSeconds(duration);
    call.end();
;
    call.charge();
    assertTrue(billing.hasBilled);
    assertEquals(75L, billing.cost);
  }

  @Test
  public void chargingPeakFareCrossingPeakTime() {
    int duration = 5 * 60 + 30; // 5.5 minutes

    fakeClock.time = LocalTime.of(17,55); // 05:55 pm, peak time
    call.start();
    fakeClock.advanceSeconds(duration);
    call.end(); // Now 6:00:30pm, off-peak

    call.charge();
    assertTrue(billing.hasBilled);
    assertEquals(150L, billing.cost); // peak fare
  }

  @Test
  public void chargingPeakFareEnteringPeakTime() {
    int duration = 5 * 60 + 30; // 5.5 minutes

    fakeClock.time = LocalTime.of(8,55); // 08:55 pm, off-peak time
    call.start();
    fakeClock.advanceSeconds(duration);
    call.end(); // Now 9:00:30pm, peak time

    call.charge();
    assertTrue(billing.hasBilled);
    assertEquals(150L, billing.cost); // peak fare
  }

  private static class FakeBilling extends ExpensiveBilling {

    public long cost;
    public boolean hasBilled = false;

    @Override
    public void addBillItem(String caller, String callee, long callCostInPence) {
      super.addBillItem(caller, callee, callCostInPence);
      cost = callCostInPence;
      hasBilled = true;
    }
  }

  private static class FakeClock implements Clock {

    public LocalTime time;

    public FakeClock(LocalTime time) {
      this.time = time;
    }

    public FakeClock() {
      this(LocalTime.now());
    }

    public void advanceSeconds(long seconds) {
      time = time.plusSeconds(seconds);
    }

    @Override
    public LocalTime getTime() {
      return time;
    }
  }
}