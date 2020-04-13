package auction.strategy.pure;

import auction.state.Auction;
import auction.state.Round;

import java.util.List;

public class LastWinnerPlusConstantStrategy implements PureStrategy {
    private final int delta;


    /**
     * Public constructor
     *
     * @param delta - must be positive
     * @throws IllegalArgumentException if {@param delta} isn't positive
     */
    public LastWinnerPlusConstantStrategy(int delta) {
        if (delta < 1) {
            throw new IllegalArgumentException("delta must be positive");
        }
        this.delta = delta;
    }

    /**
     * @implNote evaluate next bid as sum of {@link #delta} and previous round winners bid. If there was a tie, uses
     * own bid. If this is the first uses 0 as the winner's bid.
     */
    @Override
    public int nextBid(Auction auction) {
        List<Round> rounds = auction.getRounds();
        int roundsCount = rounds.size();
        int bid = 0;
        if (roundsCount == 0) {
            bid = delta;
        } else {
            Round last = rounds.get(roundsCount - 1);
            bid = delta + (last.getOpponentBid() > last.getBid() ? last.getOpponentBid() : last.getBid());
        }
        return Math.min(auction.getOwnCache(), bid);
    }
}
