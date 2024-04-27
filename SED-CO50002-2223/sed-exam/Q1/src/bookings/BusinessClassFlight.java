package bookings;

import flights.*;
import java.time.LocalDate;
import java.util.List;

public class BusinessClassFlight extends Flight {

  private static final int PENCE_PER_MILE = 35;
  private static final int STANDARD_FEE_PENCE = 8000;

  public BusinessClassFlight(
      FlightNumber flightNumber,
      LocalDate date,
      Airport origin,
      Airport destination,
      FlightInfo flightInfo) {
    super(
        flightNumber,
        date,
        origin,
        destination,
        ServiceLevel.BUSINESS,
        PENCE_PER_MILE,
        STANDARD_FEE_PENCE,
        flightInfo);
  }

  @Override
  protected List<Seat> eligibleSeats(List<Seat> availableSeats, FrequentFlyerStatus status) {
    if (status == FrequentFlyerStatus.ELITE) {
      // Elite status customers can choose any seat, even in first class.
      return availableSeats;
    }
    return availableSeats.stream().filter(s -> s.cabin() == ServiceLevel.BUSINESS).toList();
  }
}
