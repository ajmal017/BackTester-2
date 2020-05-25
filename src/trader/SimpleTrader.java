package trader;

import struct.Candle;
import struct.Signal;
import struct.Trade;
import struct.TradeType;

import java.util.ArrayList;
import java.util.List;

public class SimpleTrader implements Trader {

    private List<Trade> tradeList = new ArrayList<>();
    private Trade latestTrade = null;

    @Override
    public void sendSignal(Signal signal, Candle candle) {
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
