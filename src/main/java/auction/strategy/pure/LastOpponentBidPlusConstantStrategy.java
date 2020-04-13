package auction.strategy.pure;

import auction.state.Auction;


public class LastOpponentBidPlusConstantStrategy implements PureStrategy {
    private final int delta;

    /**
     * Public constructor
     * @param delta - must be positive
     * @throws IllegalArgumentException if {@param delta} isn't positive
     */
    public LastOpponentBidPlusConstantStrategy(int delta) {
        if(delta < 1){
            throw new IllegalArgumentException("delta must be positive");
        }
        this.delta = delta;
    }

    /**
     * @implNote evaluate next bid as sum of {@link #delta} and previous round opponent's bid. In case this is the first
     * use 0 as the opponents bid.
     */
    @Override
    public int nextBid(Auction auction) {
        int roundsCount = auction.getRounds().size();
        return Math.min(auction.getOwnCache(), delta + (roundsCount == 0 ? 0 : auction.getRounds().get(roundsCount - 1).getOpponentBid()));
    }
}