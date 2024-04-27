package bookings;

import availability.SeatManager;
import flights.FlightNumber;
import flights.Seat;
import java.time.LocalDate;
import java.util.List;

public class FlightInfoImpl implements FlightInfo {
  @Override
  public List<Seat> getAvailableSeats(FlightNumber flightNumber, LocalDate date) {
    return SeatManager.getInstance().getAvailableSeats(flightNumber, date);
  }
}
