package retail;

import java.math.BigDecimal;

public interface Biller {
  void charge(BigDecimal amount, CreditCardDetails account, Address billingAddress);
}
