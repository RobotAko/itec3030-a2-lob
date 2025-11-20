package ca.yorku.cmg.lob.stockexchange.tradingagent;

import ca.yorku.cmg.lob.stockexchange.StockExchange;
import ca.yorku.cmg.lob.stockexchange.events.NewsBoard;
import ca.yorku.cmg.lob.trader.Trader;

/**
 * Concrete implementation of {@link AbstractTradingAgentFactory}.
 *
 * It creates the correct combination of TradingAgent subtype
 * (Institutional / Retail) and trading style (Aggressive / Conservative)
 * using a single factory method call.
 */
public class SimpleTradingAgentFactory extends AbstractTradingAgentFactory {

    @Override
    public TradingAgent createAgent(String type,
                                    String style,
                                    Trader t,
                                    StockExchange e,
                                    NewsBoard n) {

        // 1. Create the correct kind of TradingAgent (Institutional or Retail)
        TradingAgent agent;

        if ("Institutional".equalsIgnoreCase(type)) {
            agent = new TradingAgentInstitutional(t, e, n, null);
        } else if ("Retail".equalsIgnoreCase(type)) {
            agent = new TradingAgentRetail(t, e, n, null);
        } else {
            throw new IllegalArgumentException("Unknown trading agent type: " + type);
        }

        // 2. Attach the correct trading style strategy (Aggressive / Conservative)
        ITradingStrategy strategy;

        if ("Aggressive".equalsIgnoreCase(style)) {
            strategy = new TradingAgentAggressive(agent);
        } else if ("Conservative".equalsIgnoreCase(style)) {
            strategy = new TradingAgentConservative(agent);
        } else {
            throw new IllegalArgumentException("Unknown trading agent style: " + style);
        }

        agent.setStrategy(strategy);
        return agent;
    }
}
