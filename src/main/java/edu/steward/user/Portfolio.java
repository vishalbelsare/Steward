package edu.steward.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableList;

import com.google.common.collect.ImmutableMap;
import edu.steward.pools.Pool;
import edu.steward.sql.DatabaseApi;
import edu.steward.sql.GainsOverTime;
import edu.steward.sql.LeaderBoard;
import edu.steward.sql.Update;
import edu.steward.stock.Fundamentals.Gains;

public class Portfolio {
  private String name;
  private String portfolioId;
  private Map<String, Integer> holdings;
  private Double balance = 0.0;
  private Pool pool;
  private String userId;
  // TODO: Add in balance to the below methods

  public Portfolio(String name, String portfolioId) {
    this.name = name;
    this.portfolioId = portfolioId;
    // TODO: Load in holdings from db
    loadInfo();
  }

  public void setName(String name) {
    this.name = name;
  }

  public void loadInfo() {
    holdings = DatabaseApi.getStocksFromPortfolio(portfolioId);
    balance = DatabaseApi.getBalanceFromPortfolio(portfolioId);
    getPool();
  }

  public Map<String, Integer> getHoldings() {
    if (holdings == null) {
      loadInfo();
    }
    return holdings;
  }

  public boolean buyStock(String ticker, int shares, int time, double price) {
    // TODO
    double cost = price * shares;
    if (cost > balance) {
      return false;
    } else {
      Integer currShares = holdings.get(ticker);
      int newShares;
      if (currShares == null) {
        newShares = shares;
      } else {
        newShares = shares + currShares;
      }

      holdings.replace(ticker, newShares);
      balance -= cost;

      return DatabaseApi.stockTransaction(portfolioId, ticker, shares, time,
          price);
    }
  }

  public boolean sellStock(String ticker, int shares, int time, double price) {
    // TODO fix this shit matt
    double cost = price * shares;
    Integer currShares = holdings.get(ticker);
    if (currShares == null) {
      return false;
    } else if (currShares < shares) {
      return false;
    } else {
      holdings.replace(ticker, currShares - shares);
      balance += cost;
      return DatabaseApi.stockTransaction(portfolioId, ticker,
          // there is a negative sign here because it is SELL
          -shares, time, price);
    }
  }

  public String getName() {
    return name;
  }

  public List<List<Object>> getUnrealized() {
    List<Gains> gains = GainsOverTime.getGainsPortfolioGraph(portfolioId);
    List<List<Object>> ret = new ArrayList<>();
    for (Gains gain : gains) {
      ret.add(ImmutableList.of(gain.getTime(), gain.getValue() * 100));
    }
    return ret;
  }

  public List<List<Object>> getHistoricalWorth() {
    List<Gains> historicalWorths =
            GainsOverTime.getGainsGameGraph(portfolioId, pool.getStart(), pool.getBal());
    List<List<Object>> ret = new ArrayList<>();
    for (Gains gain : historicalWorths) {
      ret.add(ImmutableList.of(gain.getTime(), gain.getValue()));
    }
    return ret;
  }

  public Double getBalance() {
    balance = DatabaseApi.getBalanceFromPortfolio(portfolioId);
    return balance;
  }

  public Double getNetWorth() {
    return GainsOverTime.getCurrentNetWorth(portfolioId);
  }

  public List<Gains> getGainsOverTime() {
    return GainsOverTime.getGainsGameGraph(
            portfolioId,
            pool.getStart(),
            pool.getBal()
    );
  }

  public Pool getPool() {
    if (pool == null) {
      pool = DatabaseApi.getPoolFromPortfolio(portfolioId);
    }
    return pool;
  }

  public String getPortfolioId() {
    return portfolioId;
  }

  public boolean joinPool(String poolId) {
    return DatabaseApi.addPortToPool(portfolioId, poolId);
  }

  public void setPool(Pool pool) {
    this.pool = pool;
  }

  public void setUser(String userId) {
    this.userId = userId;
  }

  public Map<String, String> getUser() {
    return DatabaseApi
        .getUserInfo(userId);
  }

  public boolean isDead() {
    return LeaderBoard.containsPortfolio(this.portfolioId);
  }

}
