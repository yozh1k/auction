package auction.strategy.corner;


import auction.state.Auction;
import auction.state.Round;
import org.junit.Assert;
import org.junit.Test;

public class CacheDeficitStrategyTest {

    private CacheDeficitStrategy strategy = new CacheDeficitStrategy();

    @Test
    public void isApplicableForFirstRoundWithoutDeficit() {
        int quantity = 10;
        Auction auction = new Auction(quantity, 10 * quantity);
        Assert.assertFalse(strategy.isApplicable(auction));
    }

    @Test
    public void isApplicableForFirstRoundWithDeficit() {
        int quantity = 100;
        Auction auction = new Auction(quantity, quantity / 10);
        Assert.assertTrue(strategy.isApplicable(auction));
    }

    @Test
    public void isApplicableForAuctionTailRoundWithNonEqualCache() {
        int quantity = 10;
        Auction auction = new Auction(quantity, 10 * quantity);
        auction.addRound(new Round(quantity * 4, quantity * 6));
        Assert.assertFalse(strategy.isApplicable(auction));
        auction.addRound(new Round(quantity * 4 , quantity * 3));
    }

    @Test
    public void isApplicableForAuctionTailRoundsWithoutDeficit() {
        int quantity = 10;
        Auction auction = new Auction(quantity, 10 * quantity);
        auction.addRound(new Round(1, 1 ));
        Assert.assertFalse(strategy.isApplicable(auction));
    }

    @Test
    public void isApplicableForAuctionTailRoundsWithDeficit() {
        int quantity = 100;
        Auction auction = new Auction(quantity, quantity / 10);
        auction.addRound(new Round(quantity * 9, quantity * 9));
        Assert.assertTrue(strategy.isApplicable(auction));
    }
    

    @Test
    public void nextBid() {
        int quantity = 1_00;
        Auction auction = new Auction(quantity, quantity / 10);
        while (auction.getRoundsLeft() > 0){
            auction.addRound(new Round(strategy.nextBid(auction), 0));
        }
        Assert.assertFalse(auction.getRounds().stream().mapToInt(Round::getBid).anyMatch(b-> b !=0 && b != 1));
        Assert.assertEquals(auction.getCacheLimit(), auction.getRounds().stream().mapToInt(Round::getBid).sum());

    }
}