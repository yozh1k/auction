package auction.utils.competitors;

import auction.Bidder;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Dummy bidder. Makes random bid according available resources
 */
public class RandomBidder implements Bidder {
    private int cash;

    public void init(int quantity, int cash) {
        this.cash = cash;
    }

    public int placeBid() {
        int bid = ThreadLocalRandom.current().nextInt(cash + 1);
        cash = cash - bid;
        return bid;
    }

    public void bids(int own, int other) {
        //no-op
    }
}
