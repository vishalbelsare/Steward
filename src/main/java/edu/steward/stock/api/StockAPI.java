package edu.steward.stock.api;

import java.util.List;

import edu.steward.stock.Fundamentals.DailyChange;
import edu.steward.stock.Fundamentals.Fundamental;
import edu.steward.stock.Fundamentals.Price;

/**
 * Created by mrobins on 4/17/17.
 */
public interface StockAPI {

  enum TIMESERIES {
    ONE_DAY, FIVE_DAY, ONE_MONTH, SIX_MONTH, ONE_YEAR, TWO_YEAR, FIVE_YEAR, TEN_YEAR
  }

  /**
   * Returns the data for a given stock.
   *
   * @param ticker
   *          The unique ticker symbol for a stock.
   * @param timeSeries
   *          The time series for data collection.
   * @return A list of prices for the stock within the time frame.
   */
  List<Price> getStockPrices(String ticker, TIMESERIES timeSeries);

  List<Fundamental> getStockFundamentals(String ticker);

  Price getCurrPrice(String ticker);

  Price getPrice(String ticker, int time);

  String getCompanyName(String ticker);

  DailyChange getDailyChange(String ticker);
  //
  // List<Fundamental> getGraphData(String ticker, TIMESERIES timeseries);

}
