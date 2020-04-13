package auction.strategy.pure;

import auction.state.Auction;
import auction.state.Round;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class RoundToWinAverageStrategyTest {

    @Test
    public void nextBid() {
        RoundToWinAverageStrategy averageStrategy = new RoundToWinAverageStrategy();
        int cashLimitParts = 1000;
        int cashLimitPart = 1000;
        Auction auction = new Auction(10_000, cashLimitPart * cashLimitParts);
        while (auction.getRoundsLeft() > 0) {
            auction.addRound(new Round(averageStrategy.nextBid(auction), 0));
        }
        int bid = auction.getCacheLimit() / (auction.getQuantity() / 4 + 1);
        Assert.assertTrue(auction.getRounds().stream().anyMatch(r -> r.getBid() == 0));
        Assert.assertTrue(auction.getRounds().stream().anyMatch(r -> r.getBid() == bid));
        Assert.assertFalse(auction.getRounds().stream().filter(r -> r.getBid() == bid || r.getBid() == 0).count() <= 1);
    }
}