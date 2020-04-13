package auction.state;

public class Round {
    private final int bid;
    private final int opponentBid;

    public Round(int bid, int opponentBid) {
        this.bid = bid;
        this.opponentBid = opponentBid;
    }

    public int getBid() {
        return bid;
    }

    public int getOpponentBid() {
        return opponentBid;
    }
}
