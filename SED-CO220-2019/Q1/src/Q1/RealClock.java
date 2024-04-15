package Q1;

import java.time.LocalTime;

public class RealClock implements Clock {

  @Override
  public LocalTime getTime() {
    return LocalTime.now();
  }
}
