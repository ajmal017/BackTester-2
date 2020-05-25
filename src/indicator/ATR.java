package indicator;

import error.NotEnoughData;
import struct.Candle;

public class ATR implements Indicator{
    int period;
    private Candle[] candles;
    private double[] TR;
    int size = 0;

    public ATR(int period) {
        this.period = period;
        candles = new Candle[period+1];
        TR = new double[period+1];
    }

    @Override
    public void addCandle(Candle candle) {
        for(int i=period-1;i>=0;i--) {
            candles[i+1] = candles[i];
            TR[i+1] = TR[i];
        }
        candles[0] = candle;
        if(size < period + 1) size++;
        if(size > 1) {
            double hl = candles[0].getHigh() - candles[0].getLow();
            double hpc = candles[0].getHigh() - candles[1].getClose();
            double lpc = candles[0].getLow() - candles[1].getClose();
            double tr = Math.max(hl, hpc);
            tr = Math.max(tr, lpc);
            TR[0] = tr;
        }
    }

    public double getATR() throws NotEnoughData {
        if(size < period + 1) throw new NotEnoughData();
        double ATR = 0;
        for(int i=period-1;i>=0;i--) ATR += TR[i];
        ATR /= period;
        return ATR;
    }
}
