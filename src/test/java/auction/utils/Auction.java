package auction.utils;

import auction.Bidder;

import java.util.Optional;

import static java.lang.Integer.compare;

public class Auction {
    private int quantity;
    private final Bidder targetBidder;
    private final Bidder alternativeBidder;
    private int targetResult = 0;
    private int alternativeResult = 0;
    private int targetCash;
    private int alternativeCash;

    public Auction(int quantity, int cashLimit, Bidder targetBidder, Bidder alternativeBidder) {
        this.quantity = quantity;
        this.targetBidder = targetBidder;
        this.alternativeBidder = alternativeBidder;
        this.targetCash = cashLimit;
        this.alternativeCash = cashLimit;
    }

    public Optional<Bidder> determineWinner() {
        while (quantity > 0) {
            quantity = quantity - 2;
            checkBids();
        }
        switch (compare(targetResult, alternativeResult)){
            case 1:
                return Optional.of(targetBidder);
            case -1:
                return Optional.of(alternativeBidder);
            case 0:
                switch (compare(targetCash, alternativeCash)){
                    case 1:
                        return Optional.of(targetBidder);
                    case -1:
                        return Optional.of(alternativeBidder);
                }
        }
        return Optional.empty();

    }

    private void checkBids() {
        int targetBid = targetBidder.placeBid();
        int alternativeBid = alternativeBidder.placeBid();
        targetCash = targetCash - targetBid;
        alternativeCash = alternativeCash - alternativeBid;
        switch (compare(targetBid, alternativeBid)) {
            case 1:
                targetResult = targetResult + 2;
                break;
            case 0:
                targetResult++;
                break;
            case -1:
                alternativeResult = alternativeResult + 2;
        }
        alternativeBidder.bids(alternativeBid, targetBid);
        targetBidder.bids(targetBid, alternativeBid);
    }
}
