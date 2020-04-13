package auction.utils;

import auction.Bidder;
import auction.MixedStrategyBidder;
import auction.PureStrategyBidder;
import auction.strategy.pure.*;
import auction.utils.competitors.ConstantBidder;
import auction.utils.competitors.RandomBidder;
import org.apache.commons.math3.util.Pair;
import org.junit.Test;

import java.util.*;
import java.util.stream.Stream;

import static java.lang.System.out;
import static java.util.stream.Collectors.toSet;

public class Competition {
    private final Set<Pair<Integer, Integer>> auctionParams=Stream.of(
            new Pair<>(1000, 50_000),
            new Pair<>(1000, 500),
            new Pair<>(5000, 5_000_000),
            new Pair<>(5000, 25_000),
            new Pair<>(2, 50_000_000),
            new Pair<>(100, 20_000)
    ).collect(toSet());
    private final int rounds = 5;


    @Test
    public void proceedPureCompetition() {
        Set<BidderWrapper> competitors = initPureCompetitors();
        proceed(competitors);
    }

    @Test
    public void proceedFinalCompetition() {
        Set<BidderWrapper> competitors = initMixedCompetitors();
        proceed(competitors);

    }

    private Set<BidderWrapper> initMixedCompetitors() {
        Set<BidderWrapper> result = new HashSet<>();
        result.add(
                new BidderWrapper(
                        new PureStrategyBidder.Builder()
                                .withPureStrategy(new LastWinnerPlusConstantStrategy(4))
                                .withDefaultCornerCaseStrategies()
                                .build(),
                        LastWinnerPlusConstantStrategy.class.getName() + "_delta=" + 4 + "_with_corner_cases"
                )
        );
        result.add(
                new BidderWrapper(
                        new MixedStrategyBidder.Builder()
                                .withPureStrategy(new LastWinnerPlusConstantStrategy(4))
                                .withPureStrategy(new LastWinnerPlusConstantStrategy(8))
                                .withDefaultCornerCaseStrategies()
                                .build(),
                        LastWinnerPlusConstantStrategy.class.getName() + "_delta=" + 4 + "_with_corner_cases" +
                                LastWinnerPlusConstantStrategy.class.getName() + "_delta=" + 8 + "_with_corner_cases"
                )
        );
        result.add(
                new BidderWrapper(
                        new MixedStrategyBidder.Builder()
                                .withPureStrategy(new LastWinnerPlusConstantStrategy(4))
                                .withPureStrategy(new LastWinnerPlusConstantStrategy(8))
                                .withPureStrategy(new LastWinnerPlusConstantStrategy(2))
                                .withDefaultCornerCaseStrategies()
                                .build(),
                        LastWinnerPlusConstantStrategy.class.getName() + "_delta=" + 2 + "_with_corner_cases" +
                                LastWinnerPlusConstantStrategy.class.getName() + "_delta=" + 4 + "_with_corner_cases" +
                                LastWinnerPlusConstantStrategy.class.getName() + "_delta=" + 8 + "_with_corner_cases"
                )
        );

        result.add(
                new BidderWrapper(
                        new MixedStrategyBidder.Builder()
                                .withPureStrategy(new OpponentMedianPlusConstantStrategy(2))
                                .withPureStrategy(new OpponentMedianPlusConstantStrategy(4))
                                .withPureStrategy(new OpponentMedianPlusConstantStrategy(8))
                                .withPureStrategy(new RoundToWinAverageStrategy())
                                .withDefaultCornerCaseStrategies()
                                .build(),
                        OpponentMedianPlusConstantStrategy.class.getName() + "_delta=" + 2 + "_with_corner_cases " +
                                OpponentMedianPlusConstantStrategy.class.getName() + "_delta=" + 4 + "_with_corner_cases " +
                                OpponentMedianPlusConstantStrategy.class.getName() + "_delta=" + 8 + "_with_corner_cases " +
                                RoundToWinAverageStrategy.class.getName()

                )
        );

        result.add(
                new BidderWrapper(
                        new MixedStrategyBidder.Builder()
                                .withPureStrategy(new MedianPlusConstantStrategy(2))
                                .withPureStrategy(new MedianPlusConstantStrategy(4))
                                .withPureStrategy(new MedianPlusConstantStrategy(8))
                                .withPureStrategy(new RoundToWinAverageStrategy())
                                .withDefaultCornerCaseStrategies()
                                .build(),
                        MedianPlusConstantStrategy.class.getName() + "_delta=" + 2 + "_with_corner_cases " +
                                MedianPlusConstantStrategy.class.getName() + "_delta=" + 4 + "_with_corner_cases " +
                                MedianPlusConstantStrategy.class.getName() + "_delta=" + 8 + "_with_corner_cases " +
                                RoundToWinAverageStrategy.class.getName()

                )
        );

        result.add(
                new BidderWrapper(
                        new MixedStrategyBidder.Builder()
                                .withPureStrategy(new LastOpponentBidPlusConstantStrategy(2))
                                .withPureStrategy(new LastOpponentBidPlusConstantStrategy(4))
                                .withPureStrategy(new LastOpponentBidPlusConstantStrategy(8))
                                .withPureStrategy(new RoundToWinAverageStrategy())
                                .withDefaultCornerCaseStrategies()
                                .build(),
                        LastOpponentBidPlusConstantStrategy.class.getName() + "_delta=" + 2 + "_with_corner_cases " +
                                LastOpponentBidPlusConstantStrategy.class.getName() + "_delta=" + 4 + "_with_corner_cases " +
                                LastOpponentBidPlusConstantStrategy.class.getName() + "_delta=" + 8 + "_with_corner_cases " +
                                RoundToWinAverageStrategy.class.getName()

                )
        );

        result.add(
                new BidderWrapper(
                        new MixedStrategyBidder.Builder()
                                .withPureStrategy(new MedianPlusConstantStrategy(2))
                                .withPureStrategy(new MedianPlusConstantStrategy(4))
                                .withPureStrategy(new MedianPlusConstantStrategy(8))
                                .withPureStrategy(new RoundToWinAverageStrategy())
                                .withDefaultCornerCaseStrategies()
                                .build(),
                        MedianPlusConstantStrategy.class.getName() + "_delta=" + 2 + "_with_corner_cases " +
                                MedianPlusConstantStrategy.class.getName() + "_delta=" + 4 + "_with_corner_cases " +
                                MedianPlusConstantStrategy.class.getName() + "_delta=" + 8 + "_with_corner_cases " +
                                RoundToWinAverageStrategy.class.getName()

                )
        );

        result.add(
                new BidderWrapper(
                        new MixedStrategyBidder.Builder()
                                .withPureStrategy(new OpponentMedianPlusConstantStrategy(2))
                                .withPureStrategy(new OpponentMedianPlusConstantStrategy(4))
                                .withPureStrategy(new OpponentMedianPlusConstantStrategy(8))

                                .withPureStrategy(new MedianPlusConstantStrategy(2))
                                .withPureStrategy(new MedianPlusConstantStrategy(4))
                                .withPureStrategy(new MedianPlusConstantStrategy(8))

                                .withPureStrategy(new LastWinnerPlusConstantStrategy(4))
                                .withPureStrategy(new LastWinnerPlusConstantStrategy(8))
                                .withPureStrategy(new LastWinnerPlusConstantStrategy(2))

                                .withPureStrategy(new LastOpponentBidPlusConstantStrategy(2))
                                .withPureStrategy(new LastOpponentBidPlusConstantStrategy(4))
                                .withPureStrategy(new LastOpponentBidPlusConstantStrategy(8))

                                .withPureStrategy(new RoundToWinAverageStrategy())
                                .withDefaultCornerCaseStrategies()
                                .build(),
                        "fullstack"

                )
        );

        return result;
    }

