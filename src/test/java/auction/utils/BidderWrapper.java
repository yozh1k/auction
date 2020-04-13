package auction.utils;

import auction.Bidder;

public class BidderWrapper implements Bidder {
    private final Bidder bidder;
    private final String name;

    BidderWrapper(Bidder bidder) {
        this.bidder = bidder;
        this.name = bidder.getClass().getName();
    }
    BidderWrapper(Bidder bidder, String name) {
        this.bidder = bidder;
        this.name = name;
    }

    @Override
    public void init(int quantity, int cash) {
        bidder.init(quantity, cash);
    }

    @Override
    public int placeBid() {
        return bidder.placeBid();
    }

    @Override
    public void bids(int own, int other) {
        bidder.bids(own, other);
    }

    public String getName() {
        return name;
    }
}
