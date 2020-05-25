import candlegenerator.CandleGenerator;
import candlegenerator.CandleGeneratorFromFile;
import controller.Controller;
import data.FileDataCleaner;
import strategy.MovingAverageStrategy;
import strategy.RelativeStrengthIndexStrategy;
import strategy.Strategy;
import strategy.SuperTrendStrategy;
import struct.Trade;
import trader.SimpleTrader;
import trader.StopLossTrader;
import trader.Trader;

import java.io.IOException;

public class MainClass {
    public static void main(String[] args) throws IOException {
        String inputFile = "/Users/saiaditya/Desktop/INDUSINDBK-EQ.csv";
        String outputFile = "/Users/saiaditya/Desktop/INDUSINDBK-EQ-clean.csv";
        FileDataCleaner cleaner = new FileDataCleaner(inputFile, outputFile);

        String filePath = outputFile;
        int period = 25;
        int numTimes = 2;

        CandleGenerator candleGenerator = new CandleGeneratorFromFile(filePath);
        //Strategy strategy = new MovingAverageStrategy(period, numTimes);
        //Strategy strategy = new RelativeStrengthIndexStrategy(period, 10);
        //Trader trader = new SimpleTrader();

        Strategy strategy = new SuperTrendStrategy(10,10,7,2,3,3);
        Trader trader = new StopLossTrader(1, 2.5);

        Controller controller = new Controller(candleGenerator, strategy, trader);
        controller.execute();
        controller.shutdown();
    }
}
