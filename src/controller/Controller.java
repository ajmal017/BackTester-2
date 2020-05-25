package controller;

import Analyser.TradeAnalyser;
import candlegenerator.CandleGenerator;
import struct.Candle;
import struct.DetailedTrade;
import struct.Signal;
import strategy.Strategy;
import struct.Trade;
import trader.Trader;

import java.text.SimpleDateFormat;

public class Controller {
    CandleGenerator candleGenerator;
    Strategy strategy;
    Trader trader;
    TradeAnalyser tradeAnalyser;
    Candle prevCandle;

    String pattern = "yyyy-MM-dd HH:mm";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

    public Controller(CandleGenerator candleGenerator, Strategy strategy, Trader trader) {
        this.candleGenerator = candleGenerator;
        this.strategy = strategy;
        this.trader = trader;
    }

    public void execute() {
        while(candleGenerator.hasNext()) {
            Candle candle = candleGenerator.next();
            strategy.addCandle(candle);
            Signal signal = strategy.getSignal();
            trader.sendSignal(signal, candle);
            System.out.println(simpleDateFormat.format(candle.getDate()) + " -- " + signal.toString());
            prevCandle = candle;
        }
        trader.endTradeIfOpen(prevCandle);
        for(Trade t : trader.getTradeList()) {
            printTrade(t);
        }

        tradeAnalyser = new TradeAnalyser(trader.getTradeList());
        tradeAnalyser.analyse();

        for(DetailedTrade dt : tradeAnalyser.getDetailedTradeList()) {
            printDetailedTrade(dt);
        }
        System.out.println(tradeAnalyser.getDetailedTradeList().size());
    }

    public void shutdown() {
        candleGenerator.shutdown();
    }

    private void printTrade(Trade t) {
        System.out.println(t.getType() +  "," +simpleDateFormat.format(t.getStartDate()) + "," + simpleDateFormat.format(t.getEndDate()) +"," + t.getStartPrice() + "," + t.getEndPrice()  );
    }

    private void printDetailedTrade(DetailedTrade t) {
        System.out.println(t.getProfit() + "," + t.getNetAssetValue());
    }
}
