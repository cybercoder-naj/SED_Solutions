package retail;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderBuilder {
  private List<Product> items = new ArrayList<>();
  private CreditCardDetails creditCardDetails;
  private Address billingAddress;
  private Address shippingAddress;
  private Courier courier;
  private boolean giftWrap;
  private BigDecimal discount;

  public OrderBuilder(CreditCardDetails creditCardDetails, Address billingAddress, Courier courier) {
    this.creditCardDetails = creditCardDetails;
    this.billingAddress = billingAddress;
    this.courier = courier;
  }

  public OrderBuilder withCreditCardDetails(CreditCardDetails creditCardDetails) {
    this.creditCardDetails = creditCardDetails;
    return this;
  }

  public OrderBuilder withBillingAddress(Address billingAddress) {
    this.billingAddress = billingAddress;
    return this;
  }

  public OrderBuilder withShippingAddress(Address shippingAddress) {
    this.shippingAddress = shippingAddress;
    return this;
  }

  public OrderBuilder withCourier(Courier courier) {
    this.courier = courier;
    return this;
  }

  public OrderBuilder withProduct(Product product) {
    this.items.add(product);
    return this;
  }

  public OrderBuilder withDiscount(BigDecimal discount) {
    this.discount = discount;
    return this;
  }

  public OrderBuilder withGiftWrap(boolean giftWrap) {
    this.giftWrap = giftWrap;
    return this;
  }

  public Order build() {
    Address shippingAddress = this.shippingAddress == null ? billingAddress : this.shippingAddress;
    if (this.items.size() > 3) {
      return new BulkOrder(items, creditCardDetails, billingAddress, shippingAddress, courier, discount);
    } else {
      return new SmallOrder(items, creditCardDetails, billingAddress, shippingAddress, courier, giftWrap);
    }
  }
}