package edu.steward.stock.api;

import edu.steward.stock.Fundamentals.Fundamental;
import edu.steward.stock.Fundamentals.Price;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by mrobins on 4/17/17.
 */
public class MockStockAPI implements StockAPI {

  //TODO: Make a mock stock api


  @Override
  public List<Price> getStockPrices(String ticker, TIMESERIES timeSeries) {
    return null;
  }

  @Override
  public List<Fundamental> getStockFundamentals(String ticker, TIMESERIES timeseries) {
    return null;
  }

  @Override
  public List<Fundamental> getGraphData(String ticker, TIMESERIES timeseries) {
    return null;
  }
}