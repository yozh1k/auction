package auction;

import auction.strategy.Strategy;
import auction.strategy.corner.CornerCaseStrategy;
import auction.strategy.pure.PureStrategy;

import java.util.List;


public class PureStrategyBidder extends AbstractBidder {
    private final PureStrategy pureStrategy;

    private PureStrategyBidder(List<CornerCaseStrategy> cornerCaseStrategies, PureStrategy pureStrategy) {
        super(cornerCaseStrategies);
        this.pureStrategy = pureStrategy;
    }

    @Override
    protected Strategy choosePureStrategy() {
      return pureStrategy;
    }

    public static class Builder extends AbstractBidder.Builder<PureStrategyBidder> {

        private PureStrategy pureStrategy = null;

        /**
         * add item to pure strategy set(list). Sometimes it makes sense to add multiple instances of same strategy to
         * manipulate probability of its applying
         *
         * @param strategy, ignored if is null
         * @return {@code this}
         */
        public Builder withPureStrategy(PureStrategy strategy){
            this.pureStrategy = strategy;
            return this;
        }

        /**
         * build instance of {@link PureStrategyBidder}. Requires non null {@link PureStrategy} was added
         * via {@link #withPureStrategy(PureStrategy)}
         * @throws IllegalStateException {@link #pureStrategy} is null
         */
        @Override
        public PureStrategyBidder build() {
            if(pureStrategy == null){
                throw new IllegalStateException("MixedStrategyBidder required non null PureStrategy");
            }
            return new PureStrategyBidder(cornerCaseStrategies, pureStrategy);
        }

        @Override
        public Builder withCornerCaseStrategies(List<CornerCaseStrategy> cornerCaseStrategies) {
            super.withCornerCaseStrategies(cornerCaseStrategies);
            return this;
        }

        @Override
        public Builder withDefaultCornerCaseStrategies() {
            super.withDefaultCornerCaseStrategies();
            return this;
        }
    }
}
