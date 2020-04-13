package auction;

import auction.strategy.Strategy;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AbstractBidderTest {
    private AbstractBidder bidder;
    private final int quantity = 100;
    private final int cacheLimit = 50_000;

    @Before
    public void before() {
        bidder = new AbstractBidder(null) {
            @Override
            protected Strategy choosePureStrategy() {
                return auction -> 0;
            }
        };
        bidder.init(quantity, cacheLimit);
    }


    @Test(expected = IllegalArgumentException.class)
    public void initWithNegativeQuantity() {
        bidder.init(-100, 100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void initWithNegativeCashLimit() {
        bidder.init(100, -100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void initWithOddQuantity() {
        bidder.init(101, 100);
    }


    @Test
    public void placeBid() {
        bidder.init(100, 100);
        bidder.placeBid();
    }


    @Test(expected = IllegalStateException.class)
    public void bids() {
        bidder.bids(0, 0);
    }

    @Test
    public void placeBidAndBids() {
        int bid = bidder.placeBid();
        bidder.bids(bid, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void tryToChangeBid() {
        int bid = bidder.placeBid();
        bidder.bids(bid + 1, 0);
    }

    @Test(expected = IllegalStateException.class)
    public void tryToDoubleCallBids() {
        int bid = bidder.placeBid();
        bidder.bids(bid, 0);
        bidder.bids(bid, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void tryToCheatWithBidSize() {
        int bid = bidder.placeBid();
        bidder.bids(bid, cacheLimit + 1);
    }

    @Test(expected = IllegalStateException.class)
    public void tryToBidsAfterAuctionClosed() {
        try {
            for (int i = 0; i < quantity / 2; i++) {
                bidder.placeBid();
                bidder.bids(0, 0);
            }
        } catch (Exception e) {
            Assert.fail();
        }
        bidder.placeBid();
    }

}