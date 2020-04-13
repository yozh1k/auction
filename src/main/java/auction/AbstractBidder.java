package auction;

import auction.state.Auction;
import auction.state.Round;
import auction.strategy.Strategy;
import auction.strategy.corner.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Abstract bidder to handle some corner cases and contract violations
 */
public abstract class AbstractBidder implements Bidder {
    private Auction auction;
    /**
     * store last value returned by {@link #placeBid()} to avoid contract violation and cheating
     */
    private Integer lastBid = 0;

    //corner cases strategies array.
    private final List<CornerCaseStrategy> cornerCaseStrategies;

    AbstractBidder(List<CornerCaseStrategy> cornerCaseStrategies) {
        this.cornerCaseStrategies = cornerCaseStrategies != null ? cornerCaseStrategies : Collections.emptyList();
    }


    @Override
    public void init(int quantity, int cash) {
        if (quantity < 0) {
            throw new IllegalArgumentException("quantity must be non negative");
        } else if (quantity % 2 == 1) {
            throw new IllegalArgumentException("quantity must be even");
        } else if (cash < 0) {
            throw new IllegalArgumentException("cash must be non negative");
        }
        auction = new Auction(quantity, cash);
        lastBid = null;
    }

    @Override
    public int placeBid() {
        if (auction.isClosed()) {
            throw new IllegalStateException("Auction closed");
        }
        return lastBid = (auction.getOwnCache() == 0 ? 0 : chooseStrategy().nextBid(auction));
    }

    @Override
    public void bids(int own, int other) {
        if (lastBid == null) {
            throw new IllegalStateException("you must call lastBid() method first");
        } else if (own < 0) {
            throw new IllegalArgumentException("own bid must be non negative");
        } else if (own > auction.getOwnCache()) {
            throw new IllegalArgumentException("own bid must be not greater than cash");
        } else if (other < 0) {
            throw new IllegalArgumentException("other bid must be non negative");
        } else if (auction.isClosed()) {
            throw new IllegalStateException("Auction closed");
        } else if (lastBid != own) {
            throw new IllegalArgumentException("possible cheating attempt detected, own bid mismatch");
        } else if (other > auction.getOpponentCache()) {
            throw new IllegalArgumentException("possible cheating attempt detected, too big other bid");
        }
        auction.addRound(new Round(own, other));
        lastBid = null;
    }

    protected abstract Strategy choosePureStrategy();

    private Strategy chooseStrategy() {
        Optional<CornerCaseStrategy> cornerCaseStrategy =
                cornerCaseStrategies.stream().filter(Objects::nonNull).filter(s -> s.isApplicable(auction)).findFirst();
        if (cornerCaseStrategy.isPresent()) {
            return cornerCaseStrategy.get();
        }
        return choosePureStrategy();
    }

    public static abstract class Builder<T extends AbstractBidder> {
        List<CornerCaseStrategy> cornerCaseStrategies;

        /**
         * Specify list of CornerCaseStrategies to apply. Order in list is order the Buider will try to apply strategies,
         *
         * @param cornerCaseStrategies - list of {@link CornerCaseStrategy}, may be null. If contains null, they will by
         *                             skipped
         */
        public Builder<T> withCornerCaseStrategies(List<CornerCaseStrategy> cornerCaseStrategies) {
            this.cornerCaseStrategies = cornerCaseStrategies.stream().filter(Objects::nonNull).collect(Collectors.toList());
            return this;
        }

        /**
         * Setup default list of CornerCaseStrategies to apply:
         * 1. {@link LastRoundStrategy}
         * 2. {@link OpponentOutOfCacheStrategy}
         * 3. {@link CacheDeficitStrategy}
         * 4. {@link OpponentBidTooSmallStrategy}
         */
        public Builder<T> withDefaultCornerCaseStrategies() {
            List<CornerCaseStrategy> cornerCaseStrategies = new ArrayList<>(4);
            cornerCaseStrategies.add(new LastRoundStrategy());
            cornerCaseStrategies.add(new OpponentOutOfCacheStrategy());
            cornerCaseStrategies.add(new CacheDeficitStrategy());
            cornerCaseStrategies.add(new OpponentBidTooSmallStrategy());
            this.cornerCaseStrategies = new ArrayList<>(cornerCaseStrategies);
            return this;
        }

        public abstract T build();

    }

}
