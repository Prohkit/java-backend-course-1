package edu.hw3.task6;

import java.util.PriorityQueue;

public class StockManager implements StockMarket {
    private final PriorityQueue<Stock> stocks;

    public StockManager() {
        stocks = new PriorityQueue<>();
    }

    public PriorityQueue<Stock> getStocks() {
        return stocks;
    }

    @Override
    public void add(Stock stock) {
        stocks.add(stock);
    }

    @Override
    public void remove(Stock stock) {
        stocks.remove(stock);
    }

    @Override
    public Stock mostValuableStock() {
        return stocks.peek();
    }
}
