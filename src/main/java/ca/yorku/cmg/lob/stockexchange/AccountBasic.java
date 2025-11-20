package ca.yorku.cmg.lob.stockexchange;

import ca.yorku.cmg.lob.trader.Trader;
import ca.yorku.cmg.lob.tradestandards.ITrade;

/**
 * A basic exchange {@linkplain Account}.
 * This type of account applies a fixed fee for all trades.
 */
public class AccountBasic extends Account {

    /**
     * Constructs an {@linkplain AccountBasic} with an associated {@linkplain Trader}
     * and an initial balance.
     *
     * @param trader      the {@linkplain Trader} associated with this account
     * @param initBalance the initial balance of the account
     */
    AccountBasic(Trader trader, long initBalance) {
        super(trader, initBalance);
    }

    /**
     * Returns the fixed fee for all trades.
     *
     * @param t the {@linkplain ITrade} for which the fee is applied (unused in this implementation)
     * @return the fixed fee amount, which is always 450
     */
    @Override
    public int getFee(ITrade t) {
        return 450;
    }
}
