package composer;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Set;

/**
 * @author Lily Collins
 * Represents a frequency table with a Hashtable<String, Integer> in which each entry represents the number of times a String has occured
 */
public class FrequencyTable {
	private Hashtable<String, Integer> tally;
	
	/**
	 * Creates an object representing a Hashtable<String, Integer> in which each entry represents the number of times a String has occured
	 */
	public FrequencyTable() {
		tally = new Hashtable<String, Integer>();
	}
	
	/**
	 * Tallies the number of occurences of the note
	 * @param note the note to be tallied
	 */
	public void addToTally(String note) {
		if(!note.isEmpty()) {
			if(!tally.containsKey(note)) {
				tally.put(note, 1);
			}
			else {
				tally.replace(note, tally.get(note)+1);
			}
		}
	}
	
	/**
	 * Randomly chooses a String in the table based on how often each String appears in the frequency table.
	 * For example, if the String "C" made up 70% of the entries, it would have a 70% chance of being chosen.
	 * @return the chosen String
	 */
	public String chooseNextNote() {
		if (tally.isEmpty()) {
			return "";
		}
		
		Collection<Integer> v = tally.values();
		int total = 0;
		for (int i : v) {
			total += i;
		}		
		int rand = (int) (Math.random()*total + 1);
		
		int add = 0;
		Set<String> k = tally.keySet();
		for (String s : k) {
			add += tally.get(s);
			if (add >= rand)
				return s;
		}
		throw new IllegalArgumentException("Error in tally: " + this);
	}
	
	/**
	 * Gets the frequency table of the object
	 * @return the frequency table
	 */
	public Hashtable<String, Integer> getTally() {
		return tally;
	}
}
