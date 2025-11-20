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
 * Aggressive trading strategy: reacts more eagerly to news.
 *
 * NOTE: This class used to be a TradingAgent subtype. It now plays the
 * Strategy role and is plugged into {@link TradingAgent} objects.
 */
public class TradingAgentAggressive implements ITradingStrategy {

	private final TradingAgent agent;

	public TradingAgentAggressive(TradingAgent agent) {
		this.agent = agent;
	}

	@Override
	public void actOnEvent(Event e, int pos, int price) {
		IOrder newOrder = null;

		Trader t = agent.getTrader();
		StockExchange exc = agent.getExchange();

		if (e instanceof GoodNews) {
			// Buy ~50% of position at +5% price (same behaviour as original)
			newOrder = new Bid(t,
					e.getSecrity(),
					(int) Math.round(price * 1.05),
					(int) Math.round(pos * 0.5),
					e.getTime());
		} else if (e instanceof BadNews) {
			// Sell ~80% of position at -10% price (same as original)
			newOrder = new Ask(t,
					e.getSecrity(),
					(int) Math.round(price * 0.90),
					(int) Math.round(pos * 0.8),
					e.getTime());
		} else {
			System.out.println("Unknown event type (Aggressive strategy)");
		}

		if (newOrder != null) {
			exc.submitOrder(newOrder, e.getTime());
		}
	}
}
