package ca.yorku.cmg.lob.stockexchange.tradingagent;

import ca.yorku.cmg.lob.stockexchange.StockExchange;
import ca.yorku.cmg.lob.stockexchange.events.NewsBoard;
import ca.yorku.cmg.lob.trader.Trader;

public abstract class AbstractTradingAgentFactory {
	/**
	 * Creates a Trading Agent object based on specification. 
	 * @param type "Institutional" for creating an Institutional trading agent, e.g., TradingAgentInstitutional, and "Retail" for creating a Retail trading agent, e.g., TradingAgentRetail.
	 * @param style "Aggressive" for assigning an aggressive strategy to the resulting trading agent, and "Conservative" for assigning a conservative one.
	 * @param t The {@linkplain Trader} object that is associated with the {@linkplain TradingAgent} object
	 * @param e Reference to the {@linkplain StockExchange}where the {@linkplain TradingAgent} will trade
	 * @param n Reference to the  {@linkplain NewsBoard} object that {@linkplain TradingAgent} will hear news from.
	 * @return A  {@linkplain TradingAgent} object constructed according to specified parameters.
	 */
	public abstract TradingAgent createAgent(String type, String style, Trader t, StockExchange e, NewsBoard n); 
}