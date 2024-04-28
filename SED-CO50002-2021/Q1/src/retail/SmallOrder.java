package retail;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class SmallOrder extends Order {

  private static final BigDecimal GIFT_WRAP_CHARGE = new BigDecimal(3);

  private final boolean giftWrap;

  public SmallOrder(
      List<Product> items,
      CreditCardDetails creditCardDetails,
      Address billingAddress,
      Address shippingAddress,
      Courier courier,
      boolean giftWrap) {
    super(Collections.unmodifiableList(items), creditCardDetails, billingAddress, shippingAddress, courier);
    this.giftWrap = giftWrap;
  }

  @Override
  public BigDecimal getTotalPrice() {
    BigDecimal total = new BigDecimal(0);

    for (Product item : getItems()) {
      total = total.add(item.unitPrice());
    }

    total = total.add(getCourier().deliveryCharge());

    if (giftWrap) {
      total = total.add(GIFT_WRAP_CHARGE);
    }
    return total;
  }

  @Override
  protected void dispatchOrder() {
    if (giftWrap) {
      getCourier().send(new GiftBox(getItems()), getShippingAddress());
    } else {
      getCourier().send(new Parcel(getItems()), getShippingAddress());
    }
  }
}
