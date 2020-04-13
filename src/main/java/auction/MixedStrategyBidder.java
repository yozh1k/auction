package auction;

import auction.strategy.Strategy;
import auction.strategy.corner.CornerCaseStrategy;
import auction.strategy.pure.PureStrategy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class MixedStrategyBidder extends AbstractBidder {
    private final List<PureStrategy> pureStrategies;

    private MixedStrategyBidder(List<CornerCaseStrategy> cornerCaseStrategies, Collection<PureStrategy> pureStrategies) {
        super(cornerCaseStrategies);
        this.pureStrategies = new ArrayList<>(pureStrategies);
    }

    @Override
    protected Strategy choosePureStrategy() {
        if (pureStrategies.size() == 1) {
            return pureStrategies.get(0);
        } else {
            return pureStrategies.get(ThreadLocalRandom.current().nextInt(pureStrategies.size()));
        }
    }

    public static class Builder extends AbstractBidder.Builder<MixedStrategyBidder> {

        private final List<PureStrategy> pureStrategies = new ArrayList<>();

        /**
         * add item to pure strategy set(list). Sometimes it makes sense to add multiple instances of same strategy to
         * manipulate probability of its applying
         *
         * @param strategy, ignored if is null
         * @return {@code this}
         */
        public Builder withPureStrategy(PureStrategy strategy){
            if(strategy != null){
                pureStrategies.add(strategy);
            }
            return this;
        }

        /**
         * build instance of {@link MixedStrategyBidder}. Requires at least two non null {@link PureStrategy} was added
         * via {@link #withPureStrategy(PureStrategy)}
         * @throws IllegalStateException if size of {@link #pureStrategies} lesser then two
         */
        @Override
        public MixedStrategyBidder build() {
            if(pureStrategies.size() < 2){
                throw new IllegalStateException("MixedStrategyBidder required at least two PureStrategy");
            }
            return new MixedStrategyBidder(cornerCaseStrategies, pureStrategies);
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
