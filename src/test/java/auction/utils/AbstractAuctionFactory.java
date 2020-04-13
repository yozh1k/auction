package auction.utils;


import auction.Bidder;

public abstract class AbstractAuctionFactory {
    private final int quantity;
    private final int cash;

    public AbstractAuctionFactory(int quantity, int cash) {
        this.quantity = quantity;
        this.cash = cash;
    }

    public Auction auction() {
        Bidder targetBidder = targetBidder();
        Bidder alternativeBidder = alternativeBidder();
        targetBidder.init(quantity, cash);
        alternativeBidder.init(quantity, cash);
        return new Auction(quantity, cash, targetBidder, alternativeBidder);
    }

    protected abstract Bidder targetBidder();

    protected abstract Bidder alternativeBidder();


}
