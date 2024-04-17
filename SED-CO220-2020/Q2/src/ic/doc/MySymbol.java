package ic.doc;

import com.londonstockexchange.TickerSymbol;

public enum MySymbol {
  AMZN,
  APPL,
  FB,
  GOOG,
  MSFT,
  NFLX;

  public static TickerSymbol toTickerSymbol(MySymbol stock) {
    switch (stock) {
      case AMZN:
        return TickerSymbol.AMZN;
      case APPL:
        return TickerSymbol.APPL;
      case FB:
        return TickerSymbol.FB;
      case GOOG:
        return TickerSymbol.GOOG;
      case MSFT:
        return TickerSymbol.MSFT;
      case NFLX:
        return TickerSymbol.NFLX;
      default:
        throw new RuntimeException("Impossible case");
    }
  }

  public static MySymbol fromTickerSymbol(TickerSymbol stock) {
    switch (stock) {
      case AMZN:
        return AMZN;
      case APPL:
        return APPL;
      case FB:
        return FB;
      case GOOG:
        return GOOG;
      case MSFT:
        return MSFT;
      case NFLX:
        return NFLX;
      default:
        throw new RuntimeException("Impossible case");
    }
  }
}
