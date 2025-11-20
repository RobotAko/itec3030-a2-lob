package ca.yorku.cmg.lob.stockexchange.events;

import ca.yorku.cmg.lob.security.Security;

/**
 * News that are considered to be good (indicative that the price will rise - "bullish") 
 */
public class GoodNews extends Event {

	public GoodNews(long time, Security sec) {
		super(time, sec);
	}

}
