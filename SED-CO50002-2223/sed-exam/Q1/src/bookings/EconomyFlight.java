package bookings;

import flights.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public class EconomyFlight extends Flight {

  private static final int PENCE_PER_MILE = 15;
  private static final int STANDARD_FEE_PENCE = 4000;

  public EconomyFlight(
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
        ServiceLevel.ECONOMY,
        PENCE_PER_MILE,
        STANDARD_FEE_PENCE,
        flightInfo);
  }

  @Override
  protected List<Seat> eligibleSeats(List<Seat> availableSeats, FrequentFlyerStatus status) {
    List<Seat> allAvailableEconomySeats =
        availableSeats.stream().filter(s -> s.cabin() == ServiceLevel.ECONOMY).toList();
    return switch (status) {
      case BASIC -> pickOneAtRandomFrom(allAvailableEconomySeats);
      case SILVER, ELITE -> allAvailableEconomySeats;
    };
  }

  private List<Seat> pickOneAtRandomFrom(List<Seat> allAvailableEconomySeats) {
    int randomPositionInList = (int) (Math.random() * (allAvailableEconomySeats.size() - 1));
    return Collections.singletonList(allAvailableEconomySeats.get(randomPositionInList));
  }
}
