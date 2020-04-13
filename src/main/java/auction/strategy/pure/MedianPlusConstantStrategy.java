package auction.strategy.pure;

import auction.state.Auction;
import org.apache.commons.math3.stat.descriptive.rank.Median;

import java.util.stream.Stream;

public class MedianPlusConstantStrategy implements PureStrategy {
    private final int delta;


    /**
     * Public constructor
     *
     * @param delta - must be positive
     * @throws IllegalArgumentException if {@param delta} isn't positive
     */
    public MedianPlusConstantStrategy(int delta) {
        if (delta < 1) {
            throw new IllegalArgumentException("delta must be positive");
        }
        this.delta = delta;
    }

    /**
     * @implNote evaluate next bid as sum of {@link #delta} and median of all previous bids. In case this is the first
     * round uses half of initial cache as the median.
     */
    @Override
    public int nextBid(Auction auction) {
        int roundsCount = auction.getRounds().size();
        int bid = 0;
        if (roundsCount == 0) {
            bid = Math.round((float) auction.getCacheLimit() / 2) + delta;
        } else {
            double[] bids = auction.getRounds().stream()
                    .flatMap(r -> Stream.of(r.getBid(), r.getOpponentBid())).mapToDouble(x -> x).toArray();
            bid = Math.toIntExact(Math.round(new Median().evaluate(bids))) + delta;
        }
        return Math.min(auction.getOwnCache(), bid);
    }
}
