package edu.hw3;

import edu.hw3.task6.Stock;
import edu.hw3.task6.StockManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task6Test {

    private StockManager stockManager;

    @BeforeEach
    void initializeStockManager() {
        stockManager = new StockManager();
        stockManager.add(new Stock("SIBN", 799.9));
        stockManager.add(new Stock("ROSN", 594.6));
        stockManager.add(new Stock("LKOH", 7_318));
    }

    @Test
    @DisplayName("The method returns the most valuable stock")
    void getMostValuableStock() {
        // given
        Stock expectedMostValuableStock = new Stock("LKOH", 7_318);

        // when
        Stock mostValuableStock = stockManager.mostValuableStock();

        // then
        assertThat(mostValuableStock)
            .isEqualTo(expectedMostValuableStock);
    }

    @Test
    @DisplayName("The method remove stock from the stock manager")
    void removeStock() {
        // given
        Stock stockToRemove = new Stock("SIBN", 799.9);
        int exceptedStockCountInStockManager = 2;

        // when
        stockManager.remove(stockToRemove);

        // then
        assertThat(exceptedStockCountInStockManager)
            .isEqualTo(stockManager.getStocks().size());
    }

    @Test
    @DisplayName("The method add stock to the stock manager")
    void addStock() {
        // given
        Stock stockToAdd = new Stock("GMKN", 17_506);
        int exceptedStockCountInStockManager = 4;

        // when
        stockManager.add(stockToAdd);

        // then
        assertThat(exceptedStockCountInStockManager)
            .isEqualTo(stockManager.getStocks().size());
    }
}
