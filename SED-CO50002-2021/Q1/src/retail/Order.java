package retail;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public abstract class Order {
  private final List<Product> items;
  private final CreditCardDetails creditCardDetails;
  private final Address billingAddress;
  private final Address shippingAddress;
  private final Courier courier;

  protected Order(List<Product> items, CreditCardDetails creditCardDetails, Address billingAddress, Address shippingAddress, Courier courier) {
    this.items = items;
    this.creditCardDetails = creditCardDetails;
    this.billingAddress = billingAddress;
    this.shippingAddress = shippingAddress;
    this.courier = courier;
  }

  public final void process(Biller biller) {
    BigDecimal total = getTotalPrice();
    biller.charge(round(total), creditCardDetails, billingAddress);
    dispatchOrder();
  }

  private BigDecimal round(BigDecimal amount) {
    return amount.setScale(2, RoundingMode.CEILING);
  }

  protected abstract BigDecimal getTotalPrice();

  protected abstract void dispatchOrder();

  public List<Product> getItems() {
    return items;
  }

  public Courier getCourier() {
    return courier;
  }

  public Address getShippingAddress() {
    return shippingAddress;
  }
}
