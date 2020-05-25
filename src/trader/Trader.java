package trader;

import struct.Candle;
import struct.Signal;
import struct.Trade;

import java.util.List;

public interface Trader {
    void sendSignal(Signal signal, Candle candle);
    boolean isTradeOpen();
    Trade getLatestTrade();
    void endTradeIfOpen(Candle candle);
    List<Trade> getTradeList();
}
