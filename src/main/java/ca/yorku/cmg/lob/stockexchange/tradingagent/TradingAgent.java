package ca.yorku.cmg.lob.stockexchange.tradingagent;

import ca.yorku.cmg.lob.stockexchange.StockExchange;
import ca.yorku.cmg.lob.stockexchange.events.Event;
import ca.yorku.cmg.lob.stockexchange.events.NewsBoard;
import ca.yorku.cmg.lob.trader.Trader;

/**
 * A trading agent that receives news and reacts by submitting ask or bid orders.
 *
 * In this redesigned version, the concrete reaction to news is delegated to an
 * {@link ITradingStrategy} object (Strategy pattern). Concrete agent types
 * ({@link TradingAgentInstitutional}, {@link TradingAgentRetail}) differ only
 * in the kind of trader they represent; both can plug in any trading style
 * (aggressive or conservative).
 */
public abstract class TradingAgent implements INewsObserver {

	protected Trader t;
	protected StockExchange exc;
	protected NewsBoard news;

	/** Current trading style for this agent (Strategy). */
	protected ITradingStrategy strategy;

	/**
	 * Constructor.
	 *
	 * @param t   The {@linkplain Trader} object associated with the agent.
	 * @param e   The {@linkplain StockExchange} at which the agent trades.
	 * @param n   The {@linkplain NewsBoard} that generates news events.
	 * @param s   The {@linkplain ITradingStrategy} that defines how the agent reacts.
	 */
	public TradingAgent(Trader t, StockExchange e, NewsBoard n, ITradingStrategy s) {
		this.t = t;
		this.exc = e;
		this.news = n;
		this.strategy = s;
	}

	public Trader getTrader() {
		return t;
	}

	public StockExchange getExchange() {
		return exc;
	}

	public NewsBoard getNewsBoard() {
		return news;
	}

	public ITradingStrategy getStrategy() {
		return strategy;
	}

	public void setStrategy(ITradingStrategy strategy) {
		this.strategy = strategy;
	}

	/**
	 * Method to be called as time advances to {@code time}. In response the
	 * TradingAgent will poll the NewsBoard for events.
	 *
	 * @param time The time to advance to.
	 */
	public void timeAdvancedTo(long time) {
		pollForEvents(time);
	}

	/**
	 * Examine if an event is relevant for the Agent, i.e., if the Agent has a
	 * position on it. If yes, delegate to the installed trading strategy.
	 *
	 * @param e The {@linkplain Event} object in question
	 */
	private void examineEvent(Event e) {
		int positionInSecurity = exc.getAccounts()
				.getTraderAccount(t)
				.getPosition(e.getSecrity().getTicker());
		if (positionInSecurity > 0 && strategy != null) {
			int currentPrice = exc.getPrice(e.getSecrity().getTicker());
			strategy.actOnEvent(e, positionInSecurity, currentPrice);
		}
	}

	/**
	 * Check into the {@linkplain NewsBoard} if there are any events relevant to the
	 * provided time; if there is one (it assumes only one event at a time), send
	 * it for examination.
	 *
	 * @param time The time for which to poll for events. Unit is days.
	 */
	private void pollForEvents(long time) {
		Event e = news.getEventAt(time);
		if (e != null) {
			examineEvent(e);
		}
	}

	/**
	 * Observer-style update: allows a NewsBoard (or similar) to directly push an
	 * event to this agent instead of it polling.
	 */
	@Override
	public void update(Event e) {
		examineEvent(e);
	}
}
