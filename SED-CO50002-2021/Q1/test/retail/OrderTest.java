package retail;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class OrderTest {
  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();

  private final Biller biller = context.mock(Biller.class, "biller");
  private final Courier courier = context.mock(Courier.class, "courier");
  private final CreditCardDetails creditCardDetails = new CreditCardDetails("1234123412341234", 111);
  private final Address billingAddress = new Address("180 Queens Gate, London, SW7 2AZ");

  @Test
  public void smallOrderPrice() {
    context.checking(new Expectations() {{
      allowing(courier).deliveryCharge();
      will(returnValue(new BigDecimal(3)));

      oneOf(biller).charge(new BigDecimal(13).setScale(2, RoundingMode.CEILING), creditCardDetails, billingAddress);
      allowing(courier);
    }});

    SmallOrder smallOrder = (SmallOrder) new OrderBuilder(
        creditCardDetails,
        billingAddress,
        courier)
        .withProduct(new Product("One Book", new BigDecimal("10.00")))
        .build();

    smallOrder.process(biller);
  }

  @Test
  public void bulkOrderPrice() {
    context.checking(new Expectations() {{
      allowing(courier).deliveryCharge();
      will(returnValue(new BigDecimal(3)));

      oneOf(biller).charge(new BigDecimal(40).setScale(2, RoundingMode.CEILING), creditCardDetails, billingAddress);
      allowing(courier);
    }});

    BulkOrder bulkOrder = (BulkOrder) new OrderBuilder(
        creditCardDetails,
        billingAddress,
        courier)
        .withProduct(new Product("One Book", new BigDecimal("10.00")))
        .withProduct(new Product("One Book", new BigDecimal("10.00")))
        .withProduct(new Product("One Book", new BigDecimal("10.00")))
        .withProduct(new Product("One Book", new BigDecimal("10.00")))
        .build();

    bulkOrder.process(biller);
  }
}