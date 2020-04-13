package auction.strategy.pure;

import auction.state.Auction;

import java.util.concurrent.ThreadLocalRandom;

/**
 * This is kind of aggressive strategy. Let N - number of rounds to win to achieve
 * overall victory, then according with this strategy next bid will be cash limit * 1/N with probability = N/(overall rounds count).
 * In rest cases bid will be 0
 */
public class RoundToWinAverageStrategy implements PureStrategy {
    @Override
    public int nextBid(Auction auction) {
        int roundsToWin = auction.getQuantity() / 4 + 1;
        if (ThreadLocalRandom.current().nextInt(auction.getQuantity() / 2 + 1) > roundsToWin) {
            int bid = auction.getCacheLimit() / roundsToWin;
            if (bid <= auction.getOwnCache()) {
                return bid;
            }
        }
        return 0;
    }
}
