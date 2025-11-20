package ca.yorku.cmg.lob.stockexchange.events;

import ca.yorku.cmg.lob.security.Security;


/**
 * A news event to come out of the {@linkplain NewsBoard}. It is associated with a specific {@linkplain Security}. For example if may be a release of the company's earnings, or a political or macroeconomic event that is understood to influence the price of the Security.   
 */
public abstract class Event {
	protected long time;
	protected Security security;
	
	/**
	 * Constructor
	 * @param time The time the event occurs. 
	 * @param sec The security related to the event. 
	 */
	Event(long time, Security sec){
		this.time = time;
		this.security = sec;
	}
	
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public Security getSecrity() {
		return security;
	}
	public void setSecrity(Security secrity) {
		this.security = secrity;
	}
}
