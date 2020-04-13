package auction.strategy.corner;

import auction.state.Auction;


/**
 * Opponent overall cache n times lesser than mine where n - count of rounds need to win left for
 * overall victory
 */
public class OpponentBidTooSmallStrategy implements CornerCaseStrategy {
    @Override
    public boolean isApplicable(Auction auction) {
        if(auction.getOwnCache() == 0){
            return false;
        }
        int tieDiff = (auction.getQuantity() / 2) - auction.getOwnQuantity();
        if (tieDiff > 0) {
           int roundsToWin = tieDiff/2 + 1;
           if(roundsToWin <= auction.getRoundsLeft()){
               return auction.getOpponentCache() <= auction.getOwnCache() / roundsToWin;
           }
        }
        return false;
    }

    @Override
    public int nextBid(Auction auction) {
        return auction.getOpponentCache();
    }
}
