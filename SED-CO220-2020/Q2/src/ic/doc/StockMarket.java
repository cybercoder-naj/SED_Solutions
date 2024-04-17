package ic.doc;

import com.londonstockexchange.StockPrice;

public interface StockMarket {
  StockPrice getStockPrice(MySymbol stock);
}
