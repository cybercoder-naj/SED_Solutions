package bookings;

import flights.FlightNumber;
import flights.Seat;
import java.time.LocalDate;
import java.util.List;

public interface FlightInfo {
  List<Seat> getAvailableSeats(FlightNumber flightNumber, LocalDate date);
}
