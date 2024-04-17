package ic.doc;

import com.londonstockexchange.StockMarketDataFeed;
import com.londonstockexchange.StockPrice;
import com.londonstockexchange.TickerSymbol;

public class LSEAdapter implements StockMarket {
  @Override
  public StockPrice getStockPrice(MySymbol stock) {
    TickerSymbol lsgStock = MySymbol.toTickerSymbol(stock);
    return StockMarketDataFeed.getInstance().currentPriceFor(lsgStock);
  }
}
