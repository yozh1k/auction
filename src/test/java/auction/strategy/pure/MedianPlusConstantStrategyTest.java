package auction.strategy.pure;

import auction.state.Auction;
import auction.state.Round;
import org.apache.commons.math3.stat.descriptive.rank.Median;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;

public class MedianPlusConstantStrategyTest {

    @Test
    public void nextBid() {
        int delta = 1;
        int cacheLimitPart = 50_000;
        int steps = 100;
        MedianPlusConstantStrategy strategy = new MedianPlusConstantStrategy(delta);
        Auction auction = new Auction(100, cacheLimitPart * steps);
        double[] bids = new double[steps * 2];
        for (int i = 0; i < steps; i++) {
            Round round = new Round(
                    ThreadLocalRandom.current().nextInt(cacheLimitPart),
                    ThreadLocalRandom.current().nextInt(cacheLimitPart)
            );
            auction.addRound(round);
            bids[i * 2] = round.getBid();
            bids[i * 2 + 1] = round.getOpponentBid();
        }
        int median =  Math.toIntExact(Math.round(new Median().evaluate(bids)));
        Assert.assertEquals(median + delta, strategy.nextBid(auction));

    }

    @Test
    public void firstRoundNextBid() {
        int delta = 1;
        int cacheLimit = 100_000;
        MedianPlusConstantStrategy strategy = new MedianPlusConstantStrategy(delta);
        Auction auction = new Auction(100, cacheLimit);
        Assert.assertEquals(cacheLimit / 2 + delta, strategy.nextBid(auction));
    }

    @Test
    public void nextBidWithTooBigDelta() {
        Auction auction = new Auction(100, 100_000);
        MedianPlusConstantStrategy strategy = new MedianPlusConstantStrategy(auction.getCacheLimit() + 5);
        auction.addRound(new Round(0, ThreadLocalRandom.current().nextInt(auction.getCacheLimit())));
        Assert.assertTrue(strategy.nextBid(auction) <= auction.getOwnCache());
    }


}