package auction.strategy.corner;

import auction.state.Auction;

import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

/**
 * In case of small initial cache amount. In this case there will be at least one round with zero bid or every bids will
 * be 1 MU.
 */
public class CacheDeficitStrategy implements CornerCaseStrategy {

    private Auction auction = null;
    private int step = 0;
    private int[] bids;

    /**
     * There isn't any sense to make bids greater than 1, but we must spent all money. To spent all money we must
     * determine bid value for every step in the beginning of auction or tail of auction with equal amounts of cache and
     * quantity.
     */
    @Override
    public boolean isApplicable(Auction auction) {
        if (this.auction == auction) {
            return true;
        }
        int currentQuantity = auction.getQuantity() - (auction.getOwnQuantity() + auction.getOpponentQuantity());
        if (
                auction.getOwnCache() == auction.getOpponentCache() && auction.getOwnQuantity() == auction.getOpponentQuantity()
                        && currentQuantity / 2 >= auction.getOwnCache()

        ) {
            this.auction = auction;
            determineBids(currentQuantity / 2, auction.getOwnCache());
            return true;
        }
        return false;

    }

    private void determineBids(int rounds, int ownCache) {
        this.step = 0;
        this.bids = new int[rounds];
        for (int i = 0; i < rounds; i++) {
            bids[i] = 0;
        }
        int cacheCounter = 0;
        while (cacheCounter < ownCache) {
            bids[ThreadLocalRandom.current().nextInt(rounds)] = 1;
            cacheCounter = IntStream.of(bids).sum();
        }
    }


    @Override
    public int nextBid(Auction auction) {
        if(isApplicable(auction)) {
            return bids[step++];
        }else {
            throw new IllegalStateException("strategy insn't applicable");
        }
    }
}
