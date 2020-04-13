package auction.strategy.corner;

import auction.state.Auction;

/**
 * Corner case strategy for opponent out of cache case
 */
public class OpponentOutOfCacheStrategy implements CornerCaseStrategy {
    @Override
    public boolean isApplicable(Auction auction) {
        return auction.getOpponentCache() == 0;
    }

    /**
     * If opponent not able to make positive bids just make minimal bids
     *
     */
    @Override
    public int nextBid(Auction auction) {
        return auction.getOwnCache() > 0 ? 1 : 0;
    }
}
