package auction.strategy.corner;

import auction.state.Auction;
import auction.state.Round;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;

public class LastRoundStrategyTest {

    private final LastRoundStrategy strategy = new LastRoundStrategy();

    @Test
    public void isApplicableForSingleRound() {
        Auction auction = new Auction(2, 100_000);
        Assert.assertTrue(strategy.isApplicable(auction));
    }
    @Test
    public void isApplicableForLastRound(){
        Auction auction = new Auction(100, 100_000);
        Assert.assertFalse(strategy.isApplicable(auction));
        while (auction.getRoundsLeft() > 1){
            auction.addRound(new Round(0,0));
        }
        Assert.assertTrue(strategy.isApplicable(auction));
    }

    @Test
    public void nextBid() {
        Auction auction = new Auction(2, 324_522);
        Assert.assertEquals(auction.getOwnCache(), strategy.nextBid(auction));
    }
}