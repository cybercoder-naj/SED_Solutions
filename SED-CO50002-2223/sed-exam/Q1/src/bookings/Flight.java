package bookings;

import flights.*;
import java.time.LocalDate;
import java.util.List;

public abstract class Flight {
  private final FlightNumber flightNumber;
  private final LocalDate date;
  private final Airport origin;
  private final Airport destination;
  private final ServiceLevel serviceLevel;
  private final int pencePerMile;
  private final int standardFeePence;
  private final FlightInfo flightInfo;

  protected Flight(
      FlightNumber flightNumber,
      LocalDate date,
      Airport origin,
      Airport destination,
      ServiceLevel serviceLevel,
      int pencePerMile,
      int standardFeePence,
      FlightInfo flightInfo) {
    this.flightNumber = flightNumber;
    this.date = date;
    this.origin = origin;
    this.destination = destination;
    this.serviceLevel = serviceLevel;
    this.pencePerMile = pencePerMile;
    this.standardFeePence = standardFeePence;
    this.flightInfo = flightInfo;
  }

  protected abstract List<Seat> eligibleSeats(
      List<Seat> availableSeats, FrequentFlyerStatus status);

  public final List<Seat> seatingOptions(FrequentFlyerStatus status) {
    if (date.isBefore(LocalDate.now())) {
      throw new BookingException("Flight is in the past");
    }
    List<Seat> availableSeats = flightInfo.getAvailableSeats(flightNumber, date);
    return eligibleSeats(availableSeats, status);
  }

  public final int calculateFare() {
    return origin.distanceTo(destination) * pencePerMile + standardFeePence;
  }

  @Override
  public String toString() {
    return "Flight "
        + flightNumber
        + " ("
        + date
        + ") from "
        + origin
        + " to "
        + destination
        + " ("
        + serviceLevel
        + ")";
  }
}
