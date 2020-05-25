package strategy;

import error.NotEnoughData;
import indicator.SuperTrend;
import struct.Candle;
import struct.Signal;

public class SuperTrendStrategy implements Strategy {

    SuperTrend superTrend1, superTrend2,superTrend3;
    Candle candle;

    public SuperTrendStrategy(int period1, int period2, int period3, double factor1, double factor2, double factor3) {
        superTrend1 = new SuperTrend(period1, factor1);
        superTrend2 = new SuperTrend(period2, factor2);
        superTrend3 = new SuperTrend(period3, factor3);
    }

    @Override
    public void addCandle(Candle c) {
        superTrend1.addCandle(c); superTrend2.addCandle(c); superTrend3.addCandle(c);
        candle = c;
    }

    @Override
    public Signal getSignal() {
        try {
            double st1 = superTrend1.getSuperTrend();
            double st2 = superTrend2.getSuperTrend();
            double st3 = superTrend3.getSuperTrend();
            if(candle.getClose() > st1 && candle.getClose() > st2 && candle.getClose() > st3) {
                return Signal.SHORT;
            }
        } catch (NotEnoughData notEnoughData) {
            notEnoughData.printStackTrace();
        }
        return Signal.NONE;
    }
}
