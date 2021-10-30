package composer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Set;


/**
 * @author Lily Collins
 * Generates new music based on the selected the music files
 */
public class Composer {
	
	private ArrayList<Note> keyboard;
	private ArrayList<Index> indexes;
	private ArrayList<Sheet> sheets;
	
	private FrequencyTable meters;
	private FrequencyTable noteLengths;
	private FrequencyTable tempos;
	private FrequencyTable keySigs;
	
	
	/**
	 * Creates Sheet object for every filename given
	 * @param filenames the names of the sheet music
	 */
	public Composer(ArrayList<String> filenames) {
		sheets = new ArrayList<Sheet>();
		keyboard = new ArrayList<Note>();
		indexes = new ArrayList<Index>();
		
		meters = new FrequencyTable();
		noteLengths = new FrequencyTable();
		tempos = new FrequencyTable();
		keySigs = new FrequencyTable();
		
		for (String s : filenames) {
			sheets.add(new Sheet(s));
		}
	}
	
	
	/**
	 * Reads the ArrayList<String> of notes within each Sheet of the selected sheet music and adds to the frequency tables of each Note and Index
	 */
	public void tally() {
		//for each Sheet s in sheets, get the arraylist notes of notes it contains
		for (Sheet s : sheets) {
			s.readFile();
			
			//handle fields
			meters.addToTally(s.getMeter());
			noteLengths.addToTally(s.getNoteLength());
			tempos.addToTally(s.getTempo());
			keySigs.addToTally(s.getKeySig());
			
			ArrayList<String> notes = s.getNotes();
			//for each string n in notes
			for (int i = 0; i < notes.size(); i++) {
				String n = notes.get(i);
				
				if (i < notes.size() - 1) {//if i is not the last note
					//if n is not in keyboard, add it
					if (getIndexOfKeyInKeyboard(n) == -1) {//if keyboard doesn't have a note with id n
						keyboard.add(new Note(n));
					}
					//add the next note to the tally of the Note n corresponds to in keyboard
					keyboard.get(getIndexOfKeyInKeyboard(n)).addToTally(notes.get(i + 1));
				}
				else {//if it is the last note
					if (getIndexOfKeyInKeyboard(n) == -1) {//if keyboard doesn't have a note with id n
						keyboard.add(new Note(n));
					}
					//add the next note to the tally of the Note n corresponds to in keyboard
					keyboard.get(getIndexOfKeyInKeyboard(n)).addToTally(notes.get(0));
				}
					
					
				//if there is not already an index i in indexes, add it
				if (indexes.size() == i) {
					Index in = new Index(i);
					indexes.add(in);
				}
				//then add note n to the tally of index i
				indexes.get(i).addToTally(n);
			}
		}			
	}
	
	
	/**
	 * Generates notes of new music into a new Sheet object once the Sheets are read and the frequency tables of the Notes and Indexes are filled
	 * @param the number of notes to be generated
	 * @return the new music generated in ABC notation
	 */
	public Sheet compose(int n) {
		//checks if tallies have been filled
		if(keyboard.isEmpty()) {
			throw new IllegalArgumentException("empty keyboard");
		}
		if(indexes.isEmpty()) {
			throw new IllegalArgumentException("empty indexes");
		}
		//if they are filled
		else {
			//create a new arraylist<string> to hold the new notes
			ArrayList<String> newNotes = new ArrayList<String>();
			//choose the first note using indexes.get(0) and add it
			newNotes.add(indexes.get(0).chooseNextNote());
			
			//repeat this section n times
			for (int i = 1; i < n; i++) {//start at 1 because the first note has already been added
//				System.out.println((i+1) + "th composed note is: " + newNotes.get(i-1));
				//make an array list of the frequency tables that will be used to choose the next note
				ArrayList<FrequencyTable> freq = new ArrayList<FrequencyTable>();
				
				//add current note
				if (getIndexOfKeyInKeyboard(newNotes.get(i-1)) == -1) {//if the current note not in keyboard
					throw new IllegalArgumentException("current note: " + newNotes.get(i-1) + " is not in keyboard.");
				}
				freq.add(keyboard.get(getIndexOfKeyInKeyboard(newNotes.get(i-1))));//sometimes this gives an ArrayIndexOutOfBoundsException: -1
				
				//add current index if it exists
				if (i < indexes.size()) {
					freq.add(indexes.get(i));
				}
				
				//choose next note and add it to newNotes
				newNotes.add(chooseNextNote(freq));//add the Note with that id from keyboard, dont make a new one
			}
			
			
			Sheet composed = new Sheet("composed");
			composed.setNotes(newNotes);
			composed.setMeter(meters.chooseNextNote());
			composed.setNoteLength(noteLengths.chooseNextNote());
			composed.setTempo(tempos.chooseNextNote());
			composed.setKeySig(keySigs.chooseNextNote());
			
			return composed;
		}
	}
	
	
	
	//chooses a new note based off of an array of FrequencyTable objects
	private String chooseNextNote(ArrayList<FrequencyTable> freq) {
		
		//combines the arraylist of frequency tables into one table
		Hashtable<String, Integer> combined = new Hashtable<String, Integer>();
		//for each frequency table provided, get the set of all its keys
		for (FrequencyTable f : freq) {
			Hashtable<String, Integer> t = f.getTally();
			Set<String> keys = t.keySet();
			//for each key in the set
			for (String key : keys) {
				//if the this key in this frequency table is already in combined, add one to the value of that key
				if (combined.containsKey(key)) {
					combined.replace(key, combined.get(key) + 1);
				}
				//if not, add this key to combined with a value of 1
				else {
					combined.put(key, 1);
				}
			}
		}
		
		//chooses next note based on combined
		Collection<Integer> v = combined.values();
		int total = 0;
		for (int i : v) {
			total += i;
		}		
		int rand = (int) (Math.random()*total + 1);
		
		int add = 0;
		Set<String> k = combined.keySet();
		for (String s : k) {
			add += combined.get(s);
			if (add >= rand)
				return s;
		}
		throw new IllegalArgumentException("Error in tally: " + this);
	}
	
	
	//returns the object in keyboard that has id n
	private int getIndexOfKeyInKeyboard(String key) {
		for(int i = 0; i < keyboard.size(); i++) {
			if (keyboard.get(i).getID().equals(key)) {
				return i;
			}
		}
		return -1;
	}
	
	
}
