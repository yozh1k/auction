package auction.strategy.corner;

import auction.state.Auction;

/**
 * Corner case strategy for last round/single round auction
 */
public class LastRoundStrategy implements CornerCaseStrategy {
    @Override
    public boolean isApplicable(Auction auction) {
        return auction.getQuantity() - (auction.getOwnQuantity() + auction.getOpponentQuantity()) == 2;
    }

    /**
     * We are going to spent all money
     */
    @Override
    public int nextBid(Auction auction) {
        return auction.getOwnCache();
    }
}
