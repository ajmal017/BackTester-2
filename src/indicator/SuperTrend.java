package indicator;

import error.NotEnoughData;
import struct.Candle;

public class SuperTrend implements Indicator{

    int size = 0;
    Candle[] candles = new Candle[2];
    private int period;
    private double factor;
    private ATR atrIndicator;
    private double prevUB, prevLB;
    private double currUB, currLB;
    private double superTrend;

    public SuperTrend(int period, double factor) {
        this.period = period;
        this.factor = factor;
        atrIndicator = new ATR(period);
    }

    @Override
    public void addCandle(Candle candle) {
        atrIndicator.addCandle(candle);
        candles[1] = candles[0];
        candles[0] = candle;
        if(size < period + 1) size++;
        else {
            try {
                prevLB = currLB;
                prevUB = currUB;
                double ub = (candle.getHigh() + candle.getLow()) / 2 + factor * atrIndicator.getATR();
                double lb = (candle.getHigh() + candle.getLow()) / 2 - factor * atrIndicator.getATR();
                if(ub < prevUB && candles[1].getClose() > prevUB) currUB = ub;
                else currUB = prevUB;
                if(lb > prevLB && candles[1].getClose() < prevLB) currLB = lb;
                else currLB = prevLB;
                if(candle.getClose() <= currUB) superTrend = currUB;
                else superTrend = currLB;
            } catch (NotEnoughData notEnoughData) {
                notEnoughData.printStackTrace();
            }
        }
    }

    public double getSuperTrend() throws NotEnoughData {
        if(size < period + 1) throw new NotEnoughData();
        return superTrend;
    }
}
