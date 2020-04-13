package auction.utils.competitors;

import auction.Bidder;

public class ConstantBidder implements Bidder {
    private int quantity;
    private int cash;

    @Override
    public void init(int quantity, int cash) {
        this.cash = cash;
        this.quantity = quantity;
    }

    @Override
    public int placeBid() {
        if (cash > 0 && quantity > 0) {
            int bid = cash / quantity;
            if (bid == 0) {
                bid = cash;
            }
            quantity--;
            cash = cash - bid;
            return bid;
        }
        return 0;
    }

    @Override
    public void bids(int own, int other) {
        //no-op
    }
}
