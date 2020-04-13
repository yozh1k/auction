package auction.strategy.pure;

import auction.state.Auction;
import auction.state.Round;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

public class LastWinnerPlusConstantStrategyTest {


    @Test
    public void nextBid() {
        int delta = 2;
        int cacheLimitPart = 50_000;
        LastWinnerPlusConstantStrategy strategy = new LastWinnerPlusConstantStrategy(delta);
        Auction auction = new Auction(100, cacheLimitPart * 2);
        auction.addRound(new Round(0, ThreadLocalRandom.current().nextInt(cacheLimitPart)));
        Assert.assertEquals(auction.getRounds().get(0).getOpponentBid() + delta, strategy.nextBid(auction));
        auction.addRound(new Round(ThreadLocalRandom.current().nextInt(cacheLimitPart), 0));
        Assert.assertEquals(auction.getRounds().get(1).getBid() + delta, strategy.nextBid(auction));
    }


    @Test
    public void firstRoundNextBid() {
        int delta = 1;
        int cacheLimit = 100_000;
        LastWinnerPlusConstantStrategy strategy = new LastWinnerPlusConstantStrategy(delta);
        Auction auction = new Auction(100, cacheLimit);
        Assert.assertEquals(delta, strategy.nextBid(auction));
    }


    @Test
    public void nextBidWithTooBigDelta() {
        Auction auction = new Auction(100, 100_000);
        LastWinnerPlusConstantStrategy strategy = new LastWinnerPlusConstantStrategy(auction.getCacheLimit() + 5);
        auction.addRound(new Round(0, ThreadLocalRandom.current().nextInt(auction.getCacheLimit())));
        Assert.assertTrue(strategy.nextBid(auction) <= auction.getOwnCache());
    }

}
