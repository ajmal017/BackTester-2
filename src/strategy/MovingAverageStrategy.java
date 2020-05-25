package strategy;

import struct.Candle;
import struct.Signal;
import error.NotEnoughData;
import indicator.SimpleMovingAverage;

public class MovingAverageStrategy implements Strategy {

    private SimpleMovingAverage sma5;
    private int numberOfTimes;

    public MovingAverageStrategy(int smaPeriod, int numberOfTimes) {
        sma5 = new SimpleMovingAverage(smaPeriod);
        this.numberOfTimes = numberOfTimes;
    }

    @Override
    public void addCandle(Candle c) {
        sma5.addCandle(c);
    }

    @Override
    public Signal getSignal() {
        try {
            int number = 0;
            for(int i=0;i<numberOfTimes;i++) {
                double sma = sma5.getSimpleMovingAverage(i);
                double c = sma5.getCandle(i).getClose();
                if(c > sma) number ++;
                else if(c < sma) number --;
            }
            if(number == numberOfTimes) return Signal.LONG;
            if(number == -1*numberOfTimes) return Signal.SHORT;
            return Signal.NONE;
        } catch (NotEnoughData e) {
            return Signal.NONE;
        }
    }
}
