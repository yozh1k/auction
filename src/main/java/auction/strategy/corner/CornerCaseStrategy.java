package auction.strategy.corner;

import auction.state.Auction;
import auction.strategy.Strategy;

/**
 * Corner case strategy. Special implementation of {@link Strategy} for corner cases(e.g Opponent out of money)
 */
public interface CornerCaseStrategy extends Strategy {
    /**
     * determine if current auction state is corresponding to particular corner case
     * @param auction - current state
     */
    boolean isApplicable(Auction auction);
}
