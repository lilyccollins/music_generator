package composer;

/**
 * @author Lily Collins and Frances Jing
 * Represents a note in sheet music with a frequency table of the notes that follow it
 */
public class Note extends FrequencyTable {
	private String id;
	
	/**
	 * Creates a note with the given identification.
	 * For example, the String "C" would represent the note middle C.
	 * @param the String identity of the note to be created
	 */
	public Note(String id) {
		super();
		this.id = id;
	}
	
	/**
	 * Gets the id of the note
	 * @return the id
	 */
	public String getID() {
		return id;
	}
	
}
