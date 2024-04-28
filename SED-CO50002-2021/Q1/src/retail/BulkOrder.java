package retail;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class BulkOrder extends Order {

  private final BigDecimal discount;

  public BulkOrder(
      List<Product> items,
      CreditCardDetails creditCardDetails,
      Address billingAddress,
      Address shippingAddress,
      Courier courier,
      BigDecimal discount) {
    super(Collections.unmodifiableList(items), creditCardDetails, billingAddress, shippingAddress, courier);
    this.discount = discount;
  }

  @Override
  protected BigDecimal getTotalPrice() {
    BigDecimal total = new BigDecimal(0);
    for (Product item : getItems()) {
      total = total.add(item.unitPrice());
    }

    if (getItems().size() > 10) {
      total = total.multiply(BigDecimal.valueOf(0.8));
    } else if (getItems().size() > 5) {
      total = total.multiply(BigDecimal.valueOf(0.9));
    }

    total = total.subtract(discount);
    return total;
  }

  @Override
  protected void dispatchOrder() {
    getCourier().send(new Parcel(getItems()), getShippingAddress());
  }
}