    private Set<BidderWrapper> initPureCompetitors() {
        Set<BidderWrapper> result = new HashSet<>();
        result.add(new BidderWrapper(new ConstantBidder()));
        result.add(new BidderWrapper(new RandomBidder()));

        for (int delta = 1; delta < 512; ) {
            result.add(
                    new BidderWrapper(
                            new PureStrategyBidder.Builder()
                                    .withPureStrategy(new LastOpponentBidPlusConstantStrategy(delta)).build(),
                            LastOpponentBidPlusConstantStrategy.class.getName() + "_delta=" + delta
                    )
            );
            result.add(
                    new BidderWrapper(
                            new PureStrategyBidder.Builder()
                                    .withPureStrategy(new LastOpponentBidPlusConstantStrategy(delta))
                                    .withDefaultCornerCaseStrategies()
                                    .build(),
                            LastOpponentBidPlusConstantStrategy.class.getName() + "_delta=" + delta + "_with_corner_cases"
                    )
            );

            result.add(
                    new BidderWrapper(
                            new PureStrategyBidder.Builder()
                                    .withPureStrategy(new LastWinnerPlusConstantStrategy(delta)).build(),
                            LastWinnerPlusConstantStrategy.class.getName() + "_delta=" + delta
                    )
            );
            result.add(
                    new BidderWrapper(
                            new PureStrategyBidder.Builder()
                                    .withPureStrategy(new LastWinnerPlusConstantStrategy(delta))
                                    .withDefaultCornerCaseStrategies()
                                    .build(),
                            LastWinnerPlusConstantStrategy.class.getName() + "_delta=" + delta + "_with_corner_cases"
                    )
            );

            result.add(
                    new BidderWrapper(
                            new PureStrategyBidder.Builder()
                                    .withPureStrategy(new MedianPlusConstantStrategy(delta)).build(),
                            MedianPlusConstantStrategy.class.getName() + "_delta=" + delta
                    )
            );
            result.add(
                    new BidderWrapper(
                            new PureStrategyBidder.Builder()
                                    .withPureStrategy(new MedianPlusConstantStrategy(delta))
                                    .withDefaultCornerCaseStrategies()
                                    .build(),
                            MedianPlusConstantStrategy.class.getName() + "_delta=" + delta + "_with_corner_cases"
                    )
            );


            result.add(
                    new BidderWrapper(
                            new PureStrategyBidder.Builder()
                                    .withPureStrategy(new OpponentMedianPlusConstantStrategy(delta)).build(),
                            OpponentMedianPlusConstantStrategy.class.getName() + "_delta=" + delta
                    )
            );
            result.add(
                    new BidderWrapper(
                            new PureStrategyBidder.Builder()
                                    .withPureStrategy(new OpponentMedianPlusConstantStrategy(delta))
                                    .withDefaultCornerCaseStrategies()
                                    .build(),
                            OpponentMedianPlusConstantStrategy.class.getName() + "_delta=" + delta + "_with_corner_cases"
                    )
            );

            delta = delta * 2;
        }
        result.add(
                new BidderWrapper(
                        new PureStrategyBidder.Builder()
                                .withPureStrategy(new RoundToWinAverageStrategy()).build(),
                        RoundToWinAverageStrategy.class.getName()
                )
        );
        result.add(
                new BidderWrapper(
                        new PureStrategyBidder.Builder()
                                .withPureStrategy(new RoundToWinAverageStrategy())
                                .withDefaultCornerCaseStrategies()
                                .build(),
                        RoundToWinAverageStrategy.class.getName()
                )
        );
        return result;
    }


    private void proceed(Set<BidderWrapper> competitors) {
        Map<BidderWrapper, Integer> results = new HashMap<>();
        for (Bidder bidder : competitors) {
            for (Bidder opponent : competitors) {
                if (bidder == opponent) {
                    continue;
                }
                for (Pair<Integer, Integer> auctionParam : auctionParams) {
                    for (int i = 0; i < rounds; i++) {
                        AbstractAuctionFactory factory = new AbstractAuctionFactory(auctionParam.getFirst(), auctionParam.getSecond()) {
                            @Override
                            protected Bidder targetBidder() {
                                return bidder;
                            }

                            @Override
                            protected Bidder alternativeBidder() {
                                return opponent;
                            }
                        };
                        Optional<Bidder> winner = factory.auction().determineWinner();
                        winner.ifPresent(w -> results.put((BidderWrapper) w, results.getOrDefault(w, 0) + 1));
                    }
                }
            }
        }
        results.entrySet().stream().sorted(Map.Entry.comparingByValue())
                .forEach(
                        e -> out.println(e.getKey().getName() + " -> " + e.getValue())
                );
    }


}
