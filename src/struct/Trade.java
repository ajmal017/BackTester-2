package struct;

import java.util.Date;

public class Trade {
    private Date startDate, endDate;
    private double startPrice, endPrice;
    private TradeType type;

    public Trade(Date startDate, double startPrice, TradeType type) {
        this.startDate = startDate;
        this.startPrice = startPrice;
        this.type = type;
    }

    public void endTrade(Date endDate, double endPrice) {
        this.endDate = endDate;
        this.endPrice = endPrice;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public double getStartPrice() {
        return startPrice;
    }

    public double getEndPrice() {
        return endPrice;
    }

    public TradeType getType() {
        return type;
    }
}
