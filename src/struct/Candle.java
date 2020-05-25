package struct;

import java.util.Date;

public class Candle {
    private double high,low,open,close;
    private Date date;

    public Candle(double high, double low, double open, double close, Date date) {
        this.high = high;
        this.low = low;
        this.open = open;
        this.close = close;
        this.date = date;
    }

    public Candle(Candle c) {
        this.high = c.getHigh();
        this.low = c.getLow();
        this.open = c.getOpen();
        this.close = c.getClose();
        this.date = c.getDate();
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getOpen() {
        return open;
    }

    public double getClose() {
        return close;
    }

    public Date getDate() {
        return date;
    }
}
