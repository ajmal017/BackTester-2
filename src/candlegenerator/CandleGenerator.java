package candlegenerator;

import struct.Candle;

import java.util.Iterator;

public interface CandleGenerator extends Iterator<Candle> {
    void start();
    void shutdown();
}
