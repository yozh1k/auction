package auction.state;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Auction state holder
 */
public class Auction {
    private final int quantity;
    private final int cacheLimit;
    private final List<Round> rounds;

    private int ownCache;
    private int ownQuantity;

    private int opponentCache;
    private int opponentQuantity;

    public Auction(int quantity, int cacheLimit) {
        this.quantity = quantity;
        this.cacheLimit = cacheLimit;
        this.rounds = new ArrayList<>(quantity / 2);
        this.ownCache = cacheLimit;
        this.ownQuantity = 0;
        this.opponentCache = cacheLimit;
        this.opponentQuantity = 0;

    }

    public int getQuantity() {
        return quantity;
    }

    public int getCacheLimit() {
        return cacheLimit;
    }

    public List<Round> getRounds() {
        return Collections.unmodifiableList(rounds);
    }

    public void addRound(Round round) {
        if (round != null) {
            rounds.add(round);
            ownCache = ownCache - round.getBid();
            opponentCache = opponentCache - round.getOpponentBid();
            int bidsCompare = Integer.compare(round.getBid(), round.getOpponentBid());
            switch (bidsCompare) {
                case 1:
                    ownQuantity = ownQuantity + 2;
                    break;
                case 0:
                    ownQuantity++;
                    opponentQuantity++;
                    break;
                case -1:
                    opponentQuantity = opponentQuantity + 2;
                    break;
            }
        }
    }

    public int getOwnCache() {
        return ownCache;
    }

    public int getOwnQuantity() {
        return ownQuantity;
    }

    public int getOpponentCache() {
        return opponentCache;
    }

    public int getOpponentQuantity() {
        return opponentQuantity;
    }

    public boolean isClosed() {
        return opponentQuantity + ownQuantity == quantity;
    }

    public int getRoundsLeft() {
        if (isClosed()) {
            return 0;
        } else {
            return (quantity - (opponentQuantity + ownQuantity)) / 2;
        }
    }
}
