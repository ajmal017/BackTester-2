package Analyser;

import struct.DetailedTrade;
import struct.Trade;

import java.util.ArrayList;
import java.util.List;

public class TradeAnalyser {

    private List<Trade> tradeList;
    private List<DetailedTrade> detailedTradeList;

    public TradeAnalyser(List<Trade> tradeList) {
        this.tradeList = tradeList;
        detailedTradeList = new ArrayList<>();
    }

    public void analyse() {
        double prevNetAssetValue = 100;
        for(Trade t : tradeList) {
            DetailedTrade dt = new DetailedTrade(t, prevNetAssetValue);
            detailedTradeList.add(dt);
            prevNetAssetValue = dt.getNetAssetValue();
        }
    }

    public List<Trade> getTradeList() {
        return tradeList;
    }

    public List<DetailedTrade> getDetailedTradeList() {
        return detailedTradeList;
    }
}
