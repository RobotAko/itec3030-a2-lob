package ca.yorku.cmg.lob.stockexchange.tradingagent;

import ca.yorku.cmg.lob.orderbook.Ask;
import ca.yorku.cmg.lob.orderbook.Bid;
import ca.yorku.cmg.lob.stockexchange.StockExchange;
import ca.yorku.cmg.lob.stockexchange.events.BadNews;
import ca.yorku.cmg.lob.stockexchange.events.Event;
import ca.yorku.cmg.lob.stockexchange.events.GoodNews;
import ca.yorku.cmg.lob.trader.Trader;
import ca.yorku.cmg.lob.tradestandards.IOrder;

/**
 * Conservative trading strategy: reacts more carefully and conservatively to news.
 *
 * NOTE: This class used to be a TradingAgent subtype. It now plays the
 * Strategy role and is plugged into {@link TradingAgent} objects.
 */
public class TradingAgentConservative implements ITradingStrategy {

	private final TradingAgent agent;

	public TradingAgentConservative(TradingAgent agent) {
		this.agent = agent;
	}

	@Override
	public void actOnEvent(Event e, int pos, int price) {
		IOrder newOrder = null;

		Trader t = agent.getTrader();
		StockExchange exc = agent.getExchange();

		if (e instanceof GoodNews) {
			// Buy ~20% of position at +5% price (same as original)
			newOrder = new Bid(t,
					e.getSecrity(),
					(int) Math.round(price * 1.05),
					(int) Math.round(pos * 0.2),
					e.getTime());
		} else if (e instanceof BadNews) {
			// Sell ~20% of position at -5% price (same as original)
			newOrder = new Ask(t,
					e.getSecrity(),
					(int) Math.round(price * 0.95),
					(int) Math.round(pos * 0.2),
					e.getTime());
		} else {
			System.out.println("Unknown event type (Conservative strategy)");
		}

		if (newOrder != null) {
			exc.submitOrder(newOrder, e.getTime());
		}
	}
}
