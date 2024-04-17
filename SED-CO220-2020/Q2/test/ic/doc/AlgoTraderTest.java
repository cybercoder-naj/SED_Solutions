package ic.doc;

import com.londonstockexchange.StockPrice;
import com.londonstockexchange.TickerSymbol;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

public class AlgoTraderTest {

  @Rule
  public JUnitRuleMockery context = new JUnitRuleMockery();
  private final Broker broker = context.mock(Broker.class);
  private final StockMarket market = context.mock(StockMarket.class);
  private AlgoTrader trader = new AlgoTrader(broker, market);

  @Test
  public void brokerBuysRisingStock() {
    int google = (int) (Math.random() * 1000);
    int microsoft = (int) (Math.random() * 1000);
    int apple = (int) (Math.random() * 1000);

    context.checking(new Expectations() {{
      exactly(1).of(market).getStockPrice(MySymbol.GOOG);
      will(returnValue(new StockPrice(TickerSymbol.GOOG, google)));

      exactly(1).of(market).getStockPrice(MySymbol.MSFT);
      will(returnValue(new StockPrice(TickerSymbol.MSFT, microsoft)));

      exactly(1).of(market).getStockPrice(MySymbol.APPL);
      will(returnValue(new StockPrice(TickerSymbol.APPL, apple)));
    }});

    trader.trade();

    context.checking(new Expectations() {{
      exactly(1).of(market).getStockPrice(MySymbol.GOOG);
      will(returnValue(new StockPrice(TickerSymbol.GOOG, google + 100)));

      exactly(1).of(market).getStockPrice(MySymbol.MSFT);
      will(returnValue(new StockPrice(TickerSymbol.MSFT, microsoft + 50)));

      exactly(1).of(market).getStockPrice(MySymbol.APPL);
      will(returnValue(new StockPrice(TickerSymbol.APPL, apple + 75)));

      exactly(1).of(broker).buy("GOOG");
      exactly(1).of(broker).buy("MSFT");
      exactly(1).of(broker).buy("APPL");
    }});

    trader.trade();
  }

  @Test
  public void brokerSellsFallingStock() {
    int google = (int) (Math.random() * 1000);
    int microsoft = (int) (Math.random() * 1000);
    int apple = (int) (Math.random() * 1000);

    context.checking(new Expectations() {{
      exactly(1).of(market).getStockPrice(MySymbol.GOOG);
      will(returnValue(new StockPrice(TickerSymbol.GOOG, google)));

      exactly(1).of(market).getStockPrice(MySymbol.MSFT);
      will(returnValue(new StockPrice(TickerSymbol.MSFT, microsoft)));

      exactly(1).of(market).getStockPrice(MySymbol.APPL);
      will(returnValue(new StockPrice(TickerSymbol.APPL, apple)));
    }});

    trader.trade();

    context.checking(new Expectations() {{
      exactly(1).of(market).getStockPrice(MySymbol.GOOG);
      will(returnValue(new StockPrice(TickerSymbol.GOOG, google - 100)));

      exactly(1).of(market).getStockPrice(MySymbol.MSFT);
      will(returnValue(new StockPrice(TickerSymbol.MSFT, microsoft - 50)));

      exactly(1).of(market).getStockPrice(MySymbol.APPL);
      will(returnValue(new StockPrice(TickerSymbol.APPL, apple - 75)));

      exactly(1).of(broker).sell("GOOG");
      exactly(1).of(broker).sell("MSFT");
      exactly(1).of(broker).sell("APPL");
    }});

    trader.trade();
  }
}