package ca.yorku.cmg.lob.stockexchange.events;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

import ca.yorku.cmg.lob.security.Security;
import ca.yorku.cmg.lob.security.SecurityList;

/**
 * A NewsBoard object generates and shares financial/economic events that affect specific securities 
 */
public class NewsBoard {

	//Events are queued ordered by time
	PriorityQueue<Event> eventQueue = new PriorityQueue<>((e1, e2) -> Long.compare(e1.getTime(), e2.getTime()));

	SecurityList securities;
	
	public NewsBoard(SecurityList x) {
		this.securities = x;
	}
	
    // Allowed event values
    private static final Set<String> VALID_EVENTS = new HashSet<>(
	        Arrays.asList("Good", "Bad")
    );
	
	
    /**
     * Load events from file. Format: [Time, Relevant Ticker, EventType], where EventType is one of "Good" or "Bad"
     * @param filePath The path of the file.
     */
    public void loadEvents(String filePath) {
    	String line;
    	String delimiter = ","; // Assuming the CSV is comma-separated

    	try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
    		while ((line = br.readLine()) != null) {
    			String[] values = line.split(delimiter);

    			// Ensure the line has exactly two columns
    			if (values.length != 3) {
    				System.err.println("Invalid line format: " + line);
    				continue;
    			}

    			String time = values[0].trim();
    			String ticker = values[1].trim();
    			String event = values[2].trim();

    			// Validate the event
    			if (!VALID_EVENTS.contains(event)) {
    				System.err.println("Invalid event value: " + event + " in line: " + line);
    				continue;
    			}
    			
    			Security s = securities.getSecurityByTicker(ticker);

    			if (s == null) {
    				System.err.println("Unknown ticker: " + ticker + " in line: " + line);
    				continue;
    			}
    			
    			Event eventObj;
    			switch (event) {
    			case "Good":
    				eventObj = new GoodNews(Long.parseLong(time),s);
    				break;
    			case "Bad":
    				eventObj = new BadNews(Long.parseLong(time),s);
    				break;
    			default:
    				throw new IllegalArgumentException("Unexpected event value: " + event);
    			}

    			eventQueue.add(eventObj);
    			
    		}
    	} catch (IOException e) {
    		System.err.println("Error reading the file: " + e.getMessage());
    	}
    }

	
	//Get the event at time time
	/**
	 * Returns the event that happened at time {@code time}
	 * @param time The time at which the event happened.
	 * @return The event that happened at that time, or {@code null} if no event happened at that time. 
	 */
	public Event getEventAt(long time) {
		
		PriorityQueue<Event> clonedQueue = new PriorityQueue<>(eventQueue);
		Event e = null;
		
		while (!clonedQueue.isEmpty()) {
			long next = clonedQueue.peek().getTime();
			if (time > next) {
				clonedQueue.poll();
			} else if (time < next) {
				return(null);
			} else {//time == next
				return(clonedQueue.poll());
			}
		}
		return (e);
	}
	
	
	/**
	 * Stub for the observer part. Runs the entire queue of events and sends notifications to registered trading agents.   
	 */
	public void runEventsList() {

	}
	
	
	
}
