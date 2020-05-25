package trader;

import struct.Candle;
import struct.Signal;
import struct.Trade;
import struct.TradeType;

import java.util.ArrayList;
import java.util.List;

public class StopLossTrader implements Trader {

    private List<Trade> tradeList = new ArrayList<>();
    private Trade latestTrade = null;
    private boolean tradeEnded = false;

    public double stopLoss, takeProfit;

    public StopLossTrader(double stopLoss, double takeProfit) {
        this.stopLoss = stopLoss; this.takeProfit = takeProfit;
    }

    private void checkStatus(Candle candle) {
        tradeEnded = false;
        if(isTradeOpen()) {
            double percentage = getPercentage(candle);
            if(percentage > takeProfit) {
                endTradeIfOpenAtTakeProfit(candle);
                tradeEnded = true;
            }
            if(percentage * -1 > stopLoss) {
                endTradeIfOpenAtStopLoss(candle);
                tradeEnded = true;
            }
        }
    }

    private void endTradeIfOpenAtStopLoss(Candle candle) {
        if(isTradeOpen()) {
            double startPrice = latestTrade.getStartPrice();
            double endPrice;
            if(latestTrade.getType() == TradeType.LONG) {
                endPrice = startPrice * ((100 - stopLoss)/100);
            }
            else {
                endPrice = startPrice * ((100 - stopLoss)/100);
            }
            latestTrade.endTrade(candle.getDate(), endPrice);
            tradeList.add(latestTrade);
            latestTrade = null;
        }
    }

    private void endTradeIfOpenAtTakeProfit(Candle candle) {
        if(isTradeOpen()) {
            double startPrice = latestTrade.getStartPrice();
            double endPrice;
            if(latestTrade.getType() == TradeType.LONG) {
                endPrice = startPrice * ((100 + takeProfit)/100);
            }
            else {
                endPrice = startPrice * ((100 + takeProfit)/100);
            }
            latestTrade.endTrade(candle.getDate(), endPrice);
            tradeList.add(latestTrade);
            latestTrade = null;
        }
    }

    private double getPercentage(Candle candle) {
        if(isTradeOpen()) {
            if (latestTrade.getType() == TradeType.SHORT) {
                return ((latestTrade.getStartPrice() - candle.getClose()) / candle.getClose()) * 100;
            }
            else {
                return ((candle.getClose() - latestTrade.getStartPrice()) / latestTrade.getStartPrice()) * 100;
            }
        }
        return 0;
    }

    @Override
    public void sendSignal(Signal signal, Candle candle) {
        checkStatus(candle);
        if(tradeEnded) return;
        switch(signal) {
            case NONE:
                handleNoneSignal(candle);
                break;
            case LONG:
                handleLongSignal(candle);
                break;
            case SHORT:
                handleShortSignal(candle);
                break;
        }
    }

    @Override
    public boolean isTradeOpen() {
        return latestTrade != null;
    }

    @Override
    public Trade getLatestTrade() {
        return latestTrade;
    }

    @Override
    public void endTradeIfOpen(Candle candle) {
        if(isTradeOpen()) {
            latestTrade.endTrade(candle.getDate(), candle.getClose());
            tradeList.add(latestTrade);
            latestTrade = null;
        }
    }

    private void handleLongSignal(Candle candle) {
        if(isTradeOpen()) {
            TradeType type = latestTrade.getType();
            switch (type) {
                case LONG:
                    break;
                case SHORT:
                    endTradeIfOpen(candle);
                    latestTrade = new Trade(candle.getDate(), candle.getClose(), TradeType.LONG);
                    break;
            }
        }
        else {
            latestTrade = new Trade(candle.getDate(), candle.getClose(), TradeType.LONG);
        }
    }

    private void handleShortSignal(Candle candle) {
        if(isTradeOpen()) {
            TradeType type = latestTrade.getType();
            switch (type) {
                case SHORT:
                    break;
                case LONG:
                    endTradeIfOpen(candle);
                    latestTrade = new Trade(candle.getDate(), candle.getClose(), TradeType.SHORT);
                    break;
            }
        }
        else {
            latestTrade = new Trade(candle.getDate(), candle.getClose(), TradeType.SHORT);
        }
    }

    private void handleNoneSignal(Candle candle) {

    }

    @Override
    public List<Trade> getTradeList() {
        return tradeList;
    }
}
