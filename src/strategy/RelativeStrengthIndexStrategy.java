package strategy;

import error.NotEnoughData;
import indicator.RelativeStrengthIndex;
import indicator.SimpleMovingAverage;
import struct.Candle;
import struct.Signal;

public class RelativeStrengthIndexStrategy implements Strategy {

    RelativeStrengthIndex rsiIndicator;
    SimpleMovingAverage sma;
    int period, smaPeriod;

    public RelativeStrengthIndexStrategy(int period, int smaPeriod) {
        this.period = period;
        rsiIndicator = new RelativeStrengthIndex(period);
        sma = new SimpleMovingAverage(smaPeriod);
    }

    @Override
    public void addCandle(Candle c) {
        rsiIndicator.addCandle(c);
        sma.addCandle(c);
    }

    @Override
    public Signal getSignal() {
        try {
            double rsi = rsiIndicator.getRelativeStrengthIndex();
            if(rsi < 50) {
                if(sma.getCandle(0).getClose() < sma.getSimpleMovingAverage(0)) return Signal.SHORT;
                else return Signal.NONE;
            }
            else if(rsi > 50) {
                if(sma.getCandle(0).getClose() > sma.getSimpleMovingAverage(0)) return Signal.LONG;
                else return Signal.NONE;
            }
            else return Signal.NONE;
        } catch (NotEnoughData notEnoughData) {
            return Signal.NONE;
        }
    }
}
