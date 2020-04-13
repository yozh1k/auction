package auction.strategy.corner;

import auction.state.Auction;
import auction.state.Round;
import org.junit.Assert;
import org.junit.Test;

public class OpponentOutOfCacheStrategyTest {
    private final OpponentOutOfCacheStrategy strategy = new OpponentOutOfCacheStrategy();
    @Test
    public void isApplicable() {
        int cacheLimitHalf = 100_000;
        Auction auction = new Auction(100, cacheLimitHalf * 2);
        auction.addRound(new Round(0, cacheLimitHalf));
        Assert.assertFalse(strategy.isApplicable(auction));
        auction.addRound(new Round(0, cacheLimitHalf));
        Assert.assertTrue(strategy.isApplicable(auction));
    }

    @Test
    public void nextBid() {

        Auction auction = new Auction(100, 100_000);
        Assert.assertEquals(1, strategy.nextBid(auction));
    }
    @Test
    public void nextBidWithoutCash() {
        int cacheLimit = 100_000;
        Auction auction = new Auction(100, cacheLimit);
        auction.addRound(new Round(cacheLimit, cacheLimit));
        Assert.assertEquals(0, strategy.nextBid(auction));
    }
}