package bookings;

import flights.*;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

public class BusinessClassFlightTest {

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private final FlightInfo flightInfo = context.mock(FlightInfo.class);
  private final List<Seat> seats = List.of(
          new Seat("A1", ServiceLevel.FIRST),
          new Seat("A2", ServiceLevel.FIRST),
          new Seat("A3", ServiceLevel.FIRST),
          new Seat("B1", ServiceLevel.BUSINESS),
          new Seat("B2", ServiceLevel.BUSINESS),
          new Seat("B3", ServiceLevel.BUSINESS),
          new Seat("B4", ServiceLevel.BUSINESS),
          new Seat("C1", ServiceLevel.ECONOMY),
          new Seat("C2", ServiceLevel.ECONOMY),
          new Seat("C3", ServiceLevel.ECONOMY),
          new Seat("C4", ServiceLevel.ECONOMY)
  );

  private final LocalDate future = LocalDate.now().plusDays(5);
  private final FlightNumber flightNumber = FlightNumber.of("EK009");
  private final BusinessClassFlight validFlight = new BusinessClassFlight(flightNumber, future, Airport.LHR, Airport.JFK, flightInfo);

  @Test(expected = BookingException.class)
  public void bookingFlightBeforeDateErrors() {
    LocalDate past = LocalDate.now().minusDays(5);
    BusinessClassFlight flight = new BusinessClassFlight(FlightNumber.of("EK009"), past, Airport.LHR, Airport.JFK, flightInfo);

    flight.seatingOptions(FrequentFlyerStatus.BASIC);
  }

  @Test
  public void bookingFlightAsEliteGivesAllSeats() {
    context.checking(new Expectations(){{
      oneOf(flightInfo).getAvailableSeats(flightNumber, future);
      will(returnValue(seats));
    }});

    List<Seat> options = validFlight.seatingOptions(FrequentFlyerStatus.ELITE);
    Assert.assertArrayEquals(options.toArray(), seats.toArray());
  }

  @Test
  public void bookingFlightAsBasicGivesSomeSeats() {
    context.checking(new Expectations(){{
      oneOf(flightInfo).getAvailableSeats(flightNumber, future);
      will(returnValue(seats));
    }});

    List<Seat> options = validFlight.seatingOptions(FrequentFlyerStatus.SILVER);
    List<Seat> onlyBusiness = seats.stream().filter(s -> s.cabin() == ServiceLevel.BUSINESS).toList();

    Assert.assertArrayEquals(options.toArray(), onlyBusiness.toArray());
  }
}
