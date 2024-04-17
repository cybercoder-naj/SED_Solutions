package ic.doc;

import com.londonstockexchange.StockPrice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/*
 * a) Singleton pattern is used here that makes unit testing difficult.
 *       StockPrice price = StockMarketDataFeed.getInstance().currentPriceFor(stock);
 *    this is the location of the singleton used.
 *
 * b) i) We would use adapters to achieve this.
 *   ii) Hexagonal (or Adapters-and-ports) architecture.
 */
public class AlgoTrader {

  private final List<MySymbol> stocksToWatch =
      List.of(MySymbol.GOOG, MySymbol.MSFT, MySymbol.APPL);

  private final Map<MySymbol, Integer> lastPrices = new HashMap<>();
  private final Broker broker;
  private final StockMarket market;

  public AlgoTrader(Broker broker, StockMarket market) {
    this.broker = broker;
    this.market = market;
  }

  public void trade() {

    for (MySymbol stock : stocksToWatch) {

      StockPrice price = market.getStockPrice(stock);

      if (isRising(stock, price)) {
        broker.buy(String.valueOf(stock));
      }

      if (isFalling(stock, price)) {
        broker.sell(String.valueOf(stock));
      }

      lastPrices.put(stock, price.inPennies());
    }
  }

  private boolean isFalling(MySymbol stock, StockPrice price) {
    int lastPrice = lastPrices.getOrDefault(stock, 0);
    logPrices(stock, price, lastPrice);
    return price.inPennies() < lastPrice;
  }

  private boolean isRising(MySymbol stock, StockPrice price) {
    int lastPrice = lastPrices.getOrDefault(stock, Integer.MAX_VALUE);
    logPrices(stock, price, lastPrice);
    return price.inPennies() > lastPrice;
  }

  public static void main(String[] args) {
    Broker broker = new SimpleBroker();
    LSEAdapter market = new LSEAdapter();
    new AlgoTrader(broker, market).start();
  }

  // code below here is not important for the exam

  private void logPrices(MySymbol stock, StockPrice price, int lastPrice) {
    System.out.printf("%s used to be %s, now %s %n", stock, lastPrice, price.inPennies());
  }

  private void start() {

    // run trade() every minute

    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    executorService.scheduleAtFixedRate(this::trade, 0, 60, TimeUnit.SECONDS);
  }
}
