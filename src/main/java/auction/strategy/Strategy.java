package auction.strategy;

import auction.state.Auction;

/**
 * An interface of strategy.
 */
public interface Strategy {
    /**
     * suggest next bid value according to current action status
     * @param auction - current auction status, must not be null
     * @return non-negative bid value according implementation less or equal than {@link Auction#getOwnCache()}
     */
    int nextBid(Auction auction);
}
