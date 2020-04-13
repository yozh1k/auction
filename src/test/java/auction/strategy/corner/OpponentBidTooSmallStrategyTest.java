package auction.strategy.corner;


import auction.state.Auction;
import auction.state.Round;
import org.junit.Assert;
import org.junit.Test;


public class OpponentBidTooSmallStrategyTest {

    private OpponentBidTooSmallStrategy strategy = new OpponentBidTooSmallStrategy();

    @Test
    public void isApplicableEqualCache() {
        Auction auction = new Auction(10, 100_000);
        Assert.assertFalse(strategy.isApplicable(auction));
    }

    @Test
    public void isApplicableOutOfCache() {
        Auction auction = new Auction(10, 100_000);
        auction.addRound(new Round(auction.getCacheLimit(), 0));
        Assert.assertFalse(strategy.isApplicable(auction));
    }

    @Test
    public void isApplicableLesserCache() {
        int cacheLimitPart = 10;
        Auction auction = new Auction(10, cacheLimitPart * 10);
        auction.addRound(new Round(cacheLimitPart * 2, cacheLimitPart));
        Assert.assertFalse(strategy.isApplicable(auction));
    }

    @Test
    public void isApplicableForTooBigOpponentCache() {
        int cacheLimitPart = 10;
        Auction auction = new Auction(10, cacheLimitPart * 10);
        auction.addRound(new Round(0 , cacheLimitPart * 6));
        auction.addRound(new Round(cacheLimitPart * 3, 0));
        Assert.assertFalse(strategy.isApplicable(auction));

        auction = new Auction(12, cacheLimitPart * 10);
        auction.addRound(new Round(0 , cacheLimitPart * 6));
        auction.addRound(new Round(cacheLimitPart, 0));
        Assert.assertFalse(strategy.isApplicable(auction));
    }

    @Test
    public void isApplicableForOpponentTooSmallBid() {
        int cacheLimitPart = 10;
        Auction auction = new Auction(10, cacheLimitPart * 10);
        auction.addRound(new Round(0 , cacheLimitPart * 9));
        auction.addRound(new Round(cacheLimitPart, 0));
        Assert.assertTrue(strategy.isApplicable(auction));

        auction = new Auction(12, cacheLimitPart * 10);
        auction.addRound(new Round(0 , cacheLimitPart * 8));
        auction.addRound(new Round(cacheLimitPart, 0));
        Assert.assertTrue(strategy.isApplicable(auction));
    }



    @Test
    public void nextBid() {
        int cacheLimitPart = 10;
        Auction auction = new Auction(10, cacheLimitPart * 10);
        auction.addRound(new Round(0 , cacheLimitPart * 9));
        Assert.assertEquals(auction.getOpponentCache(), strategy.nextBid(auction));
    }
}