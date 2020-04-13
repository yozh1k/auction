package auction.state;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;

public class AuctionTest {

    private final static int quantity = 100;
    private final static int cacheLimit = 100_000;
    private Auction auction;

    @Before
    public void init() {
        auction = new Auction(quantity, cacheLimit);
    }

    @Test
    public void addWinRound() {
        Assert.assertTrue(auction.getRounds().isEmpty());
        int initRoundsLeft = auction.getRoundsLeft();
        int ownBid = cacheLimit - 1;
        int opponentBid = cacheLimit - 2;
        auction.addRound(new Round(ownBid, opponentBid));
        Assert.assertEquals(1, auction.getRounds().size());
        Assert.assertEquals(ownBid, auction.getRounds().get(0).getBid());
        Assert.assertEquals(opponentBid, auction.getRounds().get(0).getOpponentBid());
        Assert.assertEquals(cacheLimit - ownBid, auction.getOwnCache());
        Assert.assertEquals(cacheLimit - opponentBid, auction.getOpponentCache());
        Assert.assertEquals(2, auction.getOwnQuantity());
        Assert.assertEquals(0, auction.getOpponentQuantity());
        Assert.assertEquals(1, initRoundsLeft - auction.getRoundsLeft());
    }

    @Test
    public void addLossRound() {
        Assert.assertTrue(auction.getRounds().isEmpty());
        int initRoundsLeft = auction.getRoundsLeft();
        int ownBid = cacheLimit - 2;
        int opponentBid = cacheLimit - 1;
        auction.addRound(new Round(ownBid, opponentBid));
        Assert.assertEquals(1, auction.getRounds().size());
        Assert.assertEquals(ownBid, auction.getRounds().get(0).getBid());
        Assert.assertEquals(opponentBid, auction.getRounds().get(0).getOpponentBid());
        Assert.assertEquals(cacheLimit - ownBid, auction.getOwnCache());
        Assert.assertEquals(cacheLimit - opponentBid, auction.getOpponentCache());
        Assert.assertEquals(0, auction.getOwnQuantity());
        Assert.assertEquals(2, auction.getOpponentQuantity());
        Assert.assertEquals(1, initRoundsLeft - auction.getRoundsLeft());
    }

    @Test
    public void addTieRound() {
        Assert.assertTrue(auction.getRounds().isEmpty());
        int initRoundsLeft = auction.getRoundsLeft();
        int ownBid = cacheLimit - 2;
        int opponentBid = cacheLimit - 2;
        auction.addRound(new Round(ownBid, opponentBid));
        Assert.assertEquals(1, auction.getRounds().size());
        Assert.assertEquals(ownBid, auction.getRounds().get(0).getBid());
        Assert.assertEquals(opponentBid, auction.getRounds().get(0).getOpponentBid());
        Assert.assertEquals(cacheLimit - ownBid, auction.getOwnCache());
        Assert.assertEquals(cacheLimit - opponentBid, auction.getOpponentCache());
        Assert.assertEquals(1, auction.getOwnQuantity());
        Assert.assertEquals(1, auction.getOpponentQuantity());
        Assert.assertEquals(1, initRoundsLeft - auction.getRoundsLeft());
    }

    @Test
    public void testRoundsLeft() {
        Assert.assertEquals(quantity / 2, auction.getRoundsLeft());
    }

    @Test
    public void testAuctionClosed() {
        Assert.assertFalse(auction.isClosed());
        while (auction.getRoundsLeft() > 0) {
            auction.addRound(new Round(0, 0));
        }
        Assert.assertTrue(auction.isClosed());
    }

}