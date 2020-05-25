package strategy;

import struct.Candle;
import struct.Signal;

public interface Strategy {

    void addCandle(Candle c);
    Signal getSignal();
}
