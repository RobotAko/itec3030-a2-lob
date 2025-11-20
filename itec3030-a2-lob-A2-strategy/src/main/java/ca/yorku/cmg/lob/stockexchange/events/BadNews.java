package ca.yorku.cmg.lob.stockexchange.events;

import ca.yorku.cmg.lob.security.Security;

/**
 * News that are considered to be bad (indicative that the price may fall - "bearish") 
 */
public class BadNews extends Event {

	public BadNews(long time, Security sec) {
		super(time, sec);
	}

}
