package indicator;

import struct.Candle;
import error.NotEnoughData;

public class SimpleMovingAverage implements Indicator{
    private Candle[] candles;
    private double[] sma;
    private int period;
    int size = 0;

    public SimpleMovingAverage(int period) {
        this.period = period;
        candles = new Candle[period];
        sma = new double[period];
    }

    @Override
    public void addCandle(Candle candle) {
        int sum = 0;
        for(int i=period-2;i>=0;i--) {
            candles[i+1] = candles[i];
            sma[i+1] = sma[i];
            if(candles[i+1] != null) sum += candles[i+1].getClose();
        }
        candles[0] = candle;
        sum += candles[0].getClose();
        sma[0] = sum/period;
        if(size < period) size++;
    }

    public double getSimpleMovingAverage(int index) throws NotEnoughData {
        if(size < period) throw new NotEnoughData();
        return sma[index];
    }

    public Candle getCandle(int index) throws NotEnoughData {
        if(size < period) throw new NotEnoughData();
        return candles[index];
    }
}
