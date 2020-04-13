package auction;


import auction.utils.AbstractAuctionFactory;
import auction.utils.Auction;
import auction.utils.competitors.ConstantBidder;
import auction.utils.competitors.RandomBidder;
import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class ConstantRandomTest {


    @Test
    public void test(){
        AuctionFactory factory = new AuctionFactory(100, 10);
        Auction auction = factory.auction();
        Optional<Bidder> bidder = auction.determineWinner();
        Assert.assertTrue(bidder.isPresent());
        Assert.assertTrue(bidder.get() instanceof ConstantBidder);
    }

    private static class AuctionFactory extends AbstractAuctionFactory {

        public AuctionFactory(int quantity, int cash) {
            super(quantity, cash);
        }

        @Override
        protected Bidder targetBidder() {
            return new ConstantBidder();
        }

        @Override
        protected Bidder alternativeBidder() {
            return new RandomBidder();
        }
    }

}
