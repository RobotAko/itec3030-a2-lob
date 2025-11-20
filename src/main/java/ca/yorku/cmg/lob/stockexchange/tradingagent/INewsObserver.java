package ca.yorku.cmg.lob.stockexchange.tradingagent;

import ca.yorku.cmg.lob.stockexchange.events.Event;

/**
 * Interface to be implemented by object wishing to receive events from a NewsBoard.git 
 */
public interface INewsObserver {
	public void update(Event e);
}
