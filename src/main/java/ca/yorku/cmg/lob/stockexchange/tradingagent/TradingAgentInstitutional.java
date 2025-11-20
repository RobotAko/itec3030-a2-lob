package ca.yorku.cmg.lob.stockexchange.tradingagent;

import ca.yorku.cmg.lob.stockexchange.StockExchange;
import ca.yorku.cmg.lob.stockexchange.events.NewsBoard;
import ca.yorku.cmg.lob.trader.Trader;

/**
 * Trading agent that represents an institutional investor.
 *
 * Behaviour is entirely delegated to the installed {@link ITradingStrategy}.
 */
public class TradingAgentInstitutional extends TradingAgent {

    public TradingAgentInstitutional(Trader t,
                                     StockExchange e,
                                     NewsBoard n,
                                     ITradingStrategy s) {
        super(t, e, n, s);
    }
}
