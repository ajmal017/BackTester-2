package indicator;

import error.NotEnoughData;
import struct.Candle;

import java.util.List;

public class RelativeStrengthIndex implements Indicator {
    private int period;
    private Candle[] candles;
    double avgGain,avgLoss;
    double RS, RSI;
    int size = 0;

    public RelativeStrengthIndex(int period) {
        this.period = period;
        candles = new Candle[period+1];
    }

    @Override
    public void addCandle(Candle candle) {
        for(int i=period-1;i>=0;i--) {
            candles[i+1] = candles[i];
        }
        candles[0] = candle;
        if(size < period + 1) size++;
        else {
            double sumGain = 0, sumLoss = 0;
            for(int i=0;i<period;i++) {
                double gain = candles[i].getClose() - candles[i+1].getClose();
                if(gain > 0) {
                    sumGain += gain;
                }
                else {
                    sumLoss += gain * -1;
                }
            }
            avgGain = sumGain/period;
            avgLoss = sumLoss/period;
            RS = avgGain/avgLoss;
            RSI = 100 - (100/(1+RS));
        }
    }

    public double getRelativeStrengthIndex() throws NotEnoughData {
        if(size < period + 1) throw new NotEnoughData();
        return RSI;
    }
}
