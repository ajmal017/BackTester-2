package struct;

public class DetailedTrade {
    private Trade trade;
    private double profit;
    private double netAssetValue;

    public DetailedTrade(Trade trade, double prevNetAssetValue) {
        this.trade = trade;
        calculateProfit();
        calculateNetAssetValue(prevNetAssetValue);
    }

    private void calculateProfit() {
        switch (trade.getType()) {
            case LONG:
                profit = (trade.getEndPrice() - trade.getStartPrice())/trade.getStartPrice();
                break;
            case SHORT:
                profit = (trade.getStartPrice() - trade.getEndPrice())/trade.getStartPrice();
                break;
        }
    }

    private void calculateNetAssetValue(double prevNetAssetValue) {

        netAssetValue = prevNetAssetValue * ( 1 + profit );
    }

    public Trade getTrade() {
        return trade;
    }

    public double getProfit() {
        return profit;
    }

    public double getNetAssetValue() {
        return netAssetValue;
    }
}
