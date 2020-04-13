package auction.strategy.pure;


import auction.state.Auction;
import auction.state.Round;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

public class LastOpponentBidPlusConstantStrategyTest {

    @Test
    public void nextBid() {
        int delta = 1;
        int cacheLimitPart = 50_000;
        LastOpponentBidPlusConstantStrategy strategy = new LastOpponentBidPlusConstantStrategy(delta);
        Auction auction = new Auction(100, cacheLimitPart * 2);
        auction.addRound(new Round(0, ThreadLocalRandom.current().nextInt(cacheLimitPart)));
        Assert.assertEquals(auction.getRounds().get(0).getOpponentBid() + delta, strategy.nextBid(auction));

    }

    @Test
    public void firstRoundNextBid() {
        int delta = 1;
        int cacheLimit = 100_000;
        LastOpponentBidPlusConstantStrategy strategy = new LastOpponentBidPlusConstantStrategy(delta);
        Auction auction = new Auction(100, cacheLimit);
        Assert.assertEquals(delta, strategy.nextBid(auction));
    }

    @Test
    public void nextBidWithTooBigDelta() {
        Auction auction = new Auction(100, 100_000);
        LastOpponentBidPlusConstantStrategy strategy = new LastOpponentBidPlusConstantStrategy(auction.getCacheLimit() + 5);
        auction.addRound(new Round(0, ThreadLocalRandom.current().nextInt(auction.getCacheLimit())));
        Assert.assertTrue(strategy.nextBid(auction) <= auction.getOwnCache());
    }
}